package com.dtbeat;

/**
 * Quick3Sort
 *
 * @author elvinshang
 * @version Id: Quick3Sort.java, v0.0.1 2020/9/17 10:00 dtbeat.com $
 */
public class Quick3Sort {
    private static int charAt(String s, int d) {
        return d < s.length() ? s.charAt(d) : -1;
    }

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void sort(String[] a, int lo, int hi) {
        sort(a, lo, hi, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (lo >= hi) {
            return;
        }

        int l = lo;
        int g = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= g) {
            int t = charAt(a[i], d);
            if (t < v) {
                exch(a, l++, i++);
            } else if (t > v) {
                exch(a, i, g--);
            } else {
                i++;
            }
        }

        sort(a, lo, l - 1, d);
        if (v > 0) {
            sort(a, l, g, d + 1);
        }
        sort(a, g + 1, hi, d);
    }

    private static void exch(String[] arr, int a, int b) {
        String tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
