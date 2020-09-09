package com.dtbeat.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag
 *
 * @author elvinshang
 * @version Id: Bag.java, v0.0.1 2020/9/2 15:39 dtbeat.com $
 */
public class Bag<T> implements Iterable<T> {
    private Node<T> first;
    private int n;

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first != null;
    }

    public int size() {
        return n;
    }

    public void add(T v) {
        Node<T> oldFirst = first;
        first = new Node<>(v, oldFirst);
        n++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private Node<T> current;

        public Itr() {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T next = current.v;
            current = current.next;

            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Node<T> {
        private T v;
        private Node<T> next;

        public Node(T v, Node<T> next) {
            this.v = v;
            this.next = next;
        }
    }
}
