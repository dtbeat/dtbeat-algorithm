package com.dtbeat;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class KeyIndexCountingTest {
    @Test
    public void testSort() {
        KeyIndexCounting.Entry[] data = new KeyIndexCounting.Entry[]{
                new KeyIndexCounting.Entry(2, "D"),
                new KeyIndexCounting.Entry(3, "G"),
                new KeyIndexCounting.Entry(1, "A"),
                new KeyIndexCounting.Entry(2, "E"),
                new KeyIndexCounting.Entry(3, "H"),
                new KeyIndexCounting.Entry(4, "J"),
                new KeyIndexCounting.Entry(1, "B"),
                new KeyIndexCounting.Entry(2, "F"),
                new KeyIndexCounting.Entry(1, "C"),
                new KeyIndexCounting.Entry(4, "K"),
                new KeyIndexCounting.Entry(3, "I")
        };

        KeyIndexCounting.sort(data, 5);
        List<String> values = Arrays.stream(data).map(entry -> entry.getValue()).sorted((k1, k2) -> k1.compareTo(k2)).collect(Collectors.toList());
        for (int i = 0; i < values.size(); i++) {
            assertEquals(data[i].getValue(), values.get(i));
        }
    }
}