package com.dtbeat.tree;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AVLTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(AVLTreeTest.class);

    /**
     * test LR remove and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)LR       8          (B)LL    8       (C)balance     8       (D)balance      8
     *            / \                / \                     / \                     / \
     *           6   9  lr          6   9  rr               5   9   remove 5        6   9
     *          /       =>         /       =>              / \      =>             /
     *      <- 4               -> 5                       4   6                   4
     *          \                /
     *           5              4
     */
    @Test
    public void testLRRemove() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("85469", avlTree.toPreOrderString());

        avlTree.remove(5);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toPreOrderString());
        }

        Assert.assertEquals("8649", avlTree.toPreOrderString());
    }

    /**
     * test LL remove and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)LL       4          (B)balance    4          (C)balance    4
     *            / \                      / \                      / \
     *           3   5  rr                2   5   remove 1         2   5
     *          /       =>               / \      =>                \
     *      -> 2                        1   3                        3
     *        /
     *       1
     */
    @Test
    public void testLLRemove() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 5, 2, 1};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("42135", avlTree.toPreOrderString());


        avlTree.remove(1);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toPreOrderString());
        }

        Assert.assertEquals("4235", avlTree.toPreOrderString());
    }

    /**
     * test RR remove and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)RR       4             (B)balance    4             (C)balance    4
     *            / \                         / \                         / \
     *           3   5      rr               3   6      remove 7         3   6
     *                \     =>                  / \     =>                  /
     *                6                        5   7                       5
     *                 \
     *                  7
     */
    @Test
    public void testRRRemove() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 5, 6, 7};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("43657", avlTree.toPreOrderString());


        avlTree.remove(7);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toPreOrderString());
        }

        Assert.assertEquals("4365", avlTree.toPreOrderString());
    }

    /**
     * test RL remove and rotate
     * lr: left rotate
     * rr: right rotate
     * LR: Left-Right
     * LL: Left-Left
     *
     * (A)RR       4             (B)RR         4            (C)balance     4           (D)balance      4
     *            / \                         / \                         / \                         / \
     *           3   7      rr               3   7      lr               3   8       remove 8        3   9
     *                \     =>                   \      =>                  / \      =>                 /
     *                9                           8                        7   9                       7
     *               /                            \
     *              8                             9
     */
    @Test
    public void testRLRemove() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("43879", avlTree.toPreOrderString());


        avlTree.remove(8);
        if (LOG.isDebugEnabled()) {
            LOG.debug(avlTree.toPreOrderString());
        }

        Assert.assertEquals("4397", avlTree.toPreOrderString());
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
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("85469", avlTree.toPreOrderString());
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
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 5, 2, 1};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("42135", avlTree.toPreOrderString());
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
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 5, 6, 7};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("43657", avlTree.toPreOrderString());
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
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals("43879", avlTree.toPreOrderString());
    }

    @Test
    public void testGet() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertEquals(9, avlTree.get(9).intValue());
    }

    @Test
    public void testGetNullValue() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        Assert.assertNull(avlTree.get(10));
    }

    @Test
    public void testSet() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        avlTree.set(9, 10);

        Assert.assertEquals(10, avlTree.get(9).intValue());
    }

    @Test
    public void testSetButNotSuccess() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(avlTree.toPreOrderString());
            }
        }

        avlTree.set(10, 10);

        Assert.assertNull(avlTree.get(10));
    }

    /**
     * test getPre with left child
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
    public void testGetPreWithLeftChild() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            avlTree.put(element, element);
        }

        Assert.assertEquals("85469", avlTree.toPreOrderString());
        Integer pre = avlTree.getPre(8);
        Assert.assertNotNull(pre);
        Assert.assertEquals(6, pre.intValue());
    }

    /**
     * test getPre without left child
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
    public void testGetPreWithoutLeftChild() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 5, 2, 1};
        for (int element : elements) {
            avlTree.put(element, element);
        }

        Assert.assertEquals("42135", avlTree.toPreOrderString());
        Integer pre = avlTree.getPre(3);
        Assert.assertNotNull(pre);
        Assert.assertEquals(2, pre.intValue());
    }

    /**
     * test getPost with right child
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
    public void getPostWithRightChild() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 5, 6, 7};
        for (int element : elements) {
            avlTree.put(element, element);
        }

        Assert.assertEquals("43657", avlTree.toPreOrderString());

        Integer pre = avlTree.getPost(4);
        Assert.assertNotNull(pre);
        Assert.assertEquals(5, pre.intValue());
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
    public void testGetPostWithoutRightChild() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            avlTree.put(element, element);
        }

        Assert.assertEquals("43879", avlTree.toPreOrderString());

        Integer pre = avlTree.getPost(7);
        Assert.assertNotNull(pre);
        Assert.assertEquals(8, pre.intValue());
    }
}