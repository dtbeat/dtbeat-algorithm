package com.dtbeat.leetcode._0005_.longest.palindromic.substring;

/**
 * DP Algorithm Solution
 *
 * @author elvinshang
 * @version Id: SolutionV3.java, v0.0.1 2020/9/25 22:46 dtbeat.com $
 */
public class SolutionV3 {
    /**
     * Returns longest palindrome substring
     *
     * <p>
     * DP Solution:
     * state                    : dp[i][j]
     * state transfer function  : dp[i][j] = (s[i] == s[j]) and dp[i+1][j-1]
     * boundary condition       : j - i < 3 ((j - 1) - (i + 1) + 1 < 2)
     * </p>
     *
     * <p>
     * Complexity Analysis:
     * Time Complexity: O(n^2)
     * Space Complexity: O(n^2)
     * </p>
     *
     * @param s the text to find
     * @return longest palindrome substring
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        final int N = s.length();
        boolean[][] dp = new boolean[N][N];
        int start = 0;
        int maxLen = 1;

        for (int j = 1; j < N; j++) {
            for (int i = 0; i < j; i++) {
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j] && (j - i + 1) > maxLen) {
                    start = i;
                    maxLen = j - i + 1;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }
}
