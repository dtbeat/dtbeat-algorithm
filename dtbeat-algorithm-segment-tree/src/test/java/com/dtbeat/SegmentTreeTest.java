package com.dtbeat;

import com.dtbeat.algorithm.lang.Tuple;
import org.junit.Before;
import org.junit.Test;

import javax.vecmath.Tuple2d;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class SegmentTreeTest {
    private Random rnd;

    @Before
    public void setUp() {
        rnd = new Random(System.currentTimeMillis());
    }

    @Test
    public void testMinimum() {
        Integer[] values = createList();
        Tuple<Integer, Integer> indexs = createRange(values.length);

        SegmentTree<Integer> tree = new SegmentTree<>(values);
        Integer minimum = tree.minimum(indexs.getT1(), indexs.getT2());

        assertEquals(min(indexs.getT1(), indexs.getT2(), values), minimum.intValue());
    }

    @Test
    public void testAllMinimum() {
        Integer[] values = createList();
        List<Tuple<Integer, Integer>> ranges = createRanges(values.length);
        for (Tuple<Integer, Integer> range : ranges) {
            SegmentTree<Integer> tree = new SegmentTree<>(values);
            Integer minimum = tree.minimum(range.getT1(), range.getT2());

            assertEquals(min(range.getT1(), range.getT2(), values), minimum.intValue());
        }
    }

    @Test
    public void testSum() {
        Integer[] values = createList();
        Tuple<Integer, Integer> range = createRange(values.length);

        SegmentTree<Integer> tree = new SegmentTree<>(values);
        Integer minimum = tree.sum(range.getT1(), range.getT2(), (e1, e2) -> e1 + e2);

        assertEquals(sum(range.getT1(), range.getT2(), values), minimum.intValue());
    }

    @Test
    public void testAllSum() {
        Integer[] values = createList();
        List<Tuple<Integer, Integer>> ranges = createRanges(values.length);
        for (Tuple<Integer, Integer> range : ranges) {
            SegmentTree<Integer> tree = new SegmentTree<>(values);
            Integer minimum = tree.sum(range.getT1(), range.getT2(), (e1, e2) -> e1 + e2);

            assertEquals(sum(range.getT1(), range.getT2(), values), minimum.intValue());
        }
    }

    @Test
    public void testSet() {
        Integer[] values = createList();
        Tuple<Integer, Integer> range = createRange(values.length);

        SegmentTree<Integer> tree = new SegmentTree<>(values);
        tree.set(range.getT1(), range.getT2(), 1);

        List<Integer> query = tree.query(range.getT1(), range.getT2(), (List<Integer> r, int index, Integer e) -> {
            if (r == null) {
                r = new ArrayList<>();
            }

            r.add(e);

            return r;
        });

        assertNotNull(query);
        for (Integer e : query) {
            assertEquals(1, e.intValue());
        }
    }

    @Test
    public void testAlltestSet() {
        Integer[] values = createList();
        List<Tuple<Integer, Integer>> ranges = createRanges(values.length);
        for (Tuple<Integer, Integer> range : ranges) {
            SegmentTree<Integer> tree = new SegmentTree<>(values);
            tree.set(range.getT1(), range.getT2(), 1);

            List<Integer> query = tree.query(range.getT1(), range.getT2(), (List<Integer> r, int index, Integer e) -> {
                if (r == null) {
                    r = new ArrayList<>();
                }

                r.add(e);

                return r;
            });

            assertNotNull(query);
            for (Integer e : query) {
                assertEquals(1, e.intValue());
            }
        }
    }

    private Integer[] createList() {
        int size = rnd.nextInt(100);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            res.add(rnd.nextInt(100));
        }

        return res.toArray(new Integer[0]);
    }

    private int min(int start, int end, Integer[] list) {
        List<Integer> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            res.add(list[i]);
        }

        return res.stream().min(Comparator.comparingInt(Integer::intValue)).get();
    }

    private int sum(int start, int end, Integer[] list) {
        List<Integer> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            res.add(list[i]);
        }

        return res.stream().reduce(0, (e1, e2) -> e1 + e2);
    }

    private Tuple<Integer, Integer> createRange(int bound) {
        List<Tuple<Integer, Integer>> ranges = createRanges(bound);
        int i = rnd.nextInt(ranges.size());

        return ranges.get(i);
    }

    private List<Tuple<Integer, Integer>> createRanges(int bound) {
        List<Tuple<Integer, Integer>> res = new ArrayList<>();
        for (int i = 0; i < bound; i++) {
            for (int j = i; j < bound; j++) {
                res.add(new Tuple(i, j));
            }
        }

        return res;
    }
}