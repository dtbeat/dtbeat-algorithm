package com.dtbeat.sorting;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;

/**
 * QuickSort
 *
 * @author elvinshang
 * @version Id: QuickSort.java, v0.0.1 2020/9/5 15:11 dtbeat.com $
 */
public class QuickSort {
    /**
     * Sorts array by QuickSort
     *
     * @param arr
     * @param comparator
     * @param <T>
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        Queue<State> q = new ArrayDeque<>();
        T key = arr[arr.length - 1];
        int l = 0;
        int r = arr.length - 1;
        q.offer(new State(l, r));

        while (!q.isEmpty()) {
            State state = q.poll();
            int i = state.l, j = state.r;
            while (i < j) {
                while (i < j && comparator.compare(arr[i], key) < 0) {
                    i++;
                }

                if (i < j) {
                    arr[j] = arr[i];
                    j--;
                }

                while (i < j && comparator.compare(arr[j], key) >= 0) {
                    j--;
                }

                if (i < j) {
                    arr[i] = arr[j];
                    i++;
                }
            }

            arr[i] = key;

            if (l < i - 1) {
                q.offer(new State(l, i - 1));
            }

            if (i + 1 < r) {
                q.offer(new State(i + 1, r));
            }
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        Queue<State> q = new ArrayDeque<>();
        int l = 0;
        int r = arr.length - 1;
        int key = arr[r];
        q.offer(new State(l, r));

        while (!q.isEmpty()) {
            State state = q.poll();
            l = state.l;
            r = state.r;
            key = arr[r];

            int i = l;
            int j = r;
            while (i < j) {
                while (i < j && arr[i] < key) {
                    i++;
                }

                if (i < j) {
                    arr[j] = arr[i];
                    j--;
                }

                while (i < j && arr[j] >= key) {
                    j--;
                }

                if (i < j) {
                    arr[i] = arr[j];
                    i++;
                }
            }

            arr[i] = key;

            if (l < i - 1) {
                q.offer(new State(l, i - 1));
            }

            if (i + 1 < r) {
                q.offer(new State(i + 1, r));
            }
        }
    }

    private static class State {
        int r;
        int l;

        public State(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }
}
