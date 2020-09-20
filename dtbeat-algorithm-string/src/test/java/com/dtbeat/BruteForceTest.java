package com.dtbeat;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class BruteForceTest {
    @Test
    public void testSearch() {
        String s = "aaaabaacaab";
        String pat = "aac";

        int index = BruteForce.search(s, pat);
        assertThat(index, is(s.indexOf("aac")));
    }
}