package com.dtbeat.sorting;

import java.util.Comparator;

/**
 * ShellSort
 *
 * @author elvinshang
 * @version Id: ShellSort.java, v0.0.1 2020/9/4 22:01 dtbeat.com $
 */
public class ShellSort {
    /**
     * Sorts array by Shell Sort algorithm
     *
     * @param arr        the array
     * @param comparator the comparator
     * @param <T>        the type of element class
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int increment = arr.length / 2;
        while (increment > 0) {
            for (int k = 0; k < increment; k++) {
                for (int i = k + increment; i < arr.length; i += increment) {
                    for (int j = i; j > k; j -= increment) {
                        if (comparator.compare(arr[j], arr[j - increment]) < 0) {
                            T t = arr[j - increment];
                            arr[j - increment] = arr[j];
                            arr[j] = t;
                        } else {
                            break;
                        }
                    }
                }
            }

            increment = increment / 2;
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int increment = arr.length / 2;
        while (increment > 0) {
            for (int k = 0; k < increment; k++) {
                for (int i = k + increment; i < arr.length; i += increment) {
                    for (int j = i; j > k; j -= increment) {
                        if (arr[j] < arr[j - increment]) {
                            int t = arr[j - increment];
                            arr[j - increment] = arr[j];
                            arr[j] = t;
                        } else {
                            break;
                        }
                    }
                }
            }

            increment = increment / 2;
        }
    }
}
