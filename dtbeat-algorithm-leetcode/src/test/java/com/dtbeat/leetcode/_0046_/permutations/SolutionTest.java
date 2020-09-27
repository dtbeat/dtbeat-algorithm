package com.dtbeat.leetcode._0046_.permutations;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testRecursion() {
        int[] nums = Helper.mockCase();
        int[] permutations = Helper.makePermutations();

        Solution sln = new Solution();
        List<List<Integer>> r = sln.permute(nums);
        int[] actual = Helper.toPermutations(r);
        assertArrayEquals(actual, permutations);
    }

    @Test
    public void testDfs() {
        int[] nums = Helper.mockCase();
        int[] permutations = Helper.makePermutations();

        SolutionV2 sln = new SolutionV2();
        List<List<Integer>> r = sln.permute(nums);
        int[] actual = Helper.toPermutations(r);
        assertArrayEquals(actual, permutations);
    }

    static class Helper {
        static int[] mockCase() {
            return new int[]{1, 2, 3};
        }

        static int[] makePermutations() {
            return new int[]{
                    123,
                    132,
                    213,
                    231,
                    312,
                    321
            };
        }

        static int[] toPermutations(List<List<Integer>> actual) {
            int[] permutations = new int[actual.size()];
            for (int i = 0; i < actual.size(); i++) {
                List<Integer> p = actual.get(i);
                int r = 0;
                for (Integer p1 : p) {
                    r = r * 10 + p1;
                }

                permutations[i] = r;
            }

            Arrays.sort(permutations);

            return permutations;
        }
    }
}