//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 示例 1：
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
//
//
// 示例 2：
//
// 输入: "cbbd"
//输出: "bb"
//
// Related Topics 字符串 动态规划
// 👍 2726 👎 0

package com.dtbeat.leetcode._0005_.longest.palindromic.substring;

/**
 * Force Algorithm Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/25 22:18 dtbeat.com $
 */
public class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        final int N = s.length();
        int start = 0;
        int maxLen = 1;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isPalindrome(s, i, j) && (j - i + 1) > maxLen) {
                    start = i;
                    maxLen = j - i + 1;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }

        return true;
    }
}
