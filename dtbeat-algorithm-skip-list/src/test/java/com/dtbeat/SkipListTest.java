package com.dtbeat;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SkipListTest {
    @Test
    public void testGetLength() {
        SkipList<Integer, Integer> list = new SkipList<>();

        int[] origin = new int[]{1, 3, 2, 5, 4};
        for (int i = 0; i < origin.length; i++) {
            list.insert(origin[i], i);
        }

        assertThat(list.getLength(), is(5L));
    }

    @Test
    public void testGet() {
        SkipList<Integer, Integer> list = new SkipList<>();

        int[] origin = new int[]{1, 3, 2, 5, 4};
        for (int i = 0; i < origin.length; i++) {
            list.insert(origin[i], i);
        }

        Integer actual = list.get(3);
        assertNotNull(actual);
        assertThat(actual.intValue(), is(1));
    }


    @Test
    public void testInsert() {
        SkipList<Integer, Integer> list = new SkipList<>();

        int[] origin = new int[]{1, 3, 2, 5, 4};
        for (int i = 0; i < origin.length; i++) {
            list.insert(origin[i], i);
        }

        for (int i = 0; i < origin.length; i++) {
            Integer actual = list.get(origin[i]);
            assertNotNull(actual);
            assertThat(actual.intValue(), is(i));
        }
    }

    @Test
    public void testDelete() {
        SkipList<Integer, Integer> list = new SkipList<>();

        int[] origin = new int[]{1, 3, 2, 5, 4};
        for (int i = 0; i < origin.length; i++) {
            list.insert(origin[i], i);
        }

        for (int i = 0; i < origin.length; i++) {
            Integer actual = list.delete(origin[i]);
            assertNotNull(actual);
            assertThat(actual.intValue(), is(i));
        }
    }
}