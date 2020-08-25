package com.dtbeat.datastruct.tree;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.Assert.*;

public class BPlusTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(BPlusTreeTest.class);

    @Test
    public void testPutWith5Degree() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(5);
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
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(6);
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
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 28; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27", tree.toInOrderString());
    }

    @Test
    @Ignore
    public void testPutWithBatchAndRandom() {
        final int maxKey = 128;
        int r = -1;
        for (int i1 = 3; i1 < 20; i1++) {
            for (int j1 = 0; j1 < 100000; j1++) {
                BPlusTree<Integer, Integer> tree = new BPlusTree<>(i1);
                for (int i = 1; i < maxKey; i++) {
                    tree.put(i, i);
                }

                StringBuilder writer = new StringBuilder();
                for (int i = 1; i < maxKey; i++) {
                    writer.append(i + "-");
                }

                if(writer.length() > 0) {
                    writer.deleteCharAt(writer.length() - 1);
                }

                String expected = writer.toString();

                assertEquals(expected, tree.toInOrderString());
            }

            LOG.info("Auto Test: %{}", Math.round(i1 * 100.0 / 20));
        }
    }

    @Test
    public void testRemoveWith6Degree_20_24() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(6);
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
    public void testRemoveWith3Degree_5() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 12; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(5);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-6-7-8-9-10-11", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3Degree_6() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 12; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(6);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-7-8-9-10-11", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3Degree_7() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 12; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(7);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-8-9-10-11", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3Degree_8() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 12; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(8);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-9-10-11", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3DegreeAnd13Node_7() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 13; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(7);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-8-9-10-11-12", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3DegreeAnd13Node_8() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 13; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(8);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-9-10-11-12", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3Degree_9() {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < 12; i++) {
            tree.put(i, i);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        tree.remove(9);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals("1-2-3-4-5-6-7-8-10-11", tree.toInOrderString());
    }

    @Test
    public void testRemoveWith3DegreeAnd13Node_4_11() {
        final int maxKey = 13;
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(3);
        for (int i = 1; i < maxKey; i++) {
            tree.put(i, i);
        }

        int start = 4;
        int end = 11;

        if (LOG.isErrorEnabled()) {
            LOG.debug("Start: {}, End: {}", start, end);
        }

        StringBuilder writer = new StringBuilder();
        for (int i = 1; i < maxKey; i++) {
            if(i < start || i > end) {
                writer.append(i + "-");
            }
        }

        if(writer.length() > 0) {
            writer.deleteCharAt(writer.length() - 1);
        }

        String expected = writer.toString();

        for (int i = start; i <= end; i++) {
            tree.remove(i);

            if (LOG.isErrorEnabled()) {
                LOG.debug("Remove: {}", i);
                LOG.debug(tree.toLevelOrderString());
            }
        }

        if (LOG.isErrorEnabled()) {
            LOG.debug(tree.toLevelOrderString());
        }

        assertEquals(expected, tree.toInOrderString());
    }

    @Test
    @Ignore
    public void testRemoveWithBatchAndRandom() {
        final Random rd = new Random(System.currentTimeMillis());
        final int maxKey = 128;
        for (int i1 = 3; i1 < 20; i1++) {
            for (int j1 = 0; j1 < 100000; j1++) {
                BPlusTree<Integer, Integer> tree = new BPlusTree<>(i1);
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
                    if(i < start || i > end) {
                        writer.append(i + "-");
                    }
                }

                if(writer.length() > 0) {
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