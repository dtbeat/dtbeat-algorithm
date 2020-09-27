package com.dtbeat.leetcode._0111_.minimum.depth.of.binary.tree;

/**
 * SolutionV2
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/26 23:15 dtbeat.com $
 */
public class SolutionV2 {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        int depth = Integer.MAX_VALUE;
        if (root.left != null) {
            depth = Math.min(depth, minDepth(root.left));
        }

        if (root.right != null) {
            depth = Math.min(depth, minDepth(root.right));
        }

        return depth + 1;
    }
}
