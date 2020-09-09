package com.dtbeat.tree;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.Assert.*;

public class BTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(BTreeTest.class);

    @Test
    public void testPutWith5Degree() {
        BTree<Integer, Integer> tree = new BTree<>(5);
        for (int i = 1; i < 18; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17", tree.toInOrderString());
    }

    @Test
    public void testPutWith6Degree() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testPutWith3Degree() {
        BTree<Integer, Integer> tree = new BTree<>(3);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testPut() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_1() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(1);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_1_2() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(1);
        tree.remove(2);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_1_2_3() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(1);
        tree.remove(2);
        tree.remove(3);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_13_14_15() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(13);
        tree.remove(14);
        tree.remove(15);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4_5() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        tree.remove(5);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4_5_6() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        tree.remove(5);
        tree.remove(6);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4_5_6_7_8() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        tree.remove(5);
        tree.remove(6);
        tree.remove(7);
        tree.remove(8);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4_5_6_7_8_9() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        tree.remove(5);
        tree.remove(6);
        tree.remove(7);
        tree.remove(8);
        tree.remove(9);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4_5_6_7_8_9_10() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        tree.remove(5);
        tree.remove(6);
        tree.remove(7);
        tree.remove(8);
        tree.remove(9);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(10);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemove_4_5_6_7_8_9_10_11() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        tree.remove(4);
        tree.remove(5);
        tree.remove(6);
        tree.remove(7);
        tree.remove(8);
        tree.remove(9);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(10);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(11);
        assertEquals("1-2-3-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith6Degree_20_24() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(20);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(21);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(22);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(23);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(24);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3Degree_20_24() {
        BTree<Integer, Integer> tree = new BTree<>(3);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(20);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(21);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(22);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(23);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(24);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith4Degree_22_23() {
        BTree<Integer, Integer> tree = new BTree<>(4);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(22);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }
        tree.remove(23);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-24-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith4Degree_4_24() {
        BTree<Integer, Integer> tree = new BTree<>(4);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        for (int i = 4; i < 25; i++) {
            tree.remove(i);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toLevelOrderString());
            }
        }

        assertEquals("1-2-3-25-26-27", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith4Degree_1_27() {
        BTree<Integer, Integer> tree = new BTree<>(4);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        for (int i = 1; i < 28; i++) {
            tree.remove(i);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toLevelOrderString());
            }
        }

        assertEquals("", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith6Degree_1_27() {
        BTree<Integer, Integer> tree = new BTree<>(6);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        for (int i = 1; i < 28; i++) {
            tree.remove(i);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toLevelOrderString());
            }
        }

        assertEquals("", tree.toInOrderString());
    }

    @Test
    @Ignore
    public void testRemoveWithBatchAndRandom() {
        final int maxKey = 128;
        final Random rd = new Random(System.currentTimeMillis());

        for (int i1 = 3; i1 < 20; i1++) {
            for (int j1 = 0; j1 < 100000; j1++) {
                BTree<Integer, Integer> tree = new BTree<>(i1);
                for (int i = 1; i < maxKey; i++) {
                    tree.put(i, i);
                }

                int start = rd.nextInt(maxKey);
                int end = getEndIndex(start, rd, maxKey);

                if (LOG.isErrorEnabled()) {
                    LOG.debug("[{}][M:{}]++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++", j1, i1);
                    LOG.debug("Start: {}, End: {}", start, end);
                }

                StringBuilder writer = new StringBuilder();
                for (int i = 1; i < maxKey; i++) {
                    if (i < start || i > end) {
                        writer.append(i + "-");
                    }
                }

                if (writer.length() > 0) {
                    writer.deleteCharAt(writer.length() - 1);
                }

                String expected = writer.toString();

                for (int i = start; i <= end; i++) {
                    tree.remove(i);
                }

                if (LOG.isErrorEnabled()) {
                    LOG.debug(tree.toLevelOrderString());
                    LOG.debug("[[{}][M:{}]++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++", i1, j1);
                }

                assertEquals(expected, tree.toInOrderString());
            }
            LOG.info("Auto Test: %{}", Math.round(i1 * 100.0 / 20));
        }
    }

    private int getEndIndex(int start, Random rd, int bound) {
        if (start == bound - 1) {
            return start;
        }

        int end = rd.nextInt(bound);
        while (end <= start) {
            end = rd.nextInt(bound);
        }

        return end;
    }
}