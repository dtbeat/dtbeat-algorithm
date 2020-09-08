package com.dtbeat.leetcode.stack;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrappingRainWaterTest {
    @Test
    public void testTrap() {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        TrappingRainWater tr = new TrappingRainWater();
        int ret = tr.trap(height);
        assertEquals(6, ret);
    }
}