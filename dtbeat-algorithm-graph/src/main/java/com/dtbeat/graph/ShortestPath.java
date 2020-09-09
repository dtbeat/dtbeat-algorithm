package com.dtbeat.graph;

import java.util.*;

/**
 * Shortest Path
 *
 * @author elvinshang
 * @version Id: ShortestPath.java, v0.0.1 2020/9/3 09:43 dtbeat.com $
 */
public class ShortestPath<V> {
    private HashMap<V, V> book;
    private HashMap<V, Integer> dist;
    private Graph<V, Edge<V>> g;

    public ShortestPath(Graph<V, Edge<V>> g) {
        this.g = g;
        this.book = new HashMap<>(g.vertexSize());
        this.dist = new HashMap<>(g.vertexSize());
    }

    public void add(V to, V from, int weight) {
        book.put(to, from);
        dist.put(to, weight);
    }

    public void setBook(V to, V from) {
        book.put(to, from);
    }

    public boolean inBook(V v) {
        return book.containsKey(v);
    }

    public void setDist(V to, int weight) {
        dist.put(to, weight);
    }

    public int getDist(V v) {
        return dist.containsKey(v) ? dist.get(v) : Integer.MAX_VALUE;
    }

    public int size() {
        return book.size();
    }

    @Override
    public String toString() {
        StringBuilder writer = new StringBuilder();
        book.keySet().stream().sorted(Comparator.comparingInt(o -> g.indexOf(o))).forEach(v -> {
            V from = book.get(v);
            Integer weight = dist.get(v);
            writer.append(from + "->" + v + ":" + weight + ",");
        });

        return writer.length() > 0 ? writer.deleteCharAt(writer.length() - 1).toString() : "";
    }

    /**
     * return shortest path by Dijkstra Algorithm
     *
     * @param g   the directed graph
     * @param <V> the vertex class type
     * @return shortest path
     */
    public static <V> ShortestPath<V> dijkstra(Graph<V, Edge<V>> g, V s) {
        ShortestPath<V> sp = new ShortestPath<>(g);
        int[] dist = new int[g.vertexSize()];
        V current = s;
        int curDist = Integer.MAX_VALUE;

        // init
        sp.add(s, s, 0);
        for (Edge<V> e : g.adj(s)) {
            sp.setDist(e.getTo(), e.getWeight());
            if (curDist > e.getWeight()) {
                current = e.getTo();
                curDist = e.getWeight();
            }
        }
        sp.setBook(current, s);

        V pre = s;
        Edge<V> curEdge = null;
        while (sp.size() < g.vertexSize()) {
            curDist = Integer.MAX_VALUE;
            Iterable<Edge<V>> edges = g.adj(current);
            int sDist = sp.getDist(current);
            for (Edge<V> e : edges) {
                if (!sp.inBook(e.getTo())) {
                    // relax
                    int weight = sDist + e.getWeight();
                    if (weight < sp.getDist(e.getTo())) {
                        sp.setDist(e.getTo(), weight);
                    }
                    // update current vertex
                    if (curDist > sp.getDist(e.getTo())) {
                        current = e.getTo();
                        curDist = sp.getDist(e.getTo());
                        curEdge = e;
                    }
                }
            }

            if (pre != current) {
                sp.setBook(curEdge.getTo(), curEdge.getFrom());
                pre = current;
            } else {
                break;
            }
        }

        return sp;
    }

    /**
     * Returns shortest path by Bellman-Ford Algorithm
     *
     * @param g   the directed graph
     * @param <V> the vertex class type
     * @return shortest path
     */
    public static <V> ShortestPath<V> bellmanFord(Graph<V, Edge<V>> g) {
        ShortestPath<V> sp = new ShortestPath<>(g);
        Iterable<Edge<V>> edges = g.edges();
        MinPriorityQueue<Edge<V>> q = new MinPriorityQueue<Edge<V>>(g.edgeSize(), (o1, o2) -> {
            int from1 = g.indexOf(o1.getFrom());
            int from2 = g.indexOf(o2.getFrom());
            if (from1 != from2) {
                return Integer.compare(from1, from2);
            } else {
                return Integer.compare(g.indexOf(o1.getTo()), g.indexOf(o2.getTo()));
            }
        });
        for (Edge<V> e : edges) {
            q.insert(e);
        }

        Edge<V> min = q.min();
        sp.add(min.getFrom(), min.getFrom(), 0);

        boolean hasChanged = false;
        for (int i = 0; i < g.vertexSize() - 1; i++) {
            hasChanged = false;
            for (Edge<V> e : q) {
                int weight = sp.getDist(e.getFrom()) + e.getWeight();
                if (weight < sp.getDist(e.getTo())) {
                    sp.add(e.getTo(), e.getFrom(), weight);
                    hasChanged = true;
                }
            }

            if (!hasChanged) {
                break;
            }
        }

        return hasChanged ? null : sp;
    }

    /**
     * Returns shortest path by SPFA algorithm
     *
     * @param g     the directed graph
     * @param start the start vertex
     * @param <V>   the vertex class type
     * @return shortest path
     */
    public static <V> ShortestPath<V> spfa(Graph<V, Edge<V>> g, V start) {
        ShortestPath<V> sp = new ShortestPath<>(g);
        Queue<V> q = new ArrayDeque<>();
        q.offer(start);
        sp.add(start, start, 0);

        while (!q.isEmpty()) {
            V v = q.poll();
            Iterable<Edge<V>> edges = g.adj(v);
            for (Edge<V> e : edges) {
                int weight = sp.getDist(e.getFrom()) + e.getWeight();
                if (weight < sp.getDist(e.getTo())) {
                    sp.add(e.getTo(), e.getFrom(), weight);
                    if (!q.contains(e.getTo())) {
                        q.offer(e.getTo());
                    }
                }
            }
        }

        return sp;
    }
}
