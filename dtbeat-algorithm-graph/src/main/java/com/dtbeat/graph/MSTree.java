package com.dtbeat.graph;

import com.dtbeat.uf.UnionFindSet;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;

/**
 * Minimum Spanning Tree
 *
 * @author elvinshang
 * @version Id: MSTree.java, v0.0.1 2020/9/2 20:54 dtbeat.com $
 */
public class MSTree {
    /**
     * Prim MST
     *
     * @param g   the graph
     * @param <V> the vertex class type
     * @return MST sequence
     */
    public static <V> MST<V> prim(Graph<V, Edge<V>> g) {
        MST<V> mst = new MST<>(g);
        HashSet<V> marked = new HashSet<>();

        V startVertex = null;
        for (V vertex : g.vertices()) {
            startVertex = vertex;
            break;
        }

        if (startVertex == null) {
            return null;
        }

        marked.add(startVertex);
        while (mst.size() < g.edgeSize()) {
            V curV = null;
            Edge<V> curE = null;
            int curWeight = 0;
            for (V v : marked) {
                Iterator<Edge<V>> itr = g.adj(v).iterator();
                while (itr.hasNext()) {
                    Edge<V> edge = itr.next();
                    V nextV = edge.getTo();
                    if (!marked.contains(nextV)) {
                        if (curV == null) {
                            curV = edge.getTo();
                            curWeight = edge.getWeight();
                            curE = edge;
                        } else if (curWeight > edge.getWeight()) {
                            curV = edge.getTo();
                            curWeight = edge.getWeight();
                            curE = edge;
                        }
                    }
                }
            }

            if (curV == null) {
                break;
            }

            mst.add(curE);
            marked.add(curV);
        }


        return mst;
    }

    /**
     * Kruskal MST
     *
     * @param g   the graph
     * @param <V> the vertex class type
     * @return MST
     */
    public static <V> MST<V> kruskal(Graph<V, Edge<V>> g) {
         MST<V> mst = new MST<>(g);

        Iterable<Edge<V>> edges = g.edges();
        MinPriorityQueue<Edge<V>> q = new MinPriorityQueue<>();
        for (Edge<V> edge : edges) {
            q.insert(edge);
        }

        // run greedy algorithm
        UnionFindSet uf = new UnionFindSet(g.vertexSize());
        while (!q.isEmpty() && mst.size() < g.edgeSize()) {
            Edge<V> e = q.delMin();
            V v = e.getFrom();
            V w = e.getTo();
            int vIndex = g.indexOf(v);
            int wIndex = g.indexOf(w);
            if (uf.find(vIndex) != uf.find(wIndex)) {
                // merge v and w components
                uf.union(vIndex, wIndex);
                // add edge e to mst
                mst.add(e);
            }
        }

        return mst;
    }

    public static class MST<V> {
        private Queue<Edge<V>> q;
        private int weight;
        private Graph<V, Edge<V>> g;

        public MST(Graph<V, Edge<V>> g) {
            this.g = g;
            this.q = new ArrayDeque<>();
            this.weight = 0;
        }

        public void add(Edge<V> e) {
            q.offer(e);
            weight += e.getWeight();
        }

        public int getWeight() {
            return weight;
        }

        public int size() {
            return q.size();
        }

        @Override
        public String toString() {
            StringBuilder writer = new StringBuilder();

            q.stream().sorted((o1, o2) -> {
                int tIndex1 = g.indexOf(o1.getFrom());
                int tIndex2 = g.indexOf(o2.getFrom());

                if (tIndex1 != tIndex2) {
                    return Integer.compare(tIndex1, tIndex2);
                } else {
                    int hIndex1 = g.indexOf(o1.getTo());
                    int hIndex2 = g.indexOf(o2.getTo());

                    return Integer.compare(hIndex1, hIndex2);
                }
            }).forEach(e -> {
                writer.append(e.getFrom() + "->" + e.getTo() + ",");
            });

            return writer.length() > 0 ? writer.deleteCharAt(writer.length() - 1).toString() : "";
        }
    }
}
