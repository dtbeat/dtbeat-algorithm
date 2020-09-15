package com.dtbeat.suffixtree;

import java.util.Objects;

/**
 * Suffix
 *
 * @author elvinshang
 * @version Id: Suffix.java, v0.0.1 2020/9/13 17:16 dtbeat.com $
 */
class Suffix<E, S extends Iterable<E>> {
    private int start;
    private int end;
    private Sequence<E, S> sequence;

    public Suffix(int start, int end, Sequence<E, S> sequence) {
        Objects.requireNonNull(sequence);
        ifStartAndEndIndexIllegalThrowException(start, end, sequence.getLength());

        this.start = start;
        this.end = end;
        this.sequence = sequence;
    }

    boolean isEmpty() {
        return start == end;
    }

    void decrease() {
        if (start == end) {
            throw new IndexOutOfBoundsException("suffix start position has been equal to its end position");
        }

        start++;
    }

    void increase() {
        if (end >= sequence.getLength()) {
            throw new IndexOutOfBoundsException("suffix start position has been equal to sequence length");
        }

        end++;
    }

    int getStart() {
        return start;
    }

    Object getStartItem() {
        return isEmpty() ? null : sequence.getItem(start);
    }

    int getEnd() {
        return end;
    }

    Object getEndItem() {
        return isEmpty() ? null : sequence.getItem(end - 1);
    }

    Object getItemFromEnd(int distanceFromEnd) {
        if ((end - distanceFromEnd) < start) {
            throw new IllegalArgumentException(distanceFromEnd
                    + " extends before the start of this suffix: ");
        }

        return sequence.getItem(end - distanceFromEnd);
    }


    int remaining() {
        return isEmpty() ? 0 : end - start;
    }

    void reset(int start, int end) {
        ifStartAndEndIndexIllegalThrowException(start, end, sequence.getLength());

        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        StringBuilder writer = new StringBuilder("[(");
        writer.append(start).append(", ").append(end).append(")");
        writer.append(",(");
        for (int i = start; i < end; i++) {
            if (i == end - 1) {
                writer.append(sequence.getItem(i));
            } else {
                writer.append(sequence.getItem(i)).append(",");
            }
        }
        writer.append(")]");

        return writer.toString();
    }

    private void ifStartAndEndIndexIllegalThrowException(int start, int end, int length) {
        if (start < 0 || start > length) {
            throw new IllegalArgumentException("suffix start position must be less than or equal to sequence length");
        }

        if (end < 0 || end > length) {
            throw new IllegalArgumentException("suffix end position must be less than or equal to sequence length");
        }

        if (start > end) {
            throw new IllegalArgumentException("a suffix start position must be less than or equal to its end position");
        }
    }
}
