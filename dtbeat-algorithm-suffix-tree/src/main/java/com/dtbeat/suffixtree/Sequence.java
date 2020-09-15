package com.dtbeat.suffixtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sequence
 *
 * @author elvinshang
 * @version Id: Sequence.java, v0.0.1 2020/9/13 16:53 dtbeat.com $
 */
public class Sequence<E, S extends Iterable<E>> implements Iterable<Object> {
    private List<Object> seqs = new ArrayList<>();

    public Sequence(S sequence) {
        for (E e : sequence) {
            this.seqs.add(e);
        }

        seqs.add(new SequenceTerminal<>(sequence));
    }

    public Object getItem(int index) {
        return seqs.get(index);
    }

    public int getLength() {
        return seqs.size();
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < seqs.size();
            }

            @Override
            public Object next() {
                return seqs.get(current++);
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder writer = new StringBuilder("[");
        for (int i = 0; i < seqs.size(); i++) {
            if(i == seqs.size() - 1) {
                writer.append(i);
            } else {
                writer.append(i).append(", ");
            }
        }
        writer.append("]");

        return writer.toString();
    }
}
