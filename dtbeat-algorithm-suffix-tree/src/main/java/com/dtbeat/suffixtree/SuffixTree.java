package com.dtbeat.suffixtree;

import java.util.Objects;
import java.util.Stack;

/**
 * Suffix Tree
 *
 * @author elvinshang
 * @version Id: SuffixTree.java, v0.0.1 2020/9/13 18:55 dtbeat.com $
 */
public class SuffixTree<E, S extends Iterable<E>> {
    private final Node<E, S> root;
    private final Sequence<E, S> sequence;
    private ActivePoint<E, S> activePoint;
    private Node<E, S> lastInsertedNode;
    private int insertsAtCurrentStep = 0;

    public SuffixTree(S word) {
        this.sequence = new Sequence<>(Objects.requireNonNull(word));
        this.root = new Node<>(null, sequence);
        this.activePoint = new ActivePoint<>(root, null, 0);

        this.buildTree();
    }

    @Override
    public String toString() {
        return Utils.toGraphViz(this);
    }

    private void buildTree() {
        Suffix<E, S> suffix = new Suffix<>(0, 0, this.sequence);
        SequenceCursor<E, S> cursor = new SequenceCursor<>(this.sequence);
        while (cursor.hasNext()) {
            suffix.increase();
            insertsAtCurrentStep = 0;
            insert(cursor, suffix);
            cursor.next();
        }
    }

    private void insert(SequenceCursor<E, S> cursor, Suffix<E, S> suffix) {
        while (!suffix.isEmpty()) {
            if (activePoint.getEdge() == null) {
                Node<E, S> node = activePoint.getNode();
                if (insert(cursor, node, suffix)) {
                    break;
                }
            } else {
                Edge<E, S> edge = activePoint.getEdge();
                if (insert(cursor, edge, suffix)) {
                    break;
                }
            }
        }
    }

    private boolean insert(SequenceCursor<E, S> cursor, Node<E, S> node, Suffix<E, S> suffix) {
        Object item = suffix.getEndItem();
        if (node.hasEdge(item)) {
            if (isNotFirstInsertAtCurrentStep() && !Objects.equals(activePoint.getNode(), root)) {
                setLink(activePoint.getNode());
            }

            activePoint.setEdge(node.getEdge(item));
            activePoint.increase();
            tryResetActivePointToHead();

            return true;
        }

        node.tryAddSequenceTerminal(item);
        Edge<E, S> newEdge = new Edge<>(suffix.getEnd() - 1, node, cursor);
        node.addEdge((E) suffix.getEndItem(), newEdge);
        suffix.decrease();
        fixActivePointAfterInsertion(cursor, suffix);

        if (isNotFirstInsertAtCurrentStep() && !node.equals(root)) {
            getLastInsertedNode().setLink(node);
        }

        return suffix.isEmpty();
    }

    private boolean insert(SequenceCursor<E, S> cursor, Edge<E, S> edge, Suffix<E, S> suffix) {
        Object item = suffix.getEndItem();
        Object nextItem = edge.getItemAt(activePoint.getLength());
        if (item == nextItem) {
            activePoint.increase();
            tryResetActivePointToHead();

            return true;
        }

        split(cursor, edge, suffix);
        suffix.decrease();
        fixActivePointAfterInsertion(cursor, suffix);

        return suffix.isEmpty();
    }

    private void split(SequenceCursor<E, S> cursor, Edge<E, S> edge, Suffix<E, S> suffix) {
        Node<E, S> breakNode = new Node<>(edge, sequence);
        Edge<E, S> newEdge = new Edge<>(suffix.getEnd() - 1, breakNode, cursor);
        breakNode.addEdge((E) suffix.getEndItem(), newEdge);

        Edge<E, S> oldEdge = new Edge<>(edge.getStart() + activePoint.getLength(), breakNode, cursor);
        oldEdge.setHead(edge.getHead());
        breakNode.addEdge(oldEdge.getStartItem(), oldEdge);

        edge.setHead(breakNode);
        edge.setEnd(edge.getStart() + activePoint.getLength());

        setLink(breakNode);
        increaseInsertsAtCurrentStep();
    }

    private void fixActivePointAfterInsertion(SequenceCursor<E, S> cursor, Suffix<E, S> suffix) {
        if (activePoint.getNode() == root && suffix.isEmpty()) {
            activePoint.setEdge(null);
            activePoint.setLength(0);
        } else if (activePoint.getNode() == root) {
            Object item = suffix.getStartItem();
            activePoint.setEdge(root.getEdge(item));
            activePoint.decrease();
            fixActiveEdgeAfterLink(suffix);
            if (activePoint.getLength() == 0) {
                activePoint.setEdge(null);
            }
        } else {
            Node<E, S> activeNode = activePoint.getNode().hasLink() ? activePoint.getNode().getLink() : root;
            activePoint.setNode(activeNode);
            if (activePoint.getEdge() != null) {
                Object item = activePoint.getEdge().getStartItem();
                Edge<E, S> activeEdge = activeNode.getEdge(item);
                activePoint.setEdge(activeEdge);
            }
            fixActiveEdgeAfterLink(suffix);
            if (activePoint.getLength() == 0) {
                activePoint.setEdge(null);
            }
        }
    }

    private void fixActiveEdgeAfterLink(Suffix<E, S> suffix) {
        Edge<E, S> activeEdge = activePoint.getEdge();
        int activeLength = activePoint.getLength();
        while (activeEdge != null && activeLength > activeEdge.getLength()) {
            activeLength = activeLength - activeEdge.getLength();
            Node<E, S> activeNode = activeEdge.getHead();
            Object item = suffix.getItemFromEnd(activeLength + 1);
            activeEdge = activeNode.getEdge(item);

            activePoint.set(activeNode, activeEdge, activeLength);
        }

        tryResetActivePointToHead();
    }

    private void tryResetActivePointToHead() {
        if (activePoint.getEdge() != null && activePoint.getEdge().getLength() == activePoint.getLength()
                && activePoint.getEdge().hasHead()) {
            activePoint.set(activePoint.getEdge().getHead(), null, 0);
        }
    }

    private boolean isNotFirstInsertAtCurrentStep() {
        return insertsAtCurrentStep > 0;
    }

    private void increaseInsertsAtCurrentStep() {
        insertsAtCurrentStep++;
    }

    private void setLink(Node<E, S> node) {
        if (isNotFirstInsertAtCurrentStep()) {
            lastInsertedNode.setLink(node);
        }

        lastInsertedNode = node;
    }

    private Node<E, S> getLastInsertedNode() {
        return lastInsertedNode;
    }

    private void setLastInsertedNode(Node<E, S> node) {
        this.lastInsertedNode = node;
    }

    Node<E, S> getRoot() {
        return root;
    }
}
