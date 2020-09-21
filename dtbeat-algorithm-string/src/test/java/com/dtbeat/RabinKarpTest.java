package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RabinKarpTest extends BaseStringMatchTest {
    private static final Logger LOG = LoggerFactory.getLogger(RabinKarpTest.class);

    @Test
    public void testSearch() {
        StringMatchCase[] cases = mockCases();
        for (StringMatchCase c : cases) {
            RabinKarp rk = new RabinKarp(c.getP());
            int index = rk.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }

    @Test
    public void testSearchByAuto() {
        for (Iterator<StringMatchCase> it = mockCasesByAuto(); it.hasNext(); ) {
            StringMatchCase c = it.next();
//            LOG.debug(c.toString());
            RabinKarp rk = new RabinKarp(c.getP());
            int index = rk.search(c.getS());
            assertThat(index, is(c.getIndex()));
        }
    }
}