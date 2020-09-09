package com.dtbeat.greedy;
// 问题描述：
// 一辆汽车加满油后可行驶 n公里。旅途中有若干个加油站。设计一个有效算法，指出应 在哪些加油站停靠加油，使沿途加油次数最少。
// 输入格式：
// 第一行有2个正整数n和k（k<=1000 )，表示汽车加满油后可行驶n公里，且旅途中有 k个加油站。
// 第二行有 k+1 个整数，k 表示第k个加油站与第k-1个加油站之间的距离；K+1 表示目的地与最后一个加油站（也就是第K个加油站）之间的距离；第0个表示第一个加油站与出发地之间的距离

/**
 * CarAdditionGasoline
 *
 * @author elvinshang
 * @version Id: CarAdditionGasoline.java, v0.0.1 2020/9/9 22:39 dtbeat.com $
 */
public class CarAdditionGasoline {
    public static int[] solve(int n, int k, int[] dist) {
        int[] res = new int[k];
        boolean flag = false;
        int total = 0;
        for (int i = 0; i < k; i++) {
            if (n < dist[i]) {
                flag = true;
                break;
            }

            total += dist[i];
            if (total + dist[i + 1] > n) {
                total = 0;
                res[i] = 1;
            }
        }

        return flag == true ? null : res;
    }

    public static void main(String[] args) {
        int n = 7;
        int k = 7;
        int[] dist = new int[]{1, 2, 3, 4, 5, 1, 6, 6};

        int[] ret = solve(n, k, dist);
        if (ret == null) {
            System.out.println("No Solution!");
        } else {
            System.out.println("Solution:");
            for (int i = 0; i < k; i++) {
                System.out.println((i + 1) + ": " + ret[i]);
            }
        }
    }
}
