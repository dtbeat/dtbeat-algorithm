package com.dtbeat;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HuffmanCodingTest {
    @Test
    public void testCompress() {
        HuffmanCoding coder = new HuffmanCoding();
        String r = coder.compress("AACBCAADDBBADDAABB");
        assertThat(r, is("00110101100011111110100111111001010"));
    }
}