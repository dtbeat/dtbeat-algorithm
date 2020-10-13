package com.dtbeat;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DoubleArrayTrieTest {
    @Test
    public void testCase() {
        String[] dict = {"AC", "ACE", "ACFF", "AD", "CD", "CF", "ZQ"};
        DoubleArrayTrie dat = new DoubleArrayTrie();
        dat.build(Arrays.stream(dict).collect(Collectors.toList()));
        dat.dump();

        int index = dat.exactMatchSearch("ACE");
        assertEquals(dict[index], "ACE");
    }

    @Test
    public void testCaseV2() {
        String[] dict = {"AC", "ACE", "ACFF", "AD", "CD", "CF", "ZQ"};
        DoubleArrayTrieV2 dat = new DoubleArrayTrieV2(Arrays.stream(dict).collect(Collectors.toList()));

        int index = dat.exactMatchSearch("ACFF");
        assertEquals(dict[index], "ACFF");
    }
}