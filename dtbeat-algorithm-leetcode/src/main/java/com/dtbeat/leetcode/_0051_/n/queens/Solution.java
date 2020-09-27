//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
//
//
// 上图为 8 皇后问题的一种解法。
//
// 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
//
// 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
//
//
//
// 示例：
//
// 输入：4
//输出：[
// [".Q..",  // 解法 1
//  "...Q",
//  "Q...",
//  "..Q."],
//
// ["..Q.",  // 解法 2
//  "Q...",
//  "...Q",
//  ".Q.."]
//]
//解释: 4 皇后问题存在两个不同的解法。
//
//
//
//
// 提示：
//
//
// 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
//
// Related Topics 回溯算法
// 👍 616 👎 0

package com.dtbeat.leetcode._0051_.n.queens;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/26 16:35 dtbeat.com $
 */
public class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }

        char[][] board = createBoard(n);
        dfs(n, 0, board, res);

        return res;
    }

    private void dfs(int n, int row, char[][] board, List<List<String>> res) {
        if (row == n) {
            res.add(toStringBoard(n, board));
            return;
        }

        for (int col = 0; col < n; col++) {
            if (!isValid(n, row, col, board)) {
                continue;
            }

            board[row][col] = 'Q';
            dfs(n, row + 1, board, res);
            board[row][col] = '.';
        }
    }

    private boolean isValid(int n, int row, int col, char[][] board) {
        // 检查同一列是否有Q
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        // 检查左上是否有Q
        int i = row - 1;
        int j = col - 1;
        while (i >= 0 && j >= 0) {
            if (board[i--][j--] == 'Q') {
                return false;
            }
        }

        // 检查右上是否有Q
        i = row - 1;
        j = col + 1;
        while (i >= 0 && j < n) {
            if (board[i--][j++] == 'Q') {
                return false;
            }
        }

        return true;
    }

    private char[][] createBoard(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        return board;
    }

    private List<String> toStringBoard(int n, char[][] board) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] row = board[i];
            res.add(new String(row));
        }

        return res;
    }
}
