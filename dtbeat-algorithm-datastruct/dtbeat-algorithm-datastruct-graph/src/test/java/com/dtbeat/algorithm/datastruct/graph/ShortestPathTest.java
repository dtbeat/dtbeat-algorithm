package com.dtbeat.algorithm.datastruct.graph;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class ShortestPathTest {
    private static final Logger LOG = LoggerFactory.getLogger(ShortestPath.class);

    @Test
    public void testDijkstra() {
        char[] chars = new char[]{'1', '2', '3', '4', '5', '6'};
        int[][] edges = new int[][]{
                {0, 1, 1},
                {0, 2, 12},
                {1, 2, 9},
                {1, 3, 3},
                {2, 4, 5},
                {3, 2, 4},
                {3, 4, 13},
                {3, 5, 15},
                {4, 5, 4},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }

        ShortestPath<Integer> sp = ShortestPath.dijkstra(g, 0);
        if (LOG.isDebugEnabled()) {
            LOG.debug(sp.toString());
        }

        assertEquals("0->0:0,0->1:1,3->2:8,1->3:4,2->4:13,4->5:17", sp.toString());
    }

    @Test
    public void testBellmanFord() {
        char[] chars = new char[]{'1', '2', '3', '4', '5', '6'};
        int[][] edges = new int[][]{
                {0, 1, 1},
                {0, 2, 12},
                {1, 2, 9},
                {1, 3, 3},
                {2, 4, 5},
                {3, 2, 4},
                {3, 4, 13},
                {3, 5, 15},
                {4, 5, 4},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }

        ShortestPath<Integer> sp = ShortestPath.bellmanFord(g);
        if (LOG.isDebugEnabled()) {
            LOG.debug(sp.toString());
        }

        assertEquals("0->0:0,0->1:1,3->2:8,1->3:4,2->4:13,4->5:17", sp.toString());
    }

    @Test
    public void testSPFA() {
        char[] chars = new char[]{'1', '2', '3', '4', '5', '6'};
        int[][] edges = new int[][]{
                {0, 1, 1},
                {0, 2, 12},
                {1, 2, 9},
                {1, 3, 3},
                {2, 4, 5},
                {3, 2, 4},
                {3, 4, 13},
                {3, 5, 15},
                {4, 5, 4},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }

        ShortestPath<Integer> sp = ShortestPath.spfa(g, 0);
        if (LOG.isDebugEnabled()) {
            LOG.debug(sp.toString());
        }

        assertEquals("0->0:0,0->1:1,3->2:8,1->3:4,2->4:13,4->5:17", sp.toString());
    }
}