package com.dtbeat.leetcode._0046_.permutations;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * SolutionV2
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/26 16:26 dtbeat.com $
 */
public class SolutionV2 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        final int len = nums.length;
        if(len == 0) {
            return res;
        }

        Deque<Integer> path = new ArrayDeque<>();
        boolean[] used = new boolean[len];
        dfs(nums, len, 0, path, used, res);

        return res;
    }

    private void dfs(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        // 是否满足结束条件
        if(depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            // 是否满足条件
            if(used[i]) {
                continue;
            }

            // 选择
            path.addLast(nums[i]);
            used[i] = true;

            // 深度遍历
            dfs(nums, len, depth + 1, path, used, res);

            // 撤销选择
            path.removeLast();
            used[i] = false;
        }
    }
}
