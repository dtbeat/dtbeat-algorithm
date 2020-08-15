package com.dtbeat.datastruct.avltree;

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
     * (A)RR       4             (B)RR         4            (B)balance     4
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
}
