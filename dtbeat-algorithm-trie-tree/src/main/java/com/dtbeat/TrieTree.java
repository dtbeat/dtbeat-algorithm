package com.dtbeat;

import com.dtbeat.algorithm.lang.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * Tire Search Tree
 *
 * @author elvinshang
 * @version Id: TireST.java, v0.0.1 2020/9/17 10:56 dtbeat.com $
 */
public class TrieTree<V> {
    private static final int R = 256;
    private Node root;
    private int size;

    public TrieTree() {
        this.root = null;
        this.size = 0;
    }

    public V get(String key) {
        Node node = getNode(key);

        return node == null ? null : (V) node.val;
    }

    public void put(String key, V value) {
        if (key == null || key.isEmpty()) {
            return;
        }

        if (root == null) {
            root = new Node();
        }

        final int N = key.length();
        Node x = root;
        int d = 0;

        for (; d < N; d++) {
            char c = key.charAt(d);

            Node next = x.next[c];
            if (next == null) {
                next = new Node();
                x.next[c] = next;
            }

            x = next;
        }

        x.val = value;
        this.size++;
    }

    public void delete(String key) {
        if (this.root == null
                || key == null
                || key.isEmpty()) {
            return;
        }

        final int N = key.length();
        Node x = root;
        int d = 0;
        int p = -1;
        Node pNode = null;

        for (; d < N; d++) {
            char c = key.charAt(d);
            x = x.next[c];
            if (d < N - 1 && x != null && x.val != null) {
                p = d + 1;
                pNode = x;
            }
        }

        if (x == null) {
            return;
        }

        x.val = null;

        // check that has child
        if (hasChild(x)) {
            return;
        }

        // check that prefix is key
        if (p != -1) {
            pNode.next[key.charAt(p)] = null;
        }
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String s) {
        Node node = getNode(s);
        if (node == null) {
            return null;
        }

        HashSet<String> ret = new HashSet<>();
        int r = 0;
        Stack<Tuple<Node, Tuple<String, Integer>>> q = new Stack<>();
        q.push(new Tuple<>(node, new Tuple<>(s, 0)));

        while (!q.isEmpty()) {
            Tuple<Node, Tuple<String, Integer>> state = q.peek();
            node = state.getT1();
            s = state.getT2().getT1();
            r = state.getT2().getT2();

            // key end
            if (node.val != null && !ret.contains(s)) {
                ret.add(s);
            }

            // get child
            Node child = null;
            while (r < R && (child = node.next[r++]) == null) {
            }

            if (child != null) {
                state.getT2().setT2(r);
                q.push(new Tuple<>(child, new Tuple<>(s + (char) (r - 1), 0)));
            } else {
                q.pop();
            }
        }

        return ret;
    }

    public Iterable<String> keysThatMatch(String s) {
        Node node = root;
        HashSet<String> ret = new HashSet<>();
        int r = 0;
        String pre = "";
        Stack<Tuple<Node, Tuple<String, Integer>>> q = new Stack<>();
        q.push(new Tuple<>(node, new Tuple<>(pre, 0)));

        while (!q.isEmpty()) {
            Tuple<Node, Tuple<String, Integer>> state = q.peek();
            node = state.getT1();
            pre = state.getT2().getT1();
            r = state.getT2().getT2();
            int d = pre.length();

            // key end
            if (node.val != null && !ret.contains(pre)) {
                ret.add(pre);
            }

            // check d
            if (d == s.length()) {
                q.pop();
                continue;
            }

            // get child
            Node child = null;
            for (; r < R; r++) {
                char next = s.charAt(d);
                if (node.next[r] != null && (next == '.' || next == r)) {
                    child = node.next[r];
                    r++;
                    break;
                }
            }

            if (child != null) {
                state.getT2().setT2(r);
                q.push(new Tuple<>(child, new Tuple<>(pre + (char) (r - 1), 0)));
            } else {
                q.pop();
            }
        }

        return ret;
    }

    public String longestPrefixOf(String s) {
        if (this.root == null
                || s == null
                || s.isEmpty()) {
            return "";
        }

        final int N = s.length();
        Node x = root;
        int d = 0;

        for (; d < N; d++) {
            char c = s.charAt(d);

            x = x.next[c];
            if (x == null) {
                break;
            }
        }

        return s.substring(0, d);
    }

    private Node getNode(String key) {
        if (this.root == null
                || key == null) {
            return null;
        }

        if (key.isEmpty()) {
            return this.root;
        }

        final int N = key.length();
        Node x = root;
        int d = 0;

        for (; d < N; d++) {
            char c = key.charAt(d);

            x = x.next[c];
            if (x == null) {
                break;
            }
        }

        return x;
    }

    private boolean hasChild(Node x) {
        for (int i = 0; i < R; i++) {
            if (x.next[i] != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Trie Search Tree Node
     */
    private static class Node {
        private Object val;
        private Node[] next;

        public Node() {
            this(null);
        }

        public Node(Object val) {
            this.val = val;
            this.next = new Node[R];
        }
    }
}
