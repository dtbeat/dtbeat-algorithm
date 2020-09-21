package com.dtbeat;

/**
 * KMP
 *
 * @author elvinshang
 * @version Id: KMP.java, v0.0.1 2020/9/18 22:43 dtbeat.com $
 */
public class KMP {
    private static final int R = 256;
    private final String pattern;
    private final int[] nexts;

    public KMP(String pattern) {
        this.pattern = pattern;
        this.nexts = createNexts(pattern);
    }

    public int search(String s) {
        final int N = s.length();
        final int M = pattern.length();

        int skip = 1;
        for (int i = 0; i < N; i += skip) {
            int j = 0;
            for (; j < M; j++) {
                if (pattern.charAt(j) != s.charAt(i + j)) {
                    skip = Math.max(1, j - nexts[j]);
                    break;
                }
            }

            if (j == M) {
                return i;
            }
        }

        return -1;
    }

    private int[] createNexts(String pattern) {
        final int M = pattern.length();
        int[] nexts = new int[M];
        int j = 0;
        for (int i = 2; i < M; i++) {
            while (j != 0 && pattern.charAt(j) != pattern.charAt(i - 1)) {
                j = nexts[j];
            }

            if (pattern.charAt(j) == pattern.charAt(i - 1)) {
                j++;
            }

            nexts[i] = j;
        }

        return nexts;
    }
}
