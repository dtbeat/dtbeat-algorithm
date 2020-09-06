package com.dtbeat.dp;

/**
 * Gold Minder
 *
 * @author elvinshang
 * @version Id: GoldMinder.java, v0.0.1 2020/9/1 21:18 dtbeat.com $
 */
public class GoldMinder {
    /**
     * Returns the max gold.
     *
     * @param n the number of gold mines
     * @param w the number of miners
     * @param g the array of gold number for gold mine
     * @param p the array of miner number for gold mine
     * @return the max gold
     */
    public static int getMostGolds(int n, int w, int[] g, int[] p) {
        if (n <= 0) {
            throw new IllegalArgumentException("n");
        }

        int[] result = new int[w + 1];
        int[] pre =  new int[w + 1];

        // fill boundary cell
        for (int i = 0; i <= w; i++) {
            if (i >= p[0]) {
                pre[i] = g[0];
            } else {
                pre[i] = 0;
            }
        }

        // scan all gold mines
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                if (j < p[i]) {
                    result[j] = pre[j];
                } else {
                    result[j] = Math.max(pre[j], pre[j - p[i]] + g[i]);
                }
            }

            int[] tmp = pre;
            pre = result;
            result = tmp;
        }

        return pre[w];
    }
}
