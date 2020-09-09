package com.dtbeat.algorithm.graph;

import com.dtbeat.graph.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyPathTest {
    @Test
    public void testKeyPath() {
        String[] vertices = new String[]{"V1", "V2", "V3", "V4", "V5", "V6", "V7"};
        int[][] edges = new int[][]{
                {0, 1, 3},
                {0, 2, 2},
                {0, 3, 6},
                {1, 3, 2},
                {1, 4, 4},
                {2, 3, 1},
                {2, 5, 3},
                {3, 4, 1},
                {4, 6, 3},
                {5, 6, 4},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, vertices.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }

        KeyPath<Integer> keyPath = new KeyPath<>(g);
        assertNotNull(keyPath);
        assertEquals(10, keyPath.getMaxCost());
        assertEquals("0->1,0->3,1->4,3->4,4->6", keyPath.getFormatedKeyActivities());

    }
}