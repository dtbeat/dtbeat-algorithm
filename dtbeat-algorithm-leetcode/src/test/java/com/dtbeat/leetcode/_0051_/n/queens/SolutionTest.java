package com.dtbeat.leetcode._0051_.n.queens;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testSolveNQueens() {
        int n = 4;
        String[][] expected = mockExpected();

        Solution sln = new Solution();
        List<List<String>> actual = sln.solveNQueens(n);
        assertEquals(actual.size(), expected.length);

        for (int i = 0; i < actual.size(); i++) {
            List<String> actualBoard = actual.get(i);
            String[] expectedBoard = expected[i];
            for (int j = 0; j < 4; j++) {
                String actualRow = actualBoard.get(i);
                String expectedRow = expectedBoard[i];
                assertArrayEquals(actualRow.toCharArray(), expectedRow.toCharArray());
            }
        }
    }

    private String[][] mockExpected() {
        return new String[][]{
                {".Q..",
                        "...Q",
                        "Q...",
                        "..Q."},
                {"..Q.",
                        "Q...",
                        "...Q",
                        ".Q.."}
        };
    }
}