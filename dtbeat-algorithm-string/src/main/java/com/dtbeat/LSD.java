package com.dtbeat;

/**
 * LSD
 *
 * @author elvinshang
 * @version Id: LSD.java, v0.0.1 2020/9/16 09:27 dtbeat.com $
 */
public class LSD {
    public static void sort(String[] words, int w) {
        final int R = 256;
        final int N = words.length;
        String[] aux = new String[N];

        for (int d = w - 1; d >= 0; d--) {
            int[] counts = new int[R + 1];

            // frequency
            for (int i = 0; i < N; i++) {
                counts[words[i].charAt(d) + 1]++;
            }

            // frequency to index
            for (int r = 0; r < R; r++) {
                counts[r + 1] += counts[r];
            }

            // class
            for (int i = 0; i < N; i++) {
                aux[counts[words[i].charAt(d)]++] = words[i];
            }

            // rewrite
            for (int i = 0; i < N; i++) {
                words[i] = aux[i];
            }
        }
    }
}
