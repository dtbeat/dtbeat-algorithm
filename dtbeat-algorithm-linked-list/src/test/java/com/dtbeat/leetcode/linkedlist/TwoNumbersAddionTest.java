package com.dtbeat.leetcode.linkedlist;

import org.junit.Test;

import static org.junit.Assert.*;

public class TwoNumbersAddionTest {
    @Test
    public void testAddTwoNumbers() {
        int[] number = new int[]{2, 4, 3};
        TwoNumbersAddion.ListNode l1 = null;
        TwoNumbersAddion.ListNode current = null;
        for (int i : number) {
            TwoNumbersAddion.ListNode node = new TwoNumbersAddion.ListNode(i);
            if (current == null) {
                l1 = node;
                current = node;
            } else {
                current.next = node;
                current = node;
            }
        }

        number = new int[]{5, 6, 4};
        TwoNumbersAddion.ListNode l2 = null;
        current = null;
        for (int i : number) {
            TwoNumbersAddion.ListNode node = new TwoNumbersAddion.ListNode(i);
            if (current == null) {
                l2 = node;
                current = node;
            } else {
                current.next = node;
                current = node;
            }
        }

        TwoNumbersAddion addion = new TwoNumbersAddion();
        TwoNumbersAddion.ListNode res = addion.addTwoNumbers(l1, l2);

        int[] expected = new int[] {7, 0, 8};
        for (int i : expected) {
            assertEquals(i, res.val);
            res = res.next;
        }
    }
}