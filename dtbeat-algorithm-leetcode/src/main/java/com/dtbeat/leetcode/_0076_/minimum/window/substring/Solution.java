//给你一个字符串 S、一个字符串 T 。请你设计一种算法，可以在 O(n) 的时间复杂度内，从字符串 S 里面找出：包含 T 所有字符的最小子串。
//
//
//
// 示例：
//
// 输入：S = "ADOBECODEBANC", T = "ABC"
//输出："BANC"
//
//
//
// 提示：
//
//
// 如果 S 中不存这样的子串，则返回空字符串 ""。
// 如果 S 中存在这样的子串，我们保证它是唯一的答案。
//
// Related Topics 哈希表 双指针 字符串 Sliding Window
// 👍 771 👎 0
package com.dtbeat.leetcode._0076_.minimum.window.substring;

import java.util.HashMap;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/28 16:00 dtbeat.com $
 */
public class Solution {
    public String minWindow(String s, String t) {
        if (s == null
                || s.isEmpty()
                || t == null
                || t.isEmpty()) {
            return "";
        }

        HashMap<Character, Integer> needs = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        int start = 0, minLen = Integer.MAX_VALUE;
        int count = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;

            if (needs.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).intValue() == needs.get(c).intValue()) {
                    count++;
                }
            }

            // shrink
            while (count == needs.size()) {
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }

                char d = s.charAt(left);
                left++;
                if (window.containsKey(d)) {
                    if (window.get(c).intValue() == needs.get(c).intValue()) {
                        count--;
                    }

                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
