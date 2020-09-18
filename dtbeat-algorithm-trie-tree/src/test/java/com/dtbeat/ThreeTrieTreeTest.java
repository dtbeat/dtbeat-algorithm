package com.dtbeat;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreeTrieTreeTest {
    @Test
    public void testPut() {
        ThreeTrieTree<Boolean> tree = new ThreeTrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);

        assertNull(tree.get("ab"));
        assertEquals(tree.get("abcd"), true);
        assertEquals(tree.get("abc"), false);
        assertEquals(tree.get("abcde"), true);
        assertEquals(tree.get("abe"), false);
        assertEquals(tree.get("bcd"), true);
        assertEquals(tree.get("hijk"), false);
    }
}