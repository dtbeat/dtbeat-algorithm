package com.dtbeat.greedy;
// 问题描述：
// 若有n个人，第i个人重量为wi。每艘船的最大载重量均为C，且每艘船最多只能乘两个人。为节省花销，需要用最少的船装载所有人。请计算出需要最少的船的数量。
// 贪心策略：考虑最轻的人i，如果每个人都无法和他一起坐船（重量和超过C），则唯一的方案是每个人坐一艘。否则，他应该选择能和他一起坐船的人中最重的一个j

/**
 * ByBoat
 *
 * @author elvinshang
 * @version Id: ByBoat.java, v0.0.1 2020/9/9 23:19 dtbeat.com $
 */
public class ByBoat {
    public static int solve(int[] weights, int c) {
        int res = 0;
        boolean[] state = new boolean[weights.length];
        for (int i = 0; i < weights.length; i++) {
            if (state[i]) {
                continue;
            }

            int total = 0;
            int p = -1;
            for (int j = i + 1; j < weights.length; j++) {
                if (state[j]) {
                    continue;
                }

                int weight = weights[i] + weights[j];
                if ((total == 0 && weight <= c)
                        || (total > 0 && weight <= c && total < weight)) {
                    total = weight;
                    p = j;
                }
            }

            res++;
            state[i] = true;
            if (p != -1) {
                state[p] = true;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] weights = new int[]{115, 120, 125, 135, 155, 150, 190};
        int c = 300;
        int res = solve(weights, c);
        System.out.println(res);
    }
}
