package com.dtbeat.sorting;

/**
 * BinarySort
 *
 * @author elvinshang
 * @version Id: BinarySort.java, v0.0.1 2020/9/16 16:51 dtbeat.com $
 */
public class BinarySort {
    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int value = arr[i];
            int pos = binarySearch(arr, value, start, i - 1);
            for (int j = i; j > pos; j--) {
                int tmp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = tmp;
            }
        }
    }

    private static int binarySearch(int[] arr, int value, int start, int end) {
        if (start == end) {
            return arr[start] > value ? start : start + 1;
        } else if (start > end) {
            return start;
        }

        int mid = (start + end) / 2;
        if (arr[mid] < value) {
            return binarySearch(arr, value, mid + 1, end);
        } else if (arr[mid] > value) {
            return binarySearch(arr, value, start, mid - 1);
        } else {
            return mid;
        }
    }
}
