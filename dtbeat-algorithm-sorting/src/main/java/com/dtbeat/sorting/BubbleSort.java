package com.dtbeat.sorting;

import java.util.Comparator;

/**
 * BubbleSort
 * <p>
 * time complexity : O(n^2)
 * space complexity: O(1)
 * </p>
 *
 * @author elvinshang
 * @version Id: BubbleSort.java, v0.0.1 2020/9/4 20:39 dtbeat.com $
 */
public class BubbleSort {
    /**
     * Sorts the array by BubbleSort algorithm
     *
     * @param arr        the array to sort
     * @param comparator the comparator
     * @param <T>        the type of element class
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            boolean flag = false;
            for (int j = arr.length - 1; j > i; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                    flag = true;
                }
            }

            // no swap operation occurred
            if (!flag) {
                break;
            }
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            boolean flag = false;
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    int t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                    flag = true;
                }
            }

            // no swap operation occurred
            if (!flag) {
                break;
            }
        }
    }
}
