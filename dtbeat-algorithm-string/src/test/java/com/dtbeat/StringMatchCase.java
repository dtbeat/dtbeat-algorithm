package com.dtbeat;

/**
 * StringMatchCase
 *
 * @author elvinshang
 * @version Id: StringMatchCase.java, v 2020/9/21 01:05 dtbeat.com $
 */
public class StringMatchCase {
    private String s;
    private String p;
    private int index;

    public StringMatchCase(String s, String p) {
        this.s = s;
        this.p = p;
        this.index = s.indexOf(p);
    }

    public String getS() {
        return s;
    }

    public String getP() {
        return p;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "StringMatchCase{" +
                "s='" + s + '\'' +
                ", p='" + p + '\'' +
                ", index=" + index +
                '}';
    }
}
