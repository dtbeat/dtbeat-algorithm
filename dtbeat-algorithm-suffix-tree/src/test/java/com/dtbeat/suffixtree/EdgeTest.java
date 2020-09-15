package com.dtbeat.suffixtree;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class EdgeTest {
    private static final Logger LOG = LoggerFactory.getLogger(Edge.class);

    @Test
    public void testEdge() {
        String str = "abcabd";
        Word word = new Word(str);
        Sequence<Character, Word> sequence = new Sequence<>(word);
        SequenceCursor<Character, Word> cursor = new SequenceCursor<>(sequence);

        Edge<Character, Word> edge = new Edge<>(0, null, cursor);
        edge.setEnd(word.size());

        int i = 0;
        for (Character c : edge) {
            assertEquals(str.charAt(i++), c.charValue());
        }
    }

    @Test
    public void testEdgeWithoutEnd() {
        String str = "abcabd";
        Word word = new Word(str);
        Sequence<Character, Word> sequence = new Sequence<>(word);
        SequenceCursor<Character, Word> cursor = new SequenceCursor<>(sequence);

        Edge<Character, Word> edge = new Edge<>(0, null, cursor);

        while (cursor.hasNext()) {
            cursor.next();

        }

        int i = 0;
        for (Character c : edge) {
            LOG.debug(c.toString());
            assertEquals(str.charAt(i++), c.charValue());
        }

    }
}