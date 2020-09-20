package com.dtbeat;

/**
 * Brute-Force String Matching
 *
 * @author elvinshang
 * @version Id: StringMatching.java, v0.0.1 2020/9/18 21:16 dtbeat.com $
 */
public class BruteForce {
    /**
     * Returns index of the pattern at the string, or -1 if not find
     * <p>
     * Time Complexity: O(M*N)
     * </p>
     *
     * @param s       the string to find
     * @param pattern the pattern string to match
     * @return index of the pattern at the string, or -1 if not find
     */
    public static int search(String s, String pattern) {
        if (s == null || s.isEmpty()) {
            return -1;
        }

        if (pattern == null || pattern.isEmpty()) {
            return -1;
        }

        final int N = s.length();
        final int M = pattern.length();

        for (int i = 0; i < N - M; i++) {
            int j = 0;
            for (; j < M; j++) {
                if (s.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }

            if (j == M) {
                return i;
            }
        }

        return -1;
    }

    public static int searchByBack(String s, String pattern) {
        if (s == null || s.isEmpty()) {
            return -1;
        }

        if (pattern == null || pattern.isEmpty()) {
            return -1;
        }

        final int N = s.length();
        final int M = pattern.length();
        int i = 0;
        int j = 0;

        for (; i < N && j < M; i++) {
            if (s.charAt(i) != pattern.charAt(j)) {
                j++;
            } else {
                i -= j;
                j = 0;
            }
        }

        return j == M ? i - M : -1;
    }
}
