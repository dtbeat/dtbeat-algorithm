package com.dtbeat.datastruct.avltree;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

/**
 * AVL Tree
 *
 * @author elvinshang
 * @version $Id: AvlTree.java, v0.0.1 2020/8/14 15:28 btbeat.com $
 */
public class AvlTree<K, V> implements Comparator<K> {
    private Node<K, V> root;
    private int size;
    private Comparator<K> comparator;

    public AvlTree() {
        this.root = null;
        this.size = 0;
    }

    public AvlTree(Comparator<K> comparator) {
        this();
        this.comparator = Objects.requireNonNull(comparator);
    }

    /**
     * Puts the specified value with the specified key into this tree.
     * if the tree previously contaned a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value) {
        root = putVal(this.root, Objects.requireNonNull(key), value, false);
    }

    /**
     * Puts the specified value with the specified key into this tree.
     * if the tree previously contaned a mapping for the key, the old
     * value is replaced.
     *
     * @param node         node to put
     * @param key          key with which the specified value is to be associated
     * @param value        value to be associated with the specified key
     * @param onlyIfAbsent if true, don't change existing value
     * @return new root node
     */
    final Node<K, V> putVal(Node<K, V> node, K key, V value, boolean onlyIfAbsent) {
        if (node == null) {
            this.size++;

            return new Node<>(key, value);
        }

        if (compare(key, node.key) < 0) {
            node.left = putVal(node.left, key, value, onlyIfAbsent);
        } else if(compare(key, node.key) > 0) {
            node.right = putVal(node.right, key, value, onlyIfAbsent);
        } else if (!onlyIfAbsent) {
            node.value = value;
        }

        // update depth
        updateDepth(node);
        int factor = getBalanceFactor(node);
        
        // LL
        if (factor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // RR
        if (factor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // LR
        if (factor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (factor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node<K,V> leftRotate(Node<K,V> node) {
        Node<K, V> newRoot = node.right;
        Node<K, V> rightNode = newRoot.left;

        newRoot.left = node;
        node.right = rightNode;

        updateDepth(node);
        updateDepth(newRoot);

        return newRoot;
    }

    private Node<K,V> rightRotate(Node<K,V> node) {
        Node<K, V> newRoot = node.left;
        Node<K, V> leftNode = newRoot.right;

        newRoot.right = node;
        node.left = leftNode;

        updateDepth(node);
        updateDepth(newRoot);

        return newRoot;
    }

    @Override
    public int compare(K o1, K o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        } else if (o1 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        } else {
            throw new RuntimeException("No match comparator.");
        }
    }

    private int getDepth(Node<K, V> node) {
        if (node == null) {
            return 0;
        }

        return node.depth;
    }

    private void updateDepth(Node<K, V> node) {
        if (node == null) {
            return;
        }

        int leftDepth = getDepth(node.left);
        int rightDepth = getDepth(node.right);

        node.depth = Math.max(leftDepth, rightDepth) + 1;
    }

    private int getBalanceFactor(Node<K, V> node) {
        if (node == null) {
            return 0;
        }

        return getDepth(node.left) - getDepth(node.right);
    }

    public String toVlrString() {
        StringBuilder writer = new StringBuilder();
        toVlrString0(writer, this.root, null);

        return writer.toString();
    }

    private void toVlrString0(StringBuilder writer, Node<K, V> node, HashSet<K> cache) {
        if (node == null) {
            return;
        }

        if (cache == null) {
            cache = new HashSet<>();
        }

        if (writer == null) {
            writer = new StringBuilder();
        }

        writer.append(node.key.toString());
        cache.add(node.key);

        toVlrString0(writer, node.left, cache);
        toVlrString0(writer, node.right, cache);
    }

    @Override
    public String toString() {
        return "AvlTree{" +
                "key=" + root != null ? root.key.toString() : "null"  +
                ", size=" + size +
                '}';
    }

    /**
     * AVL Node
     */
    static class Node<K, V> {
        final K key;
        V value;
        int depth;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.depth = 1;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", depth=" + depth +
                    '}';
        }
    }
}
