package com.dtbeat.leetcode._0111_.minimum.depth.of.binary.tree;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testBFS() {
        TreeNode root = mockBinaryTree();
        Solution sln = new Solution();
        int actual = sln.minDepth(root);
        assertEquals(2, actual);
    }

    @Test
    public void testDFS() {
        TreeNode root = mockBinaryTree();
        SolutionV2 sln = new SolutionV2();
        int actual = sln.minDepth(root);
        assertEquals(2, actual);
    }

    private TreeNode mockBinaryTree() {
        Integer[] tree = new Integer[]{3, 9, 20, null, null, 15, 7};
        final int len = tree.length;
        TreeNode[] nodes = new TreeNode[len];

        for (int i = 0; i < len; i++) {
            if (nodes[i] == null && tree[i] != null) {
                nodes[i] = new TreeNode(tree[i]);
            }

            int left = 2 * i + 1;
            if (left < len && tree[left] != null) {
                nodes[left] = new TreeNode(tree[left]);
                nodes[i].left = nodes[left];
            }

            int right = 2 * i + 2;
            if (right < len && tree[right] != null) {
                nodes[right] = new TreeNode(tree[right]);
                nodes[i].right = nodes[right];
            }
        }

        return nodes[0];
    }
}