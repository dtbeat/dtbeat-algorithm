package com.dtbeat.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class KnapsackTest {
    @Test
    public void testGetMostPrice() {
        int[] f = new int[]{6, 3, 5, 4, 6};
        int[] w = new int[]{2, 2, 6, 5, 4};
        int n = 5;
        int c = 10;

        int price = Knapsack.getMaxPrice(n, w, f, c);
        assertEquals(15, price);
    }
}