package com.dtbeat.algorithm.datastruct.graph;

/**
 * Graph Interface
 *
 * @author elvinshang
 * @version Id: Graph.java, v0.0.1 2020/8/30 21:30 dtbeat.com $
 */
public interface Graph<V, E> extends Iterable<V> {
    /**
     * Returns graph type.
     *
     * @return graph type
     */
    GraphType getType();

    /**
     * Returns the vertices adjacent from vertex {@code v} in this graph
     *
     * @param v the vertex
     * @return the vertices adjacent from vertex {@code v} in this graph
     */
    Iterable<E> adj(V v);

    /**
     * Adds edge to graph
     *
     * @param v the side vertex of the edge
     * @param w the other size vertex of the edge
     */
    default void addEdge(V v, V w) {
        addEdge(v, w, 1);
    }

    /**
     * Adds edge to graph
     *
     * @param v      the side vertex of the edge
     * @param w      the other size vertex of the edge
     * @param weight the weight of the edge
     */
    void addEdge(V v, V w, int weight);

    /**
     * Returns in-degree of the specified vertex
     *
     * @param v the specified vertex
     * @return in degree of the specified vertex
     */
    int inDegree(V v);

    /**
     * Returns out-degree of the specified vertex
     *
     * @param vertex the specified vertex
     * @return out degree of the specified vertex
     */
    int outDegree(V v);

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    int vertexSize();

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    int edgeSize();

    /**
     * Returns vertices of the graph
     *
     * @return vertices of the graph
     */
    Iterable<V> vertices();

    /**
     * Returns edges of the graph
     *
     * @return edges of the graph
     */
    default Iterable<Edge<V>> edges() {
        return edges(true);
    }

    /**
     * Returns edges of the graph
     *
     * @param ignoredSymEdge if true and the graph is undigraph, ignore sym edge
     * @return edges of the graph
     */
    Iterable<Edge<V>> edges(boolean ignoredSymEdge);

    /**
     * Returns index of the vertex
     *
     * @param v the vertex
     * @return index of the vertex
     */
    int indexOf(V v);
}
