package com.dtbeat.suffixtree;

import java.util.Iterator;
import java.util.Objects;

/**
 * Edge
 *
 * @author elvinshang
 * @version Id: Edge.java, v0.0.1 2020/9/13 17:54 dtbeat.com $
 */
class Edge<E, S extends Iterable<E>> implements Iterable<E> {
    private final int start;
    private int end = -1;
    private final Node<E, S> tail;
    private final Sequence<E, S> sequence;
    private final SequenceCursor<E, S> cursor;

    private Node<E, S> head;

    public Edge(int start, Node<E, S> tail, SequenceCursor<E, S> cursor) {
        this.start = start;
        this.tail = tail;
        this.sequence = cursor.getSequence();
        this.cursor = cursor;
    }

    boolean startsWith(Object item) {
        return Objects.equals(sequence.getItem(start), item);
    }

    int getStart() {
        return start;
    }

    int getEnd() {
        return end == -1 ? cursor.getCurrent() : end;
    }

    void setEnd(int end) {
        this.end = end;
    }

    int getLength() {
        return getEnd() - start;
    }

    Node<E, S> getTail() {
        return tail;
    }

    Node<E, S> getHead() {
        return head;
    }

    boolean hasHead() {
        return head != null;
    }

    void setHead(Node<E, S> head) {
        this.head = head;
    }

    E getStartItem() {
        return getItemAt(0);
    }

    E getEndItem() {
        return getItemAt(this.getLength() - 1);
    }

    @SuppressWarnings("unchecked")
    E getItemAt(int index) {
        if (index < 0 || index >= getLength()) {
            throw new IndexOutOfBoundsException("index");
        }

        index += start;

        return (E) sequence.getItem(index);
    }

    boolean isSequenceTerminal(int index) {
        return sequence.getItem(index).getClass().equals(SequenceTerminal.class);
    }

    @Override
    public String toString() {
        StringBuilder writer = new StringBuilder();
        for (int i = start; i < getEnd(); i++) {
            writer.append(sequence.getItem(i).toString());
            if (isSequenceTerminal(i)) {
                break;
            }
            writer.append(", ");
        }
        return writer.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < getLength();
            }

            @Override
            public E next() {
                return getItemAt(current++);
            }
        };
    }
}
