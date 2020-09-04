package com.dtbeat.algorithm.datastruct.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * KeyPath
 *
 * @author elvinshang
 * @version Id: KeyPath.java, v0.0.1 2020/9/4 17:27 dtbeat.com $
 */
public class KeyPath<V> {
    private int[] ve;
    private int[] vl;
    private int[][] e;
    private int[][] l;
    private List<Edge<V>> keyActivities;

    private Graph<V, Edge<V>> g;
    private static final int INF = Integer.MAX_VALUE;

    public KeyPath(Graph<V, Edge<V>> g) {
        this.g = g;
        this.ve = new int[g.vertexSize()];
        this.vl = new int[g.vertexSize()];
        this.e = new int[g.vertexSize()][g.vertexSize()];
        this.l = new int[g.vertexSize()][g.vertexSize()];
        this.keyActivities = new ArrayList<>();

        for (int i = 0; i < g.vertexSize(); i++) {
            this.ve[i] = i == 0 ? 0 : INF;
            this.vl[i] = INF;
        }

        for (int i = 0; i < g.vertexSize(); i++) {
            for (int j = 0; j < g.vertexSize(); j++) {
                this.e[i][j] = INF;
                this.l[i][j] = INF;
            }
        }

        analyze();
    }

    private void analyze() {
        Iterable<V> vertices = TopologySorting.dfs(g);
        Deque<V> q = new ArrayDeque<>(g.vertexSize());

        // ve
        for (V v : vertices) {
            q.addFirst(v);
            for (Edge<V> e : g.adj(v)) {
                int to = g.indexOf(e.getTo());
                int from = g.indexOf(e.getFrom());
                int d = ve[from] + e.getWeight();
                ve[to] = ve[to] == INF ? d : Math.max(ve[to], d);
            }
        }

        // vl
        V lastV = q.getFirst();
        vl[g.indexOf(lastV)] = ve[g.indexOf(lastV)];
        for (V v : q) {
            for (Edge<V> e : g.adj(v)) {
                int to = g.indexOf(e.getTo());
                int from = g.indexOf(e.getFrom());
                int d = vl[to] - e.getWeight();
                vl[from] = vl[from] == INF ? d : Math.min(vl[from], d);
            }
        }

        // e 和 l
        for (Edge<V> edge : g.edges(false)) {
            int to = g.indexOf(edge.getTo());
            int from = g.indexOf(edge.getFrom());
            e[from][to] = ve[from];
            l[from][to] = vl[to] - edge.getWeight();
            if (e[from][to] == l[from][to]) {
                keyActivities.add(edge);
            }
        }
    }

    public int getMaxCost() {
        return Arrays.stream(ve).max().getAsInt();
    }

    public Iterable<Edge<V>> getKeyActivities() {
        return Collections.unmodifiableList(keyActivities);
    }

    public String getFormatedKeyActivities() {
        StringBuilder writer = new StringBuilder();
        List<Edge<V>> activities = keyActivities.stream().sorted((e1, e2) -> {
            int from1 = g.indexOf(e1.getFrom());
            int from2 = g.indexOf(e2.getFrom());

            if (from1 != from2) {
                return Integer.compare(from1, from2);
            } else {
                int to1 = g.indexOf(e1.getTo());
                int to2 = g.indexOf(e2.getTo());

                return Integer.compare(to1, to2);
            }
        }).collect(Collectors.toList());

        for (Edge<V> e : activities) {
            writer.append(e.getFrom() + "->" + e.getTo() + ",");
        }

        return writer.length() > 0 ? writer.deleteCharAt(writer.length() - 1).toString() : "";
    }

}
