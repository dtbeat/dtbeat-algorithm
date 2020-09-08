package com.dtbeat.pq;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Binomial Heap
 *
 * @author elvinshang
 * @version Id: BinomialHeap.java, v0.0.1 2020/9/8 18:15 dtbeat.com $
 */
public class BinomialHeap<E> {
    private final E MIN_KEY;
    private Comparator<E> comparator;
    private Node head;
    private int size;

    public BinomialHeap(E minKey) {
        this(null, minKey, null);
    }

    public BinomialHeap(E minKey, Comparator<E> comparator) {
        this(null, minKey, comparator);
    }

    public BinomialHeap(E key, E minKey) {
        this(key, minKey, null);
    }

    public BinomialHeap(E key, E minKey, Comparator<E> comparator) {
        this.MIN_KEY = Objects.requireNonNull(minKey);
        this.comparator = comparator;
        this.size = key == null ? 0 : 1;
        this.head = key == null ? null : new Node(key);
    }

    /**
     * Inserts new key
     *
     * @param key the new key to insert
     */
    public void insert(E key) {
        BinomialHeap<E> h1 = new BinomialHeap<>(key, this.MIN_KEY, comparator);
        BinomialHeap<E> newHeap = union(this, h1);
        this.size = newHeap.size;
        this.head = newHeap.head;
    }

    /**
     * Union the tow binomial heaps
     *
     * @param h1 the binomial heap to be union
     * @param h2 the other binomial heap to be union
     */
    public BinomialHeap<E> union(BinomialHeap<E> h1, BinomialHeap<E> h2) {
        BinomialHeap<E> h = new BinomialHeap<>(this.MIN_KEY, h1.comparator);
        h.head = merge(h1, h2);
        h.size = h1.size + h2.size;
        if (h.head == null) {
            return null;
        }

        Node pre = null;
        Node x = h.head;
        Node next = h.head.sibling;
        while (next != null) {
            if (x.degree == next.degree) {
                if (compare(x.key, next.key) < 0) {
                    x.sibling = next.sibling;
                    link(next, x);
                    next = x;
                } else {
                    if (pre == null) {
                        h.head = next;
                    } else {
                        pre.sibling = next;
                    }

                    link(x, next);
                    x = next;
                }
            } else {
                pre = x;
                x = next;
            }

            next = next.sibling;
        }

        return h;
    }

    /**
     * Returns minimum key
     *
     * @return minimum key
     */
    public E minimum() {
        Node min = findMin();
        return min == null ? null : min.key;
    }

    /**
     * Extracts the min key
     *
     * @return the min key
     */
    public E extractMin() {
        Node min = extractMinNode();

        return min == null ? null : min.key;
    }

    /**
     * Decrease the oldKey value by new key
     *
     * @param oldKey the old key
     * @param newKey the new key
     */
    public void decrease(E oldKey, E newKey) {
        if (compare(newKey, oldKey) > 0) {
            throw new IllegalArgumentException("new key is greater than current key.");
        }

        Node x = find(oldKey);
        if (x == null) {
            return;
        }

        decrease(x, newKey);
    }

    /**
     * Deletes the key
     *
     * @param key the key to delete
     */
    public void delete(E key) {
        Node x = find(key);
        if (x == null) {
            return;
        }

        decrease(x, MIN_KEY);
        extractMinNode();
    }

    public String render() {
        Node t = this.head;
        if (t == null) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        writer.append("[root");
        foreach(this.head, node -> writer.append(" " + node.render()));
        writer.append("]");

        return writer.toString();
    }

    private void insert(Node x) {
        BinomialHeap<E> h1 = new BinomialHeap<>(this.MIN_KEY, comparator);
        h1.head = x;
        h1.size = (int)Math.pow(2, x.degree);

        BinomialHeap<E> newHeap = union(this, h1);
        this.size = newHeap.size;
        this.head = newHeap.head;
    }

    private void decrease(Node x, E newKey) {
        x.key = newKey;
        Node z = x.parent;
        while (z != null && compare(x.key, z.key) < 0) {
            E t = z.key;
            z.key = x.key;
            x.key = t;

            x = z;
            z = x.parent;
        }
    }

    private Node find(E key) {
        if (this.head == null) {
            return null;
        }

        Queue<Node> q = new ArrayDeque<>(this.size);
        foreach(this.head, x -> q.offer(x));

        while (!q.isEmpty()) {
            Node x = q.poll();
            if (x.equals(key)) {
                return x;
            }

            foreach(x.child, y -> q.offer(y));
        }

        return null;
    }

    private Node extractMinNode() {
        Node min = findMin();
        if (min == null) {
            return null;
        }

        removeNode(min);

        BinomialHeap<E> h1 = new BinomialHeap<>(this.MIN_KEY, comparator);
        foreach(min.child, child -> {
            removeNode(child);
            h1.insert(child);
        });

        BinomialHeap<E> newHeap = union(this, h1);
        if (newHeap != null) {
            this.head = newHeap.head;
            this.size = newHeap.size;
        } else {
            this.head = null;
            this.size = 0;
        }

        return min;
    }

    private Node findMin() {
        if (this.head == null) {
            return null;
        }

        Node min = this.head;
        Node x = this.head;
        while (x != null) {
            if (compare(x.key, min.key) < 0) {
                min = x;
            }

            x = x.sibling;
        }

        return min;
    }

    private void foreach(Node node, Consumer<Node> consumer) {
        while (node != null) {
            Node t = node.sibling;
            consumer.accept(node);
            node = t;
        }
    }

    private void removeNode(Node x) {
        if (x.parent != null && x.parent.child == x) {
            x.parent.child = x.sibling;
        } else if (this.head == x) {
            this.head = x.sibling;
        } else {
            Node y = x.parent != null ? x.parent.child : this.head;
            while (y.sibling != x) {
                y = y.sibling;
            }

            y.sibling = x.sibling;
        }

        x.sibling = null;
        x.parent = null;
        this.size -= (int) Math.pow(2, x.degree);
    }

    private Node merge(BinomialHeap<E> h1, BinomialHeap<E> h2) {
        Node x = h1.head;
        Node y = h2.head;

        if (x == null || y == null) {
            return x == null ? y : x;
        }

        Node res = null;
        Node current = null;
        while (x != null || y != null) {
            Node t = null;
            if (x.degree < y.degree) {
                t = x;
                x = x.sibling;
                t.sibling = null;
            } else {
                t = y;
                y = y.sibling;
                t.sibling = null;
            }

            if (current == null) {
                res = t;
                current = t;
            } else {
                current.sibling = t;
                current = t;
            }

            if (x == null || y == null) {
                current.sibling = x == null ? y : x;
                break;
            }
        }

        return res;
    }

    private void link(Node y, Node z) {
        y.parent = z;
        y.sibling = z.child;
        z.child = y;
        z.degree++;
    }

    private int compare(E k1, E k2) {
        return comparator != null ? comparator.compare(k1, k2) :
                ((Comparable<? super E>) k1).compareTo(k2);
    }

    /**
     * Binomial Heap Node
     */
    private class Node {
        private E key;
        private int degree;
        private Node sibling;
        private Node parent;
        private Node child;

        public Node(E key) {
            this.key = key;
            this.degree = 0;
            this.sibling = null;
            this.parent = null;
            this.child = null;
        }

        public String render() {
            StringBuilder writer = new StringBuilder();
            writer.append("[" + key);
            foreach(this.child, child -> writer.append(" " + child.render()));
            writer.append("]");

            return writer.toString();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    '}';
        }
    }
}
