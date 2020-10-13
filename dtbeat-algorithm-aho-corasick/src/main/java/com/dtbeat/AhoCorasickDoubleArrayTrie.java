package com.dtbeat;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AhoCorasickDoubleArrayTrie
 *
 * @author elvinshang
 * @version Id: AhoCorasickDoubleArrayTrie.java, v0.0.1 2020/10/12 19:49 dtbeat.com $
 */
public class AhoCorasickDoubleArrayTrie<V> implements Serializable {
    private static final long serialVersionUID = 8640127188720781855L;

    private int[] base;
    private int[] check;
    private int[] fail;
    private int[][] outputs;
    private int size;
    private V[] values;
    private int[] lengths;

    public AhoCorasickDoubleArrayTrie(Map<String, V> keyValues) {
        new Builder().build(keyValues);
    }

    public List<Hit<V>> parseText(String text) {
        ArrayList<Hit<V>> hits = new ArrayList<>();
        int currentState = 0;
        for (int i = 0; i < text.length(); i++) {
            currentState = trans(currentState, text.charAt(i));
            int[] hitOutputs = outputs[currentState];
            if (hitOutputs != null) {
                for (int hit : hitOutputs) {
                    hits.add(new Hit<>(i - lengths[hit] + 1, i + 1, values[hit]));
                }
            }
        }

        return hits;
    }

    private int trans(int currentState, char c) {
        int newState = transWithBase(currentState, c);
        if (newState == -1) {
            currentState = fail[currentState];
            newState = transWithBase(currentState, c);
        }

        return newState;
    }

    private int transWithBase(int currentState, char c) {
        int b = base[currentState];
        int p = b + c + 1;
        if (b != check[p]) {
            return currentState == 0 ? 0 : -1;
        }

        return p;
    }

    public static class Hit<V> {
        private final int start;
        private final int end;
        private final V value;

        public Hit(int start, int end, V value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.format("[%d:%d]=%s", start, end, value);
        }
    }

    public class Builder {
        private State root = new State();
        private int nextCheckPos;
        private boolean[] used;
        private int allocSize;
        private int keySize;

        public void build(Map<String, V> keyValues) {
            values = (V[]) keyValues.values().toArray();
            lengths = new int[keyValues.size()];

            Set<String> keySet = keyValues.keySet();
            buildTrie(keySet);

            buildDoubleArrayTrie(keySet.size());
            used = null;

            buildFail();
            root = null;
        }

        private void buildFail() {
            fail = new int[size + 1];
            outputs = new int[size + 1][];
            Deque<State> q = new ArrayDeque<>();

            for (State state : root.getStates()) {
                state.setFailure(root, fail);
                q.offer(state);
                buildOutput(state);
            }

            while (!q.isEmpty()) {
                State state = q.poll();

                for (Character c : state.getTransitions()) {
                    State target = state.nextState(c);
                    q.offer(target);

                    State fafail = state.getFailure();
                    while (fafail.nextState(c) == null) {
                        fafail = fafail.getFailure();
                    }

                    State newFail = fafail.nextState(c);
                    target.setFailure(newFail, fail);
                    target.addOutputs(newFail.getOutputs());
                    buildOutput(target);
                }
            }
        }

        private void buildOutput(State state) {
            Collection<Integer> outputs = state.getOutputs();
            if (outputs == null || outputs.isEmpty()) {
                return;
            }

            int[] output = new int[outputs.size()];
            Iterator<Integer> it = outputs.iterator();
            for (int i = 0; i < outputs.size(); i++) {
                output[i] = it.next();
            }
            AhoCorasickDoubleArrayTrie.this.outputs[state.getIndex()] = output;
        }

        private void buildTrie(Set<String> keySet) {
            int index = 0;
            for (String keyword : keySet) {
                State cur = this.root;
                for (Character c : keyword.toCharArray()) {
                    cur = cur.addState(c);
                }
                cur.addOutput(index);
                lengths[index] = keyword.length();
                index++;
            }
        }

        private void buildDoubleArrayTrie(int keySize) {
            this.keySize = keySize;
            this.resize(65536 * 32);

            base[0] = 1;
            check[0] = 0;

            List<Map.Entry<Integer, State>> siblings = fetch(root);
            if (!siblings.isEmpty()) {
                insert(siblings);
            }
        }

        private void insert(List<Map.Entry<Integer, State>> siblings) {
            Deque<Map.Entry<Integer, List<Map.Entry<Integer, State>>>> q = new ArrayDeque<>();
            q.offer(new AbstractMap.SimpleEntry<>(null, siblings));

            while (!q.isEmpty()) {
                Map.Entry<Integer, List<Map.Entry<Integer, State>>> current = q.poll();
                siblings = current.getValue();

                int begin = 0;
                int pos = Math.max(siblings.get(0).getKey() + 1, nextCheckPos) - 1;
                int nonzeroNum = 0;
                boolean first = true;

                if (allocSize <= pos) {
                    resize(pos + 1);
                }

                outer:
                while (true) {
                    pos++;

                    if (check[pos] != 0) {
                        nonzeroNum++;
                        continue;
                    }

                    if (first) {
                        nextCheckPos = pos;
                        first = false;
                    }

                    begin = pos - siblings.get(0).getKey();
                    if (allocSize <= (begin + siblings.get(siblings.size() - 1).getKey())) {
                        resize(begin + siblings.get(siblings.size() - 1).getKey() + 1);
                    }

                    if (used[begin]) {
                        continue;
                    }

                    for (int i = 1; i < siblings.size(); i++) {
                        if (check[begin + siblings.get(i).getKey()] != 0) {
                            continue outer;
                        }
                    }

                    break;
                }

                // -- Simple heuristics --
                // if the percentage of non-empty contents in check between the
                // index
                // 'next_check_pos' and 'check' is greater than some constant value
                // (e.g. 0.9),
                // new 'next_check_pos' index is written by 'check'.
                if (1.0 * nonzeroNum / (pos - nextCheckPos + 1) >= 0.95) {
                    // 从位置 next_check_pos 开始到 pos 间，如果已占用的空间在95%以上，下次插入节点时，直接从 pos 位置处开始查找
                    nextCheckPos = pos;
                }

                size = Math.max(size, begin + siblings.get(siblings.size() - 1).getKey() + 1);
                used[begin] = true;
                for (Map.Entry<Integer, State> sibling : siblings) {
                    check[begin + sibling.getKey()] = begin;
                }

                for (Map.Entry<Integer, State> sibling : siblings) {
                    List<Map.Entry<Integer, State>> newSiblings = fetch(sibling.getValue());
                    if (newSiblings.isEmpty()) {
                        base[begin + sibling.getKey()] = (-sibling.getValue().getLargestValueId() - 1);
                    } else {
                        q.offer(new AbstractMap.SimpleEntry<>(begin + sibling.getKey(), newSiblings));
                    }
                    sibling.getValue().setIndex(begin + sibling.getKey());
                }

                Integer baseIndex = current.getKey();
                if (baseIndex != null) {
                    base[baseIndex] = begin;
                }
            }
        }

        private List<Map.Entry<Integer, State>> fetch(State parent) {
            ArrayList<Map.Entry<Integer, State>> ret = new ArrayList<>();
            if (parent.isFinalState()) {
                State finalState = new State(-(parent.getDepth() + 1));
                finalState.addOutput(parent.getLargestValueId());
                ret.add(new AbstractMap.SimpleEntry<>(0, finalState));
            }

            for (Map.Entry<Character, State> entry : parent.getSuccess().entrySet()) {
                ret.add(new AbstractMap.SimpleEntry<>(entry.getKey() + 1, entry.getValue()));
            }

            return ret;
        }

        private int resize(int newSize) {
            int[] base2 = new int[newSize];
            int[] check2 = new int[newSize];
            boolean[] used2 = new boolean[newSize];
            if (allocSize > 0) {
                System.arraycopy(base, 0, base2, 0, allocSize);
                System.arraycopy(check, 0, check2, 0, allocSize);
                System.arraycopy(used, 0, used2, 0, allocSize);
            }

            base = base2;
            check = check2;
            used = used2;

            return allocSize = newSize;
        }
    }
}
