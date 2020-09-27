package com.dtbeat.leetcode._0052_.n.queens.ii;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/26 17:40 dtbeat.com $
 */
public class Solution {
    private int total = 0;

    public int totalNQueens(int n) {
        total = 0;
        if (n <= 0) {
            return total;
        }

        boolean[][] board = new boolean[n][n];
        dfs(n, 0, board);

        return total;
    }

    private void dfs(int n, int row, boolean[][] board) {
        if (row == n) {
            this.total++;
            return;
        }

        for (int col = 0; col < n; col++) {
            if (!isValid(n, row, col, board)) {
                continue;
            }

            board[row][col] = true;
            dfs(n, row + 1, board);
            board[row][col] = false;
        }
    }

    private boolean isValid(int n, int row, int col, boolean[][] board) {
        // 检查同一列是否有Q
        for (int i = 0; i < row; i++) {
            if (board[i][col]) {
                return false;
            }
        }

        // 检查左上是否有Q
        int i = row - 1;
        int j = col - 1;
        while (i >= 0 && j >= 0) {
            if (board[i--][j--]) {
                return false;
            }
        }

        // 检查右上是否有Q
        i = row - 1;
        j = col + 1;
        while (i >= 0 && j < n) {
            if (board[i--][j++]) {
                return false;
            }
        }

        return true;
    }
}
