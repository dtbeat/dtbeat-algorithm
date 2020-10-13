package com.dtbeat;

import java.util.ArrayList;
import java.util.List;

/**
 * DoubleArrayTrieV2
 *
 * @author elvinshang
 * @version Id: DoubleArrayTrieV2.java, v0.0.1 2020/10/11 09:04 dtbeat.com $
 */
public class DoubleArrayTrieV2 {
    private final static int BUF_SIZE = 65536;
    private final static int UNIT_SIZE = 8;

    private int[] base;
    private int[] check;
    private int size;
    private int allocSize;
    private List<String> keys;
    private int nextCheckPos;
    private boolean[] used;

    public DoubleArrayTrieV2(List<String> keys) {
        this.build(keys);
    }

    public int exactMatchSearch(String key) {
        int result = -1;
        if (key == null || key.isEmpty()) {
            return result;
        }

        int b = base[0];
        int p;

        for (int i = 0; i < key.length(); i++) {
            p = b + key.charAt(i) + 1;
            if (b == check[p]) {
                b = base[p];
            } else {
                return result;
            }
        }

        p = b;
        int n = base[p];
        if (b == check[p] && n < 0) {
            result = -n - 1;
        }

        return result;
    }

    public List<Integer> commonPrefixSearch(String key) {
        ArrayList<Integer> result = new ArrayList<>();
        if (key == null || key.isEmpty()) {
            return result;
        }

        int b = base[0];
        int p;
        int n;

        for (int i = 0; i < key.length(); i++) {
            p = b;
            n = base[p];
            if (b == check[p] && n < 0) {
                result.add(-n - 1);
            }

            p = key.charAt(i) + 1;
            if (b == check[p]) {
                b = check[p];
            } else {
                return result;
            }
        }

        p = b;
        n = base[p];
        if (b == check[p] && n < 0) {
            result.add(-n - 1);
        }

        return result;
    }

    private void build(List<String> keys) {
        if (keys == null || keys.size() == 0) {
            throw new IllegalArgumentException("keys");
        }

        this.keys = keys;
        this.resize(BUF_SIZE * 32);

        this.base[0] = 1;
        this.check[0] = 0;
        this.nextCheckPos = 0;

        Node root = new Node();
        root.code = 0;
        root.depth = 0;
        root.left = 0;
        root.right = keys.size();

        List<Node> siblings = fetch(root);
        insert(siblings);
    }

    private int insert(List<Node> siblings) {
        int begin = 0;
        int pos = ((siblings.get(0).code + 1 > nextCheckPos) ? siblings.get(0).code + 1 : nextCheckPos) - 1;

        boolean first = true;
        outer:
        while (true) {
            pos++;
            if (pos > allocSize) {
                resize(pos + 1);
            }

            if (check[pos] != 0) {
                continue;
            }

            if (first) {
                nextCheckPos = pos;
                first = false;
            }

            begin = pos - siblings.get(0).code;
            if (allocSize < begin + siblings.get(siblings.size() - 1).code) {
                resize(begin + siblings.get(siblings.size() - 1).code + 1);
            }

            if (used[begin]) {
                continue;
            }

            for (int i = 1; i < siblings.size(); i++) {
                if (check[begin + siblings.get(i).code] != 0) {
                    continue outer;
                }
            }

            break;
        }

        used[begin] = true;
        size = size > (begin + siblings.get(siblings.size() - 1).code + 1) ? size : (begin + siblings.get(siblings.size() - 1).code + 1);

        for (int i = 0; i < siblings.size(); i++) {
            check[begin + siblings.get(i).code] = begin;
        }

        // dfs
        for (int i = 0; i < siblings.size(); i++) {
            List<Node> newSiblings = fetch(siblings.get(i));
            if (newSiblings.isEmpty()) {
                base[begin + siblings.get(i).code] = -siblings.get(i).left - 1;
            } else {
                int s = insert(newSiblings);
                base[begin + siblings.get(i).code] = s;
            }
        }

        return begin;
    }

    private List<Node> fetch(Node parent) {
        ArrayList<Node> siblings = new ArrayList<>();
        int pre = 0;

        for (int i = parent.left; i < parent.right; i++) {
            String s = keys.get(i);
            if (s.length() < parent.depth) {
                continue;
            }

            int cur = 0;
            if (s.length() != parent.depth) {
                cur = s.charAt(parent.depth) + 1;
            }

            if (pre > cur) {
                throw new RuntimeException("Keys is unordered");
            }

            if (cur != pre || siblings.isEmpty()) {
                Node node = new Node();
                node.code = cur;
                node.left = i;
                node.depth = parent.depth + 1;

                if (!siblings.isEmpty()) {
                    siblings.get(siblings.size() - 1).right = i;
                }

                siblings.add(node);
            }

            pre = cur;
        }

        if (!siblings.isEmpty()) {
            siblings.get(siblings.size() - 1).right = parent.right;
        }

        return siblings;
    }

    private int resize(int newSize) {
        int[] newBase = new int[newSize];
        int[] newCheck = new int[newSize];
        boolean[] newUsed = new boolean[newSize];

        if (allocSize > 0) {
            System.arraycopy(base, 0, newBase, 0, allocSize);
            System.arraycopy(check, 0, newCheck, 0, allocSize);
            System.arraycopy(used, 0, newUsed, 0, allocSize);
        }

        base = newBase;
        check = newCheck;
        used = newUsed;

        return (allocSize = newSize);
    }

    private static class Node {
        private int code;
        private int depth;
        private int left;
        private int right;
    }
}
