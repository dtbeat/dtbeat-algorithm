package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TireTreeTest {
    private static final Logger LOG = LoggerFactory.getLogger(TireTreeTest.class);

    @Test
    public void testInsert() {
        TireTree tree = new TireTree();
        tree.insert("abcd");
        tree.insert("abc");
        tree.insert("abcde");
        tree.insert("abe");
        tree.insert("bcd");
        tree.insert("hijk");

        if (LOG.isDebugEnabled()) {
            LOG.debug(tree.render());
        }

        assertEquals("[TireTree [a:false [b:false [e:true] [c:true [d:true [e:true]]]]] [b:false [c:false [d:true]]] [h:false [i:false [j:false [k:true]]]]]", tree.render());
    }

    @Test
    public void testDelete() {
        TireTree tree = new TireTree();
        tree.insert("abcd");
        tree.insert("abc");
        tree.insert("abcde");
        tree.insert("abe");
        tree.insert("bcd");
        tree.insert("hijk");

        assertEquals("[TireTree [a:false [b:false [e:true] [c:true [d:true [e:true]]]]] [b:false [c:false [d:true]]] [h:false [i:false [j:false [k:true]]]]]", tree.render());

        tree.delete("abcd");
        assertEquals("[TireTree [a:false [b:false [e:true] [c:true [d:false [e:true]]]]] [b:false [c:false [d:true]]] [h:false [i:false [j:false [k:true]]]]]", tree.render());

        tree.delete("abcde");
        assertEquals("[TireTree [a:false [b:false [e:true] [c:true]]] [b:false [c:false [d:true]]] [h:false [i:false [j:false [k:true]]]]]", tree.render());

        tree.delete("bcd");
        assertEquals("[TireTree [a:false [b:false [e:true] [c:true]]] [h:false [i:false [j:false [k:true]]]]]", tree.render());
    }

    @Test
    public void testFind() {
        TireTree tree = new TireTree();
        tree.insert("abcd");
        tree.insert("abc");
        tree.insert("abcde");
        tree.insert("abe");
        tree.insert("bcd");
        tree.insert("hijk");

        assertEquals("[TireTree [a:false [b:false [e:true] [c:true [d:true [e:true]]]]] [b:false [c:false [d:true]]] [h:false [i:false [j:false [k:true]]]]]", tree.render());

        boolean actual = tree.find("abcd");
        assertEquals(true, actual);

        actual = tree.find("bcd");
        assertEquals(true, actual);

        actual = tree.find("cf");
        assertEquals(false, actual);
    }
}