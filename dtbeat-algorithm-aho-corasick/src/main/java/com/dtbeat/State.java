package com.dtbeat;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Aho-Corasick State
 *
 * @author elvinshang
 * @version Id: State.java, v0.0.1 2020/10/12 19:57 dtbeat.com $
 */
public class State {
    private int depth;
    private int index;
    private State failure;
    private Set<Integer> outputs;
    private TreeMap<Character, State> success = new TreeMap<>();

    public State() {
        this(0);
    }

    public State(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addOutput(int keyword) {
        if (this.outputs == null) {
            this.outputs = new TreeSet<>(Collections.reverseOrder());
        }

        this.outputs.add(keyword);
    }

    public void addOutputs(Collection<Integer> keywords) {
        for (Integer keyword : keywords) {
            addOutput(keyword);
        }
    }

    public Collection<Integer> getOutputs() {
        return this.outputs == null ? Collections.emptyList() : this.outputs;
    }

    public Integer getLargestValueId() {
        if (outputs == null || outputs.size() == 0) return null;

        return outputs.iterator().next();
    }

    public Map<Character, State> getSuccess() {
        return Collections.unmodifiableMap(this.success);
    }

    public Collection<State> getStates() {
        return this.success.values();
    }

    public Collection<Character> getTransitions() {
        return this.success.keySet();
    }

    public State getFailure() {
        return this.failure;
    }

    public void setFailure(State failure, int fail[]) {
        this.failure = failure;
        fail[index] = failure.index;
    }

    public State nextState(Character character) {
        return nextState(character, false);
    }

    public State addState(Character character) {
        State nextState = nextStateIgnoreRootState(character);
        if (nextState == null) {
            nextState = new State(this.depth + 1);
            this.success.put(character, nextState);
        }
        return nextState;
    }

    public boolean isFinalState() {
        return this.depth > 0 && this.outputs != null;
    }

    private State nextStateIgnoreRootState(Character character) {
        return nextState(character, true);
    }

    private State nextState(Character character, boolean ignoreRootState) {
        State nextState = this.success.get(character);
        if (!ignoreRootState && nextState == null && this.depth == 0) {
            nextState = this;
        }
        return nextState;
    }
}
