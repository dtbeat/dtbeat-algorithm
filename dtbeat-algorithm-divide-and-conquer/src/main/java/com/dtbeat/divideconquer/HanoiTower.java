package com.dtbeat.divideconquer;

import java.util.Stack;

/**
 * Hanoi Tower
 *
 * @author elvinshang
 * @version Id: HanNuoTa.java, v0.0.1 2020/9/10 00:08 dtbeat.com $
 */
public class HanoiTower {
    public static void solve(int n, Stack<Integer> s, Stack<Integer> temp, Stack<Integer> t) {
        if (n == 1) {
            t.push(s.pop());
        } else {
            solve(n - 1, s, t, temp);
            t.push(s.pop());
            solve(n - 1, temp, s, t);
        }
    }

    public static void main(String[] args) {
        int n = 10;
        Stack<Integer> s = new Stack<>();
        Stack<Integer> temp = new Stack<>();
        Stack<Integer> t = new Stack<>();

        for (int i = 10; i >= 1; i--) {
            s.push(i);
        }

        solve(10, s, temp, t);

        while (!t.isEmpty()) {
            System.out.println(t.pop());
        }
    }
}
