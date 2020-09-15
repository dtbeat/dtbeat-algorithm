package com.dtbeat.suffixtree;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuffixTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(SuffixTreeTest.class);

    @Test
    public void testSimpleSuffixTree() {
        Word word = new Word("abcabd");
        SuffixTree<Character, Word> tree = new SuffixTree<>(word);
        LOG.debug(tree.toString());
    }
}