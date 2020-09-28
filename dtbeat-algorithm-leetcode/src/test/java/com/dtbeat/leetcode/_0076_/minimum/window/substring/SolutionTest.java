package com.dtbeat.leetcode._0076_.minimum.window.substring;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolution() {
        String s = "ADOBECODEBANC";
        String t = "ABC";

        Solution sln = new Solution();
        String actual = sln.minWindow(s, t);
        assertEquals("BANC", actual);
    }

    @Test
    public void testSolution_aa() {
        String s = "aa";
        String t = "aa";

        Solution sln = new Solution();
        String actual = sln.minWindow(s, t);
        assertEquals("aa", actual);
    }
}