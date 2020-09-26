package com.dtbeat.leetcode._0322_.coin.change;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void testCoinChange() {
        // 输入: coins = [1, 2, 5], amount = 11
        // 输出: 3
        // 解释: 11 = 5 + 5 + 1
        int[] coins = {1, 2, 5};
        int amount = 11;

        Solution s = new Solution();
        assertThat(s.coinChange(coins, amount), is(3));
    }
}