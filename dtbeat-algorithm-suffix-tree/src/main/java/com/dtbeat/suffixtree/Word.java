package com.dtbeat.suffixtree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Word
 *
 * @author elvinshang
 * @version Id: Word.java, v0.0.1 2020/9/13 16:48 dtbeat.com $
 */
public class Word implements Iterable<Character> {
    private char[] word;

    public Word(String s) {
        Objects.requireNonNull(s);
        this.word = s.toCharArray();
    }

    public int size() {
        return word.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word that = (Word) o;
        return Arrays.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(word);
    }

    @Override
    public String toString() {
        return new String(word);
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < word.length;
            }

            @Override
            public Character next() {
                return word[current++];
            }
        };
    }
}
