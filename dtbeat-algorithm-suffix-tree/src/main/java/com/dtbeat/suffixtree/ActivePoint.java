package com.dtbeat.suffixtree;

/**
 * ActivePoint
 *
 * @author elvinshang
 * @version Id: ActivePoint.java, v0.0.1 2020/9/13 18:57 dtbeat.com $
 */
class ActivePoint<E, S extends Iterable<E>> {
    private Node<E, S> node;
    private Edge<E, S> edge;
    private int length;

    public ActivePoint(Node<E, S> node, Edge<E, S> edge, int length) {
        this.node = node;
        this.edge = edge;
        this.length = length;
    }

    public void set(Node<E, S> node, Edge<E, S> edge, int length) {
        this.node = node;
        this.edge = edge;
        this.length = length;
    }

    public Node<E, S> getNode() {
        return node;
    }

    public void setNode(Node<E, S> node) {
        this.node = node;
    }

    public Edge<E, S> getEdge() {
        return edge;
    }

    public void setEdge(Edge<E, S> edge) {
        this.edge = edge;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void increase() {
        this.length++;
    }

    public void decrease() {
        this.length--;
    }

    @Override
    public String toString() {
        return "{" + node + ", " + edge + ", " + length + "}";
    }
}
