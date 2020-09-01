package com.dtbeat.algorithm.datastruct.graph;

/**
 * Graph Interface
 *
 * @author elvinshang
 * @version Id: Graph.java, v0.0.1 2020/8/30 21:30 dtbeat.com $
 */
public interface Graph {
    /**
     * Adds edge to graph
     *
     * @param v      the side vertex of the edge
     * @param w      the other size vertex of the edge
     * @param weight the weight of the edge
     */
    void addEdge(int v, int w, double weight);

    /**
     * Returns in-degree of the specified vertex
     *
     * @param vertex the specified vertex
     * @return in degree of the specified vertex
     */
    int getIndegree(int vertex);

    /**
     * Returns out-degree of the specified vertex
     *
     * @param vertex the specified vertex
     * @return out degree of the specified vertex
     */
    int getOutdegree(int vertex);

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    int getVertexNum();

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    int getEdgeNum();
}
