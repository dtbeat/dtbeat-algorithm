package com.dtbeat;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiFunction;

/**
 * Segment Tree
 *
 * @author elvinshang
 * @version Id: SegmentTree.java, v0.0.1 2020/9/9 14:59 dtbeat.com $
 */
public class SegmentTree<E> {
    private Comparator<E> comparator;
    private Node root;
    private E[] data;

    public SegmentTree(E[] data) {
        this(null, data);
    }

    public SegmentTree(Comparator<E> comparator, E[] data) {
        this.comparator = comparator;
        this.data = Objects.requireNonNull(data);
        this.root = buildTree(data);
    }

    /**
     * Returns the minimum value
     *
     * @param start the start index of range
     * @param end   the end index of rangd
     * @return the minimum value
     */
    public E minimum(int start, int end) {
        return query(start, end, (E r, int index, E v) -> {
            if (r == null) {
                return v;
            }

            return compare(r, v) < 0 ? r : v;
        });
    }

    /**
     * Returns the summation
     *
     * @param start       the start index of range
     * @param end         the end index of rangd
     * @param accumulator the accumulator
     * @return the summation
     */
    public E sum(int start, int end, BiFunction<E, E, E> accumulator) {
        return query(start, end, (E r, int index, E v) -> {
            if (r == null) {
                return v;
            }

            return accumulator.apply(r, v);
        });
    }

    /**
     * Set new value at the specified index
     *
     * @param i     the index to set
     * @param value the new value to set at specified index
     */
    public void set(int i, E value) {
        this.set(i, i, value);
    }

    /**
     * Set new value at the specified index
     *
     * @param start the start index to set
     * @param end   the end index to set
     * @param value the new value to set at specified index
     */
    public void set(int start, int end, E value) {
        query(start, end, (E r, int index, E v) -> {
            data[index] = value;
            return null;
        });
    }

    /**
     * Return the result of merging between start index and end index
     *
     * @param start  the start index to query
     * @param end    the end index to query
     * @param merger the merger of result
     * @return the result of merging
     */
    public <R> R query(int start, int end, Merger<E, R> merger) {
        if (start > end) {
            throw new IllegalArgumentException("the start index is greater than the end index");
        }

        ifIndexOutOfBoundsThrowException(start, "start");
        ifIndexOutOfBoundsThrowException(end, "end");

        R result = null;
        Stack<QueryState> st = new Stack<>();
        st.push(new QueryState(this.root, start, end));
        int queryStart;
        int queryEnd;
        while (!st.isEmpty()) {
            QueryState state = st.pop();
            Node queryNode = state.node;
            queryStart = state.start;
            queryEnd = state.end;

            if (!queryNode.hasChildren()) {
                result = merger.merge(result, queryStart, data[queryStart]);
            } else {
                Node lchild = queryNode.lchild;
                QueryState s = createQueryState(lchild, queryStart, queryEnd);
                if (s != null) {
                    st.push(s);
                }

                Node rchild = queryNode.rchild;
                s = createQueryState(rchild, queryStart, queryEnd);
                if (s != null) {
                    st.push(s);
                }
            }
        }

        return result;
    }

    private QueryState createQueryState(Node node, int start, int end) {
        if (end < node.left || start > node.right) {
            return null;
        }

        return new QueryState(node, Math.max(start, node.left), Math.min(end, node.right));
    }

    private void ifIndexOutOfBoundsThrowException(int index, String message) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException(message);
        }
    }

    private Node buildTree(E[] data) {
        Node res = new Node(0, data.length - 1);
        Queue<Node> q = new ArrayDeque<>();
        q.offer(res);

        while (!q.isEmpty()) {
            Node x = q.poll();

            Node lchild = new Node(x.left, (x.left + x.right) / 2);
            insertLeftChild(lchild, x);
            if (lchild.getSize() > 1) {
                q.offer(lchild);
            }

            Node rchild = new Node((x.left + x.right) / 2 + 1, x.right);
            insertRightChild(rchild, x);
            if (rchild.getSize() > 1) {
                q.offer(rchild);
            }
        }

        return res;
    }

    private void insertLeftChild(Node child, Node parent) {
        child.parent = parent;
        parent.lchild = child;
    }

    private void insertRightChild(Node child, Node parent) {
        child.parent = parent;
        parent.rchild = child;
    }

    private int compare(E k1, E k2) {
        return comparator != null ? comparator.compare(k1, k2) :
                ((Comparable<? super E>) k1).compareTo(k2);
    }

    /**
     * Segment Tree Node
     */
    private class Node {
        private int left;
        private int right;
        private Node parent;
        private Node lchild;
        private Node rchild;

        public Node(int left, int right) {
            this.left = left;
            this.right = right;
            this.parent = null;
            this.lchild = null;
            this.rchild = null;
        }

        public int getSize() {
            return right - left + 1;
        }

        public boolean isLeaf() {
            return lchild == null && rchild == null;
        }

        public boolean hasChildren() {
            return lchild != null && rchild != null;
        }

        @Override
        public String toString() {
            return "[" + left + ", " + right + "]";
        }

        public String render() {
            StringBuilder writer = new StringBuilder();
            writer.append(String.format("[%d-%d", left, right));
            if (lchild != null && rchild != null) {
                writer.append(" " + lchild.render());
                writer.append(" " + rchild.render());
            }
            writer.append("]");

            return writer.toString();
        }
    }

    /**
     * Query State
     */
    private class QueryState {
        private Node node;
        private int start;
        private int end;

        public QueryState(Node node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "QueryState{" +
                    "node=" + node +
                    ", start=" + start +
                    ", end=" + end +
                    '}';
        }
    }
}
