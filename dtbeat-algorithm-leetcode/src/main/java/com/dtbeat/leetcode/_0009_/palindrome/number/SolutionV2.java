package com.dtbeat.leetcode._0009_.palindrome.number;

/**
 * SolutionV2
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/26 09:15 dtbeat.com $
 */
public class SolutionV2 {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && (x % 10) == 0)) {
            return false;
        }

        int y = 0;
        while (x > y) {
            y = y * 10 + x % 10;
            x /= 10;
        }

        return x == y || x == (y / 10);
    }
}
