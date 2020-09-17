package com.dtbeat;

/**
 * MSD
 *
 * @author elvinshang
 * @version Id: MSD.java, v0.0.1 2020/9/17 08:47 dtbeat.com $
 */
public class MSD {
    private static final int R = 256;
    private static final int M = 1;

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void sort(String[] a, int lo, int hi) {
        if (a == null || a.length <= 1) {
            return;
        }

        final int N = a.length;
        final String[] aux = new String[N];
        sort0(a, aux, lo, hi, 0);
    }

    private static void sort0(String[] a, String[] aux, int lo, int hi, int d) {
        if(lo >= hi) {
            return;
        }

        if (lo <= hi + M) {
            insertionSort(a, lo, hi, d);
            return;
        }

        int[] counts = new int[R + 2];

        // frequency
        for (int i = lo; i <= hi; i++) {
            counts[charAt(a[i], d) + 2]++;
        }

        // frequency to index
        for (int r = 0; r < R + 1; r++) {
            counts[r + 1] += counts[r];
        }

        // data class
        for (int i = lo; i <= hi; i++) {
            aux[counts[charAt(a[i], d) + 1]++] = a[i];
        }

        // rewrite
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        for (int r = 0; r < R; r++) {
            sort0(a, aux, lo + counts[r], lo + counts[r + 1] - 1, d + 1);
        }
    }

    private static int charAt(String s, int d) {
        return d < s.length() ? s.charAt(d) : -1;
    }

    public static void insertionSort(String[] arr, int lo, int hi, int d) {
        if (lo >= hi) {
            return;
        }

        for (int i = lo; i < hi; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (subString(arr[j], d).compareTo(subString(arr[j - 1], d)) < 0) {
                    String t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                } else {
                    break;
                }
            }
        }
    }

    private static String subString(String s, int d) {
        return d < s.length() ? s.substring(d) : "";
    }
}
