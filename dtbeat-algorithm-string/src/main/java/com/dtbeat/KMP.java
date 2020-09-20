package com.dtbeat;

/**
 * KMP
 *
 * @author elvinshang
 * @version Id: KMP.java, v0.0.1 2020/9/18 22:43 dtbeat.com $
 */
public class KMP {


    public static class DFA {
        private static final int R = 256;

        private final int M;
        private String pattern;
        private int[][] dfa;

        public DFA(String pattern) {
            this.M = pattern.length();
            this.pattern = pattern;
            this.dfa = new int[R][M];
        }

        private void build() {
            dfa[pattern.charAt(0)][0] = 1;
            for (int x = 0, j = 1; j < M; j++) {
                for (int c = 0; c < R; c++) {
                    dfa[c][j] = dfa[c][x];
                }
                x = dfa[pattern.charAt(j)][x];
            }
        }
    }
}
