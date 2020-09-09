package com.dtbeat.pq;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Fibonacci Heap
 *
 * @author elvinshang
 * @version Id: FibonacciHeap.java, v0.0.1 2020/9/7 15:21 dtbeat.com $
 */
public class FibonacciHeap<E> {
    private Comparator<E> comparator;
    private final E MIN_KEY;
    private int size;
    private Node min;

    public FibonacciHeap(E minimumKey) {
        this(minimumKey, null);
    }

    public FibonacciHeap(E minimumKey, Comparator<E> comparator) {
        this.MIN_KEY = Objects.requireNonNull(minimumKey);
        this.comparator = comparator;
        this.size = 0;
        this.min = null;
    }

    /**
     * Inserts new key to heap
     *
     * @param key the new key to insert
     */
    public void insert(E key) {
        insert(new Node(key));
    }

    /**
     * Union the two heaps
     *
     * @param h1 the one heap to union
     * @param h2 the another heap to union
     */
    public FibonacciHeap<E> union(FibonacciHeap<E> h1, FibonacciHeap<E> h2) {
        FibonacciHeap<E> t = new FibonacciHeap<>(this.MIN_KEY, comparator);
        t.min = h1.min;

        // concat
        if(h2.min != null) {
            Node x = h2.min;
            t.insert(x);

            while (x.left != h2.min) {
                x = x.left;
                t.insert(x);
            }
        }

        if (h1.min == null || (h2.min != null && compare(h1.min.key, h2.min.key) >= 0)) {
            t.min = h2.min;
        }

        t.size = h1.size + h2.size;

        return t;
    }

    /**
     * Returns the minimum key
     *
     * @return the minimum key
     */
    public E minimum() {
        return this.min == null ? null : min.key;
    }

    /**
     * Extracts the min key and delete it if exists
     *
     * @return the min key
     */
    public E extractMin() {
        Node ret = extractMinNode();
         return ret == null ? null : ret.key;
    }

    /**
     * Decrease the key with new key
     *
     * @param oldKey the old key
     * @param newKey the new key
     */
    public void decrease(E oldKey, E newKey) {
        if (compare(oldKey, newKey) < 0) {
            throw new IllegalArgumentException("new key is greater than current key.");
        }

        Node x = search(oldKey);
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
        Node x = search(key);
        decrease(x, MIN_KEY);
        extractMin();
    }

    private void decrease(Node x, E newKey) {
        x.key = newKey;
        Node y = x.parent;
        if (y != null && compare(x.key, y.key) < 0) {
            cut(x, y);
            cascadingCut(y);
        }

        if (compare(x.key, this.min.key) < 0) {
            this.min = x;
        }
    }

    private void cascadingCut(Node y) {
        Node z = y.parent;
        if (z != null) {
            if (!y.mark) {
                y.mark = true;
            } else {
                cut(z, y);
                cascadingCut(z);
            }
        }
    }

    private void cut(Node x, Node y) {
        remove(x);
        insert(this.min, x);
        y.degree--;
        x.parent = null;
        x.mark = false;
    }

    private Node search(Node root, E key) {
        Node t = root;
        if (t == null) {
            return null;
        }

        Node ret = null;
        Queue<Node> q = new ArrayDeque<>(this.size);
        q.offer(t);
        while (t.right != root) {
            t = t.right;
            q.offer(t);
        }

        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (cur.key == key) {
                ret = cur;
                break;
            }

            for (Node child : cur.children()) {
                q.offer(child);
            }
        }

        return ret;
    }

    private Node search(E key) {
        if (min == null)
            return null;

        return search(min, key);
    }

    public boolean contains(E key) {
        return search(key) != null;
    }

    private Node extractMinNode() {
        Node z = this.min;
        if (z != null) {
            for (Node child : z.children()) {
                insert(z, child);
                child.parent = null;
            }

            // remove z from the root list of heap
            remove(z);

            // the heap has only one node
            if (z == z.right) {
                this.min = null;
            } else {
                this.min = z.right;
                consolidate();
            }

            this.size--;
        }

        return z;
    }

    private Node delMinTree() {
        Node t = this.min;
        if (t == null) {
            return null;
        }

        remove(t);
        this.min = t != t.right ? t.right : null;
        t.left = t.right = t;

        return t;
    }

    private void consolidate() {
        Node root = this.min;
        if (root == null) {
            return;
        }

        // Heap`s max degree is log2^n
        int maxDegree = (int) Math.floor(Math.log(size) / Math.log(2));
        int D = maxDegree + 1;
        Node[] A = (Node[]) Array.newInstance(Node.class, D);
        for (int i = 0; i < D; i++) {
            A[i] = null;
        }

        Node x = null;
        while ((x = delMinTree()) != null) {
            int d = x.degree;
            while (A[d] != null) {
                Node y = A[d];
                if (compare(y.key, x.key) < 0) {
                    Node tmp = x;
                    x = y;
                    y = tmp;
                }

                link(y, x);
                A[d] = null;
                d++;
            }
            A[d] = x;
        }

        this.min = null;
        for (int i = 0; i < D; i++) {
            if (A[i] != null) {
                if (this.min == null) {
                    this.min = A[i];
                } else {
                    insert(this.min, A[i]);
                    if (compare(A[i].key, this.min.key) < 0) {
                        this.min = A[i];
                    }
                }
            }
        }
    }

    private void link(Node y, Node x) {
        remove(y);
        if (x.child == null) {
            x.child = y;
            y.left = y.right = y;
        } else {
            insert(x.child, y);
            y.mark = false;
        }

        x.degree++;
    }

    private void remove(Node x) {
        x.left.right = x.right;
        x.right.left = x.left;
    }

    private void insert(Node x) {
        Node t = this.min;
        if (t == null) {
            this.min = x;
        } else {
            // insert x into H`s root list
            insert(t, x);
            if (compare(x.key, t.key) < 0) {
                this.min = x;
            }
        }

        this.size++;
    }

    private void insert(Node root, Node x) {
        x.right = root;
        x.left = root.left;
        root.left.right = x;
        root.left = x;
    }

    private int compare(E k1, E k2) {
        return comparator != null ? comparator.compare(k1, k2) :
                ((Comparable<? super E>) k1).compareTo(k2);
    }

    public String render() {
        Node t = this.min;
        if(t == null) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        writer.append("[root " + t.render());
        while (this.min != t.right) {
            t = t.right;
            writer.append(" " + t.render());
        }
        writer.append("]");

        return writer.toString();
    }

    /**
     * Fibonacci Node
     */
    private class Node {
        private E key;
        private int degree;
        private Node left;
        private Node right;
        private Node parent;
        private Node child;
        private boolean mark;

        public Node(E key) {
            this.key = key;
            this.degree = 0;
            this.mark = false;
            this.parent = null;
            this.child = null;
            this.left = this;
            this.right = this;
        }

        public Iterable<Node> children() {
            return () -> new Itr();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    "}";
        }

        public String render() {
            StringBuilder writer = new StringBuilder();
            writer.append("[" + key);
            for (Node node : children()) {
                writer.append(" " + node.render());
            }
            writer.append("]");

            return writer.toString();
        }

        private class Itr implements Iterator<Node> {
            Node cur;
            boolean first;

            public Itr() {
                this.cur = child;
                first = true;
            }

            @Override
            public boolean hasNext() {
                return (first && cur != null) || cur != child;
            }

            @Override
            public Node next() {
                Node next = this.cur;
                cur = cur.right;
                first = false;

                return next;
            }
        }
    }
}
