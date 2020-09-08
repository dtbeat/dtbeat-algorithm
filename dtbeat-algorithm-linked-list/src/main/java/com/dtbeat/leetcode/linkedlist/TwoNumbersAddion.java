package com.dtbeat.leetcode.linkedlist;
//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
// 示例：
//
// 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//输出：7 -> 0 -> 8
//原因：342 + 465 = 807
//
// Related Topics 链表 数学
// 👍 4854 👎 0

import java.util.List;

/**
 * TwoNumberPlus
 *
 * @author elvinshang
 * @version Id: TwoNumberPlus.java, v0.0.1 2020/9/8 16:48 dtbeat.com $
 */
public class TwoNumbersAddion {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = null;
        ListNode current = null;
        ListNode left = l1;
        ListNode right = l2;
        int carryOver = 0;

        while (left != null || right != null || carryOver > 0) {
            int v = value(left) + value(right) + carryOver;
            carryOver = v / 10;
            v = v % 10;

            ListNode node = new ListNode(v);
            if (current != null) {
                current.next = node;
                current = node;
            } else {
                res = node;
                current = node;
            }

            left = next(left);
            right = next(right);
        }

        return res;
    }

    public static int value(ListNode node) {
        return node == null ? 0 : node.val;
    }

    public static ListNode next(ListNode node) {
        return node == null ? null : node.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int x) {
            val = x;
        }
    }
}
