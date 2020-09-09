package com.dtbeat.graph;

import java.util.*;

/**
 * Topology Sorting
 *
 * @author elvinshang
 * @version Id: TopologySorting.java, v0.0.1 2020/9/4 16:00 dtbeat.com $
 */
public class TopologySorting {
    /**
     * Zero InDegree Algorithm
     *
     * @param g   the DAG graph
     * @param <V> the type of vertex class
     * @return topology sequence
     */
    public static <V> Iterable<V> zeroInDegree(Graph<V, Edge<V>> g) {
        Iterable<Edge<V>> edges = g.edges(false);
        Iterable<V> vertices = g.vertices();
        HashSet<Edge<V>> removedEdges = new HashSet<>();
        HashSet<V> removedVertices = new HashSet<>();

        List<V> result = new ArrayList<>(g.vertexSize());
        for (int i = 0; i < g.vertexSize(); i++) {
            V v = findZeroInDegreeVertex(g, vertices, edges, removedVertices, removedEdges);
            if (v == null) {
                break;
            }

            result.add(v);
            removedVertices.add(v);
            for (Edge<V> edge : g.adj(v)) {
                removedEdges.add(edge);
            }
        }

        return result;
    }

    private static <V> V findZeroInDegreeVertex(Graph<V, Edge<V>> g, Iterable<V> vertices, Iterable<Edge<V>> edges, HashSet<V> removedVertices, HashSet<Edge<V>> removedEdges) {
        for (V v : vertices) {
            if (removedVertices.contains(v)) {
                continue;
            }

            boolean find = true;
            for (Edge<V> edge : edges) {
                if (removedEdges.contains(edge)) {
                    continue;
                }

                if (edge.getTo() == v) {
                    find = false;
                    break;
                }
            }

            if (find) {
                return v;
            }
        }

        return null;
    }

    private static <V> V findZeroInDegreeVertex(Graph<V, Edge<V>> g) {
        Iterable<Edge<V>> edges = g.edges(false);
        Iterable<V> vertices = g.vertices();

        for (V v : vertices) {
            boolean find = true;
            for (Edge<V> edge : edges) {
                if (edge.getTo() == v) {
                    find = false;
                    break;
                }
            }

            if (find) {
                return v;
            }
        }

        return null;
    }

    /**
     * Topology Sorting By DFS
     *
     * @param g   the DAG graph
     * @param <V> the type of vertex class
     * @return topology sequence
     */
    public static <V> Iterable<V> dfs(Graph<V, Edge<V>> g) {
        Stack<Tuple<V, Iterator<Edge<V>>>> s = new Stack<>();
        ArrayDeque<V> result = new ArrayDeque<>();
        HashSet<V> marked = new HashSet<>();

        V startV = findZeroInDegreeVertex(g);
        if (startV == null) {
            return result;
        }

        V curV = startV;
        Iterator<Edge<V>> curItr = g.adj(curV).iterator();
        s.push(new Tuple<>(curV, curItr));
        while (!s.isEmpty()) {
            if (curItr.hasNext()) {
                Edge<V> next = curItr.next();
                if (marked.contains(next.getTo())) {
                    continue;
                }

                curV = next.getTo();
                curItr = g.adj(curV).iterator();
                s.push(new Tuple<>(curV, curItr));
            } else {
                Tuple<V, Iterator<Edge<V>>> tuple = s.peek();
                curV = tuple.getT1();
                curItr = tuple.getT2();

                if(!curItr.hasNext()) {
                    s.pop();
                    marked.add(curV);
                    result.addFirst(curV);
                }
            }
        }



        return result;
    }


}
