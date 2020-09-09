package com.dtbeat.pq;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParingHeapTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(ParingHeapTest.class);

    @Test
    public void testInsert() {
        insertAndExtractMinByAutoCase(new ParingHeap<>(), (e1, e2) -> Integer.compare(e1, e2.intValue()));
    }

    @Test
    public void testUnion() {
        List<Integer> e1 = createList();
        ParingHeap<Integer> h1 = createHeap(e1);

        List<Integer> e2 = createList();
        ParingHeap<Integer> h2 = createHeap(e2);

        List<Integer> expected = new ArrayList<>(e1);
        expected.addAll(e2);

        ParingHeap<Integer> heap = h1.union(h1, h2);
        assertEquals(expected.size(), heap.getSize());

        expected.sort(Comparator.comparingInt(Integer::intValue));
        int expectedSize = expected.size();
        for (Integer v : expected) {
            expectedSize--;
            assertEquals(v.intValue(), heap.extractMin().intValue());
            assertEquals(expectedSize, heap.getSize());
        }
    }

    @Test
    public void testMinimum() {
        List<Integer> expected = createList();
        ParingHeap<Integer> heap = createHeap(expected);

        assertEquals(expected.size(), heap.getSize());
        assertEquals(expected.stream().min(Comparator.comparingInt(Integer::intValue)).get(), heap.minimum());
    }

    @Test
    public void testExtractMin() {
        insertAndExtractMinByAutoCase(new ParingHeap<>(), (e1, e2) -> Integer.compare(e1, e2.intValue()));
    }

    @Test
    public void testDecrease() {
        List<Integer> original = createList();
        for (int i = 0; i < original.size(); i++) {
            List<Integer> expected = new ArrayList<>(original);
            ParingHeap<Integer> heap = createHeap(expected);

            int newKey = expected.get(i) - rnd.nextInt(expected.get(i) - 1);
            expected.set(i, newKey);

            if (LOG.isDebugEnabled()) {
                LOG.debug("==begin==\n" + heap.render() + "\n==end==");
            }

            heap.decrease(original.get(i), newKey);

            if (LOG.isDebugEnabled()) {
                LOG.debug("==begin==\n" + heap.render() + "\n==end==");
            }


            expected.sort(Comparator.comparingInt(Integer::intValue));
            int expectedSize = expected.size();
            for (Integer v : expected) {
                expectedSize--;
                assertEquals(v.intValue(), heap.extractMin().intValue());
                assertEquals(expectedSize, heap.getSize());
            }
        }
    }

    @Test
    public void testDelete() {
        List<Integer> original = createList();
        for (int i = 0; i < original.size(); i++) {
            List<Integer> expected = new ArrayList<>(original);
            ParingHeap<Integer> heap = createHeap(expected);
//
//            if (LOG.isDebugEnabled()) {
//                LOG.debug(toString(expected));
//            }

            Integer key = expected.remove(i);


//            if (LOG.isDebugEnabled()) {
//                LOG.debug("==begin==\n" + heap.render() + "\n==end==");
//            }

            heap.delete(key);

//            if (LOG.isDebugEnabled()) {
//                LOG.debug("==begin==\n" + heap.render() + "\n==end==");
//            }


            expected.sort(Comparator.comparingInt(Integer::intValue));
            int expectedSize = expected.size();
            for (Integer v : expected) {
                expectedSize--;
                assertEquals(v.intValue(), heap.extractMin().intValue());
                assertEquals(expectedSize, heap.getSize());
            }
        }
    }

    @Test
    public void testParingHeap() {
        ParingHeap<Integer> q = new ParingHeap<>();

        List<Integer> arr = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        Integer[] input = new Integer[]{55, 7, 62, 14, 40, 98, 44, 53};

        for (Integer v : input) {
            if (arr.contains(v)) {
                continue;
            }
            arr.add(v);
            expected.add(v);
        }

        for (Integer v : arr) {
            q.insert(v);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("==begin==\n" + q.render() + "\n==end==");
        }

        expected.sort(Comparator.comparingInt(Integer::intValue));
        for (Integer v : expected) {
            assertEquals(v.intValue(), q.extractMin().intValue());
            if (LOG.isDebugEnabled()) {
                LOG.debug("==begin==\n" + q.render() + "\n==end==");
            }
        }
    }

    @Test
    public void testParingHeapByDynamic() {
        insertAndExtractMinByAutoCase(new ParingHeap<>(), (e1, e2) -> Integer.compare(e1, e2.intValue()));
    }

    @Test
    public void testParingHeapWith100KBatch() {
        for (int i = 0; i < 100000; i++) {
            insertAndExtractMinByAutoCase(new ParingHeap<>(), (e1, e2) -> Integer.compare(e1, e2.intValue()));
            LOG.info("Auto Test: %{}", Math.round(i * 100.0 / 100000));
        }
    }

    private void insertAndExtractMinByAutoCase(ParingHeap<Integer> q, Comparator<Integer> comparator) {
        int size = rnd.nextInt(1000);
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
            q.insert(v);
        }

        assertEquals(expected.size(), q.getSize());

        int expectedSize = expected.size();
        expected.sort(comparator);
        for (Integer v : expected) {
            expectedSize--;
            assertEquals(v.intValue(), q.extractMin().intValue());
            assertEquals(expectedSize, q.getSize());
        }
    }

    private ParingHeap<Integer> createHeap(List<Integer> list) {
        ParingHeap<Integer> heap = new ParingHeap<>();
        for (Integer v : list) {
            heap.insert(v);
        }

        return heap;
    }
}