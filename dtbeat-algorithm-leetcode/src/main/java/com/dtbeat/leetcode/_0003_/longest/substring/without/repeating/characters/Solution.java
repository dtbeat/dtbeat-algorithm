//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
//
// 示例 1:
//
// 输入: "abcabcbb"
//输出: 3
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
//
//
// 示例 2:
//
// 输入: "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
//
//
// 示例 3:
//
// 输入: "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
//
// Related Topics 哈希表 双指针 字符串 Sliding Window
// 👍 4380 👎 0

package com.dtbeat.leetcode._0003_.longest.substring.without.repeating.characters;

import java.util.HashMap;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/28 21:32 dtbeat.com $
 */
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int count = 0;
        int res = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            right++;

            window.put(c, window.getOrDefault(c, 0) + 1);
            if (window.get(c).intValue() == 1) {
                count++;
            }

            if ((right - left) == count) {
                res = Math.max(res, count);
            } else {
                while (right - left > count) {
                    char d = s.charAt(left);
                    left++;

                    if (window.get(d).intValue() == 1) {
                        count--;
                    }

                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return res;
    }
}
