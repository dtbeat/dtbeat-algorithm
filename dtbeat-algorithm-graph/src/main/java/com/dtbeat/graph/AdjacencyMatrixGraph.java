package com.dtbeat.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Adjacency Matrix Graph
 *
 * @author elvinshang
 * @version Id: AdjacencyMatrixGraph.java, v0.0.1 2020/9/2 14:39 dtbeat.com $
 */
public class AdjacencyMatrixGraph implements Graph<Integer, Edge<Integer>> {
    private final int vertexSize;
    private int edgeSize;
    private Edge<Integer>[][] edges;
    private int[] inDegrees;
    private int[] outDegrees;
    private GraphType type;

    public AdjacencyMatrixGraph(int v, GraphType type) {
        this.type = type;
        if (v <= 0) {
            throw new IllegalArgumentException("v");
        }

        this.vertexSize = v;
        this.edgeSize = 0;
        this.edges = new Edge[v][v];
        this.inDegrees = new int[v];
        this.outDegrees = new int[v];
    }

    @Override
    public GraphType getType() {
        return type;
    }

    @Override
    public Iterable<Edge<Integer>> adj(Integer v) {
        validateVertex(v);

        return () -> new EdgeItr(v.intValue());
    }

    @Override
    public void addEdge(Integer v, Integer w, int weight) {
        validateVertex(v);
        validateVertex(w);
        if (v.compareTo(w) == 0) {
            throw new IllegalArgumentException("the two vertices cannot coincide");
        }

        edges[v.intValue()][w.intValue()] = new Edge(v.intValue(), w.intValue(), 1);
        inDegrees[w.intValue()] += 1;
        outDegrees[v.intValue()] += 1;
        edgeSize += 1;

    }

    @Override
    public int inDegree(Integer v) {
        validateVertex(v);

        return inDegrees[v.intValue()];
    }

    @Override
    public int outDegree(Integer v) {
        validateVertex(v);

        return outDegrees[v.intValue()];
    }

    @Override
    public int vertexSize() {
        return vertexSize;
    }

    @Override
    public int edgeSize() {
        return edgeSize;
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

    @Override
    public Iterable<Integer> vertices() {
        return this::iterator;
    }

    @Override
    public Iterable<Edge<Integer>> edges(boolean ignoredSymEdge) {
        List<Edge<Integer>> result = new ArrayList<>(this.edgeSize);
        HashSet<String> marked = new HashSet<>();

        for (int i = 0; i < vertexSize; i++) {
            for (int j = 0; j < vertexSize; j++) {
                Edge<Integer> edge = edges[i][j];
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

    @Override
    public int indexOf(Integer v) {
        return v;
    }

    private class EdgeItr implements Iterator<Edge<Integer>> {
        int cursor = 0;
        private final int v;

        public EdgeItr(int v) {
            this.v = v;
            this.cursor = findCursor(0);
        }

        @Override
        public boolean hasNext() {
            return this.cursor != vertexSize;
        }

        @Override
        public Edge next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            try {
                Edge next = edges[v][cursor];
                cursor = findCursor(cursor + 1);

                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private int findCursor(int index) {
            int result = -1;
            for (int i = index; i < vertexSize; i++) {
                if (edges[v][i] != null) {
                    result = i;
                    break;
                }
            }

            return result == -1 ? vertexSize : result;
        }
    }
}
