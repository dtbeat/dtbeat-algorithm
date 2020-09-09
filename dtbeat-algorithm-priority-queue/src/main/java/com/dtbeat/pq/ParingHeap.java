package com.dtbeat.pq;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Paring Heap
 *
 * @author elvinshang
 * @version Id: ParingHeap.java, v0.0.1 2020/9/9 00:13 dtbeat.com $
 */
public class ParingHeap<E> {
    private Comparator<E> comparator;
    private Node root;
    private int size;

    public ParingHeap() {
        this(null);
    }

    public ParingHeap(Comparator<E> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns size of the heap
     *
     * @return size of the heap
     */
    public int getSize() {
        return size;
    }

    /**
     * Inserts a new key
     *
     * @param key the new key to insert
     */
    public void insert(E key) {
        insert(new Node(key));
    }

    /**
     * Unions the two heaps
     *
     * @param h1 the one heap to union
     * @param h2 the other heap to union
     * @return a new heap
     */
    public ParingHeap<E> union(ParingHeap<E> h1, ParingHeap<E> h2) {
        ParingHeap<E> h = new ParingHeap<>(comparator);
        if (h1.root == null || h2.root == null) {
            h.root = (h1.root == null) ? h2.root : h1.root;
            h.size = h1.size + h2.size;
            return h;
        }

        Node y = h2.root;
        Node z = h1.root;
        if (compare(h2.root.key, h1.root.key) < 0) {
            Node t = y;
            y = z;
            z = t;
        }

        insert(y, z);
        h.root = z;
        h.size = h1.size + h2.size;

        return h;
    }

    /**
     * Returns the min key
     *
     * @return the min key
     */
    public E minimum() {
        return this.root == null ? null : this.root.key;
    }

    /**
     * Returns the min key and remove it.
     *
     * @return the min key
     */
    public E extractMin() {
        Node res = this.root;
        if (res == null) {
            return null;
        }

        removeNode(res);

        Node sibling = res.child;
        while (sibling != null) {
            Node t = sibling.sibling;
            removeNode(sibling);

            ParingHeap<E> h = new ParingHeap<>(comparator);
            h.root = sibling;

            ParingHeap<E> newH = union(this, h);
            this.root = newH.root;

            sibling = t;
        }

        this.size--;

        return res.key;
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

        removeNode(x);

        Node sibling = x.child;
        while (sibling != null) {
            Node t = sibling.sibling;

            removeNode(sibling);

            ParingHeap<E> h = new ParingHeap<>(comparator);
            h.root = sibling;

            ParingHeap<E> newH = union(this, h);
            this.root = newH.root;

            sibling = t;
        }

        this.size--;
    }

    public String render() {
        Node t = this.root;
        if (t == null) {
            return "";
        }

        StringBuilder writer = new StringBuilder();
        writer.append("[root " + root.render() + "]");

        return writer.toString();
    }

    private void decrease(Node x, E newKey) {
        x.key = newKey;
        removeNode(x);

        ParingHeap<E> h = new ParingHeap<>(comparator);
        h.root = x;
        ParingHeap<E> newH = union(this, h);
        this.root = newH.root;
    }

    private Node find(E key) {
        if (this.root == null) {
            return null;
        }

        Queue<Node> q = new ArrayDeque<>(this.size);
        foreach(this.root, x -> q.offer(x));

        while (!q.isEmpty()) {
            Node x = q.poll();
            if (compare(x.key, key) == 0) {
                return x;
            }

            foreach(x.child, y -> q.offer(y));
        }

        return null;
    }

    private void foreach(Node node, Consumer<Node> consumer) {
        while (node != null) {
            Node t = node.sibling;
            consumer.accept(node);
            node = t;
        }
    }

    private void removeNode(Node x) {
        // most-left child
        if (x.left != null && x.left.child == x) {
            x.left.child = x.sibling;
        } else if (x.left != null) {
            x.left.sibling = x.sibling;
        } else {
            this.root = x.sibling;
        }

        if (x.sibling != null) {
            x.sibling.left = x.left;
        }

        x.left = x.sibling = null;
    }

    private void insert(Node x) {
        if (this.root == null) {
            this.root = x;
        } else {
            if (compare(x.key, this.root.key) < 0) {
                insert(this.root, x);
                this.root = x;
            } else {
                insert(x, this.root);
            }
        }

        this.size++;
    }

    private void insert(Node y, Node z) {
        y.left = z;
        y.sibling = z.child;

        if (z.child != null) {
            z.child.left = y;
        }

        z.child = y;
        z.degree++;
    }

    private int compare(E k1, E k2) {
        return comparator != null ? comparator.compare(k1, k2) :
                ((Comparable<? super E>) k1).compareTo(k2);
    }

    /**
     * Paring Node
     */
    private class Node {
        private E key;
        private int degree;
        private Node child;
        private Node left;
        private Node sibling;

        public Node(E key) {
            this.key = key;
            this.degree = 0;
            this.child = null;
            this.left = null;
            this.sibling = null;
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
