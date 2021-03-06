package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KMPTest extends BaseStringMatchTest {
    private static final Logger LOG = LoggerFactory.getLogger(KMPTest.class);

    @Test
    public void testSearch_abababca() {
        String parttern = "abababca";
        KMP kmp = new KMP(parttern);
        int index = kmp.search(parttern);
        assertThat(index, is(0));
    }

    @Test
    public void testSearch() {
        StringMatchCase[] cases = mockCases();
        for (StringMatchCase c : cases) {
            KMP kmp = new KMP(c.getP());
            int index = kmp.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }

    @Test
    public void testSearchByAuto() {
        for (Iterator<StringMatchCase> it = mockCasesByAuto(); it.hasNext(); ) {
            StringMatchCase c = it.next();
//            LOG.debug(c.toString());
            KMP kmp = new KMP(c.getP());
            int index = kmp.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }

    @Test
    public void testDfaSearch_ABABC() {
        final String pattern = "ABABC";
        KMP kmp = KMP.createDfa(pattern);
        int index = kmp.search(pattern);
        assertThat(index, is(0));
    }

    @Test
    public void testDfaSearch() {
        StringMatchCase[] cases = mockCases();
        for (StringMatchCase c : cases) {
            KMP kmp = KMP.createDfa(c.getP());
            int index = kmp.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }

    @Test
    public void testDfaSearchByAuto() {
        for (Iterator<StringMatchCase> it = mockCasesByAuto(); it.hasNext(); ) {
            StringMatchCase c = it.next();
//            LOG.debug(c.toString());
            KMP kmp = KMP.createDfa(c.getP());
            int index = kmp.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }
}