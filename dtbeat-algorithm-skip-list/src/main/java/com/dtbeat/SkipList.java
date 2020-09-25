package com.dtbeat;

import java.util.Comparator;
import java.util.Random;

/**
 * Skip List
 *
 * @author elvinshang
 * @version Id: SkipList.java, v0.0.1 2020/9/24 09:09 dtbeat.com $
 */
public class SkipList<K, V> {
    private static final int MAX_LEVEL = 64;
    private static final double P = 0.25;

    private Random rnd;
    private Comparator<K> comparator;
    private Node header;
    private Node tail;
    private long length;
    private int level;

    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.rnd = new Random(System.currentTimeMillis());
        this.comparator = comparator;
        this.level = 1;
        this.length = 0;
        this.header = new Node(null, null, MAX_LEVEL);
        this.tail = null;
    }

    public long getLength() {
        return length;
    }

    public boolean contains(K key) {
        return getNode(key) != null;
    }

    public V get(K key) {
        Node node = getNode(key);

        return node == null ? null : node.getVal();
    }

    public void insert(K key, V val) {
        Node[] update = new Node[MAX_LEVEL];
        int[] rank = new int[MAX_LEVEL];

        Node x = this.header;
        for (int i = this.level - 1; i >= 0; i--) {
            rank[i] = i == (this.level - 1) ? 0 : rank[i + 1];
            while (x.level[i].forward != null
                    && (x.level[i].forward.getKey() == null || compare(x.level[i].forward.getKey(), key) < 0)) {
                rank[i] += x.level[i].span;
                x = x.level[i].forward;
            }

            update[i] = x;
        }

        int newLevel = randomLevel();
        if (newLevel > this.level) {
            for (int i = this.level; i < newLevel; i++) {
                rank[i] = 0;
                update[i] = this.header;
                update[i].level[i].span = this.length;
            }

            this.level = newLevel;
        }

        x = new Node(key, val, newLevel);
        for (int i = 0; i < newLevel; i++) {
            x.level[i].forward = update[i].level[i].forward;
            update[i].level[i].forward = x;

            x.level[i].span = update[i].level[i].span - (rank[0] - rank[i]);
            update[i].level[i].span = (rank[0] - rank[i]) + 1;
        }

        for (int i = newLevel; i < this.level; i++) {
            update[i].level[i].span++;
        }

        x.backward = (update[0] == this.header) ? null : update[0];
        if (x.level[0].forward != null) {
            x.level[0].forward.backward = x;
        } else {
            this.tail = x;
        }

        this.length++;
    }

    public V delete(K key) {
        Node[] update = new Node[MAX_LEVEL];
        Node x = this.header;
        for (int i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && (x.level[i].forward.getKey() == null || compare(x.level[i].forward.getKey(), key) < 0)) {
                x = x.level[i].forward;
            }

            update[i] = x;
        }

        x = x.level[0].forward;
        if (x != null && compare(x.getKey(), key) == 0) {
            delete(x, update);

            return x.getVal();
        }

        return null;
    }

    private void delete(Node x, Node[] update) {
        for (int i = 0; i < this.level; i++) {
            if (update[i].level[i].forward == x) {
                update[i].level[i].span += x.level[i].span - 1;
                update[i].level[i].forward = x.level[i].forward;
            } else {
                update[i].level[i].span -= 1;
            }
        }

        if (x.level[0].forward != null) {
            x.level[0].forward.backward = x.backward;
        } else {
            this.tail = x.backward;
        }

        while (this.level > 1 && this.header.level[this.level - 1].forward == null) {
            this.level--;
        }

        this.length--;
    }

    private Node getNode(K key) {
        if (this.length == 0) {
            return null;
        }

        Node x = this.header;
        for (int i = this.level - 1; i >= 0; i--) {
            while (x.level[i].forward != null
                    && (x.level[i].forward.getKey() == null || compare(x.level[i].forward.getKey(), key) < 0)) {
                x = x.level[i].forward;
            }
        }

        x = x.level[0].forward;

        return x != null && compare(x.getKey(), key) == 0 ? x : null;
    }

    private int randomLevel() {
        int level = 1;
        while ((rnd.nextLong() & 0xFFFF) < (int) (P * 0xFFFF)) {
            level++;
        }

        return level;
    }

    @SuppressWarnings("unchecked")
    private int compare(K e1, K e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable<K>) e1).compareTo(e2);
    }

    /**
     * Skip List Level
     */
    private static class Level {
        private Node forward;
        private long span;

        public Level() {
            this.forward = null;
            this.span = 0;
        }
    }

    /**
     * Skip List Node
     */
    private static class Node {
        private Object key;
        private Object val;
        private Node backward;
        private Level[] level;

        public Node(Object key, Object val, int level) {
            this.key = key;
            this.val = val;
            this.level = new Level[level];
            for (int i = 0; i < level; i++) {
                this.level[i] = new Level();
            }
            this.backward = null;
        }

        public <K> K getKey() {
            return (K) key;
        }

        public <V> V getVal() {
            return (V) val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", level= " + level.length +
                    ranksToString() +
                    '}';
        }

        private String ranksToString() {
            StringBuilder writer = new StringBuilder();
            for (int i = 0; i < this.level.length; i++) {
                if (level[i].forward != null) {
                    writer.append(String.format(", level[%d].span= %d", i, level[i].span));
                }
            }

            return writer.toString();
        }
    }
}
