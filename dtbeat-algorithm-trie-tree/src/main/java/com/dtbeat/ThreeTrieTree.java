package com.dtbeat;

/**
 * Three TrieTree
 *
 * @author elvinshang
 * @version Id: ThreeTrieTree.java, v0.0.1 2020/9/18 17:46 dtbeat.com $
 */
public class ThreeTrieTree<V> {
    private Node root;
    private int size;

    public ThreeTrieTree() {
        this.root = null;
        this.size = 0;
    }

    public V get(String s) {
        Node ret = getNode(s);

        return ret == null ? null : (V) ret.val;
    }

    public void put(String s, V value) {
        if (root == null) {
            root = new Node();
        }

        final int N = s.length();
        Node x = this.root;
        int d = 0;
        while (d < N) {
            char c = s.charAt(d);
            if (c < x.c) {
                x.left = getOrCreate(x.left, c);
                x = x.left;
            } else if (c > x.c) {
                x.right = getOrCreate(x.right, c);
                x = x.right;
            } else {
                x.mid = getOrCreate(x.mid, c);
                x = x.mid;
                d++;
            }
        }

        x.val = value;
    }

    private Node getNode(String s) {
        if (root == null) {
            return null;
        }

        final int N = s.length();
        Node x = this.root;
        int d = 0;
        while (d < N && x != null) {
            char c = s.charAt(d);
            if (c < x.c) {
                x = x.left;
            } else if (c > x.c) {
                x = x.right;
            } else {
                x = x.mid;
                d++;
            }
        }

        return x;
    }

    private Node getOrCreate(Node x, char c) {
        if (x == null) {
            x = new Node();
            x.c = c;
        }

        return x;
    }

    /**
     * Three Trie Tree Node
     */
    private static class Node {
        private char c;
        private Object val;
        private Node left;
        private Node right;
        private Node mid;
    }
}
