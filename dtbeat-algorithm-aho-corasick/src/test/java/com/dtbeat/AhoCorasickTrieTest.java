package com.dtbeat;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;

public class AhoCorasickTrieTest {
    @Test
    public void textExtractMathSearch() {
        String[] patterns = {"she", "shr", "say", "he", "his", "her"};
        AhoCorasickTrie ac = new AhoCorasickTrie(Arrays.stream(patterns).collect(Collectors.toList()));
        List<Integer> expected = ac.exactMatchSearch("ahisher");
        Assert.assertThat(4, is(expected.size() / 2));
    }

    @Test
    public void textParseTest() {
        String[] patterns = {"she", "shr", "say", "he", "his", "her"};
        TreeMap<String, String> keyValues = new TreeMap<>();
        for (String pattern : patterns) {
            keyValues.put(pattern, pattern);
        }

        String text = "ahisher";
        AhoCorasickDoubleArrayTrie<String> ac = new AhoCorasickDoubleArrayTrie<String>(keyValues);
        List<AhoCorasickDoubleArrayTrie.Hit<String>> expected = ac.parseText(text);
        for (AhoCorasickDoubleArrayTrie.Hit<String> hit : expected) {
            System.out.println(String.format("%s=%s", text.substring(hit.getStart(), hit.getEnd()), hit.getValue()));
        }
        Assert.assertThat(4, is(expected.size()));
    }
}