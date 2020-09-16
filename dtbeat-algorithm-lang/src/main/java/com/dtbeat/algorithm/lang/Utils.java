package com.dtbeat.algorithm.lang;

/**
 * Utils
 *
 * @author elvinshang
 * @version Id: Utils.java, v0.0.1 2020/9/16 18:30 dtbeat.com $
 */
public class Utils {
    public static String toString(int[] arr) {
        StringBuilder writer = new StringBuilder();
        for (int i : arr) {
            writer.append(i + ",");
        }

        return writer.length() > 0 ? writer.deleteCharAt(writer.length() - 1).toString() : writer.toString();
    }
}
