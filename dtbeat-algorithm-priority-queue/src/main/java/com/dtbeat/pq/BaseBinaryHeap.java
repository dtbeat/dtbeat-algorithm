package com.dtbeat.pq;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * MaxHeap
 *
 * @author elvinshang
 * @version Id: MaxHeap.java, v0.0.1 2020/9/6 11:52 dtbeat.com $
 */
public abstract class BaseBinaryHeap<E> {
    private ArrayList<E> data;
    private Comparator<E> comparator;

    public BaseBinaryHeap() {
        this(-1);
    }

    public BaseBinaryHeap(int capacity) {
        this(-1, null);
    }

    public BaseBinaryHeap(Comparator<E> comparator) {
        this(-1, comparator);
    }

    public BaseBinaryHeap(int capacity, Comparator<E> comparator) {
        this.data = capacity <= 0 ? new ArrayList<>() : new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int leftChild(int i) {
        return i * 2 + 1;
    }

    public int rightChild(int i) {
        return i * 2 + 2;
    }

    public int parent(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("i");
        }

        return (i - 1) / 2;
    }

    public void add(E e) {
        data.add(e);
        shiftUp(size() - 1);
    }

    public E find() {
        if (isEmpty()) {
            return null;
        }

        return data.get(0);
    }

    public E remove() {
        E max = find();
        if (max == null) {
            return null;
        }

        swap(0, size() - 1);
        data.remove(size() - 1);
        shiftDown(0);

        return max;
    }

    public E replace(E e) {
        E ret = find();
        if (ret == null) {
            return null;
        }

        data.set(0, ret);
        shiftUp(0);

        return ret;
    }

    protected final void shiftUp(int k) {
        shiftUp(data, k);
    }

    protected final void shiftDown(int k) {
        shiftDown(data, k);
    }

    protected abstract void shiftUp(ArrayList<E> data, int k);

    protected abstract void shiftDown(ArrayList<E> data, int k);

    protected void swap(int i, int j) {
        E t = data.get(i);
        data.set(i, data.get(j));
        data.set(j, t);
    }

    protected int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<? super E>) e1).compareTo(e2)
                : comparator.compare(e1, e2);
    }
}
