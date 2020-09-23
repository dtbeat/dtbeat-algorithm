package com.dtbeat;

import com.dtbeat.pq.MinPriorityQueue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * HuffmanCoding
 *
 * @author elvinshang
 * @version Id: HuffmanCoding.java, v0.0.1 2020/9/23 22:37 dtbeat.com $
 */
public class HuffmanCoding {
    private static final int R = 256;

    public String compress(String s) {
        final int N = s.length();
        int[] freq = new int[R];

        for (int i = 0; i < N; i++) {
            freq[s.charAt(i)]++;
        }

        Node root = createHuffmanTree(freq);
        String[] codes = codeHuffmanTree(root);

        StringBuilder writer = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (codes[s.charAt(i)] != null) {
                writer.append(codes[s.charAt(i)]);
            }
        }

        return writer.toString();
    }

    private String[] codeHuffmanTree(Node root) {
        String[] codes = new String[R];

        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (node.parent != null) {
                node.code = node.parent.code + (node.parent.left == node ? "0" : "1");
            }

            if(node.isLeaf()) {
                codes[node.c] = node.code;
            } else {
                q.offer(node.left);
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
        }

        return codes;
    }

    private Node createHuffmanTree(int[] freq) {
        MinPriorityQueue<Node> q = new MinPriorityQueue<>();
        for (int i = 0; i < R; i++) {
            if (freq[i] != 0) {
                q.add(new Node((char) i, freq[i]));
            }
        }

        Node root = null;
        while (!q.isEmpty()) {
            Node left = q.dequeue();
            Node right = q.isEmpty() ? null : q.dequeue();

            root = new Node(left.weight + (right == null ? 0 : right.weight));
            root.left = left;
            root.right = right;
            left.parent = root;
            if (right != null) {
                right.parent = root;
            }

            if (!q.isEmpty()) {
                q.add(root);
            }
        }


        return root;
    }

    /**
     * Huffman Tree Node
     */
    private class Node implements Comparable<Node> {
        private char c;
        private String code;
        private int weight;
        private Node parent;
        private Node left;
        private Node right;

        public Node(char c, int weight) {
            this.c = c;
            this.weight = weight;
            this.code = "";
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public Node(int weight) {
            this('\0', weight);
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "c=" + c +
                    ", code='" + code + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}

