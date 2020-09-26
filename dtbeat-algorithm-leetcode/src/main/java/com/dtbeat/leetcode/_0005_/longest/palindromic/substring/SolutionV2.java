package com.dtbeat.leetcode._0005_.longest.palindromic.substring;

/**
 * Expand Center Algorithm Solution
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/25 22:26 dtbeat.com $
 */
public class SolutionV2 {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        final int N = s.length();
        int start = 0;
        int maxLen = 1;

        for (int i = 1; i < N - 1; i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);

            if (len > maxLen) {
                start = i - (len - 1) / 2;
                maxLen = len;
            }
        }

        return s.substring(start, start + maxLen);
    }

    private int expandAroundCenter(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }

        return r - l - 1;
    }
}
