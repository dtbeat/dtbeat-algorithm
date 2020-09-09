package com.dtbeat.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Adjacency List Graph
 *
 * @author elvinshang
 * @version Id: AdjacencyListGraph.java, v0.0.1 2020/9/2 15:38 dtbeat.com $
 */
public class AdjacencyListGraph implements Graph<Integer, Edge<Integer>> {
    private final int vertexSize;
    private GraphType type;
    private int edgeSize;
    private Bag<Edge<Integer>>[] adj;
    private int[] inDegrees;

    public AdjacencyListGraph(int size, GraphType type) {
        this.vertexSize = size;
        this.type = type;
        this.edgeSize = 0;
        this.inDegrees = new int[size];
        this.adj = new Bag[size];
        for (int i = 0; i < this.adj.length; i++) {
            this.adj[i] = new Bag<>();
        }
    }

    @Override
    public int indexOf(Integer v) {
        return v;
    }

    @Override
    public GraphType getType() {
        return type;
    }

    @Override
    public Iterable<Edge<Integer>> adj(Integer v) {
        validateVertex(v);

        return adj[v.intValue()];
    }

    @Override
    public void addEdge(Integer v, Integer w, int weight) {
        validateVertex(v);
        validateVertex(w);
        if (v.compareTo(w) == 0) {
            throw new IllegalArgumentException("the two vertices ");
        }

        adj[v.intValue()].add(new Edge(v.intValue(), w.intValue(), weight));
        inDegrees[v.intValue()] += 1;
        edgeSize += 1;
    }

    @Override
    public int inDegree(Integer vertex) {
        validateVertex(vertex);

        return inDegrees[vertex.intValue()];
    }

    @Override
    public int outDegree(Integer vertex) {
        validateVertex(vertex);

        return adj[vertex.intValue()].size();
    }

    @Override
    public int vertexSize() {
        return vertexSize;
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public Iterable<Integer> vertices() {
        return this::iterator;
    }

    @Override
    public Iterable<Edge<Integer>> edges(boolean ignoredSymEdge) {
        List<Edge<Integer>> result = new ArrayList<>(this.edgeSize);
        HashSet<String> marked = new HashSet<String>();

        for (Bag<Edge<Integer>> edges : adj) {
            for (Edge<Integer> edge : edges) {
                if (type == GraphType.Undirected && ignoredSymEdge) {
                    Integer hV = edge.getTo();
                    Integer tV = edge.getFrom();
                    String eKey = hV.intValue() >= tV.intValue() ? tV.toString() + "_" + hV.toString() :
                            hV.toString() + "_" + tV.toString();
                    if (!marked.contains(eKey)) {
                        result.add(edge);
                        marked.add(eKey);
                    }
                } else {
                    result.add(edge);
                }
            }
        }

        return result;
    }

    private void validateVertex(Integer v) {
        Objects.requireNonNull(v);
        if (v.intValue() < 0 || v.intValue() >= this.vertexSize) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.vertexSize - 1));
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new VertexItr();
    }

    private class VertexItr implements Iterator<Integer> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < vertexSize;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Integer next = Integer.valueOf(cursor);
            cursor += 1;

            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
