package com.dtbeat.pq;

import java.util.Comparator;
import java.util.Objects;

/**
 * MaxPriorityQueue
 *
 * @author elvinshang
 * @version Id: MaxPriorityQueue.java, v0.0.1 2020/9/6 13:54 dtbeat.com $
 */
public class PriorityQueue<E> {
    private BaseHeap<E> heap;

    public PriorityQueue(BaseHeap<E> heap) {
        this.heap = Objects.requireNonNull(heap);
    }

    public void add(E e) {
        heap.add(e);
    }

    public int getSize() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public E get() {
        return heap.find();
    }

    public void enqueue(E e) {
        heap.add(e);
    }

    public E dequeue() {
        return heap.remove();
    }
}
