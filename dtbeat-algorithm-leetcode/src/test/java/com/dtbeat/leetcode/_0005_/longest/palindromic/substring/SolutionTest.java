package com.dtbeat.leetcode._0005_.longest.palindromic.substring;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void testForceAlgorithm() {
        Solution sln = new Solution();
        String s = "babad";

        String actual = sln.longestPalindrome(s);
        assertThat(actual, is("bab"));
    }

    @Test
    public void testExpandAroundCenterAlgorithm() {
        SolutionV2 sln = new SolutionV2();
        String s = "babad";

        String actual = sln.longestPalindrome(s);
        assertThat(actual, is("bab"));
    }

    @Test
    public void testDPAlgorithm() {
        SolutionV3 sln = new SolutionV3();
        String s = "babad";

        String actual = sln.longestPalindrome(s);
        assertThat(actual, is("bab"));
    }

    @Test
    public void testManacherAlgorithm() {
        SolutionV4 sln = new SolutionV4();
        String s = "babad";

        String actual = sln.longestPalindrome(s);
        assertThat(actual, is("bab"));
    }
}