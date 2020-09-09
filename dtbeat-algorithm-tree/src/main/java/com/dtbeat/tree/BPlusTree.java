package com.dtbeat.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * BPlusTree is an implementation of a B+Tree
 *
 * <p>
 * BPlusTree stores Item instances in an ordered structure, allowing easy insertion,
 * removal, and iteration.
 * </p>
 *
 * @author elvinshang
 * @version Id:: BPlusTree.java, v0.0.1 2020/8/24 21:57 dtbeat.com $
 */
public class BPlusTree<K, V> implements Serializable {
    private static final long serialVersionUID = 5455061436401372989L;

    private static final Logger LOG = LoggerFactory.getLogger(BPlusTree.class);
    private static final int DEFAULT_DEGREE = 3;

    private int degree;
    private int minKeys;
    private int maxKeys;
    private Node<K, V> root;
    private int size;
    private Comparator<? super K> comparator;

    public BPlusTree(int degree) {
        this(degree, null);
    }

    public BPlusTree(int degree, Comparator<? super K> comparator) {
        if (degree < DEFAULT_DEGREE) {
            throw new IllegalArgumentException("the degree must be equal or greater than " + DEFAULT_DEGREE);
        }

        this.degree = degree;
        this.minKeys = computeMinKeys(degree);
        this.maxKeys = degree - 1;
        this.root = null;
        this.size = 0;
        this.comparator = comparator;
    }

    final static int computeMinKeys(int degree) {
        int minKeys = (int) Math.ceil(degree * 1.0 / 2) - 1;
        if (minKeys < 1) {
            minKeys = 1;
        }

        return minKeys;
    }

    /**
     * Compares two keys using the correct comparison method for this tree.
     */
    @SuppressWarnings("unchecked")
    final int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super K>) k1).compareTo((K) k2)
                : comparator.compare((K) k1, (K) k2);
    }

    public V put(K key, V value) {
        Node<K, V> t = root;
        if (null == t) {
            t = new LeafNode<>(null);
            t.add(new Entry<>(key, value));

            root = t;
            size = 1;

            return null;
        }

        Node<K, V> insertionNode = null;
        do {
            insertionNode = t;
            Node<K, V> cur = t;
            for (int i = 0; i < cur.entrySize(); i++) {
                Entry<K, V> entry = cur.getEntry(i);
                int cmp = compare(key, entry.key);
                if (cmp < 0) {
                    t = cur.getChild(i);
                    break;
                } else if (cmp > 0) {
                    t = cur.getChild(i + 1);
                } else {
                    t = cur.getChild(i + 1);
                    if (t instanceof LeafNode) {
                        return entry.setValue(value);
                    }
                    break;
                }
            }
        } while (t != null);

        insertionNode.add(new Entry<>(key, value));
        fixAfterInsertion(insertionNode);
        size++;

        return null;
    }

    public V remove(K key) {
        FindKey<K, V> findKey = find(key);
        if (findKey == null) {
            return null;
        }

        V oldValue = findKey.entry.value;
        if (!findKey.node.isLeaf()) {
            oldValue = find(findKey.right, key).entry.value;
        }

        deleteEntry(findKey);

        return oldValue;
    }

    private void deleteEntry(FindKey<K, V> findKey) {
        size--;

        if (!findKey.node.isLeaf()) {
            // left-most right node of the node
            // s has not left child
            FindKey<K, V> findLeafKey = find(findKey.right, findKey.entry.key);
            FindKey<K, V> s = successorOrPrecursor(findLeafKey);
            findKey.entry.key = s.entry.key;
            findKey = findLeafKey;
        }

        findKey.node.removeEntry(findKey.entry);
        if (findKey.node.parent == null && findKey.node.isLeaf() && findKey.node.entrySize() == 0) {
            root = null;
        } else {
            fixAfterDeletion(findKey.node);
        }
    }

    private void fixAfterDeletion(Node<K, V> node) {
        while (node != null && node != root && node.entrySize() < minKeys) {
            int index = node.parent.indexOf(node);

            // left node, right node
            Node<K, V> leftNode = index == 0 ? null : node.parent.getChild(index - 1);
            Node<K, V> rightNode = node.parent.getChild(index + 1);

            if ((leftNode == null || leftNode.entrySize() <= minKeys)
                    && node.entrySize() <= minKeys && (rightNode == null || rightNode.entrySize() <= minKeys)) {
                // left and right
                Node<K, V> left = leftNode == null ? node : leftNode;
                Node<K, V> right = rightNode == null || leftNode != null ? node : rightNode;
                Entry<K, V> entry = right.parent.getEntry(right.parent.indexOf(right) - 1);

                // entry
                if (!left.isLeaf()) {
                    left.add(entry);
                }
                right.entries.forEach(e -> left.add(e));

                // child
                right.children.forEach(child -> left.add(child));
                right.children.forEach(child -> child.parent = left);

                // remove entry and right child
                right.parent.removeEntry(entry);
                right.parent.removeChild(right.parent.indexOf(right));

                // double-linked list
                if (left.isLeaf()) {
                    LeafNode<K, V> rightLeaf = (LeafNode<K, V>) right;
                    LeafNode<K, V> leftLeaf = (LeafNode<K, V>) left;

                    leftLeaf.next = rightLeaf.next;
                    if (rightLeaf.next != null) {
                        rightLeaf.next.previous = leftLeaf;
                    }
                }

                if (node.parent.parent == null && node.parent.entrySize() == 0) {
                    root.clear();
                    root = left;
                    root.parent = null;
                }

                node = left;
            } else {
                // left and right
                Node<K, V> left = leftNode != null && leftNode.entrySize() > minKeys ? leftNode : node;
                Node<K, V> right = left == node ? rightNode : node;
                Entry<K, V> entry = right.parent.getEntry(right.parent.indexOf(right) - 1);

                Entry<K, V> borrowedEntry = left.entrySize() > minKeys ? left.lastEntry() : right.firstEntry();
                Node<K, V> borrowedChild = left.entrySize() > minKeys ? left.lastNode() : right.firstNode();
                Node<K, V> targetNode = left.entrySize() > minKeys ? right : left;
                Node<K, V> borrowedNode = left.entrySize() > minKeys ? left : right;

                if (targetNode.isLeaf()) {
                    targetNode.add(borrowedEntry);
                    borrowedNode.removeEntry(borrowedEntry);
                    entry.key = borrowedNode.firstEntry().key;
                } else {
                    targetNode.add(new Entry<>(entry.key, null));
                    targetNode.add(borrowedChild);
                    borrowedChild.parent = targetNode;
                    borrowedNode.removeEntry(borrowedEntry);
                    borrowedNode.removeChild(borrowedChild);
                    entry.key = borrowedEntry.key;
                }
            }

            node = node.parent;
        }
    }


    /**
     * Returns the successor or precursor of the special key.
     *
     * @param findKey the key info
     * @return the successor of the special key.
     */
    private FindKey<K, V> successorOrPrecursor(FindKey<K, V> findKey) {
        if (findKey == null) {
            return null;
        }

        if (!findKey.node.isLeaf()) {
            findKey = find(findKey.right, findKey.entry.key);
        }

        LeafNode node = (LeafNode) findKey.node;

        int index = node.indexOf(findKey.entry);
        if (index < node.entrySize() - 1) {
            return new FindKey<>(node, node.getEntry(index + 1), null, null);
        } else if (node.next != null && node.next.parent == node.parent) {
            return new FindKey<>(node.next, node.next.firstEntry(), null, null);
        } else {
            return new FindKey<>(node.previous, node.previous.lastEntry(), null, null);
        }
    }

    private FindKey<K, V> find(K key) {
        return find(root, key);
    }

    private FindKey<K, V> find(Node<K, V> node, K key) {
        Node<K, V> t = node;
        if (null == t) {
            return null;
        }

        do {
            Node<K, V> cur = t;
            for (int i = 0; i < cur.entrySize(); i++) {
                Entry<K, V> entry = cur.getEntry(i);
                int cmp = compare(key, entry.key);
                if (cmp < 0) {
                    t = cur.getChild(i);
                    break;
                } else if (cmp > 0) {
                    t = cur.getChild(i + 1);
                } else {
                    return new FindKey<>(cur, entry, cur.getChild(i), cur.getChild(i + 1));
                }
            }
        } while (t != null);

        return null;
    }

    private void fixAfterInsertion(Node<K, V> node) {
        while (node != null && node.isSpill()) {
            Node<K, V> parent = node.parent;
            if (parent == null) {
                parent = new InternalNode<>(null);
                root = parent;
                parent.add(node);
                node.parent = parent;
            }

            split(InternalNode.class.cast(parent), node);
            node = parent;
        }
    }

    private void split(InternalNode<K, V> parent, Node<K, V> child) {
        final int entrySize = child.entrySize();
        final int childSize = child.childSize();

        // the entry for moving up
        Entry<K, V> middleEntry = child.getEntry(entrySize / 2);

        // right start index for sub-entries and sub-children
        int rightEntryStartIndex;
        int rightChildNodeStartIndex;
        if (child.isLeaf()) {
            rightEntryStartIndex = entrySize > 0 ? entrySize / 2 : 0;
            rightChildNodeStartIndex = 0;
        } else {
            rightEntryStartIndex = entrySize / 2 + 1;
            rightChildNodeStartIndex = (int) Math.ceil(childSize * 1.0 / 2);
        }

        // right node
        List<Entry<K, V>> rightEntries = new ArrayList<>(child.entries.subList(rightEntryStartIndex, entrySize));
        List<Node<K, V>> rightChildren = new ArrayList<>(child.children.subList(rightChildNodeStartIndex, childSize));
        Node<K, V> right = child.isLeaf() ? new LeafNode<K, V>(parent, rightEntries, rightChildren) : new InternalNode<K, V>(parent, rightEntries, rightChildren);
        rightChildren.forEach(r -> r.parent = right);

        // left node
        child.truncate(entrySize / 2);
        Node<K, V> left = child;

        // double-linked list
        if (child.isLeaf()) {
            LeafNode<K, V> rightLeaf = (LeafNode<K, V>) right;
            LeafNode<K, V> leftLeaf = (LeafNode<K, V>) left;

            rightLeaf.next = leftLeaf.next;
            rightLeaf.previous = leftLeaf;
            leftLeaf.next = rightLeaf;
        }

        // add middleEntry and right node to parent
        parent.add(new Entry<>(middleEntry.key, null));
        parent.add(right);
    }

    final LeafNode<K, V> findMin() {
        Node<K, V> t = root;
        if (null == t) {
            return null;
        }

        while (t.firstNode() != null) {
            t = t.firstNode();
        }

        return LeafNode.class.cast(t);
    }

    final String toInOrderString() {
        LeafNode<K, V> t = findMin();
        if (t == null) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        while (t != null) {
            t.entries.forEach(e -> writer.append(e.key + "-"));
            t = t.next;
        }

        return writer.deleteCharAt(writer.length() - 1).toString();
    }

    final String toLevelOrderString() {
        Node<K, V> t = root;
        if (null == t) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        Queue<Node<K, V>> q = new ArrayDeque<>();
        q.offer(t);

        while (!q.isEmpty()) {
            t = q.poll();
            writer.append(String.format("[Height: %d][%s]", t.getHeight(), toLeftParentKeyAndRightParentKeyString(t)));
            t.entries.forEach(e -> writer.append(e.key + "-"));
            writer.deleteCharAt(writer.length() - 1);
            writer.append("\n");

            t.children.forEach(child -> q.offer(child));
        }

        return writer.toString();
    }

    final String toLeftParentKeyAndRightParentKeyString(Node<K, V> node) {
        if (node.parent == null) {
            return "left-parent: null right-parent: null";
        }

        int index = node.parent.indexOf(node);

        if (LOG.isDebugEnabled()) {
            LOG.debug("node:{} node.parent:{} index: {} entrySize: {} childSize: {}", node, node.parent, index, node.parent.entrySize(), node.parent.childSize());
        }

        if (index == 0) {
            return "left-parent: null right-parent: " + node.parent.getEntry(index).key;
        } else if (index == node.parent.childSize() - 1) {
            return "left-parent: " + node.parent.getEntry(index - 1).key + " right-parent: null";
        } else {
            return "left-parent: " + node.parent.getEntry(index - 1).key + " right-parent: " + node.parent.getEntry(index).key;
        }
    }

    /**
     * FindNode is an result for finding by key
     */
    private class FindKey<K, V> {
        private Node<K, V> node;
        private Entry<K, V> entry;
        private Node<K, V> left;
        private Node<K, V> right;

        public FindKey(Node<K, V> node, Entry<K, V> entry, Node<K, V> left, Node<K, V> right) {
            this.node = node;
            this.entry = entry;
            this.left = left;
            this.right = right;
        }
    }


    /**
     * Leaf Node
     */
    private class LeafNode<K, V> extends Node<K, V> {
        private LeafNode<K, V> previous;
        private LeafNode<K, V> next;

        public LeafNode(InternalNode<K, V> parent) {
            this(parent, new ArrayList<>(), new ArrayList<>());
        }

        public LeafNode(InternalNode<K, V> parent, List<Entry<K, V>> entries, List<Node<K, V>> children) {
            super(parent, entries, children);
            this.next = null;
            this.previous = null;
        }
    }

    /**
     * Intenal Node
     */
    private class InternalNode<K, V> extends Node<K, V> {
        public InternalNode(InternalNode<K, V> parent) {
            this(parent, new ArrayList<>(), new ArrayList<>());
        }

        public InternalNode(InternalNode<K, V> parent, List<Entry<K, V>> entries, List<Node<K, V>> children) {
            super(parent, entries, children);
        }
    }

    /**
     * Node is an internal node in a tree.
     */
    abstract class Node<K, V> {
        Node<K, V> parent;
        List<Entry<K, V>> entries;
        List<Node<K, V>> children;

        public Node(Node<K, V> parent, List<Entry<K, V>> entries, List<Node<K, V>> children) {
            this.parent = parent;
            this.entries = entries;
            this.children = children;
        }

        public List<Entry<K, V>> getEntries() {
            return entries;
        }

        public List<K> entryKeys() {
            return entries.isEmpty() ? Collections.emptyList() : entries.stream().map(Entry::getKey).collect(Collectors.toList());
        }

        public List<V> entryValues() {
            return entries.isEmpty() ? Collections.emptyList() : entries.stream().map(Entry::getValue).collect(Collectors.toList());
        }

        public List<Node<K, V>> getChildren() {
            return children;
        }

        public Node<K, V> getParent() {
            return parent;
        }

        public int getHeight() {
            return this.parent == null ? 1 : this.parent.getHeight() + 1;
        }

        public boolean isLeaf() {
            return children.size() == 0;
        }

        public boolean isFull() {
            return entries.size() >= maxKeys;
        }

        public boolean isSpill() {
            return entries.size() > maxKeys;
        }

        public Entry<K, V> getEntry(int index) {
            if (index >= 0 && index < entries.size()) {
                return entries.get(index);
            }

            return null;
        }

        public Entry<K, V> firstEntry() {
            return entries.isEmpty() ? null : entries.get(0);
        }

        public Entry<K, V> lastEntry() {
            return entries.isEmpty() ? null : entries.get(entries.size() - 1);
        }

        public Entry<K, V> popEntry() {
            Entry<K, V> last = lastEntry();
            this.removeEntry(entrySize() - 1);
            return last;
        }

        public Node<K, V> getChild(int index) {
            if (index < children.size() && index >= 0) {
                return children.get(index);
            }

            return null;
        }

        public Node<K, V> firstNode() {
            final int size = children.size();
            if (size == 0) {
                return null;
            }
            return children.get(0);
        }

        public Node<K, V> lastNode() {
            final int size = children.size();
            if (size == 0) {
                return null;
            }
            return children.get(size - 1);
        }

        public Node<K, V> popChild() {
            return this.removeChild(children.size() - 1);
        }

        public K getMaxKey() {
            return entries.isEmpty() ? null : entries.get(entries.size() - 1).key;
        }

        public V getValue(int index) {
            Entry<K, V> entry = this.getEntry(index);
            return entry == null ? null : entry.value;
        }

        public int entrySize() {
            return entries.size();
        }

        public int childSize() {
            return children.size();
        }

        public int indexOf(K key) {
            if (key == null || entries.isEmpty()) {
                return -1;
            }

            return entryKeys().indexOf(key);
        }

        public int indexOf(Entry<K, V> entry) {
            return entries.isEmpty() ? -1 : entries.indexOf(entry);
        }

        public int indexOf(Node<K, V> child) {
            return children.isEmpty() ? -1 : children.indexOf(child);
        }

        public void setEntry(int index, Entry<K, V> entry) {
            entries.set(index, entry);
        }

        public void setEntry(List<Entry<K, V>> entries) {
            this.entries = entries;
        }

        public void setNode(int index, Node<K, V> node) {
            children.set(index, node);
        }

        public void add(int index, Entry<K, V> e) {
            if (e == null) {
                return;
            }

            if (index >= 0 && index < entrySize()) {
                this.entries.add(index, e);
            }
        }

        public void add(Entry<K, V> e) {
            if (e == null) {
                return;
            }

            this.entries.add(e);
            this.sortKeys();
        }

        public void addEntries(List<Entry<K, V>> entries) {
            Objects.requireNonNull(entries);

            this.entries.addAll(entries);
            this.sortKeys();
        }

        public void add(Node<K, V> child) {
            if (child == null) {
                return;
            }

            this.children.add(child);
            this.sortChildren();
        }

        public void addChildren(List<Node<K, V>> children) {
            if (children == null || children.isEmpty()) {
                return;
            }

            this.children.addAll(children);
            this.sortChildren();
        }

        public Entry<K, V> removeEntry(int index) {
            if (index >= 0 && index < entries.size()) {
                return entries.remove(index);
            }

            return null;
        }

        public Entry<K, V> removeEntry(K key) {
            if (key == null) {
                return null;
            }

            int index = indexOf(key);
            if (index == -1) {
                return null;
            }

            return removeEntry(index);
        }

        public boolean removeEntry(Entry<K, V> e) {
            if (e == null) {
                return false;
            }

            return entries.remove(e);
        }

        public Node<K, V> removeChild(int index) {
            if (index >= 0 && index < children.size()) {
                return children.remove(index);
            }

            return null;
        }

        public boolean removeChild(Node<K, V> child) {
            if (child == null) {
                return false;
            }

            return this.children.remove(child);
        }

        public void sortKeys() {
            if (!entries.isEmpty()) {
                entries.sort((o1, o2) -> BPlusTree.this.compare(o1.key, o2.key));
            }
        }

        public void sortChildren() {
            if (children != null && children.size() > 0) {
                children.sort((o1, o2) -> BPlusTree.this.compare(o1.getMaxKey(), o2.getMaxKey()));
            }
        }

        public void clear() {
            this.parent = null;
            this.entries.clear();
            this.entries = null;
            this.children.clear();
            this.children = null;
        }

        public void truncate(int index) {
            // remove entry
            int size = entrySize();
            for (int i = index; i < size; i++) {
                entries.remove(entrySize() - 1);
            }

            // remove child
            size = childSize();
            for (int i = index == 0 ? 0 : index + 1; i < size; i++) {
                children.remove(childSize() - 1);
            }
        }

        @Override
        public String toString() {
            if (entries.isEmpty()) {
                return "";
            }

            StringBuilder writer = new StringBuilder();
            entries.forEach(e -> writer.append(e.key + "-"));
            writer.deleteCharAt(writer.length() - 1);

            return writer.toString();
        }
    }

    /**
     * An node entry (key-value pair)
     */
    private class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;

            return oldValue;
        }

        @Override
        public String toString() {
            return key + ":" + value;
        }
    }
}
