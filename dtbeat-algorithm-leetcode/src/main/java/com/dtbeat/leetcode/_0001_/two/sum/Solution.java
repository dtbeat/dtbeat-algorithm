//给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
//
//
//
// 示例:
//
// 给定 nums = [2, 7, 11, 15], target = 9
//
//因为 nums[0] + nums[1] = 2 + 7 = 9
//所以返回 [0, 1]
//
// Related Topics 数组 哈希表
// 👍 9233 👎 0

package com.dtbeat.leetcode._0001_.two.sum;

import java.util.HashMap;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/28 15:17 dtbeat.com $
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(nums[0], 0);

        for (int i = 1; i <= nums.length - 1; i++) {
            int v = target - nums[i];
            if (map.containsKey(v)) {
                return new int[]{map.get(v), i};
            } else {
                map.put(nums[i], i);
            }
        }

        return new int[]{-1, -1};
    }
}
