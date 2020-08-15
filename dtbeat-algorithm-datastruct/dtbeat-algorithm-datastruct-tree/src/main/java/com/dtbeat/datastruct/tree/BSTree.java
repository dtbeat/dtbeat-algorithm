package com.dtbeat.datastruct.tree;

import java.util.Comparator;

/**
 * BSTree
 *
 * @author elvinshang
 * @version Id:: BSTree.java, v0.0.1 2020/8/15 23:11 dtbeat.com $
 */
public class BSTree<K, V> extends AbstractBinaryTree<K, V> {
    public BSTree() {
        super();
    }

    public BSTree(Comparator<K> comparator) {
        super(comparator);
    }
}
