package com.dtbeat.leetcode._0001_.two.sum;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolution() {
        int[] nums = {3, 2, 4};
        int target = 6;

        Solution sln = new Solution();
        int[] expected = sln.twoSum(nums, 6);
        assertArrayEquals(new int[]{1, 2}, expected);
    }
}