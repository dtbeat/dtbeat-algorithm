package com.dtbeat.suffixtree;

import java.util.*;

/**
 * Node
 *
 * @author elvinshang
 * @version Id: Node.java, v0.0.1 2020/9/13 17:54 dtbeat.com $
 */
class Node<E, S extends Iterable<E>> implements Iterable<Edge<E, S>> {
    private final Edge<E, S> in;
    private final Sequence<E, S> sequence;
    private final Map<E, Edge<E, S>> edges;
    private final Set<SequenceTerminal<S>> sequenceTerminals;
    private Node<E, S> link;

    public Node(Edge<E, S> in, Sequence<E, S> sequence) {
        this.in = in;
        this.sequence = sequence;
        this.edges = new HashMap<>();
        this.sequenceTerminals = new HashSet<>();
    }

    void setLink(Node<E, S> node) {
        this.link = node;
    }

    Node<E, S> getLink() {
        return link;
    }

    boolean hasLink() {
        return link != null;
    }

    boolean hasEdge(Object item) {
        return edges.containsKey(item);
    }

    Edge<E, S> getEdge(Object item) {
        return edges.get(item);
    }

    void addEdge(E startItem, Edge<E, S> edge) {
        edges.put(startItem, edge);
    }

    Collection<Edge<E, S>> getEdges() {
        return edges.values();
    }

    int getEdgeSize() {
        return edges.size();
    }

    @Override
    public Iterator<Edge<E, S>> iterator() {
        return edges.values().iterator();
    }

    void tryAddSequenceTerminal(Object item) {
        if (item.getClass().equals(SequenceTerminal.class)) {
            sequenceTerminals.add((SequenceTerminal<S>) item);
        }
    }

    @Override
    public String toString() {
        return in == null ? "root" : "head of edge [" + in.toString() + "]";
    }
}