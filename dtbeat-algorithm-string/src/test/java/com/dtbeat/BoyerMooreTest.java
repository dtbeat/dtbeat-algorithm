package com.dtbeat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoyerMooreTest {
    private static final Logger LOG = LoggerFactory.getLogger(BoyerMooreTest.class);
    private static final String table = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

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
            BoyerMoore bm = new BoyerMoore(c.p);
            int index = bm.search(c.s);
            assertThat(index, is(c.index));
        }
    }

    @Test
    public void testSearchByAuto() {
        for (Iterator<StringMatchCase> it = mockCasesByAuto(); it.hasNext(); ) {
            StringMatchCase c = it.next();
//            LOG.debug(c.toString());
            BoyerMoore bm = new BoyerMoore(c.p);
            int index = bm.search(c.s);
            assertThat(index, is(c.index));
        }
    }

    public Iterator<StringMatchCase> mockCasesByAuto() {
        return new Iterator<StringMatchCase>() {
            final int MAX = 1000000;
            Random rnd = new Random(System.currentTimeMillis());
            int current = 0;

            @Override
            public boolean hasNext() {
                return current < MAX;
            }

            @Override
            public StringMatchCase next() {
                int m = rnd.nextInt(50);
                while (m < 1) {
                    m = rnd.nextInt(50);
                }
                int n = rnd.nextInt(500);
                while (n <= m) {
                    n = rnd.nextInt(500);
                }
                int index = rnd.nextInt(n - m);

                StringBuilder writer = new StringBuilder();
                for (int i = 0; i < m; i++) {
                    writer.append(table.charAt(rnd.nextInt(62)));
                }
                String p = writer.toString();

                writer = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    if (i >= index && i < index + m) {
                        writer.append(p.charAt(i - index));
                    } else {
                        writer.append(table.charAt(rnd.nextInt(62)));
                    }
                }
                String t = writer.toString();
                current++;
                return new StringMatchCase(t, p);
            }
        };
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

        @Override
        public String toString() {
            return "StringMatchCase{" +
                    "s='" + s + '\'' +
                    ", p='" + p + '\'' +
                    ", index=" + index +
                    '}';
        }
    }
}