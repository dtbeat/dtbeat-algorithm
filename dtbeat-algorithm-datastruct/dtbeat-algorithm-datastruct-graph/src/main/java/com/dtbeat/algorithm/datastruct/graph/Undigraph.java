package com.dtbeat.algorithm.datastruct.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Undirected Graph
 *
 * @author elvinshang
 * @version Id: UndirectGraph.java, v0.0.1 2020/8/30 21:51 dtbeat.com $
 */
public class Undigraph {
    private int vertices;
    private int edges;
    private List<Node> adj;

    public Undigraph(int vertices) {
        this.vertices = vertices;
        this.edges = 0;
        this.adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            this.adj.add(new Node(i, null));
        }
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        Node node = this.adj.get(v);
        Node newNode = new Node(w, node.next);
        node.next = newNode;

        node = this.adj.get(w);
        newNode = new Node(v, node.next);
        node.next = newNode;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (vertices - 1));
        }
    }


    private class Node {
        private int vertex;
        private Node next;

        public Node(int vertex, Node next) {
            this.vertex = vertex;
            this.next = next;
        }
    }
}
