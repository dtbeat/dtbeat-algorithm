package com.dtbeat.leetcode._0009_.palindrome.number;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void testSolution() {
        int x = 121;
        Solution sln = new Solution();
        boolean actual = sln.isPalindrome(x);
        assertThat(actual, is(true));
    }

    @Test
    public void testSolutionV2() {
        int x = 1221;
        SolutionV2 sln = new SolutionV2();
        boolean actual = sln.isPalindrome(x);
        assertThat(actual, is(true));
    }
}