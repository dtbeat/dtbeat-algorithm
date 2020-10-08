package com.dtbeat;

/**
 * KMP
 *
 * @author elvinshang
 * @version Id: KMP.java, v0.0.1 2020/9/18 22:43 dtbeat.com $
 */
public class KMP {
    private static final int R = 256;
    private SearchEngine engine;

    public KMP(String pattern) {
        this.engine = new NextsSearchEngine(pattern, R);
    }

    public KMP(SearchEngine engine) {
        this.engine = engine;
    }

    public static KMP createDfa(String pattern) {
        return new KMP(new DFASearchEngine(pattern, R));
    }

    public static KMP createNexts(String pattern) {
        return new KMP(new NextsSearchEngine(pattern, R));
    }

    public int search(String s) {
        return engine.search(s);
    }


    public static interface SearchEngine {
        /**
         * Returns index of the pattern in string s or -1 if none
         *
         * @param s the text to search
         * @return index of the pattern in string s or -1 if none
         */
        int search(String s);
    }

    /**
     * Nexts Search Engine
     */
    public static class NextsSearchEngine implements SearchEngine {
        private final int R;
        private final String pattern;
        private final int[] nexts;

        public NextsSearchEngine(String pattern, int r) {
            this.pattern = pattern;
            this.R = r;
            this.nexts = getNexts(pattern);
            //createNexts(pattern);
        }

        @Override
        public int search(String s) {
            final int N = s.length();
            final int M = pattern.length();

            int i = 0, j = 0;
            while (i < N && j < M) {
                if (j == -1 || s.charAt(i) == pattern.charAt(j)) {
                    i++;
                    j++;
                } else {
                    j = nexts[j];
                }
            }

            return j == N ? i - j : -1;

//            int i = 0, j = 0;
//            for (; i < N && j < M; i++) {
//                while (j > 0 && pattern.charAt(j) != s.charAt(i)) {
//                    j = nexts[j];
//                }
//
//                if (pattern.charAt(j) == s.charAt(i)) {
//                    j++;
//                }
//            }
//
//            return j >= M ? i - M : -1;
        }

        private int[] getNexts(String pattern) {
            final int M = pattern.length();
            int[] nexts = new int[M];
            nexts[0] = -1;
            int i = 0, j = -1;

            while (i < M - 1) {
                if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                    i++;
                    j++;
                    nexts[i] = j;
                } else {
                    j = nexts[j];
                }
            }

            return nexts;
        }

        private int[] createNexts(String pattern) {
            final int M = pattern.length();
            int[] nexts = new int[M];
            int j = 0;
            for (int i = 2; i < M; i++) {
                while (j != 0 && pattern.charAt(j) != pattern.charAt(i - 1)) {
                    j = nexts[j];
                }

                if (pattern.charAt(j) == pattern.charAt(i - 1)) {
                    j++;
                }

                nexts[i] = j;
            }

            return nexts;
        }

    }

    /**
     * DFA Search Engine
     */
    public static class DFASearchEngine implements SearchEngine {
        private final int R;
        private final String pattern;
        private final int[][] dfa;

        public DFASearchEngine(String pattern, int r) {
            R = r;
            this.pattern = pattern;
            this.dfa = createDFA(pattern);
        }

        @Override
        public int search(String s) {
            final int N = s.length();
            final int M = pattern.length();

            int i = 0, j = 0;
            for (; i < N && j < M; i++) {
                j = dfa[s.charAt(i)][j];
            }

            if (j == M) {
                return i - M;
            }

            return -1;
        }

        private int[][] createDFA(String pattern) {
            final int M = pattern.length();
            int[][] dfa = new int[R][M];

            dfa[pattern.charAt(0)][0] = 1;
            for (int i = 1, x = 0; i < M; i++) {
                for (int j = 0; j < R; j++) {
                    dfa[j][i] = dfa[j][x];
                }

                dfa[pattern.charAt(i)][i] = i + 1;
                x = dfa[pattern.charAt(i)][x];
            }

            return dfa;
        }
    }
}
