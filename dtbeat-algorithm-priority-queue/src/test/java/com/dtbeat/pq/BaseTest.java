package com.dtbeat.pq;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BaseTest
 *
 * @author elvinshang
 * @version Id: BaseTest.java, v0.0.1 2020/9/9 10:17 dtbeat.com $
 */
public class BaseTest {
    protected Random rnd;

    @Before
    public void setUp() {
        rnd = new Random(System.currentTimeMillis());
    }

    protected List<Integer> createList() {
        int size = rnd.nextInt(20);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            res.add(rnd.nextInt(100));
        }

        return res;
    }

    protected List<Integer> createList(int[] input) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i : input) {
            res.add(i);
        }

        return res;
    }

    protected String toString(List<Integer> arr) {
        StringBuilder s = new StringBuilder();
        for (Integer v : arr) {
            s.append(v.intValue() + ",");
        }

        return s.length() > 0 ? s.deleteCharAt(s.length() - 1).toString() : "";
    }
}
