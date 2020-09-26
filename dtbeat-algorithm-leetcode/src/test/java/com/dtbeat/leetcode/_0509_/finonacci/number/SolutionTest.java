package com.dtbeat.leetcode._0509_.finonacci.number;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void testFib() {
        Solution s = new Solution();
        SolutionV2 s2 = new SolutionV2();

        assertThat(s2.fib(0), is(0));
        assertThat(s2.fib(1), is(1));
        assertThat(s2.fib(2), is(1));
        assertThat(s2.fib(3), is(2));
        assertThat(s2.fib(4), is(3));


        int i = 10;
        assertThat(s2.fib(i), is(s.fib(i)));
    }
}