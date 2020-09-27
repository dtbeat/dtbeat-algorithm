package com.dtbeat.leetcode._0052_.n.queens.ii;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolveNQueens() {
        int n = 4;
        Solution sln = new Solution();
        int actual = sln.totalNQueens(n);
        assertEquals(actual, 2);
    }
}