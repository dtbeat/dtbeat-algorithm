//给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
//
// 换句话说，第一个字符串的排列之一是第二个字符串的子串。
//
// 示例1:
//
//
//输入: s1 = "ab" s2 = "eidbaooo"
//输出: True
//解释: s2 包含 s1 的排列之一 ("ba").
//
//
//
//
// 示例2:
//
//
//输入: s1= "ab" s2 = "eidboaoo"
//输出: False
//
//
//
//
// 注意：
//
//
// 输入的字符串只包含小写字母
// 两个字符串的长度都在 [1, 10,000] 之间
//
// Related Topics 双指针 Sliding Window
// 👍 180 👎 0
package com.dtbeat.leetcode._0567_.permutation.in.string;

import java.util.HashMap;

/**
 * Solution
 *
 * @author elvinshang
 * @version Id: Solution.java, v0.0.1 2020/9/28 18:11 dtbeat.com $
 */
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
            return false;
        }

        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> needs = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        int count = 0;
        while (right < s2.length()) {
            char c = s2.charAt(right);
            right++;

            if(needs.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if(window.get(c).intValue() == needs.get(c).intValue()) {
                    count++;
                }
            }

            if (right - left == s1.length()) {
                if(count == needs.size()) {
                    return true;
                }

                char d = s2.charAt(left);
                left++;
                if(needs.containsKey(d)) {
                    if(window.get(d).intValue() == needs.get(d).intValue()) {
                        count--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return false;
    }
}
