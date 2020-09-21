package com.dtbeat;

import java.math.BigInteger;
import java.util.Random;

/**
 * RabinKarp
 *
 * @author elvinshang
 * @version Id: RabinKarp.java, v0.0.1 2020/9/21 22:34 dtbeat.com $
 */
public class RabinKarp {
    private static final int R = 256;

    private final long Q;
    private final String pattern;
    private final int M;
    private long RM;
    private final long p;

    public RabinKarp(String pattern) {
        this.pattern = requireNonNullOrEmpty(pattern);
        this.M = pattern.length();
        this.Q = longRandomPrime();
        this.p = hash(pattern, M);

        long rm = 1;
        for (int i = 0; i < M - 1; i++) {
            rm = (R * rm) % Q;
        }
        this.RM = rm;
    }

    public int search(String s) {
        requireNonNullOrEmpty(s);

        final int N = s.length();
        if (N < M) {
            return -1;
        }

        long t0 = hash(s, M);
        if (t0 == p && check(s, 0)) {
            return 0;
        }

        long pre = t0;
        for (int i = M; i < N; i++) {
            long ti = pre + Q - s.charAt(i - M) * RM % Q;
            ti = (ti * R + s.charAt(i)) % Q;
            if (ti == p && check(s, i - M + 1)) {
                return i - M + 1;
            }

            pre = ti;
        }

        return -1;
    }

    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    /**
     * Returns hash code by Honer Hash algorithm
     *
     * @param s the string to hash
     * @param M the length to hash
     * @return hash code
     */
    private long hash(String s, int M) {
        long h = 0;
        for (int i = 0; i < M; i++) {
            h = (R * h + s.charAt(i)) % Q;
        }

        return h;
    }

    private boolean check(String s, int i) {
        for (int j = 0; j < M; j++) {
            if (pattern.charAt(j) != s.charAt(i + j)) {
                return false;
            }
        }

        return true;
    }

    private String requireNonNullOrEmpty(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("s");
        }

        return s;
    }
}
