package com.dtbeat.tree;

import java.util.Comparator;
import java.util.Stack;

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
     * Returns the value to which the specified key is mapped, or null if none
     *
     * @param key key with which the specified value is to be associated
     * @return the value to which the specified key is mapped, or null if none
     */
    public V get(K key) {
        Node<K, V> node = getNode(key);
        return node == null ? null : node.value;
    }

    public V set(K key, V value) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            return node.setValue(value);
        }

        return null;
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

    public V remove(K key) {
        Node<K, V> node = getNode(key);
        if (null == node) {
            return null;
        }

        V oldValue = node.value;
        deleteNode(node);

        return oldValue;
    }

    final void deleteNode(Node<K, V> node) {
        size--;

        // node has left and right child node
        if (node.left != null && node.right != null) {
            // left-most right node of the node
            // s has not left child
            Node<K, V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node = s;
        }

        Node<K, V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            // node has left or right child node
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (replacement == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            // release node
            node.left = node.right = node.parent = null;

            if (node.color == BLACK) {
                fixAfterDeletion(replacement);
            }
        } else if (node.parent == null) {
            // the tree has only one node
            root = null;
        } else {
            if (node.color == BLACK) {
                fixAfterDeletion(node);
            }

            // the parent node of the node is not null
            if (node == node.parent.left) {
                node.parent.left = null;
            } else if (node == node.parent.right) {
                node.parent.right = null;
            }
            node.parent = null;
        }
    }

    private void fixAfterDeletion(Node<K, V> x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Node<K, V> s = rightOf(parentOf(x));
                // the color of sibling node is red (the color of the parent is black)
                // change the color of the sibling node to black
                // change the color of the parent node to red
                // rotate left by parent node
                // re-get the sibling node of the x node
                if (colorOf(s) == RED) {
                    setColor(s, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    s = rightOf(parentOf(x));
                }

                // set the color of the sibling node to red, local balance
                // set x to the parent node of the x node
                // recall up
                if (colorOf(leftOf(s)) == BLACK
                        && colorOf(rightOf(s)) == BLACK) {
                    setColor(s, RED);
                    x = parentOf(x);
                } else {
                    // the color of the sibling node is black
                    // the color of the right child node of the sibling node is black
                    // the color of the left child node of the sibling node is red
                    // set the color of the sibling to red
                    // set the color of the left child node of the sibling node to black
                    // rotate right by the sibling node
                    // re-get the sibling node of the x node
                    if (colorOf(rightOf(s)) == BLACK) {
                        setColor(leftOf(s), BLACK);
                        setColor(s, RED);
                        rotateRight(s);
                        s = rightOf(parentOf(x));
                    }

                    // exchange color between the parent node of the sibling node and the sibling node
                    // set the color of the right child node of the sibling node to black
                    // rotate left by the parent node of the sibling node
                    // x = root indicate exit from while cycle
                    setColor(s, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(s), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else {
                Node<K, V> s = leftOf(parentOf(x));
                if (colorOf(s) == RED) {
                    setColor(s, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    s = leftOf(parentOf(x));
                }

                if (colorOf(leftOf(s)) == BLACK
                        && colorOf(rightOf(s)) == BLACK) {
                    setColor(s, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(s)) == BLACK) {
                        setColor(rightOf(s), BLACK);
                        setColor(s, RED);
                        rotateLeft(s);
                        s = leftOf(parentOf(x));
                    }

                    setColor(s, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(s), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }

    static <K, V> Node<K, V> successor(Node<K, V> node) {
        if (null == node) {
            return null;
        }

        if (null != node.right) {
            Node<K, V> p = node.right;
            while (p.left != null) {
                p = p.left;
            }

            return p;
        } else {
            Node<K, V> p = node.parent;
            Node<K, V> t = node;
            while (p != null && t == p.right) {
                t = p;
                p = p.parent;
            }

            return p;
        }
    }

    static <K, V> Node<K, V> preducessor(Node<K, V> node) {
        if (null == node) {
            return null;
        }

        if (node.left != null) {
            Node<K, V> t = node.left;
            while (t.right != null) {
                t = t.right;
            }

            return t;
        } else {
            Node<K, V> p = node.parent;
            Node<K, V> t = node;
            while (p != null && t == p.left) {
                t = p;
                p = p.parent;
            }

            return p;
        }
    }

    final Node<K, V> getNode(K key) {
        if (null == key) {
            throw new NullPointerException();
        }

        Node<K, V> t = root;
        if (null == t) {
            return null;
        }

        int cmp;
        do {
            cmp = compare(key, t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                return t;
            }
        } while (null != t);

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
