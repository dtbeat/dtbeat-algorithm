package com.dtbeat.greedy;
// 问题描述：
// 小明手中有1,5,10,50,100五种面额的纸币，每种纸币对应张数分别为5,2,2,3,5张。若小明需要支付456元，则需要多少张纸币？
// 贪心策略：在允许的条件下选择面额最大的纸币

/**
 * PaperMoneySelection
 *
 * @author elvinshang
 * @version Id: PaperMoneySelection.java, v0.0.1 2020/9/9 22:00 dtbeat.com $
 */
public class PaperMoneySelection {
    public static int[] solve(int[] counts, int[] values, int money) {
        int[] result = new int[values.length];
        for (int i = values.length - 1; i >= 0; i--) {
            int count = Math.min(money / values[i], counts[i]);
            result[i] = count;
            money -= count * values[i];
        }

        return money > 0 ? null : result;
    }

    public static void main(String[] args) {
        int[] values = new int[]{1, 5, 10, 50, 100};
        int[] counts = new int[]{5, 2, 2, 3, 5};
        int[] ret = solve(counts, values, 456);
        if (ret == null) {
            System.out.println("No Solution!");
        } else {
            System.out.println("Solution:");
            for (int i = values.length - 1; i >= 0; i--) {
                System.out.println(values[i] + ": " + ret[i]);
            }
        }
    }
}
