package com.dtbeat.dp;

/**
 * Longest Increment Sequence
 *
 * @author elvinshang
 * @version Id: LongestIncrementSequence.java, v0.0.1 2020/9/4 14:50 dtbeat.com $
 */
public class LongestIncrementSequence {
    /**
     * LIS DP Algorithm
     * <p>
     * 1. Stage: Sub Sequnce Lenght
     * 2. State: dp[0-j]
     * 3. State Transfer Equation: dp[i] = max{dp[i], dp[j] + 1 | A[i] > A[j]}
     * </p>
     *
     * @param numberSequence
     * @return
     */
    public static int lis(String numberSequence) {
        if (numberSequence == null || numberSequence.trim().isEmpty()) {
            throw new IllegalArgumentException("numberSequence");
        }

        char[] chars = numberSequence.toCharArray();
        int n = chars.length;
        int[] dp = new int[n];

        int max = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (chars[i] > chars[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }

            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
