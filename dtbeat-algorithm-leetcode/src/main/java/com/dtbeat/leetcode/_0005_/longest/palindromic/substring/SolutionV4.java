package com.dtbeat.leetcode._0005_.longest.palindromic.substring;

/**
 * Manacher Algorithm Solution
 *
 * @author elvinshang
 * @version Id: SolutionV4.java, v0.0.1 2020/9/25 23:34 dtbeat.com $
 */
public class SolutionV4 {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        String str = addSeperators(s, "#");
        final int N = 2 * s.length() + 1;
        int[] p = new int[N];

        int maxRight = 0;
        int center = 0;

        int start = 0;
        int maxLen = 0;

        for (int i = 0; i < N; i++) {
            if (i < maxRight) {
                int mirror = 2 * center - i;
                p[i] = Math.min(p[mirror], maxRight - i);
            }

            int l = i - (p[i] + 1);
            int r = i + (p[i] + 1);
            while (l >= 0 && r < N && str.charAt(l) == str.charAt(r)) {
                p[i]++;
                l--;
                r++;
            }

            if ((i + p[i]) > maxRight) {
                maxRight = i + p[i];
                center = i;
            }

            if (p[i] > maxLen) {
                maxLen = p[i];
                start = (i - maxLen) / 2;
            }
        }

        return s.substring(start, start + maxLen);
    }

    private String addSeperators(String s, String separator) {
        StringBuilder writer = new StringBuilder();
        writer.append(separator);
        for (int i = 0; i < s.length(); i++) {
            writer.append(s.charAt(i)).append(separator);
        }

        return writer.toString();
    }
}
