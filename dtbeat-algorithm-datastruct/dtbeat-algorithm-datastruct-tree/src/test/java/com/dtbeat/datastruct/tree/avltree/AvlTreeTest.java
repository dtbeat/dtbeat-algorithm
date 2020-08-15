package com.dtbeat.datastruct.tree.avltree;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AvlTreeTest
 *
 * @author elvinshang
 * @version $Id:: AvlTreeTest.java, v0.0.1 2020/8/15 11:49 geekdancer.com $
 */
public class AvlTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(AvlTreeTest.class);

    /**
     * test LR delete and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)LR       8          (B)LL    8       (C)balance     8       (D)balance      8
     *            / \                / \                     / \                     / \
     *           6   9  lr          6   9  rr               5   9   delete 5        6   9
     *          /       =>         /       =>              / \      =>             /
     *      <- 4               -> 5                       4   6                   4
     *          \                /
     *           5              4
     */
    @Test
    public void testLRDelete() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("85469", avlTree.toVlrString());

        avlTree.delete(5);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toVlrString());
        }

        Assert.assertEquals("8649", avlTree.toVlrString());
    }

    /**
     * test LL delete and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)LL       4          (B)balance    4          (C)balance    4
     *            / \                      / \                      / \
     *           3   5  rr                2   5   delete 1         2   5
     *          /       =>               / \      =>                \
     *      -> 2                        1   3                        3
     *        /
     *       1
     */
    @Test
    public void testLLDelete() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 5, 2, 1};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("42135", avlTree.toVlrString());


        avlTree.delete(1);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toVlrString());
        }

        Assert.assertEquals("4235", avlTree.toVlrString());
    }

    /**
     * test RR delete and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)RR       4             (B)balance    4             (C)balance    4
     *            / \                         / \                         / \
     *           3   5      rr               3   6      delete 7         3   6
     *                \     =>                  / \     =>                  /
     *                6                        5   7                       5
     *                 \
     *                  7
     */
    @Test
    public void testRRDelete() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 5, 6, 7};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("43657", avlTree.toVlrString());


        avlTree.delete(7);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toVlrString());
        }

        Assert.assertEquals("4365", avlTree.toVlrString());
    }

    /**
     * test RL delete and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)RR       4             (B)RR         4            (C)balance     4           (D)balance      4
     *            / \                         / \                         / \                         / \
     *           3   7      rr               3   7      lr               3   8       delete 8        3   9
     *                \     =>                   \      =>                  / \      =>                 /
     *                9                           8                        7   9                       7
     *               /                            \
     *              8                             9
     */
    @Test
    public void testRLDelete() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("43879", avlTree.toVlrString());


        avlTree.delete(8);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toVlrString());
        }

        Assert.assertEquals("4397", avlTree.toVlrString());
    }

    /**
     * test LR put and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)LR       8          (B)LL    8       (C)balance     8
     *            / \                / \                     / \
     *           6   9  lr          6   9  rr               5   9
     *          /       =>         /       =>              / \
     *      <- 4               -> 5                       4   6
     *          \                /
     *           5              4
     */
    @Test
    public void testLRPut() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("85469", avlTree.toVlrString());
    }

    /**
     * test LL put and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)LL       4          (B)balance    4
     *            / \                      / \
     *           3   5  rr                2   5
     *          /       =>               / \
     *      -> 2                        1   3
     *        /
     *       1
     */
    @Test
    public void testLLPut() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 5, 2, 1};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("42135", avlTree.toVlrString());
    }

    /**
     * test RR put and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)RR       4             (B)balance    4
     *            / \                         / \
     *           3   5      rr               3   6
     *                \     =>                  / \
     *                6                        5   7
     *                 \
     *                  7
     */
    @Test
    public void testRRPut() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 5, 6, 7};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("43657", avlTree.toVlrString());
    }

    /**
     * test RL put and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)RR       4             (B)RR         4            (C)balance     4
     *            / \                         / \                         / \
     *           3   7      rr               3   7      lr               3   8
     *                \     =>                   \      =>                  / \
     *                9                           8                        7   9
     *               /                            \
     *              8                             9
     */
    @Test
    public void testRLPut() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals("43879", avlTree.toVlrString());
    }

    @Test
    public void testGet() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertEquals(9, avlTree.get(9).intValue());
    }

    @Test
    public void testGetNullValue() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        Assert.assertNull(avlTree.get(10));
    }

    @Test
    public void testSet() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        avlTree.set(9, 10);

        Assert.assertEquals(10, avlTree.get(9).intValue());
    }

    @Test
    public void testSetButNotSuccess() {
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toVlrString());
            }
        }

        avlTree.set(10, 10);

        Assert.assertNull(avlTree.get(10));
    }
}
