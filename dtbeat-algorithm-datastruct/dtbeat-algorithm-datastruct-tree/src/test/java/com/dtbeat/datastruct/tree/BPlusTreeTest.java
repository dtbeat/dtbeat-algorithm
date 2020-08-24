package com.dtbeat.datastruct.tree;

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
    public void testRemoveDynamic() {
        final int maxKey = 128;
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