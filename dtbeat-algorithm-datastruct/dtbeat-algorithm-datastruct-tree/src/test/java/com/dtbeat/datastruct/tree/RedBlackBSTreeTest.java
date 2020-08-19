package com.dtbeat.datastruct.tree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Red-Black Binary Search Tree Test
 *
 * @author elvinshang
 */
public class RedBlackBSTreeTest {
    @Test
    public void testPutWithEmptyTree() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{1};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("1-b", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutLeftWithBlackParent() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 3};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("3-r4-b", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutRightWithBlackParent() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("4-b5-r", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutLeftWithLeftRedParentAndUncleIsNull() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 3, 2};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("2-r3-b4-r", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutRightWithLeftRedParentAndUncleIsNull() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 2, 3};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("2-r3-b4-r", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutRightWithRightRedParentAndUncleIsNull() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 5, 6};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("4-r5-b6-r", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutLeftWithRightRedParentAndUncleIsNull() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 6, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("4-r5-b6-r", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutLeftWithLeftRedParentAndRedUncle() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 3, 5, 2};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("2-r3-b4-b5-b", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutRightWithLeftRedParentAndRedUncle() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 2, 5, 3};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("2-b3-r4-b5-b", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutRightWithRightRedParentAndRedUncle() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 3, 5, 6};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("3-b4-b5-b6-r", tree.toInOrderWithColorString());
    }

    @Test
    public void testPutLeftWithRightRedParentAndRedUncle() {
        RedBlackBSTree<Integer, Integer> tree = new RedBlackBSTree<>();
        int[] elements = new int[]{4, 3, 6, 5};
        for (int element : elements) {
            tree.put(element, element);
        }
        assertEquals("3-b4-b5-r6-b", tree.toInOrderWithColorString());
    }
}