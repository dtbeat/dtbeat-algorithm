package com.dtbeat.uf;

/**
 * UnionFindSet
 *
 * @author elvinshang
 * @version Id: UnionFindSet.java, v0.0.1 2020/9/3 00:16 dtbeat.com $
 */
public class UnionFindSet {
    private int[] parent;
    private int[] rank;
    private int size;

    public UnionFindSet(int size) {
        this.size = size;
        this.parent = new int[size];
        this.rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * Returns root of the specified element
     *
     * @param x the element
     * @return root of the specified element
     */
    public int find(int x) {
        if (x < 0 || x >= size) {
            throw new IndexOutOfBoundsException("x");
        }

        return find0(x);
    }

    private int find0(int x) {
        // find root
        int root = x;
        while (root != parent[root]) {
            root = parent[root];
        }

        // compact
        while (x != parent[x]) {
            int p = parent[x];
            parent[x] = root;
            x = p;
        }

        return root;
    }

    /**
     * Unions the specified two elements
     *
     * @param x one element to union
     * @param y other element to union
     */
    public void union(int x, int y) {
        if (x == y) {
            return;
        }

        if (x < 0 || x >= size) {
            throw new IndexOutOfBoundsException("x");
        }

        if (y < 0 || y >= size) {
            throw new IndexOutOfBoundsException("x");
        }

        // merge
        if (rank[x] <= rank[y]) {
            parent[find0(x)] = find0(y);
        } else {
            parent[find0(y)] = find0(x);
        }

        // update rank
        if (rank[x] == rank[y]) {
            rank[y] += 1;
        }
    }
}
