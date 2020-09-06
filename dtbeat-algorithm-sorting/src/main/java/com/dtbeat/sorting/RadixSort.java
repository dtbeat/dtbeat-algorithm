package com.dtbeat.sorting;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * RadixSort
 *
 * @author elvinshang
 * @version Id: RadixSort.java, v0.0.1 2020/9/5 23:36 dtbeat.com $
 */
public class RadixSort {
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Sorts array by RadixSort algorithm
     *
     * @param arr the array
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        OptionalInt min = Arrays.stream(arr).min();
        if (!min.isPresent()) {
            throw new IllegalArgumentException("arr");
        }
        int compensation = min.getAsInt() < 0 ? -min.getAsInt() : 0;

        State[] r = new State[10];
        int c = 1;
        boolean flag = false;
        while (!flag) {
            flag = true;
            for (int i = 0; i < arr.length; i++) {
                int v = arr[i] + compensation;
                int divisor = v / c;
                int remainder = divisor % 10;
                flag = flag && (divisor == 0 || divisor / 10 == 0);

                State s = r[remainder];
                if (s == null) {
                    r[remainder] = new State(v, null);
                } else {
                    while (s.next != null) {
                        s = s.next;
                    }

                    s.next = new State(v, null);
                }
            }

            int k = 0;
            for (int i = 0; i < r.length; i++) {
                State state = r[i];
                while (state != null) {
                    arr[k++] = state.v - compensation;
                    state = state.next;
                }
                r[i] = null;
            }

            c = c * 10;
        }
    }

    private static class State {
        int v;
        State next;

        public State(int v, State next) {
            this.v = v;
            this.next = next;
        }
    }
}
