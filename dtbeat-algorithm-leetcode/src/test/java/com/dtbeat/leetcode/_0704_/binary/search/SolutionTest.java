package com.dtbeat.leetcode._0704_.binary.search;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolution() {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;

        Solution sln = new Solution();
        int actual = sln.search(nums, target);
        assertEquals(4, actual);
    }

    @Test
    public void testSolutionV2() {
        int[] nums = {1, 2, 2, 4};
        int target = 2;

        SolutionV2 sln = new SolutionV2();
        int actual = sln.search(nums, target);
        assertEquals(1, actual);
    }

    @Test
    public void testSolutionV3() {
        int[] nums = {1, 2, 2, 4};
        int target = 2;

        SolutionV3 sln = new SolutionV3();
        int actual = sln.search(nums, target);
        assertEquals(2, actual);
    }
}