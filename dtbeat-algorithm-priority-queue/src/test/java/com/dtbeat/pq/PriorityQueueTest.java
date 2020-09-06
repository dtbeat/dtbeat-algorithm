package com.dtbeat.pq;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PriorityQueueTest {
    private static final Logger LOG = LoggerFactory.getLogger(PriorityQueueTest.class);

    @Test
    public void testMaxPriorityQueue_17_73() {
        MaxPriorityQueue<Integer> q = new MaxPriorityQueue<>();

        List<Integer> arr = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        arr.add(17);
        expected.add(17);

        arr.add(73);
        expected.add(73);

        for (Integer v : arr) {
            q.add(v);
        }

        expected.sort((e1, e2) -> Integer.compare(e2.intValue(), e1.intValue()));
        for (Integer v : expected) {
            assertEquals(v.intValue(), q.dequeue().intValue());
        }
    }

    @Test
    public void testMaxPriorityQueue_67_29_63_21() {
        MaxPriorityQueue<Integer> q = new MaxPriorityQueue<>();

        List<Integer> arr = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        Integer[] input = new Integer[]{67, 29, 63, 21};

        for (Integer v : input) {
            arr.add(v);
            expected.add(v);
        }

        for (Integer v : arr) {
            q.add(v);
        }

        expected.sort((e1, e2) -> Integer.compare(e2.intValue(), e1.intValue()));
        for (Integer v : expected) {
            assertEquals(v.intValue(), q.dequeue().intValue());
        }
    }

    @Test
    public void testMaxPriorityQueue() {
        testDynamic(new MaxPriorityQueue<>(), (e1, e2) -> Integer.compare(e2.intValue(), e1.intValue()));
    }

    @Test
    public void testMaxPriorityQueueByDynamic() {
        for (int i = 0; i < 100000; i++) {
            testDynamic(new MaxPriorityQueue<>(), (e1, e2) -> Integer.compare(e2.intValue(), e1.intValue()));
            LOG.info("Auto Test: %{}", Math.round(i * 100.0 / 100000));
        }
    }

    @Test
    public void testMinPriorityQueue_17_73() {
        MinPriorityQueue<Integer> q = new MinPriorityQueue<>();

        List<Integer> arr = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        arr.add(17);
        expected.add(17);

        arr.add(73);
        expected.add(73);

        for (Integer v : arr) {
            q.add(v);
        }

        expected.sort(Comparator.comparingInt(Integer::intValue));
        for (Integer v : expected) {
            assertEquals(v.intValue(), q.dequeue().intValue());
        }
    }

    @Test
    public void testMinPriorityQueue_67_29_63_21() {
        MinPriorityQueue<Integer> q = new MinPriorityQueue<>();

        List<Integer> arr = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        Integer[] input = new Integer[]{67, 29, 63, 21};

        for (Integer v : input) {
            arr.add(v);
            expected.add(v);
        }

        for (Integer v : arr) {
            q.add(v);
        }

        expected.sort(Comparator.comparingInt(Integer::intValue));
        for (Integer v : expected) {
            assertEquals(v.intValue(), q.dequeue().intValue());
        }
    }

    @Test
    public void testMinPriorityQueue() {
        testDynamic(new MinPriorityQueue<>(), Comparator.comparingInt(Integer::intValue));
    }

    @Test
    public void testMinPriorityQueueByDynamic() {
        for (int i = 0; i < 100000; i++) {
            testDynamic(new MinPriorityQueue<>(), (e1, e2) -> Integer.compare(e1, e2.intValue()));
            LOG.info("Auto Test: %{}", Math.round(i * 100.0 / 100000));
        }
    }

    private void testDynamic(PriorityQueue<Integer> q, Comparator<Integer> comparator) {
        Random rnd = new Random();
        int size = rnd.nextInt(5);
        List<Integer> arr = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            arr.add(rnd.nextInt(100));
            expected.add(arr.get(i));
        }

//        if (LOG.isDebugEnabled()) {
//            LOG.debug(toString(expected));
//        }

        for (Integer v : arr) {
            q.add(v);
        }

        expected.sort(comparator);
        for (Integer v : expected) {
            assertEquals(v.intValue(), q.dequeue().intValue());
        }
    }

    private String toString(List<Integer> arr) {
        StringBuilder s = new StringBuilder();
        for (Integer v : arr) {
            s.append(v.intValue() + ",");
        }

        return s.length() > 0 ? s.deleteCharAt(s.length() - 1).toString() : "";
    }

}