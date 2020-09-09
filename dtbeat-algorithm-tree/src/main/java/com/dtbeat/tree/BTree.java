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
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * BTree is an implementation of a B-Tree
 *
 * <p>
 * BTree stores Item instances in an ordered structure, allowing easy insertion,
 * removal, and iteration.
 * </p>
 *
 * @author elvinshang
 * @version Id:: BTree.java, v0.0.1 2020/8/22 17:36 dtbeat.com $
 */
public class BTree<K, V> implements Serializable {
    private static final long serialVersionUID = -7375033267031630654L;

    private static final Logger LOG = LoggerFactory.getLogger(BTree.class);
    private static final int DEFAULT_DEGREE = 3;

    private int degree;
    private int minKeys;
    private int maxKeys;
    private Node<K, V> root;
    private int size;
    private Comparator<? super K> comparator;

    public BTree(int degree) {
        this(degree, null);
    }

    public BTree(int degree, Comparator<? super K> comparator) {
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

    public V get(K key) {
        FindKey<K, V> node = find(key);
        return node != null ? node.entry.value : null;
    }

    public V set(K key, V value) {
        FindKey<K, V> node = find(key);

        return node == null ? null : node.entry.setValue(value);
    }

    public int getSize() {
        return size;
    }

    public V put(K key, V value) {
        Node<K, V> t = root;
        if (null == t) {
            t = new Node<>(null);
            t.add(new Entry<>(key, value));

            root = t;
            size = 1;

            return null;
        }

        int slot = 0;
        Node<K, V> insertionNode = null;
        do {
            insertionNode = t;
            Node<K, V> cur = t;
            for (int i = 0; i < cur.entrySize(); i++) {
                Entry<K, V> entry = cur.getEntry(i);
                int cmp = compare(key, entry.key);
                if (cmp < 0) {
                    slot = i;
                    t = cur.getChild(slot);
                    break;
                } else if (cmp > 0) {
                    slot = i + 1;
                    t = cur.getChild(slot);
                } else {
                    return entry.setValue(value);
                }
            }
        } while (t != null);

        insertionNode.add(new Entry<>(key, value));
        fixAfterInsertion(insertionNode);
        size++;

        return null;
    }

    public V remove(K key) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("remove key {}", key);
        }

        FindKey<K, V> result = find(key);
        if (result == null) {
            return null;
        }

        V oldValue = result.entry.value;
        deleteEntry(result);
        return oldValue;
    }

    private void deleteEntry(FindKey<K, V> findKey) {
        size--;

        if (!findKey.node.isLeaf()) {
            // left-most right node of the node
            // s has not left child
            FindKey<K, V> s = successor(findKey);
            findKey.entry.key = s.entry.key;
            findKey.entry.value = s.entry.value;
            findKey = s;
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
                left.add(entry);
                right.entries.forEach(e -> left.add(e));

                // child
                right.children.forEach(child -> left.add(child));
                right.children.forEach(child -> child.parent = left);

                // remove entry and right child
                right.parent.removeEntry(entry);
                right.parent.removeChild(right.parent.indexOf(right));

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

                targetNode.add(new Entry<>(entry.key, entry.value));
                borrowedNode.removeEntry(borrowedEntry);

                if (!targetNode.isLeaf()) {
                    targetNode.add(borrowedChild);
                    borrowedChild.parent = targetNode;
                    borrowedNode.removeEntry(borrowedEntry);
                    borrowedNode.removeChild(borrowedChild);
                }

                entry.key = borrowedEntry.key;
                entry.value = borrowedEntry.value;
            }

            node = node.parent;
        }
    }

    private void fixAfterInsertion(Node<K, V> node) {
        while (node != null && node.isSpill()) {
            Node<K, V> parent = node.parent;
            if (parent == null) {
                parent = new Node<>(null);
                root = parent;
                parent.add(node);
                node.parent = parent;
            }

            split(parent, node);

            node = parent;
        }
    }

    private void split(Node<K, V> parent, Node<K, V> child) {
        final int entrySize = child.entrySize();
        final int childSize = child.childSize();

        // the entry for moving up
        Entry<K, V> middleEntry = child.getEntry(entrySize / 2);

        // right node
        int rightEntrySplitStartIndex = entrySize > 0 ? entrySize / 2 + 1 : 0;
        int rightNodeSplitStartIndex = childSize > 0 ? (int) Math.ceil(childSize * 1.0 / 2) : 0;

        List<Entry<K, V>> rightEntries = new ArrayList<>(child.entries.subList(rightEntrySplitStartIndex, entrySize));
        List<Node<K, V>> rightChildren = new ArrayList<>(child.children.subList(rightNodeSplitStartIndex, childSize));
        Node<K, V> rightNode = new Node<K, V>(parent, rightEntries, rightChildren);
        rightChildren.forEach(r -> r.parent = rightNode);

        // right node
        child.truncate(entrySize / 2);
        Node<K, V> leftNode = child;
        leftNode.children.forEach(l -> l.parent = leftNode);

        parent.add(middleEntry);
        parent.add(rightNode);
    }

    private int computeMinKeys(int degree) {
        int minKeys = (int) Math.ceil(degree * 1.0 / 2) - 1;
        if (minKeys < 1) {
            minKeys = 1;
        }

        return minKeys;
    }

    /**
     * Returns the successor of the special key.
     *
     * @param findKey the key info
     * @return the successor of the special key.
     */
    private FindKey<K, V> successor(FindKey<K, V> findKey) {
        if (findKey == null || findKey.node == null) {
            return null;
        }

        if (null != findKey.right) {
            Node<K, V> p = findKey.right;
            while (p.firstNode() != null) {
                p = p.firstNode();
            }

            return new FindKey<>(p, p.firstEntry(), null, null);
        } else {
            Node<K, V> p = findKey.node.parent;
            Node<K, V> t = findKey.node;
            while (p != null) {
                int i = p.indexOf(t);
                if (i == 0) {
                    return new FindKey<>(p, p.firstEntry(), p.firstNode(), p.getChild(1));
                } else if (i == p.childSize() - 1) {
                    t = p;
                    p = p.parent;
                } else {
                    return new FindKey<>(p, p.getEntry(i), t, p.getChild(i + 1));
                }
            }

            return null;
        }
    }

    private FindKey<K, V> find(K key) {
        Node<K, V> t = root;
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

    final String toInOrderString() {
        Node<K, V> t = root;
        if (null == t) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        Stack<Entry<Node<K, V>, Integer>> stack = new Stack<>();
        int index = 0;

        while (!stack.isEmpty() || null != t) {
            if (null != t) {
                stack.push(new Entry<>(t, index));
                t = t.getChild(index);
            } else {
                Entry<Node<K, V>, Integer> pop = stack.pop();
                t = pop.key;
                index = pop.value;

                if (t.isLeaf()) {
                    t.entries.forEach(e -> writer.append(e.key + "-"));
                    t = null;
                } else {
                    Entry<K, V> entry = t.getEntry(index);
                    Node<K, V> child = t.getChild(index + 1);
                    if (entry != null) {
                        writer.append(entry.key + "-");

                        pop.value = index + 1;
                        stack.push(pop);
                    }

                    t = child;
                    index = 0;
                }
            }
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
            LOG.debug("node:{} node.parent:{} index: {} entrySize: {} childSize: {}",node, node.parent, index, node.parent.entrySize(), node.parent.childSize());
        }

        if (index == 0) {
            return "left-parent: null right-parent: " + node.parent.getEntry(index).key;
        } else if(index == node.parent.childSize() - 1) {
            return "left-parent: " + node.parent.getEntry(index - 1).key + " right-parent: null";
        } else {
            return "left-parent: " + node.parent.getEntry(index - 1).key + " right-parent: " + node.parent.getEntry(index).key;
        }
    }

    /**
     * Compares two keys using the correct comparison method for this tree.
     */
    @SuppressWarnings("unchecked")
    final int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super K>) k1).compareTo((K) k2)
                : comparator.compare((K) k1, (K) k2);
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
     * Node is an internal node in a tree.
     */
    private class Node<K, V> {
        private Node<K, V> parent;
        private List<Entry<K, V>> entries;
        private List<Node<K, V>> children;

        public Node(Node<K, V> parent) {
            this(parent, new ArrayList<>(), new ArrayList<>());
        }

        private Node(Node<K, V> parent, List<Entry<K, V>> entries, List<Node<K, V>> children) {
            this.parent = parent;
            this.entries = entries;
            this.children = children;
        }

        private List<Entry<K, V>> getEntries() {
            return entries;
        }

        private List<K> entryKeys() {
            return entries.isEmpty() ? Collections.emptyList() : entries.stream().map(Entry::getKey).collect(Collectors.toList());
        }

        private List<V> entryValues() {
            return entries.isEmpty() ? Collections.emptyList() : entries.stream().map(Entry::getValue).collect(Collectors.toList());
        }

        private List<Node<K, V>> getChildren() {
            return children;
        }

        private Node<K, V> getParent() {
            return parent;
        }

        private int getHeight() {
            return this.parent == null ? 1 : this.parent.getHeight() + 1;
        }

        private boolean isLeaf() {
            return children.size() == 0;
        }

        private boolean isFull() {
            return entries.size() >= maxKeys;
        }

        private boolean isSpill() {
            return entries.size() > maxKeys;
        }

        private Entry<K, V> getEntry(int index) {
            if (index >= 0 && index < entries.size()) {
                return entries.get(index);
            }

            return null;
        }

        private Entry<K, V> firstEntry() {
            return entries.isEmpty() ? null : entries.get(0);
        }

        private Entry<K, V> lastEntry() {
            return entries.isEmpty() ? null : entries.get(entries.size() - 1);
        }

        private Entry<K, V> popEntry() {
            Entry<K, V> last = lastEntry();
            this.removeEntry(entrySize() - 1);
            return last;
        }

        private Node<K, V> getChild(int index) {
            if (index < children.size() && index >= 0) {
                return children.get(index);
            }

            return null;
        }

        private Node<K, V> firstNode() {
            final int size = children.size();
            if (size == 0) {
                return null;
            }
            return children.get(0);
        }

        private Node<K, V> lastNode() {
            final int size = children.size();
            if (size == 0) {
                return null;
            }
            return children.get(size - 1);
        }

        private Node<K, V> popChild() {
            return this.removeChild(children.size() - 1);
        }

        private K getMaxKey() {
            return entries.isEmpty() ? null : entries.get(entries.size() - 1).key;
        }

        private V getValue(int index) {
            Entry<K, V> entry = this.getEntry(index);
            return entry == null ? null : entry.value;
        }

        private int entrySize() {
            return entries.size();
        }

        private int childSize() {
            return children.size();
        }

        private int indexOf(K key) {
            if (key == null || entries.isEmpty()) {
                return -1;
            }

            return entryKeys().indexOf(key);
        }

        private int indexOf(Entry<K, V> entry) {
            return entries.isEmpty() ? -1 : entries.indexOf(entry);
        }

        private int indexOf(Node<K, V> child) {
            return children.isEmpty() ? -1 : children.indexOf(child);
        }

        private void setEntry(int index, Entry<K, V> entry) {
            entries.set(index, entry);
        }

        private void setEntry(List<Entry<K, V>> entries) {
            this.entries = entries;
        }

        private void setNode(int index, Node<K, V> node) {
            children.set(index, node);
        }

        private void add(int index, Entry<K, V> e) {
            if (e == null) {
                return;
            }

            if (index >= 0 && index < entrySize()) {
                this.entries.add(index, e);
            }
        }

        private void add(Entry<K, V> e) {
            if (e == null) {
                return;
            }

            this.entries.add(e);
            this.sortKeys();
        }

        private void addEntries(List<Entry<K, V>> entries) {
            Objects.requireNonNull(entries);

            this.entries.addAll(entries);
            this.sortKeys();
        }

        private void add(Node<K, V> child) {
            if (child == null) {
                return;
            }

            this.children.add(child);
            this.sortChildren();
        }

        private void addChildren(List<Node<K, V>> children) {
            if (children == null || children.isEmpty()) {
                return;
            }

            this.children.addAll(children);
            this.sortChildren();
        }

        private Entry<K, V> removeEntry(int index) {
            if (index >= 0 && index < entries.size()) {
                return entries.remove(index);
            }

            return null;
        }

        private Entry<K, V> removeEntry(K key) {
            if (key == null) {
                return null;
            }

            int index = indexOf(key);
            if (index == -1) {
                return null;
            }

            return removeEntry(index);
        }

        private boolean removeEntry(Entry<K, V> e) {
            if (e == null) {
                return false;
            }

            return entries.remove(e);
        }

        private Node<K, V> removeChild(int index) {
            if (index >= 0 && index < children.size()) {
                return children.remove(index);
            }

            return null;
        }

        private boolean removeChild(Node<K, V> child) {
            if (child == null) {
                return false;
            }

            return this.children.remove(child);
        }

        private void sortKeys() {
            if (!entries.isEmpty()) {
                entries.sort((o1, o2) -> BTree.this.compare(o1.key, o2.key));
            }
        }

        private void sortChildren() {
            if (children != null && children.size() > 0) {
                children.sort((o1, o2) -> BTree.this.compare(o1.getMaxKey(), o2.getMaxKey()));
            }
        }

        private void clear() {
            this.parent = null;
            this.entries.clear();
            this.entries = null;
            this.children.clear();
            this.children = null;
        }

        private void truncate(int index) {
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
