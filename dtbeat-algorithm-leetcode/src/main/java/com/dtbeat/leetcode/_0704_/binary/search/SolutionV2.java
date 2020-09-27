package com.dtbeat.leetcode._0704_.binary.search;

/**
 * SolutionV2
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/27 22:56 dtbeat.com $
 */
public class SolutionV2 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] >= target) {
                right = mid - 1;
            }
        }

        if(left == nums.length || nums[left] != target) {
            return -1;
        }

        return left;
    }
}
