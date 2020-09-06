package com.dtbeat.pq;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Max Heap
 *
 * @author elvinshang
 * @version Id: MaxHeap.java, v0.0.1 2020/9/6 11:52 dtbeat.com $
 */
public class MaxHeap<E> extends BaseHeap<E> {
    public MaxHeap() {
        this(-1);
    }

    public MaxHeap(int capacity) {
        this(-1, null);
    }

    public MaxHeap(Comparator<E> comparator) {
        this(-1, comparator);
    }

    public MaxHeap(int capacity, Comparator<E> comparator) {
        super(capacity, comparator);
    }

    public E findMax() {
       return find();
    }

    public E delMax() {
       return remove();
    }

    @Override
    protected void shiftUp(ArrayList<E> data, int k) {
        while (k > 0 && compare(data.get(parent(k)), data.get(k)) < 0) {
            swap(parent(k), k);
            k = parent(k);
        }
    }

    @Override
    protected void shiftDown(ArrayList<E> data, int k) {
        while (leftChild(k) < size()) {
            int j = leftChild(k);
            if (j + 1 < size() && compare(data.get(j), data.get(j + 1)) < 0) {
                j++;
            }

            if (compare(data.get(k), data.get(j)) >= 0) {
                break;
            }

            swap(k, j);
            k = j;
        }
    }
}
