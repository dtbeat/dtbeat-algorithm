package com.dtbeat.leetcode._0438_.find.all.anagrams.in.a.string;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolution() {
        String s = "cbaebabacd", p = "abc";
        Solution sln = new Solution();
        List<Integer> actual = sln.findAnagrams(s, p);
        assertArrayEquals(new Integer[]{0, 6}, actual.toArray(new Integer[2]));
    }

    @Test
    public void testSolution_1() {
        String s = "abab", p = "ab";
        Solution sln = new Solution();
        List<Integer> actual = sln.findAnagrams(s, p);
        assertArrayEquals(new Integer[]{0, 1, 2}, actual.toArray(new Integer[3]));
    }

}