package com.dtbeat.suffixtree;

/**
 * Sequence Cursor
 *
 * @author elvinshang
 * @version Id: SequenceCursor.java, v0.0.1 2020/9/13 19:28 dtbeat.com $
 */
class SequenceCursor<E, S extends Iterable<E>> {
    private final int start;
    private final int end;
    private int current;
    private final Sequence<E, S> sequence;

    public SequenceCursor(Sequence<E, S> sequence) {
        this(0, sequence.getLength(), sequence);
    }

    public SequenceCursor(int start, int end, Sequence<E, S> sequence) {
        this.start = start;
        this.end = end;
        this.current = start;
        this.sequence = sequence;
    }

    int getStart() {
        return start;
    }

    int getEnd() {
        return end;
    }

    int getCurrent() {
        return current;
    }

    Sequence<E, S> getSequence() {
        return sequence;
    }

    boolean hasNext() {
        return current < end;
    }

    Object next() {
        return sequence.getItem(current++);
    }

    @Override
    public String toString() {
        StringBuilder writer = new StringBuilder();
        writer.append("[");
        writer.append("(").append(start).append(", ").append(current).append(")");
        for (int i = start; i < sequence.getLength(); i++) {
            if (i == current) {
                writer.append(", ").append("/**").append(sequence.getItem(i)).append("**/");
            } else {
                writer.append(", ").append(sequence.getItem(i));
            }
        }

        return writer.toString();
    }
}
