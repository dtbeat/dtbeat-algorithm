package com.dtbeat.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class GoldMinderTest {
    @Test
    public void testGetMostGolds() {
        int[] g = new int[]{400, 500, 200, 300, 350};
        int[] p = new int[]{5, 5, 3, 4, 3};
        int n = 5;
        int w = 10;

        int golds = GoldMinder.getMostGolds(n, w, g, p);
        assertEquals(900, golds);
    }
}