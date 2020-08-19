package com.dtbeat.datastruct.tree;

import java.util.Comparator;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Red-Black Binary Search Tree
 *
 * @author elvinshang
 * @version Id:: RedBlackBSTree.java, v0.0.1 2020/8/19 10:18 dtbeat.com $
 */
public class RedBlackBSTree<K, V> {
    /**
     * comparator used to maintain order in this tree,
     * or null if it uses the natural ordering of its keys
     */
    private Comparator<? super K> comparator;
    private Node<K, V> root;
    private int size;

    public RedBlackBSTree() {
        comparator = null;
    }

    public RedBlackBSTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    /**
     * Associates the specified value with the specified key in this tree.
     * If the tree previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map
     * previously associated {@code null} with {@code key}.)
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map uses natural ordering, or its comparator
     *                              does not permit null keys
     */
    public V put(K key, V value) {
        Node<K, V> t = root;
        if (t == null) {
            compare(key, key);

            root = new Node<>(key, value, null);
            size = 1;

            return null;
        }

        int cmp;
        Node<K, V> parent = null;
        do {
            parent = t;
            cmp = compare(key, t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                return t.setValue(value);
            }
        } while (t != null);

        Node<K, V> node = new Node<>(key, value, parent);
        if (cmp < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        fixAfterInsertion(node);
        size++;

        return null;
    }

    public String toInOrderString() {
        Node<K, V> t = root;
        if (null == t) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        Stack<Node<K, V>> stack = new Stack<>();

        while (!stack.isEmpty() || null != t) {
            if (null != t) {
                stack.push(t);
                t = t.left;
            } else {
                t = stack.pop();
                writer.append(t.key);
                t = t.right;
            }
        }

        return writer.toString();
    }

    final String toInOrderWithColorString() {
        Node<K, V> t = root;
        if (null == t) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        Stack<Node<K, V>> stack = new Stack<>();

        while (!stack.isEmpty() || null != t) {
            if (null != t) {
                stack.push(t);
                t = t.left;
            } else {
                t = stack.pop();
                writer.append(t.key + "-" + (t.color == RED ? "r" : "b"));
                t = t.right;
            }
        }

        return writer.toString();
    }

    private void rotateLeft(Node<K, V> p) {
        if (p == null) {
            return;
        }

        Node<K, V> r = p.right;
        p.right = r.left;
        if (r.left != null) {
            r.left.parent = p;
        }

        r.parent = p.parent;
        if (p.parent == null) {
            root = r;
        } else if (p.parent.left == p) {
            p.parent.left = r;
        } else {
            p.parent.right = r;
        }

        r.left = p;
        p.parent = r;
    }

    private void rotateRight(Node<K, V> p) {
        if (p == null) {
            return;
        }

        Node<K, V> l = p.left;
        p.left = l.right;
        if (l.right != null) l.right.parent = p;
        l.parent = p.parent;
        if (p.parent == null)
            root = l;
        else if (p.parent.right == p)
            p.parent.right = l;
        else p.parent.left = l;
        l.right = p;
        p.parent = l;
    }

    private void fixAfterInsertion(Node<K, V> x) {
        x.color = RED;

        // 1. parent`s color is red
        while (x != null && x != root && x.parent.color == RED) {
            // 1.1 the parent node is left node of the grand parent
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Node<K, V> y = rightOf(parentOf(parentOf(x)));
                // 1.1.1
                // the color of the uncle node color is red
                // set the color of the parent node to black
                // set the color of the uncle node to black
                // set the color of the grand parent node to red
                // change the current node to the grand parent node
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    // 1.1.2 the color of the uncle node is black

                    // 1.1.3
                    // the current node is right node of the parent node
                    // change the current node x to the parent node
                    // rotate left by current node
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }

                    // 1.1.4
                    // set the color of the parent node to black
                    // set the color of the grand parent node to red
                    // rotate right by the grand parent node
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                // 1.2 the parent node is right node of the grand parent

                Node<K, V> y = leftOf(parentOf(parentOf(x)));
                // 1.2.1 the color of the uncle node color is red
                // the color of the uncle node color is red
                // set the color of the parent node to black
                // set the color of the uncle node to black
                // set the color of the grand parent node to red
                // change the current node to the grand parent node
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    // 1.2.2 the color of the uncle node is black

                    // 1.2.3
                    // the current node is left node of the parent node
                    // change the current node x to the parent node
                    // rotate left by current node
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }

                    // 1.2.4
                    // set the color of the parent node to black
                    // set the color of the grand parent node to red
                    // rotate right by the grand parent node
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }

        root.color = BLACK;
    }

    private static <K, V> boolean colorOf(Node<K, V> p) {
        return (p == null ? BLACK : p.color);
    }

    private static <K, V> Node<K, V> parentOf(Node<K, V> node) {
        return node == null ? null : node.parent;
    }

    private static <K, V> Node<K, V> leftOf(Node<K, V> node) {
        return node == null ? null : node.left;
    }

    private static <K, V> Node<K, V> rightOf(Node<K, V> node) {
        return node == null ? null : node.right;
    }

    private static <K, V> void setColor(Node<K, V> node, boolean color) {
        if (node != null) {
            node.color = color;
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

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    static final class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = BLACK;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
