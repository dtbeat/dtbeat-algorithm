package com.dtbeat.leetcode._0509_.finonacci.number;

/**
 * SolutionV2
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/25 22:22 dtbeat.com $
 */
public class SolutionV2 {
    public int fib(int N) {
        if (N == 0 || N == 1) {
            return N;
        }

        int res = 0;
        int pre = 0, next = 1;
        for (int i = 2; i <= N; i++) {
            res = pre + next;
            pre = next;
            next = res;
        }

        return res;
    }

}
