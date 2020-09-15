package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TrieTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(TrieTreeTest.class);

    @Test
    public void testInsert() {
        TrieTree tree = new TrieTree();
        tree.insert("abcd");
        tree.insert("abc");
        tree.insert("abcde");
        tree.insert("abe");
        tree.insert("bcd");
        tree.insert("hijk");

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.render());
        }

        assertEquals("[TireTree [h:false [i:false [j:false [k:true]]]] [b:false [c:false [d:true]]] [a:false [b:false [e:true] [c:true [d:true [e:true]]]]]]", tree.render());
    }

    @Test
    public void testDelete() {
        TrieTree tree = new TrieTree();
        tree.insert("abcd");
        tree.insert("abc");
        tree.insert("abcde");
        tree.insert("abe");
        tree.insert("bcd");
        tree.insert("hijk");

        assertEquals("[TireTree [h:false [i:false [j:false [k:true]]]] [b:false [c:false [d:true]]] [a:false [b:false [e:true] [c:true [d:true [e:true]]]]]]", tree.render());

        tree.delete("abcd");
        assertEquals("[TireTree [h:false [i:false [j:false [k:true]]]] [b:false [c:false [d:true]]] [a:false [b:false [e:true] [c:true [d:false [e:true]]]]]]", tree.render());

        tree.delete("abcde");
        assertEquals("[TireTree [h:false [i:false [j:false [k:true]]]] [b:false [c:false [d:true]]] [a:false [b:false [e:true] [c:true]]]]", tree.render());

        tree.delete("bcd");
        assertEquals("[TireTree [h:false [i:false [j:false [k:true]]]] [a:false [b:false [e:true] [c:true]]]]", tree.render());
    }

    @Test
    public void testFind() {
        TrieTree tree = new TrieTree();
        tree.insert("abcd");
        tree.insert("abc");
        tree.insert("abcde");
        tree.insert("abe");
        tree.insert("bcd");
        tree.insert("hijk");

        assertEquals("[TireTree [h:false [i:false [j:false [k:true]]]] [b:false [c:false [d:true]]] [a:false [b:false [e:true] [c:true [d:true [e:true]]]]]]", tree.render());

        boolean actual = tree.find("abcd");
        assertEquals(true, actual);

        actual = tree.find("bcd");
        assertEquals(true, actual);

        actual = tree.find("cf");
        assertEquals(false, actual);
    }
}