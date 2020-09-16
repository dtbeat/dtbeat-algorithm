package com.dtbeat.sorting;

import com.dtbeat.algorithm.lang.Utils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class SortingTest {
    private static final Logger LOG = LoggerFactory.getLogger(SortingTest.class);

    @Test
    public void testBubbleSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        BubbleSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testSelectionSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        SelectionSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testInsertionSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        InsertionSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testShellSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        ShellSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testQuickSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testQuickSortByRecursion() {
        int[] origin = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        QuickSort.sortByRecursion(arr);
        Arrays.sort(origin);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testQuickSortWithDualPivotByRecursion() {
        int[] origin = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        QuickSort.sortWithDualPivotByRecursion(arr);
        Arrays.sort(origin);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testMergeSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Ignore
    public void testMergeSortByDynamic() {
        Random rnd = new Random();
        for (int i = 0; i < 100000; i++) {
            int len = rnd.nextInt(1000);

            int[] arr = new int[len];
            int[] expected = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = rnd.nextInt(100);
                expected[j] = arr[j];
            }

            Arrays.sort(expected);
            MergeSort.sort(arr);
            assertArrayEquals(expected, arr);
        }
    }

    @Test
    public void testCountingSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        CountingSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Ignore
    public void testCountingSortByDynamic() {
        Random rnd = new Random();
        for (int i = 0; i < 100000; i++) {
            int len = rnd.nextInt(1000);

            int[] arr = new int[len];
            int[] expected = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = rnd.nextInt(100);
                expected[j] = arr[j];
            }

            Arrays.sort(expected);
            CountingSort.sort(arr);
            assertArrayEquals(expected, arr);
        }
    }

    @Test
    public void testRadixSort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        RadixSort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Ignore
    public void testRadixSortByDynamic() {
        Random rnd = new Random();
        for (int i = 0; i < 100000; i++) {
            int len = rnd.nextInt(1000);

            int[] arr = new int[len];
            int[] expected = new int[len];
            int[] o = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = rnd.nextInt(100);
                expected[j] = arr[j];
                o[j] = arr[j];
            }

            Arrays.sort(expected);
            RadixSort.sort(arr);
            assertArrayEquals(expected, arr);
        }
    }

    @Test
    public void testBinarySort() {
        int[] arr = new int[]{42, 20, 17, 13, 28, 14, 23, 15};
        BinarySort.sort(arr);
        assertArrayEquals(new int[]{13, 14, 15, 17, 20, 23, 28, 42}, arr);
    }

    @Test
    public void testTimSort() {
        int[] arr = new int[]{3,87,1,50,71,90};
        TimSort.sort(arr);
        assertArrayEquals(new int[]{1,3,50,71,87,90}, arr);
    }

    @Ignore
    public void testTimSortByDynamic() {
        Random rnd = new Random();
        for (int i = 0; i < 100000; i++) {
            int len = rnd.nextInt(100);

            int[] arr = new int[len];
            int[] expected = new int[len];
            int[] o = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = rnd.nextInt(100);
                expected[j] = arr[j];
                o[j] = arr[j];
            }

//            LOG.debug(Utils.toString(expected));
            Arrays.sort(expected);
            TimSort.sort(arr);
//            LOG.debug(Utils.toString(expected));
//            LOG.debug(Utils.toString(arr));
            assertArrayEquals(expected, arr);
        }
    }
}