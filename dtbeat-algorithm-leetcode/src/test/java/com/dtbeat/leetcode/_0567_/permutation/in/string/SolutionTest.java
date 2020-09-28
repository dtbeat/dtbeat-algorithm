package com.dtbeat.leetcode._0567_.permutation.in.string;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolution() {
        String s1 = "ab", s2 = "eidboaoo";
        Solution sln = new Solution();
        boolean actual = sln.checkInclusion(s1, s2);
        assertEquals(false, actual);
    }
}