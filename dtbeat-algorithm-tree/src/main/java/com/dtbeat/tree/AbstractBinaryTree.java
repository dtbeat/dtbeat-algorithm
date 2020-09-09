package com.dtbeat.tree;

import java.util.Comparator;

/**
 * Abstract Binary Tree
 *
 * @author elvinshang
 * @version Id:: AbstractBinaryTree.java, v0.0.1 2020/8/15 23:07 btbeat.com $
 */
public abstract class AbstractBinaryTree<K, V> {
    private Node<K, V> root;
    private int size;
    private Comparator<K> comparator;

    public AbstractBinaryTree() {
        this.root = null;
        this.size = 0;
    }

    public AbstractBinaryTree(Comparator<K> comparator) {
        this.comparator = comparator;
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
     * Returns the number of key-value mappings in this tree.
     *
     * @return the number of key-value mappings in this tree
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this tree contains no key-value mappings.
     *
     * @return <tt>true</tt> if this tree contains no key-value mappings
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this tree contains a mapping for the
     * specified key.
     *
     * @param key The key whose presence in this tree is to be tested
     * @return <tt>true</tt> if this tree contains a mapping for the specified
     * key.
     */
    public boolean containsKey(K key) {
        return getVal(root, key) != null;
    }

    /**
     * Puts the specified value with the specified key into this tree.
     * if the tree previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value) {
        root = putNode(root, key, value);
        if (root != null) {
            root.parent = null;
        }
    }

    protected Node<K, V> putNode(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }

        if (compare(key, node.key) < 0) {
            node.left = putNode(node.left, key, value);
            if (node.left != null) {
                node.left.parent = node;
            }
        } else if (compare(key, node.key) > 0) {
            node.right = putNode(node.right, key, value);
            if (node.right != null) {
                node.right.parent = node;
            }
        } else {
            node.value = value;
        }

        return node;
    }

    public void remove(K key) {
        root = removeNode(root, key);
        if (root != null) {
            root.parent = null;
        }
    }

    protected Node<K, V> removeNode(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        if (compare(key, node.key) < 0) {
            node.left = removeNode(node.left, key);
            if (node.left != null) {
                node.left.parent = node;
            }
        } else if (compare(key, node.key) > 0) {
            node.right = removeNode(node.right, key);
            if (node.right != null) {
                node.right.parent = node;
            }
        } else {
            if (node.left != null && node.right == null) {
                Node<K, V> leftNode = node.left;
                node.left = node.parent = null;
                size--;
                node = leftNode;
            } else if (node.left == null && node.right != null) {
                Node<K, V> rightNode = node.right;
                node.right = node.parent = null;
                size--;
                node = rightNode;
            } else if (node.left == null && node.right == null) {
                size--;
                node.parent = null;
                node = null;
            } else {
                Node<K, V> minNode = findMin(node.right);
                minNode.right = removeNode(node.right, minNode.key);
                minNode.left = node.left;
                node.left = node.right = node.parent = null;
                size--;
                node = minNode;
            }
        }

        return node;
    }

    final Node<K, V> findMin(Node<K, V> node) {
        if (node.left != null) {
            return findMin(node.left);
        }

        return node;
    }

    final int compare(K o1, K o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        } else if (o1 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        } else {
            throw new RuntimeException("No match comparator.");
        }
    }

    /**
     * Returns a preorder based string
     *
     * @return a preorder based string
     */
    public String toPreOrderString() {
        StringBuilder writer = new StringBuilder();
        toPreOrderString0(writer, this.root);

        return writer.toString();
    }

    /**
     * Returns pre node of the specified key
     *
     * @param key the key
     * @return pre node of the specified key
     */
    public K getPre(K key) {
        Node<K, V> node = getVal(root, key);
        if (node == null) {
            return null;
        }

        Node<K, V> s;

        // has left child and find right-most child node
        if (node.left != null) {
            for (s = node.left; s.right != null; s = s.right) ;
            return s != null ? s.key : null;
        }

        // find ancestor node and the ancestor node's right node is ancestor node or self
        for (s = node; s.parent != null && s.parent.left == s; s = s.parent) ;
        return s.parent != null ? s.parent.key : null;
    }

    /**
     * Returns post node of the specified key
     *
     * @param key the key
     * @return post node of the specified key
     */
    public K getPost(K key) {
        Node<K, V> node = getVal(root, key);
        if (node == null) {
            return null;
        }

        Node<K, V> s;

        // has left child and find left-most child node
        if (node.right != null) {
            for (s = node.right; s.left != null; s = s.left) ;
            return s != null ? s.key : null;
        }

        // find ancestor node and the ancestor node's right node is ancestor node or self
        for (s = node; s.parent != null && s.parent.right == s; s = s.parent) ;
        return s.parent != null ? s.parent.key : null;
    }

    private void toPreOrderString0(StringBuilder writer, Node<K, V> node) {
        if (node == null) {
            return;
        }

        if (writer == null) {
            writer = new StringBuilder();
        }

        writer.append(node.key.toString());

        toPreOrderString0(writer, node.left);
        toPreOrderString0(writer, node.right);
    }

    final int getDepth(Node<K, V> node) {
        if (node == null) {
            return 0;
        }

        return node.depth;
    }

    final void updateDepth(Node<K, V> node) {
        if (node == null) {
            return;
        }

        int leftDepth = getDepth(node.left);
        int rightDepth = getDepth(node.right);

        node.depth = Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * BSTree Node
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
            this.left = null;
            this.right = null;
            this.parent = null;
            this.depth = 1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
