package com.dtbeat.tree;

import com.dtbeat.tree.AbstractBinaryTree;

import java.util.Comparator;

/**
 * AVLTree
 *
 * @author elvinshang
 * @version Id:: AVLTree.java, v0.0.1 2020/8/15 23:13 dtbeat.com $
 */
public class AVLTree<K, V> extends AbstractBinaryTree<K, V> {
    public AVLTree() {
        super();
    }

    public AVLTree(Comparator<K> comparator) {
        super(comparator);
    }

    @Override
    protected Node<K, V> putNode(Node<K, V> node, K key, V value) {
        Node<K, V> resultNode = super.putNode(node, key, value);
        return resultNode == null ? null : rebalance(resultNode);
    }

    @Override
    protected Node<K, V> removeNode(Node<K, V> node, K key) {
        Node<K, V> resultNode = super.removeNode(node, key);
        return resultNode == null ? null : rebalance(resultNode);
    }

    private Node<K, V> rebalance(Node<K, V> node) {
        // update depth
        updateDepth(node);

        int factor = getBalanceFactor(node);

        // LL
        if (factor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // RR
        if (factor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // LR
        if (factor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (factor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.right;
        Node<K, V> rightNode = newRoot.left;

        newRoot.left = node;
        node.right = rightNode;
        node.parent = newRoot;

        updateDepth(node);
        updateDepth(newRoot);

        return newRoot;
    }

    private Node<K, V> rightRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.left;
        Node<K, V> leftNode = newRoot.right;

        newRoot.right = node;
        node.left = leftNode;
        node.parent = newRoot;

        updateDepth(node);
        updateDepth(newRoot);

        return newRoot;
    }

    private int getBalanceFactor(Node<K, V> node) {
        if (node == null) {
            return 0;
        }

        return getDepth(node.left) - getDepth(node.right);
    }

}
