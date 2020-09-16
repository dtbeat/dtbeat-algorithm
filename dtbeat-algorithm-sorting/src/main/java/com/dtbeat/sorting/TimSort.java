package com.dtbeat.sorting;

import com.dtbeat.algorithm.lang.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * TimSort
 *
 * @author elvinshang
 * @version Id: TimSort.java, v0.0.1 2020/9/16 15:47 dtbeat.com $
 */
public class TimSort {
    private static final int DEFAULT_MIN_RUN = 4;

    public static void sort(int[] arr) {
        sort(arr, DEFAULT_MIN_RUN);
    }

    public static void sort(int[] arr, int minRun) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        if (minRun < DEFAULT_MIN_RUN) {
            minRun = DEFAULT_MIN_RUN;
        }

        List<Tuple<Integer, Integer>> runs = new ArrayList<>();
        List<Tuple<Integer, Integer>> sortedRun = new ArrayList<>();
        final int N = arr.length;

        Tuple<Integer, Integer> run = new Tuple<>(0, 0);
        boolean asc = true;

        for (int i = 1; i < N; i++) {
            if (run == null) {
                run = new Tuple<>(i, i);
                continue;
            }

            if (arr[run.getT2().intValue()] <= arr[i]) {
                run.setT2(i);
            } else {
                asc = false;
                run.setT2(i);
            }

            if (run.getT2() - run.getT1() + 1 == minRun) {
                if (!asc) {
                    BinarySort.sort(arr, run.getT1(), run.getT2());
                }

                asc = true;
                sortedRun.add(run);
                run = null;
            }
        }

        if (run != null) {
            if (!asc) {
                BinarySort.sort(arr, run.getT1(), run.getT2());
            }
            sortedRun.add(run);
        }


        merge(arr, sortedRun);
    }

    private static void merge(int[] arr, List<Tuple<Integer, Integer>> sortedRuns) {
        if (sortedRuns.size() == 1) {
            return;
        }

        for (int i = sortedRuns.size() - 1; i > 0; i--) {
            int a = i - 2 >= 0 ? getLength(sortedRuns.get(i - 2)) : -1;
            int b = getLength(sortedRuns.get(i - 1));
            int c = getLength(sortedRuns.get(i));

            Tuple<Integer, Integer> runB = sortedRuns.get(i - 1);
            Tuple<Integer, Integer> runC = sortedRuns.get(i);
            if (i - 2 >= 0 && a <= b + c) {
                if (a < c) {
                    Tuple<Integer, Integer> runA = sortedRuns.get(i - 2);
                    merge(arr, runA.getT1(), runA.getT2(), runB.getT1(), runB.getT2());
                    runA.setT2(runB.getT2());
                    runB.setT1(runC.getT1());
                    runB.setT2(runC.getT2());
                } else {
                    merge(arr, runB.getT1(), runB.getT2(), runC.getT1(), runC.getT2());
                    runB.setT2(runC.getT2());
                }
            } else if (b <= c){
                merge(arr, runB.getT1(), runB.getT2(), runC.getT1(), runC.getT2());
                runB.setT2(runC.getT2());
            } else {
                merge(arr, i, sortedRuns);
                break;
            }
        }
    }

    private static void merge(int[] arr, int end, List<Tuple<Integer, Integer>> sortedRuns) {
        if (end == 0) {
            return;
        }

        for (int i = end; i > 0; i--) {
            int a = i - 2 >= 0 ? getLength(sortedRuns.get(i - 2)) : -1;
            int b = getLength(sortedRuns.get(i - 1));
            int c = getLength(sortedRuns.get(i));

            Tuple<Integer, Integer> runB = sortedRuns.get(i - 1);
            Tuple<Integer, Integer> runC = sortedRuns.get(i);
            if (i - 2 >= 0 && a < c) {
                Tuple<Integer, Integer> runA = sortedRuns.get(i - 2);
                merge(arr, runA.getT1(), runA.getT2(), runB.getT1(), runB.getT2());
                runA.setT2(runB.getT2());
                runB.setT1(runC.getT1());
                runB.setT2(runC.getT2());
            } else {
                merge(arr, runB.getT1(), runB.getT2(), runC.getT1(), runC.getT2());
                runB.setT2(runC.getT2());
            }
        }
    }

    private static int getLength(Tuple<Integer, Integer> run) {
        return run.getT2() - run.getT1() + 1;
    }

    private static void merge(int[] arr, int lStart, int lEnd, int rStart, int rEnd) {
        int ls = lStart;
        int le = lEnd;
        int rs = rStart;
        int re = rEnd;

        int[] copy = new int[lEnd - lStart + 1 + rEnd - rStart + 1];
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
            arr[index + i] = copy[i];
        }
    }
}
