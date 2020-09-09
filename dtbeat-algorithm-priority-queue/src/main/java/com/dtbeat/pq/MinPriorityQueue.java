package com.dtbeat.pq;

import java.util.Comparator;

/**
 * MaxPriorityQueue
 *
 * @author elvinshang
 * @version Id: MaxPriorityQueue.java, v0.0.1 2020/9/6 13:54 dtbeat.com $
 */
public class MinPriorityQueue<E> extends PriorityQueue<E> {
    public MinPriorityQueue() {
        this(-1, null);
    }

    public MinPriorityQueue(int capacity) {
        this(capacity, null);
    }

    public MinPriorityQueue(Comparator<E> comparator) {
        this(-1, comparator);
    }

    public MinPriorityQueue(int capacity, Comparator<E> comparator) {
        super(new MinBinaryHeap<>(capacity, comparator));
    }
}
