package com.dtbeat.pq;

import java.util.Comparator;

/**
 * MaxPriorityQueue
 *
 * @author elvinshang
 * @version Id: MaxPriorityQueue.java, v0.0.1 2020/9/6 13:54 dtbeat.com $
 */
public class MaxPriorityQueue<E> extends PriorityQueue<E> {
    public MaxPriorityQueue() {
        this(-1, null);
    }

    public MaxPriorityQueue(int capacity) {
        this(capacity, null);
    }

    public MaxPriorityQueue(Comparator<E> comparator) {
        this(-1, comparator);
    }

    public MaxPriorityQueue(int capacity, Comparator<E> comparator) {
        super(new MaxHeap<>(capacity, comparator));
    }
}
