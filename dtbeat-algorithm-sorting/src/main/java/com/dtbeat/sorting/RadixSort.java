package com.dtbeat.sorting;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * RadixSort
 *
 * @author elvinshang
 * @version Id: RadixSort.java, v0.0.1 2020/9/5 23:36 dtbeat.com $
 */
public class RadixSort {
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Sorts array by RadixSort algorithm
     *
     * @param arr the array
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        final int N = arr.length;
        final int R = 10;
        int[] aux = new int[N];

        int div = 1;
        while (true) {
            int[] counts = new int[R + 1];
            boolean flag = false;

            // frequency
            for (int i = 0; i < N; i++) {
                int r = (arr[i] / div);
                int b = (r % 10);
                if(r > 0) {
                    flag = true;
                }

                counts[b + 1]++;
            }

            if (!flag) {
                break;
            }

            // frequency to index
            for (int i = 0; i < R; i++) {
                counts[i + 1] += counts[i];
            }

            // class
            for (int i = 0; i < N; i++) {
                int b = (arr[i] / div % 10);
                aux[counts[b]++] = arr[i];
            }

            // rewrite
            for (int i = 0; i < N; i++) {
                arr[i] = aux[i];
            }

            div *= 10;
        }
    }
}
