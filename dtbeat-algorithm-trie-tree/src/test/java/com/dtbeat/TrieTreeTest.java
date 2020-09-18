package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TrieTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(TrieTreeTest.class);

    @Test
    public void testPut() {
        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);

        assertEquals(tree.get("abcd"), true);
        assertEquals(tree.get("abc"), false);
        assertEquals(tree.get("abcde"), true);
        assertEquals(tree.get("abe"), false);
        assertEquals(tree.get("bcd"), true);
        assertEquals(tree.get("hijk"), false);
    }

    @Test
    public void testDelete() {
        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);

        assertEquals(tree.get("abcd"), true);
        assertEquals(tree.get("abc"), false);
        assertEquals(tree.get("abcde"), true);
        assertEquals(tree.get("abe"), false);
        assertEquals(tree.get("bcd"), true);
        assertEquals(tree.get("hijk"), false);

        tree.delete("abc");

        assertEquals(tree.get("abcd"), true);
        assertNull(tree.get("abc"));
        assertEquals(tree.get("abcde"), true);
        assertEquals(tree.get("abe"), false);
        assertEquals(tree.get("bcd"), true);
        assertEquals(tree.get("hijk"), false);
    }

    @Test
    public void testGet() {
        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);

        assertEquals(tree.get("abcd"), true);
        assertEquals(tree.get("abc"), false);
        assertEquals(tree.get("abcde"), true);
        assertEquals(tree.get("abe"), false);
        assertEquals(tree.get("bcd"), true);
        assertEquals(tree.get("hijk"), false);

        tree.delete("abc");

        assertEquals(tree.get("abcd"), true);
        assertNull(tree.get("abc"));
        assertEquals(tree.get("abcde"), true);
        assertEquals(tree.get("abe"), false);
        assertEquals(tree.get("bcd"), true);
        assertEquals(tree.get("hijk"), false);
    }

    @Test
    public void testKeys() {
        Set<String> origin = new HashSet<>();
        origin.add("abcd");
        origin.add("abc");
        origin.add("abcde");
        origin.add("abe");
        origin.add("bcd");
        origin.add("hijk");

        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);

        Iterable<String> keys = tree.keys();
        for (String key : keys) {
            assertThat(origin.contains(key), is(true));
            origin.remove(key);
        }

        assertThat(true, is(origin.isEmpty()));
    }

    @Test
    public void testKeysWithPrefix() {
        Set<String> origin = new HashSet<>();
        origin.add("abcd");
        origin.add("abc");
        origin.add("abcde");
        origin.add("abe");

        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);

        Iterable<String> keys = tree.keysWithPrefix("a");
        for (String key : keys) {
            assertThat(origin.contains(key), is(true));
            origin.remove(key);
        }

        assertThat(true, is(origin.isEmpty()));
    }

    @Test
    public void testKeysThatMatch() {
        Set<String> origin = new HashSet<>();
        origin.add("abe");
        origin.add("bce");

        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);
        tree.put("bce", false);

        Iterable<String> keys = tree.keysThatMatch("..e");
        for (String key : keys) {
            assertThat(origin.contains(key), is(true));
            origin.remove(key);
        }

        assertThat(true, is(origin.isEmpty()));
    }

    @Test
    public void testLongestPrefixOf() {
        TrieTree<Boolean> tree = new TrieTree<>();
        tree.put("abcd", true);
        tree.put("abc", false);
        tree.put("abcde", true);
        tree.put("abe", false);
        tree.put("bcd", true);
        tree.put("hijk", false);
        tree.put("bce", false);

        String res = tree.longestPrefixOf("bcejd");
        assertThat("bce", is(res));
    }
}