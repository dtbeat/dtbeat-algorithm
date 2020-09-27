package com.dtbeat.leetcode._0752_.open.the.lock;

import java.util.HashSet;
import java.util.Set;

/**
 * SolutionV2
 *
 * @author elvinshang
 * @version Id: SolutionV2.java, v0.0.1 2020/9/27 00:12 dtbeat.com $
 */
public class SolutionV2 {
    public int openLock(String[] deadends, String target) {
        if (target == null || target.length() != 4) {
            return -1;
        }

        Set<String> deadendsSet = new HashSet<>();
        if (deadends != null) {
            for (String deadend : deadends) {
                deadendsSet.add(deadend);
            }
        }

        String start = "0000";
        Set<String> q1 = new HashSet<>();
        Set<String> q2 = new HashSet<>();
        Set<String> visited = new HashSet<>();

        q1.add(start);
        q2.add(target);
        int step = 0;

        while (!q1.isEmpty() && !q2.isEmpty()) {
            Set<String> temp = new HashSet<>();

            for (String cur : q1) {
                if (deadendsSet.contains(cur)) {
                    continue;
                }

                if (q2.contains(cur)) {
                    return step;
                }

                visited.add(cur);
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 2; k++) {
                        String lock = k == 0 ? turnUp(cur, j) : turnDown(cur, j);
                        if (!visited.contains(lock)) {
                            temp.add(lock);
                        }
                    }
                }
            }

            step++;
            q1 = q2;
            q2 = temp;
        }

        return -1;
    }

    private String turnUp(String s, int i) {
        char[] lock = s.toCharArray();
        if (lock[i] == '9') {
            lock[i] = '0';
        } else {
            lock[i] += 1;
        }

        return new String(lock);
    }

    private String turnDown(String s, int i) {
        char[] lock = s.toCharArray();
        if (lock[i] == '0') {
            lock[i] = '9';
        } else {
            lock[i] -= 1;
        }

        return new String(lock);
    }
}
