package com.dtbeat.datastruct.tree.avltree;

import java.util.Comparator;
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
     * Returns value with specified key.
     *
     * @param key key with which the specified value is to be associated
     * @return value to be associated with the specified key. or null if none
     */
    public V get(K key) {
        Node<K, V> node = getVal(root, key);
        return node == null ? null : node.value;
    }

    final Node<K, V> getVal(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        if (compare(key, node.key) < 0) {
            return getVal(node.left, key);
        } else if (compare(key, node.key) > 0) {
            return getVal(node.right, key);
        } else {
            return node;
        }
    }

    /**
     * Sets new value with specified key.
     *
     * @param key   key with which the specified value is to be associated
     * @param value new value to be associated with the specified key
     */
    public void set(K key, V value) {
        Node<K, V> node = getVal(root, key);
        if (node != null) {
            node.value = value;
        }
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
        } else if (compare(key, node.key) > 0) {
            node.right = putVal(node.right, key, value, onlyIfAbsent);
        } else if (!onlyIfAbsent) {
            node.value = value;
        }

        return rebalance(node);
    }

    /**
     * Deletes node with the specified key.
     *
     * @param key key with which the specified node is to be associated
     */
    public void delete(K key) {
        root = deleteVal(this.root, key);
    }

    final Node<K, V> deleteVal(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        if (compare(key, node.key) < 0) {
            node.left = deleteVal(node.left, key);
        } else if (compare(key, node.key) > 0) {
            node.right = deleteVal(node.right, key);
        } else {
            // only has left child
            if (node.right == null && node.left != null) {
                Node<K, V> leftNode = node.left;
                node.left = null;
                node = leftNode;
                size--;
            } else if (node.right != null && node.left == null) {
                // only has right child
                Node<K, V> rightNode = node.right;
                node.right = null;
                node = rightNode;
                size--;
            } else if (node.right == null) {
                // leaf node
                node = null;
                size--;
            } else {
                // has left and right child
                // find min node of the right tree
                Node<K, V> rightMinNode = findMin(node.right);
                // delete min node from right tree
                rightMinNode.right = deleteVal(node.right, rightMinNode.key);
                rightMinNode.left = node.left;
                node.left = node.right = null;
                node = rightMinNode;
            }
        }

        if (node == null) {
            return null;
        }

        return rebalance(node);
    }

    private Node<K, V> rebalance(Node<K, V> node) {
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

    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.right;
        Node<K, V> rightNode = newRoot.left;

        newRoot.left = node;
        node.right = rightNode;

        updateDepth(node);
        updateDepth(newRoot);

        return newRoot;
    }

    private Node<K, V> rightRotate(Node<K, V> node) {
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

    private Node<K, V> findMin(Node<K, V> node) {
        if (node.left != null) {
            return findMin(node.left);
        }

        return node;
    }

    private int getBalanceFactor(Node<K, V> node) {
        if (node == null) {
            return 0;
        }

        return getDepth(node.left) - getDepth(node.right);
    }

    public String toVlrString() {
        StringBuilder writer = new StringBuilder();
        toVlrString0(writer, this.root);

        return writer.toString();
    }

    private void toVlrString0(StringBuilder writer, Node<K, V> node) {
        if (node == null) {
            return;
        }

        if (writer == null) {
            writer = new StringBuilder();
        }

        writer.append(node.key.toString());

        toVlrString0(writer, node.left);
        toVlrString0(writer, node.right);
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
