package com.dtbeat.leetcode.stack;
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
//
// 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Mar
//cos 贡献此图。
//
// 示例:
//
// 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
//输出: 6
// Related Topics 栈 数组 双指针
// 👍 1624 👎 0

import java.util.Stack;

/**
 * TrapRain
 *
 * @author elvinshang
 * @version Id: TrapRain.java, v0.0.1 2020/9/8 10:27 dtbeat.com $
 */
public class TrappingRainWater {
    /**
     * Traps rain water by Stack algorithm
     *
     * <p>
     * Time Complexity:  O(n)
     * Space Complexity: O(n)
     * </p>
     *
     * 解题思路：
     * 遍历柱高数组：
     * 1. 入栈条件：栈为空或者当前柱高小于等于栈顶柱高，将当前柱高的下标入栈
     * 2. 出栈条件：循环执行出栈条件，栈不为空且当前柱高大于栈顶柱高，栈顶出栈，并计算收集雨水量
     *    2.1 如果此时，栈已为空，则跳出出栈循环条件
     *    2.2 计算当前柱高与栈顶之间的柱子个数，即柱子个数
     *    2.3 柱高差值 = 取当前柱高与栈顶柱高最小值 - 出栈柱高
     *    2.4 更新：雨水量 += 柱子个数 * 柱高差值
     *    2.5 循环执行出栈条件，直到栈为空或条件不成立
     *
     *
     * @param height the height`s array
     * @return the total rain water number
     */
    public int trap(int[] height) {
        if (height == null || height.length <= 1) {
            return 0;
        }

        int res = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }

                int dist = i - stack.peek() - 1;
                int v = Math.min(height[i], height[stack.peek()]) - height[top];
                res += dist * v;
            }

            stack.push(i);
        }

        return res;
    }

    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        TrappingRainWater tr = new TrappingRainWater();
        int ret = tr.trap(height);
        System.out.println(ret);
    }
}
