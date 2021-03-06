package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoyerMooreTest extends BaseStringMatchTest {
    private static final Logger LOG = LoggerFactory.getLogger(BoyerMooreTest.class);

    @Test
    public void testSearchBySpecifiedString() {
        String t = "XYZZZwsWC2hQM";
        String p = "YZZZ";

        BoyerMoore bm = new BoyerMoore(p);
        int index = bm.search(t);
        assertThat(index, is(1));
    }

    @Test
    public void testSearch() {
        StringMatchCase[] cases = mockCases();
        for (StringMatchCase c : cases) {
            BoyerMoore bm = new BoyerMoore(c.getP());
            int index = bm.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }

    @Test
    public void testSearchByAuto() {
        for (Iterator<StringMatchCase> it = mockCasesByAuto(); it.hasNext(); ) {
            StringMatchCase c = it.next();
//            LOG.debug(c.toString());
            BoyerMoore bm = new BoyerMoore(c.getP());
            int index = bm.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }
}