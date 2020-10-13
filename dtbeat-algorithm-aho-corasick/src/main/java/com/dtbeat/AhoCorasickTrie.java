package com.dtbeat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * AhoCorasick
 *
 * @author elvinshang
 * @version Id: AhoCorasick.java, v0.0.1 2020/10/12 10:02 dtbeat.com $
 */
public class AhoCorasickTrie {
    private static final int R = 256;

    private Node root;
    private List<String> patterns;

    public AhoCorasickTrie(List<String> patterns) {
        this.patterns = Objects.requireNonNull(patterns);
        this.root = new Node('\0');
        this.buildTrie(patterns);
        this.buildFail();
    }

    private void buildTrie(List<String> patterns) {
        for (int i = 0; i < patterns.size(); i++) {
            Node cur = this.root;
            String pattern = patterns.get(i);
            for (int j = 0; j < pattern.length(); j++) {
                char c = pattern.charAt(j);
                if (cur.next[c] == null) {
                    cur.next[c] = new Node(c);
                }
                cur = cur.next[c];
            }

            cur.output.add(pattern.length());
        }
    }

    private void buildFail() {
        Deque<Node> q = new ArrayDeque<>();
        q.offer(this.root);

        while (!q.isEmpty()) {
            Node node = q.poll();
            for (int i = 0; i < node.next.length; i++) {
                Node child = node.next[i];
                if (child == null) {
                    continue;
                }

                if (node == root) {
                    child.fail = root;
                } else {
                    Node fafail = node.fail;
                    while (fafail != null) {
                        if (fafail.next[i] != null) {
                            child.fail = fafail.next[i];
                            if (!child.fail.output.isEmpty()) {
                                child.output.addAll(child.fail.output);
                            }

                            break;
                        }

                        fafail = fafail.fail;
                    }

                    if (fafail == null) {
                        child.fail = root;
                    }
                }

                q.offer(child);
            }
        }
    }

    public List<Integer> exactMatchSearch(String key) {
        List<Integer> ret = new ArrayList<>();
        if (key == null || key.isEmpty()) {
            return ret;
        }

        Node cur = this.root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(cur.next[c] == null && cur == root) {
                continue;
            }

            for (Integer wc : cur.output) {
                ret.add(i - wc + 1);
                ret.add(wc);
            }

            while (cur != root && cur.next[c] == null) {
                cur = cur.fail;
            }

            if(cur.next[c] == null) {
                continue;
            }

            cur = cur.next[c];
        }

        for (Integer wc : cur.output) {
            ret.add(key.length() - wc);
            ret.add(wc);
        }

        return ret;
    }

    /**
     * Aho-Corasick Node
     */
    private static class Node {
        private char c;
        private Node[] next;
        private Node fail;
        private List<Integer> output;

        public Node(char c) {
            this.c = c;
            this.next = new Node[R];
            this.fail = null;
            this.output = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "c=" + c +
                    '}';
        }
    }
}
