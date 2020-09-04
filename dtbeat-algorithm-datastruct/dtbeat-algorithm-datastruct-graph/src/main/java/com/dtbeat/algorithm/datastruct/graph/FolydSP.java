package com.dtbeat.algorithm.datastruct.graph;

import java.util.Objects;

/**
 * Folyd Shortest Path
 *
 * @author elvinshang
 * @version Id: FolydSP.java, v0.0.1 2020/9/3 21:27 dtbeat.com $
 */
public class FolydSP<V> {
    private int[][] dist;
    private int[][] path;
    private int n;
    private Graph<V, Edge<V>> g;

    private static final int INF = Integer.MAX_VALUE;

    public FolydSP(Graph<V, Edge<V>> g) {
        this.g = g;
        this.n = g.vertexSize();
        this.dist = new int[n][n];
        this.path = new int[n][n];;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = (i == j) ? 0 : INF;
                path[i][j] = (i == j) ? -1 : j;
            }
        }

        Iterable<Edge<V>> edges = g.edges(false);
        for (Edge<V> e : edges) {
            dist[g.indexOf(e.getFrom())][g.indexOf(e.getTo())] = e.getWeight();
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && k != i && k != j) {
                        int weight = dist[i][k] == INF || dist[k][j] == INF ? INF : dist[i][k] + dist[k][j];
                        if (weight < dist[i][j]) {
                            dist[i][j] = weight;
                            path[i][j] = path[i][k];
                        }
                    }
                }
            }
        }
    }

    public int getDist(V from, V to) {
        validateVetex(from);
        validateVetex(to);

        return dist[g.indexOf(from)][g.indexOf(to)];
    }

    public int getPath(V from, V to) {
        validateVetex(from);
        validateVetex(to);

        return path[g.indexOf(from)][g.indexOf(to)];
    }

    private void validateVetex(V v) {
        Objects.requireNonNull(v);
        int i = g.indexOf(v);
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("v");
        }
    }
}
