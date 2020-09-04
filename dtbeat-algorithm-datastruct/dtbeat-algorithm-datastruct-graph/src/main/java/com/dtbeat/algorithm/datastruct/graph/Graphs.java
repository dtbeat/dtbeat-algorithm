package com.dtbeat.algorithm.datastruct.graph;

import java.util.*;

/**
 * Graph Utility
 *
 * @author elvinshang
 * @version Id: Graphs.java, v0.0.1 2020/9/2 17:17 dtbeat.com $
 */
public class Graphs {
    /**
     * Deep First Search
     *
     * @param graph the graph
     * @param <V>   the vertex class type
     * @return vertices of the searching
     */
    public static <V> List<V> dfs(Graph<V, Edge<V>> graph) {
        HashSet<V> marked = new HashSet<>();
        Stack<Tuple<V, Iterator<Edge<V>>>> s = new Stack<>();
        List<V> result = new ArrayList<>();

        for (V v : graph.vertices()) {
            if (!marked.contains(v)) {
                dfs0(graph, v, marked, s, result);
            }
        }

        return result;
    }

    private static <V> void dfs0(Graph<V, Edge<V>> g, V v, HashSet<V> marked, Stack<Tuple<V, Iterator<Edge<V>>>> s, List<V> result) {
        Iterator<Edge<V>> edges = g.adj(v).iterator();
        s.push(new Tuple<>(v, edges));
        marked.add(v);
        result.add(v);

        V t = v;
        Iterator<Edge<V>> itr = edges;
        while (!s.empty() || itr.hasNext()) {
            if (itr.hasNext()) {
                Edge<V> edge = itr.next();
                V child = edge.getTo();
                if (!marked.contains(child)) {
                    t = child;
                    itr = g.adj(t).iterator();
                    s.push(new Tuple<>(t, itr));
                    marked.add(t);
                    result.add(t);
                }
            } else {
                Tuple<V, Iterator<Edge<V>>> tuple = s.pop();
                 t = tuple.getT1();
                 itr = tuple.getT2();
            }
        }
    }

    /**
     * Bread First Searching
     *
     * @param g   the graph
     * @param <V> the vertex class type
     * @return
     */
    public static <V> List<V> bfs(Graph<V, Edge<V>> g) {
        HashSet<V> marked = new HashSet<>();
        Queue<Tuple<V, Iterator<Edge<V>>>> s = new ArrayDeque<>();
        List<V> result = new ArrayList<>();

        for (V v : g.vertices()) {
            if (!marked.contains(v)) {
                bfs0(g, v, marked, s, result);
            }
        }

        return result;
    }

    private static <V> void bfs0(Graph<V, Edge<V>> g, V v, HashSet<V> marked, Queue<Tuple<V, Iterator<Edge<V>>>> q, List<V> result) {
        Iterator<Edge<V>> edges = g.adj(v).iterator();
        q.offer(new Tuple<>(v, edges));
        marked.add(v);
        result.add(v);

        V t = v;
        Iterator<Edge<V>> itr = edges;
        while (!q.isEmpty()) {
            Tuple<V, Iterator<Edge<V>>> tuple = q.poll();
            t = tuple.getT1();
            itr = tuple.getT2();

            while (itr.hasNext()) {
                V sibling = itr.next().getTo();
                if (!marked.contains(sibling)) {
                    Iterator<Edge<V>> siblingItr = g.adj(sibling).iterator();
                    q.offer(new Tuple<>(sibling, siblingItr));
                    marked.add(sibling);
                    result.add(sibling);
                }
            }
        }
    }

    public static Graph<Integer, Edge<Integer>> newDigraph(GraphStoreType storeType, int v) {
        return newGraph(storeType, GraphType.Directed, v);
    }

    public static Graph<Integer, Edge<Integer>> newUndigraph(GraphStoreType storeType, int v) {
        return newGraph(storeType, GraphType.Undirected, v);
    }

    public static Graph<Integer, Edge<Integer>> newGraph(GraphStoreType storeType, GraphType type, int v) {
        Graph<Integer, Edge<Integer>> result = null;
        switch (storeType) {
            case AdjacencyMatrix:
                result = new AdjacencyMatrixGraph(v, type);
                break;
            case AdjacencyList:
                result = new AdjacencyListGraph(v, type);
                break;
            default:
                throw new UnsupportedOperationException();

        }

        return result;
    }
}
