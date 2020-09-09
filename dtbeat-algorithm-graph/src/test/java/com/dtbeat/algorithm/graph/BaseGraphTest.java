package com.dtbeat.algorithm.graph;

import java.util.List;

/**
 * Base Graph Test
 *
 * @author elvinshang
 * @version Id: GraphTest.java, v0.0.1 2020/9/2 20:38 dtbeat.com $
 */
public class BaseGraphTest {
    String toString(char[] chars, List<Integer> vertices) {
        StringBuilder s = new StringBuilder();
        vertices.forEach(v -> s.append(chars[v.intValue()]));

        return s.toString();
    }

    static <V> String toString(Iterable<V> seq) {
        StringBuilder s = new StringBuilder();
        seq.forEach(v -> s.append(v + ","));

        return s.length() > 0 ? s.deleteCharAt(s.length() - 1).toString() : "";
    }
}
