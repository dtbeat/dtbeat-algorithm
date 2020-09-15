package com.dtbeat.suffixtree;

import java.util.Objects;

/**
 * SequenceTerminal
 *
 * @author elvinshang
 * @version Id: SequenceTerminal.java, v0.0.1 2020/9/13 17:06 dtbeat.com $
 */
class SequenceTerminal<S> {
    private final S sequence;

    public SequenceTerminal(S sequence) {
        this.sequence = sequence;
    }

    public S getSequence() {
        return sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SequenceTerminal<?> that = (SequenceTerminal<?>) o;
        return Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence);
    }

    @Override
    public String toString() {
        return "$" + sequence.toString() + "$";
    }
}
