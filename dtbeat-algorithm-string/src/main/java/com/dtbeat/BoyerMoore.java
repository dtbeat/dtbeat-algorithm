package com.dtbeat;

import java.util.Objects;

/**
 * Boyer-Moore String Match
 * <p>
 * Badest Time Complexity: O(N)
 * </p>
 *
 * @author elvinshang
 * @version Id: BM.java, v0.0.1 2020/9/19 14:25 dtbeat.com $
 */
public class BoyerMoore {
    private static int R = 256;
    private final String pattern;
    private final int[] badChars;
    private final int[] suffix;

    public BoyerMoore(String pattern) {
        this.pattern = requireNonNullOrEmpty(pattern);
        this.badChars = createBadChars(pattern);
        this.suffix = createGoodSuffix(pattern);
    }

    public int length() {
        return pattern.length();
    }

    public int search(String s) {
        requireNonNullOrEmpty(s);

        final int M = pattern.length();
        final int N = s.length();
        int ret = -1;

        int skip = 1;
        for (int i = 0; i <= N - M; i += skip) {
            int j = M - 1;
            for (; j >= 0; j--) {
                if (pattern.charAt(j) != s.charAt(i + j)) {
                    skip = Math.max(1, shift(j, s.charAt(i + j)));
                    break;
                }
            }

            if (j == -1) {
                ret = i;
                break;
            }
        }

        return ret;
    }

    private int[] createBadChars(String s) {
        final int M = s.length();
        int[] bc = new int[R];

        for (int r = 0; r < R; r++) {
            bc[r] = -1;
        }

        for (int i = 0; i < M; i++) {
            bc[s.charAt(i)] = i;
        }

        return bc;
    }

    private int[] createGoodSuffix(String pattern) {
        final int M = pattern.length();
        int[] suffix = new int[M];
        int lastPrefix = -1;

        for (int i = 0; i < M; i++) {
            suffix[i] = -1;
        }

        for (int i = 0; i < M - 1; i++) {
            int j = i;
            int k = 0;
            while (j >= 0 && pattern.charAt(j) == pattern.charAt(M - 1 - k)) {
                suffix[++k] = j--;
            }

            if (j == -1) {
                lastPrefix = k;
            } else if (suffix[k] == -1 && lastPrefix != -1) {
                suffix[i + 1] = lastPrefix - 1;
            }
        }

        return suffix;
    }

    private int shift(int badCharIndex, char badChar) {
        if (this.badChars[badChar] == -1) {
            return badCharIndex + 1;
        }

        final int M = length();
        int x = badCharIndex - this.badChars[badChar];
        int y = badCharIndex < M - 1 ? (badCharIndex + 1 - this.suffix[M - 1 - badCharIndex]) : -1;

        return Math.max(x, y);
    }

    private String requireNonNullOrEmpty(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("s");
        }

        return s;
    }
}
