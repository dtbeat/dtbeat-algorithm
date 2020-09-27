package com.dtbeat.leetcode._0752_.open.the.lock;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testBFS() {
        String target = "0202";
        String[] deadends = mockDeadends();

        Solution sln = new Solution();
        int actual = sln.openLock(deadends, target);
        assertEquals(6, actual);
    }

    @Test
    public void testTwoWayBFS() {
        String target = "0202";
        String[] deadends = mockDeadends();

        SolutionV2 sln = new SolutionV2();
        int actual = sln.openLock(deadends, target);
        assertEquals(6, actual);
    }

    private String[] mockDeadends() {
        return new String[]{"0201", "0101", "0102", "1212", "2002"};
    }
}