package com.dtbeat.dp;

/**
 * 0/1-Knapsack
 *
 * @author elvinshang
 * @version Id: Knapsack.java, v0.0.1 2020/9/4 10:03 dtbeat.com $
 */
public class Knapsack {
    /**
     * Returns max price value
     * <p>
     * Notice that the capacity is zero
     * </p>
     *
     * @param n the number of items
     * @param w the weight array for items
     * @param f the cost array for items
     * @param c the capacity for the knapsack
     * @return max price value
     */
    public static int getMaxPrice(int n, int[] w, int f[], int c) {
        // prepare state
        int[][] p = new int[n][c + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < c + 1; j++) {
                p[i][n] = 0;
            }
        }

        // init state
        for (int i = 0; i < c + 1; i++) {
            p[0][i] = i < w[0] ? 0 : f[0];
        }

        // recursion from bottom to top
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < c + 1; j++) {
                if (j < w[i]) {
                    p[i][j] = p[i - 1][j];
                } else {
                    p[i][j] = Math.max(p[i - 1][j], p[i - 1][j - w[i]] + f[i]);
                }
            }
        }

        return p[n - 1][c];
    }
}
