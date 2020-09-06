package com.dtbeat.sorting;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Stack;

/**
 * MergeSort
 *
 * @author elvinshang
 * @version Id: MergeSort.java, v0.0.1 2020/9/5 19:22 dtbeat.com $
 */
public class MergeSort {
    /**
     * Sorts the array by MergeSort algorithm
     *
     * @param arr        the array to sort
     * @param comparator the comparator
     * @param <T>        the type of element class
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        Stack<State> s = new Stack<>();
        int mid = arr.length / 2;
        s.push(new State(0, mid - 1, mid, arr.length - 1));
        T[] copy = (T[]) Array.newInstance(arr[0].getClass(), arr.length);
        HashSet<String> marked = new HashSet<>();

        while (!s.isEmpty()) {
            State state = s.peek();
            if (marked.contains(state.leftKey()) && marked.contains(state.rightKey())) {
                s.pop();
                merge(arr, copy, state.lStart, state.lEnd, state.rStart, state.rEnd, comparator);
                marked.remove(state.leftKey());
                marked.remove(state.rightKey());
                marked.add(state.key());
            } else if (!marked.contains(state.leftKey()) && state.lStart < state.lEnd) {
                mid = (state.lEnd - state.lStart + 1) / 2;
                s.push(new State(state.lStart, state.lStart + mid - 1, state.lStart + mid, state.lEnd));
            } else {
                marked.add(state.leftKey());
                if (state.rStart < state.rEnd) {
                    mid = (state.rEnd - state.rStart + 1) / 2;
                    s.push(new State(state.rStart, state.rStart + mid - 1, state.rStart + mid, state.rEnd));
                } else {
                    s.pop();
                    merge(arr, copy, state.lStart, state.lEnd, state.rStart, state.rEnd, comparator);
                    marked.add(state.key());
                    marked.remove(state.leftKey());
                }
            }
        }
    }

    private static <T> void merge(T[] arr, T[] copy, int lStart, int lEnd, int rStart, int rEnd, Comparator<T> comparator) {
        int index = lStart;
        int k = 0;
        while (lStart <= lEnd && rStart <= rEnd) {
            if (comparator.compare(arr[lStart], arr[rStart]) < 0) {
                copy[k++] = arr[lStart++];
            } else {
                copy[k++] = arr[rStart++];
            }
        }

        while (lStart <= lEnd) {
            copy[k++] = arr[lStart++];
        }

        while (rStart <= rEnd) {
            copy[k++] = arr[rStart++];
        }

        for (int i = 0; i < k; i++) {
            arr[index++] = copy[i];
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        Stack<State> s = new Stack<>();
        int mid = arr.length / 2;
        s.push(new State(0, mid - 1, mid, arr.length - 1));
        int[] copy = new int[arr.length];
        HashSet<String> marked = new HashSet<>();

        while (!s.isEmpty()) {
            State state = s.peek();
            if (marked.contains(state.leftKey()) && marked.contains(state.rightKey())) {
                s.pop();
                merge(arr, copy, state.lStart, state.lEnd, state.rStart, state.rEnd);
                marked.remove(state.leftKey());
                marked.remove(state.rightKey());
                marked.add(state.key());
            } else if (!marked.contains(state.leftKey()) && state.lStart < state.lEnd) {
                mid = (state.lEnd - state.lStart + 1) / 2;
                s.push(new State(state.lStart, state.lStart + mid - 1, state.lStart + mid, state.lEnd));
            } else {
                marked.add(state.leftKey());
                if (state.rStart < state.rEnd) {
                    mid = (state.rEnd - state.rStart + 1) / 2;
                    s.push(new State(state.rStart, state.rStart + mid - 1, state.rStart + mid, state.rEnd));
                } else {
                    s.pop();
                    merge(arr, copy, state.lStart, state.lEnd, state.rStart, state.rEnd);
                    marked.add(state.key());
                    marked.remove(state.leftKey());
                }
            }
        }
    }

    private static void merge(int[] arr, int[] copy, int lStart, int lEnd, int rStart, int rEnd) {
        int index = lStart;
        int k = 0;
        while (lStart <= lEnd && rStart <= rEnd) {
            if (arr[lStart] < arr[rStart]) {
                copy[k++] = arr[lStart++];
            } else {
                copy[k++] = arr[rStart++];
            }
        }

        while (lStart <= lEnd) {
            copy[k++] = arr[lStart++];
        }

        while (rStart <= rEnd) {
            copy[k++] = arr[rStart++];
        }

        for (int i = 0; i < k; i++) {
            arr[index++] = copy[i];
        }
    }


    private static class State {
        int lStart;
        int lEnd;
        int rStart;
        int rEnd;

        public State(int lStart, int lEnd, int rStart, int rEnd) {
            this.lStart = lStart;
            this.lEnd = lEnd;
            this.rStart = rStart;
            this.rEnd = rEnd;
        }

        String leftKey() {
            return lStart + "_" + lEnd;
        }

        String rightKey() {
            return rStart + "_" + rEnd;
        }

        String key() {
            return lStart + "_" + rEnd;
        }

        @Override
        public String toString() {
            return "State{" +
                    "lStart=" + lStart +
                    ", lEnd=" + lEnd +
                    ", rStart=" + rStart +
                    ", rEnd=" + rEnd +
                    '}';
        }
    }
}
