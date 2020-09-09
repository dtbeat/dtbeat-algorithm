package com.dtbeat.algorithm.graph;

import com.dtbeat.graph.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TopologySortingTest extends BaseGraphTest {
    @Test
    public void testZeroInDegree() {
        char[] chars = new char[]{'1', '2', '3', '4', '5'};
        int[][] edges = new int[][]{
                {0, 1},
                {0, 3},
                {1, 2},
                {2, 4},
                {3, 2},
                {3, 4},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }

        Iterable<Integer> seq = TopologySorting.zeroInDegree(g);
        assertNotNull(seq);

        assertEquals("0,1,3,2,4", toString(seq));
    }

    @Test
    public void testDFS() {
        char[] chars = new char[]{'1', '2', '3', '4', '5'};
        int[][] edges = new int[][]{
                {0, 1},
                {0, 3},
                {1, 2},
                {2, 4},
                {3, 2},
                {3, 4},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }

        Iterable<Integer> seq = TopologySorting.dfs(g);
        assertNotNull(seq);

        assertEquals("0,1,3,2,4", toString(seq));
    }
}