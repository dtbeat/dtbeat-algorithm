package com.dtbeat.sorting;

import java.util.Comparator;

/**
 * Selection Sorting
 * <p>
 * time complexity : O(n^2)
 * space complexity: O(1)
 * </p>
 *
 * @author elvinshang
 * @version Id: SelectionSort.java, v0.0.1 2020/9/4 21:13 dtbeat.com $
 */
public class SelectionSort {
    /**
     * Sorts array by SelectionSort algorithm
     *
     * @param arr        the array
     * @param comparator the comparator
     * @param <T>        the type of element class
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[min], arr[j]) < 0) {
                    min = j;
                }
            }

            if (min != i) {
                T t = arr[min];
                arr[min] = arr[i];
                arr[i] = t;
            }
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }

            if (min != i) {
                int t = arr[min];
                arr[min] = arr[i];
                arr[i] = t;
            }
        }
    }
}
