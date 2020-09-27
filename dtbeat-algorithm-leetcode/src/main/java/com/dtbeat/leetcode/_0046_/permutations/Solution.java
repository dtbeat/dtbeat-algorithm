//给定一个 没有重复 数字的序列，返回其所有可能的全排列。
//
// 示例:
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//]
// Related Topics 回溯算法
// 👍 918 👎 0

package com.dtbeat.leetcode._0046_.permutations;

import java.util.*;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/26 15:44 dtbeat.com $
 */
public class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums.length == 0) {
            return res;
        }

        Deque<Integer> q = new ArrayDeque<>(nums.length);
        premute(nums, q, res);

        return res;
    }

    private void premute(int[] nums, Deque<Integer> q, List<List<Integer>> res) {
        // 满足条件
        if(q.size() == nums.length) {
            res.add(new ArrayList<>(q));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if(q.contains(nums[i])) {
                continue;
            }

            q.addLast(nums[i]);
            premute(nums, q, res);
            q.removeLast();
        }
    }
}
