package com.dtbeat.algorithm.graph;

import com.dtbeat.graph.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * DigraphTest
 *
 * @author elvinshang
 * @version Id: DigraphTest.java, v0.0.1 2020/9/2 20:38 dtbeat.com $
 */
public class DigraphTest extends BaseGraphTest {
    @Test
    public void testDfsWithAdjacencyListAndDigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 1},
                {1, 5},
                {1, 4},
                {1, 2},
                {3, 2},
                {4, 3},
                {4, 1},
                {5, 6},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, 7);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.dfs(g);

        assertEquals("ABCEDFG", toString(chars, result));
    }

    @Test
    public void testDfsWithAdjacencyMatrixAndDigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 1},
                {1, 5},
                {1, 4},
                {1, 2},
                {3, 2},
                {4, 3},
                {4, 1},
                {5, 6},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyMatrix, 7);
        assertTrue(g instanceof AdjacencyMatrixGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.dfs(g);

        assertEquals("ABCEDFG", toString(chars, result));
    }

    @Test
    public void testBfsWithAdjacencyListAndDigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 1},
                {1, 5},
                {1, 4},
                {1, 2},
                {3, 2},
                {4, 3},
                {4, 1},
                {5, 6},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyList, 7);
        assertTrue(g instanceof AdjacencyListGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.bfs(g);

        assertEquals("ABCEFDG", toString(chars, result));
    }

    @Test
    public void testBfsWithAdjacencyMatrixAndDigraph() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] edges = new int[][]{
                {0, 1},
                {1, 5},
                {1, 4},
                {1, 2},
                {3, 2},
                {4, 3},
                {4, 1},
                {5, 6},
        };

        Graph<Integer, Edge<Integer>> g = Graphs.newDigraph(GraphStoreType.AdjacencyMatrix, 7);
        assertTrue(g instanceof AdjacencyMatrixGraph);
        for (int i = 0; i < edges.length; i++) {
            g.addEdge(edges[i][0], edges[i][1]);
        }
        List<Integer> result = Graphs.bfs(g);

        assertEquals("ABCEFDG", toString(chars, result));
    }
}
