package com.dtbeat;

/**
 * Merger
 *
 * @author elvinshang
 * @version Id: Merger.java, v0.0.1 2020/9/9 15:50 dtbeat.com $
 */
public interface Merger<E, R> {
    /**
     * Merges the two elements
     *
     * @param r     the init result
     * @param index the index of e
     * @param e     the element to merge
     * @return the result for merging
     */
    R merge(R r, int index, E e);
}
