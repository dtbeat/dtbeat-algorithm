package com.dtbeat.datastruct.tree;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BSTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(BSTreeTest.class);

    /**
     * test put
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         4
     *          \
     *           5
     */
    @Test
    public void testPut() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertEquals("86459", tree.toPreOrderString());
    }

    /**
     * test remove node with only left child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         4
     *        /
     *       3
     */
    @Test
    public void testRemoveNodeWithOnlyLeftChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 3};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertEquals("86439", tree.toPreOrderString());

        tree.remove(4);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toPreOrderString());
        }

        Assert.assertEquals("8639", tree.toPreOrderString());
    }

    /**
     * test remove node with only right child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         4
     *          \
     *           5
     */
    @Test
    public void testRemoveNodeWithOnlyRightChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 4, 5};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertEquals("86459", tree.toPreOrderString());

        tree.remove(4);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toPreOrderString());
        }

        Assert.assertEquals("8659", tree.toPreOrderString());
    }

    /**
     * test remove node with left and right child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         2
     *        / \
     *       1   4
     *          / \
     *         3  5
     */
    @Test
    public void testRemoveNodeWithLeftRightChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 2, 1, 4, 3, 5};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertEquals("86214359", tree.toPreOrderString());

        tree.remove(2);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toPreOrderString());
        }

        Assert.assertEquals("8631459", tree.toPreOrderString());
    }

    /**
     * test remove node with none child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         2
     *        / \
     *       1   4
     *          / \
     *         3  5
     */
    @Test
    public void testRemoveNodeWithoutChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 2, 1, 4, 3, 5};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertEquals("86214359", tree.toPreOrderString());

        tree.remove(1);
        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.toPreOrderString());
        }

        Assert.assertEquals("8624359", tree.toPreOrderString());
    }

    @Test
    public void testGet() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertEquals(9, tree.get(9).intValue());
    }

    @Test
    public void testGetNullValue() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        Assert.assertNull(tree.get(10));
    }

    @Test
    public void testSet() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        tree.set(9, 10);

        Assert.assertEquals(10, tree.get(9).intValue());
    }

    @Test
    public void testSetButNotSuccess() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{4, 3, 7, 9, 8};
        for (int element : elements) {
            tree.put(element, element);
            if (LOG.isDebugEnabled()) {
                LOG.debug(tree.toPreOrderString());
            }
        }

        tree.set(10, 10);

        Assert.assertNull(tree.get(10));
    }

    /**
     * test get pre node with left child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         2
     *        / \
     *       1   4
     *          / \
     *         3  5
     */
    @Test
    public void testGetPreWithLeftChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 2, 1, 4, 3, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        Assert.assertEquals("86214359", tree.toPreOrderString());

        Integer preNodeKey = tree.getPre(6);
        Assert.assertNotNull(preNodeKey);
        Assert.assertEquals(5, preNodeKey.intValue());
    }

    /**
     * test get pre node without left child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         2
     *        / \
     *       1   4
     *          / \
     *         3  5
     */
    @Test
    public void testGetPreWithoutLeftChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 2, 1, 4, 3, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        Assert.assertEquals("86214359", tree.toPreOrderString());

        Integer preNodeKey = tree.getPre(3);
        Assert.assertNotNull(preNodeKey);
        Assert.assertEquals(2, preNodeKey.intValue());
    }

    /**
     * test get post node with right child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         2
     *        / \
     *       1   4
     *          / \
     *         3  5
     */
    @Test
    public void testGetPostWithRightChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 2, 1, 4, 3, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        Assert.assertEquals("86214359", tree.toPreOrderString());

        Integer preNodeKey = tree.getPost(2);
        Assert.assertNotNull(preNodeKey);
        Assert.assertEquals(3, preNodeKey.intValue());
    }

    /**
     * test get post node without right child
     *
     *             8
     *            / \
     *           6   9
     *          /
     *         2
     *        / \
     *       1   4
     *          / \
     *         3  5
     */
    @Test
    public void testGetPostWithoutRightChild() {
        BSTree<Integer, Integer> tree = new BSTree<>();
        int[] elements = new int[]{8, 6, 9, 2, 1, 4, 3, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        Assert.assertEquals("86214359", tree.toPreOrderString());

        Integer preNodeKey = tree.getPost(5);
        Assert.assertNotNull(preNodeKey);
        Assert.assertEquals(6, preNodeKey.intValue());
    }
}