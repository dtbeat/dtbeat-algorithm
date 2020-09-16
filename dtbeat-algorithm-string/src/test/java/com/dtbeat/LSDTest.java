package com.dtbeat;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LSDTest {
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

        LSD.sort(words, 6);
        Arrays.sort(origin);

        for (int i = 0; i < origin.length; i++) {
            assertEquals(words[i], origin[i]);
        }
    }
}