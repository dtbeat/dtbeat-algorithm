package com.dtbeat;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Quick3SortTest {
    @Test
    public void testSort() {
        String[] origin = new String[]{
                "4BCDE2",
                "1DEFG4",
                "3CDEF3",
                "1ABCD0",
                "2ABCD1",
        };

        String[] words = new String[]{
                "4BCDE2",
                "1DEFG4",
                "3CDEF3",
                "1ABCD0",
                "2ABCD1",
        };

        Quick3Sort.sort(words);
        Arrays.sort(origin);

        for (int i = 0; i < origin.length; i++) {
            assertEquals(words[i], origin[i]);
        }
    }
}