package com.dtbeat.leetcode._0008_.string.to.integer.atoi;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void testSolution_1() {
        String str = "16";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(16));
    }

    @Test
    public void testSolution_2() {
        String str = "+16";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(16));
    }

    @Test
    public void testSolution_3() {
        String str = "-16";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(-16));
    }

    @Test
    public void testSolution_4() {
        String str = "  +16";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(16));
    }

    @Test
    public void testSolution_5() {
        String str = "  -16";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(-16));
    }

    @Test
    public void testSolution_6() {
        String str = "2  -16";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(2));
    }

    @Test
    public void testSolution_7() {
        String str = "16   ";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(16));
    }

    @Test
    public void testSolution_8() {
        String str = "16+   ";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(16));
    }

    @Test
    public void testSolution_9() {
        String str = "1-6   ";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(1));
    }

    @Test
    public void testSolution_10() {
        String str = "-91283472332";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(Integer.MIN_VALUE));
    }

    @Test
    public void testSolution11() {
        String str = "9223372036854775808";
        Solution sln = new Solution();
        int actual = sln.myAtoi(str);
        assertThat(actual, is(2147483647));
    }
}