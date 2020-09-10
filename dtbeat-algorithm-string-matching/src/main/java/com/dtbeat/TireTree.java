package com.dtbeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Tire Tree
 *
 * @author elvinshang
 * @version Id: TireTree.java, v0.0.1 2020/9/10 17:25 dtbeat.com $
 */
public class TireTree {
    private List<Node> heads;

    public TireTree() {
        heads = new ArrayList<>();
    }

    /**
     * Inserts new word.
     *
     * @param word the word to insert
     */
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("word");
        }

        char[] chars = word.toCharArray();
        Node head = findHead(chars[0]);
        if (head == null) {
            head = createNode(chars);
            heads.add(head);
            return;
        }

        Node current = head;
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            Node child = findChild(current, c);
            if (child == null) {
                child = new Node(c);
                insert(child, current);
            }

            current = child;
        }

        current.end = true;
    }

    /**
     * Deletes the word.
     *
     * @param word the word to delete
     */
    public boolean delete(String word) {
        char[] chars = word.toCharArray();
        Node head = findHead(chars[0]);
        if (head == null) {
            return false;
        }

        Node current = head;
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            Node child = findChild(current, c);
            if (child == null) {
                return false;
            }

            current = child;
        }

        if (current.child != null) {
            current.end = false;
        } else {
            current = current.parent;
            while (current != null && !current.end) {
                Node t = current.parent;
                removeNode(current);
                current = t;
            }
        }

        return true;
    }

    /**
     * Returns true if the word in tree or false if none
     *
     * @param word the word to find
     * @return true if the word in tree or false if none
     */
    public boolean find(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        char[] chars = word.toCharArray();
        Node head = findHead(chars[0]);
        if (head == null) {
            return false;
        }

        Node current = head;
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            Node child = findChild(current, c);
            if (child == null) {
                return false;
            }

            current = child;
        }

        return true;
    }

    /**
     * Renders formatted tree
     *
     * @return formatted tree
     */
    public String render() {
        StringBuilder writer = new StringBuilder();
        writer.append("[TireTree");
        for (Node head : heads) {
            writer.append(" " + head.render());
        }
        writer.append("]");

        return writer.toString();
    }

    private void removeNode(Node node) {
        if (node.parent == null) {
            this.heads.remove(node);
            return;
        }

        if (node.left == null) {
            node.parent.child = node.sibling;
        }

        if (node.sibling != null) {
            node.sibling.left = node.left;
        }

        if (node.left != null) {
            node.left.sibling = node.sibling;
        }
    }

    private Node createNode(char[] chars) {
        return createNode(chars, 0);
    }

    private Node createNode(char[] chars, int start) {
        Node res = null;
        Node current = null;
        for (int i = start; i < chars.length; i++) {
            char c = chars[i];
            if (res == null) {
                res = new Node(c);
                current = res;
            } else {
                Node child = new Node(c);
                insert(child, current);
                current = child;
            }
        }

        current.end = true;

        return res;
    }

    private void insert(Node node, Node parent) {
        node.parent = parent;

        if (parent.child == null) {
            parent.child = node;
        } else {
            parent.child.left = node;
            node.sibling = parent.child;
            parent.child = node;
        }
    }


    private Node findChild(Node parent, char c) {
        Node node = parent.child;
        while (node != null) {
            if (node.c == c) {
                return node;
            }

            node = node.sibling;
        }

        return null;
    }

    private Node findHead(char c) {
        for (Node node : heads) {
            if (node.c == c) {
                return node;
            }
        }

        return null;
    }

    private void foreachChild(Node node, Consumer<Node> consumer) {
        Node child = node.child;
        while (child != null) {
            Node t = child.sibling;
            consumer.accept(child);
            child = t;
        }
    }

    /**
     * Tire Tree Node
     */
    private class Node {
        private char c;
        private Node parent;
        private Node child;
        private Node left;
        private Node sibling;
        private boolean end;

        public Node(char c) {
            this.c = c;
            this.end = false;
            this.parent = null;
            this.left = null;
            this.sibling = null;
            this.child = null;
        }

        @Override
        public String toString() {
            return "charater=" + c;
        }

        public String render() {
            StringBuilder writer = new StringBuilder();
            writer.append("[" + c + ":" + end);
            foreachChild(this, node -> writer.append(" " + node.render()));
            writer.append("]");

            return writer.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return c == node.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(c);
        }
    }
}
