package com.dtbeat.leetcode.doublepointer;
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
//
//
//
// 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Mar
//cos 贡献此图。
//
// 示例:
//
// 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
//输出: 6
// Related Topics 栈 数组 双指针
// 👍 1626 👎 0

/**
 * TrappingRainWater
 *
 * @author elvinshang
 * @version Id: TrappingRainWater.java, v0.0.1 2020/9/8 15:39 dtbeat.com $
 */
public class TrappingRainWater {
    /**
     * Traps rain water by Double-Pointer algorithm
     *
     * <p>
     * Time Complexity:  O(n)
     * Space Complexity: O(1)
     * </p>
     *
     * @param height the height`s array
     * @return the total rain water number
     */
    public int trap(int[] height) {
        if (height == null || height.length <= 1) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int res = 0;

        while (left <= right) {
            int v = 0;
            if (leftMax <= rightMax) {
                v = leftMax - height[left];
                res += v > 0 ? v : 0;
                leftMax = Math.max(leftMax, height[left]);
                left++;
            } else {
                v = rightMax - height[right];
                res += v > 0 ? v : 0;
                rightMax = Math.max(rightMax, height[right]);
                right--;
            }
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
