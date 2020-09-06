package com.dtbeat.sorting;

import java.util.Comparator;

/**
 * Insertion Sort
 * <p>
 * time complexity : O(n^2)
 * space complexity: O(1)
 * </p>
 *
 * @author elvinshang
 * @version Id: InsertionSort.java, v0.0.1 2020/9/4 21:39 dtbeat.com $
 */
public class InsertionSort {
    /**
     * Sorts array by Insertion Sort algorithm
     *
     * @param arr        the array
     * @param comparator the comparator
     * @param <T>        the type of element class
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                } else {
                    break;
                }
            }
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                } else {
                    break;
                }
            }
        }
    }
}
