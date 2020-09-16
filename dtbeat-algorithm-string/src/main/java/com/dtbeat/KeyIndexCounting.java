package com.dtbeat;

import java.util.Map;

/**
 * KeyIndexCounting
 *
 * @author elvinshang
 * @version Id: KeyIndexCounting.java, v0.0.1 2020/9/15 21:19 dtbeat.com $
 */
public class KeyIndexCounting {
    /**
     * Sorts the entries
     *
     * @param entries the entries to be sorted
     * @param <K>     the key class type
     * @param <V>     the value class type
     */
    public static void sort(Entry[] entries, int R) {
        final int N = entries.length;
        Entry[] aux = new Entry[N];
        int[] counts = new int[R + 1];

        // frequency
        for (int i = 0; i < N; i++) {
            Entry entry = entries[i];
            counts[entry.getKey() + 1]++;
        }

        // frequency to indexes
        // start index for key
        for (int i = 0; i < R; i++) {
            counts[i + 1] += counts[i];
        }

        // class
        for (int i = 0; i < N; i++) {
            aux[counts[entries[i].getKey()]++] = entries[i];
        }

        // rewrite
        for (int i = 0; i < N; i++) {
            entries[i] = aux[i];
        }
    }

    /**
     * The Key-Value Entry
     */
    public static class Entry {
        private int key;
        private String value;

        public Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
