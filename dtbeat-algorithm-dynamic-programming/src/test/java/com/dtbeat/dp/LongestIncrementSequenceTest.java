package com.dtbeat.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestIncrementSequenceTest {
    @Test
    public void testLIS() {
        String str = "645978";
        int len = LongestIncrementSequence.lis(str);
        assertEquals(len, 4);
    }
}