package com.dtbeat;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoyerMooreTest {
    @Test
    public void testSearch() {
        StringMatchCase[] cases = mockCases();
        for (StringMatchCase c : cases) {
            BoyerMoore bm = new BoyerMoore(c.p);
            int index = bm.search(c.s);
            assertThat(c.index, is(index));
        }
    }

    public StringMatchCase[] mockCases() {
        StringMatchCase[] cases = {
                new StringMatchCase(" simple example", "example"),
                new StringMatchCase("BEBCDAB BABCDAB CDECDAB", "BABCDAB"),
                new StringMatchCase("ABAAAABAAAAAAAAA", "BAAAAA")
        };

        return cases;
    }

    private class StringMatchCase {
        private String s;
        private String p;
        private int index;

        public StringMatchCase(String s, String p) {
            this.s = s;
            this.p = p;
            this.index = s.indexOf(p);
        }
    }
}