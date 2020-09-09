package com.dtbeat.graph;

import java.util.Objects;

/**
 * Edge
 *
 * @author elvinshang
 * @version Id: Edge.java, v0.0.1 2020/9/2 16:31 dtbeat.com $
 */
public class Edge<V> implements Comparable<Edge<V>> {
    private V from;
    private V to;
    private int weight;

    public Edge(V from, V to, int weight) {
        this.from =  Objects.requireNonNull(from);
        this.to =  Objects.requireNonNull(to);
        this.weight = weight;
    }

    public V getFrom() {
        return from;
    }

    public V getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge<V> o) {
        return Integer.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return weight == edge.weight &&
                Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }
}
