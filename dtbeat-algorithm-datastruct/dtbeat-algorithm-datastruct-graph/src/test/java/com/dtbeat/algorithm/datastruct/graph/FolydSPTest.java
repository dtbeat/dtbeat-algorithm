package com.dtbeat.algorithm.datastruct.graph;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FolydSPTest {
    @Test
    public void testFolyd() {
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

        FolydSP<Integer> folyd = new FolydSP<>(g);
        assertEquals(1, folyd.getDist(0, 1));
        assertEquals(8, folyd.getDist(0, 2));
        assertEquals(4, folyd.getDist(0, 3));
        assertEquals(13, folyd.getDist(0, 4));
        assertEquals(17, folyd.getDist(0, 5));
    }
}