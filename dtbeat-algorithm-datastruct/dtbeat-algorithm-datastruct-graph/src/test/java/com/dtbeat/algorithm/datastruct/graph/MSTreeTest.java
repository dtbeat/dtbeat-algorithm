package com.dtbeat.algorithm.datastruct.graph;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MSTreeTest extends BaseGraphTest{
    private static final Logger LOG = LoggerFactory.getLogger(MSTreeTest.class);

    @Test
    public void testPrim() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] edges = new int[][]{
                {0, 2, 3},
                {0, 1, 6},
                {1, 5, 3},
                {1, 3, 2},
                {1, 2, 4},
                {1, 0, 6},
                {2, 5, 7},
                {2, 4, 8},
                {2, 1, 4},
                {2, 0, 3},
                {3, 5, 6},
                {3, 1, 2},
                {4, 5, 7},
                {4, 2, 8},
                {5, 4, 7},
                {5, 3, 6},
                {5, 2, 7},
                {5, 1, 3},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newUndigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }

        // Prim
        MSTree.MST<Integer> result = MSTree.prim(g);
        assertNotNull(result);
        if (LOG.isDebugEnabled()) {
            LOG.debug(result.toString());
        }

        assertEquals(19, result.getWeight());
        assertEquals("0->2,1->3,1->5,2->1,5->4", result.toString());
    }

    @Test
    public void testKruskal() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] edges = new int[][]{
                {0, 2, 3},
                {0, 1, 6},
                {1, 5, 3},
                {1, 3, 2},
                {1, 2, 4},
                {1, 0, 6},
                {2, 5, 7},
                {2, 4, 8},
                {2, 1, 4},
                {2, 0, 3},
                {3, 5, 6},
                {3, 1, 2},
                {4, 5, 7},
                {4, 2, 8},
                {5, 4, 7},
                {5, 3, 6},
                {5, 2, 7},
                {5, 1, 3},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newUndigraph(GraphStoreType.AdjacencyList, chars.length);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }

        // Kruskal
        MSTree.MST<Integer> result = MSTree.kruskal(g);
        assertNotNull(result);
        if (LOG.isDebugEnabled()) {
            LOG.debug(result.toString());
        }

        assertEquals(19, result.getWeight());
        assertEquals("0->2,1->2,1->3,1->5,4->5", result.toString());
    }
}