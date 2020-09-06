package com.dtbeat.sorting;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Counction Sort
 *
 * @author elvinshang
 * @version Id: BinSort.java, v0.0.1 2020/9/5 23:01 dtbeat.com $
 */
public class CountingSort {
    /**
     * Sorts array by CountingSort algorithm
     *
     * @param arr the array
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        OptionalInt optional =  Arrays.stream(arr).min();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("arr");
        }
        int min = optional.getAsInt();

        optional =  Arrays.stream(arr).max();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("arr");
        }
        int max = optional.getAsInt();

        int[] c = new int[max - min + 1];
         for (int i = 0; i < c.length; i++) {
            c[i] = 0;
        }

        for (int i = 0; i < arr.length; i++) {
            c[arr[i] - min]++;
        }

        int k = 0;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i]; j++) {
                arr[k++] = i + min;
            }
        }
    }
}
