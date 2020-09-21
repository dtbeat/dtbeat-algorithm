package com.dtbeat;

import java.util.Iterator;
import java.util.Random;

/**
 * BaseStringMatchTest
 *
 * @author elvinshang
 * @version Id: BaseStringMatchTest.java, v0.0.1 2020/9/21 01:06 dtbeat.com $
 */
public class BaseStringMatchTest {
    private static final String table = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    protected Iterator<StringMatchCase> mockCasesByAuto() {
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

    protected StringMatchCase[] mockCases() {
        StringMatchCase[] cases = {
                new StringMatchCase(" simple example", "example"),
                new StringMatchCase("BEBCDAB BABCDAB CDECDAB", "BABCDAB"),
                new StringMatchCase("ABAAAABAAAAAAAAA", "BAAAAA")
        };

        return cases;
    }
}
