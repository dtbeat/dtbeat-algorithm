package com.dtbeat.algorithm.graph;

import com.dtbeat.graph.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UndigraphTest extends BaseGraphTest {
    @Test
    public void testDfsWithAdjacencyListAndUndigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 5},
                {0, 3},
                {0, 2},
                {1, 4},
                {1, 2},
                {2, 3},
                {2, 1},
                {2, 0},
                {3, 0},
                {4, 1},
                {5, 6},
                {5, 0},
                {6, 5},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newUndigraph(GraphStoreType.AdjacencyList, 7);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.dfs(g);

        assertEquals("ACBEDFG", toString(chars, result));
    }

    @Test
    public void testDfsWithAdjacencyMatrixAndUndigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 5},
                {0, 3},
                {0, 2},
                {1, 4},
                {1, 2},
                {2, 3},
                {2, 1},
                {2, 0},
                {3, 0},
                {4, 1},
                {5, 6},
                {5, 0},
                {6, 5},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newUndigraph(GraphStoreType.AdjacencyMatrix, 7);
        assertTrue(g instanceof AdjacencyMatrixGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.dfs(g);

        assertEquals("ACBEDFG", toString(chars, result));
    }

    @Test
    public void testBfsWithAdjacencyListAndUndigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 5},
                {0, 3},
                {0, 2},
                {1, 4},
                {1, 2},
                {2, 3},
                {2, 1},
                {2, 0},
                {3, 0},
                {4, 1},
                {5, 6},
                {5, 0},
                {6, 5},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newUndigraph(GraphStoreType.AdjacencyList, 7);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.bfs(g);

        assertEquals("ACDFBGE", toString(chars, result));
    }

    @Test
    public void testBfsWithAdjacencyMatrixAndUndigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 5},
                {0, 3},
                {0, 2},
                {1, 4},
                {1, 2},
                {2, 3},
                {2, 1},
                {2, 0},
                {3, 0},
                {4, 1},
                {5, 6},
                {5, 0},
                {6, 5},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newUndigraph(GraphStoreType.AdjacencyMatrix, 7);
        assertTrue(g instanceof AdjacencyMatrixGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.bfs(g);

        assertEquals("ACDFBGE", toString(chars, result));
    }
}