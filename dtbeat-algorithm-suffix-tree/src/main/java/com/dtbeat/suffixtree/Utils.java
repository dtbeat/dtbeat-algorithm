package com.dtbeat.suffixtree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Utils
 *
 * @author elvinshang
 * @version Id: Utils.java, v0.0.1 2020/9/15 08:53 dtbeat.com $
 */
class Utils {
    static <E, S extends Iterable<E>> String toGraphViz(SuffixTree<E, S> tree) {
        return toGraphViz(tree, true);
    }

    /**
     * Generates a .dot format string for visualizing a suffix tree.
     *
     * @param tree The tree for which we are generating a dot file.
     * @return A string containing the contents of a .dot representation of the
     * tree.
     */
    static <E, S extends Iterable<E>> String toGraphViz(SuffixTree<E, S> tree, boolean withLinks) {
        LinkedList<Node<E, S>> stack = new LinkedList<Node<E, S>>();
        stack.add(tree.getRoot());
        Map<Node<E, S>, Integer> nodeMap = new HashMap<Node<E, S>, Integer>();
        nodeMap.put(tree.getRoot(), 0);
        int nodeId = 1;

        StringBuilder sb = new StringBuilder(
                "\ndigraph suffixTree{\n node [shape=circle, label=\"\", fixedsize=true, width=0.1, height=0.1]\n");

        while (stack.size() > 0) {
            LinkedList<Node<E, S>> childNodes = new LinkedList<Node<E, S>>();
            for (Node<E, S> node : stack) {

                // List<Edge> edges = node.getEdges();
                for (Edge<E, S> edge : node) {
                    int id = nodeId++;
                    if (edge.hasHead()) {
                        childNodes.push(edge.getHead());
                        nodeMap.put(edge.getHead(), id);
                    }

                    sb.append(nodeMap.get(node)).append(" -> ").append(id)
                            .append(" [label=\"");

                    for (E item : edge) {
                        //if(item != null)
                        sb.append(item.toString());
                    }
                    sb.append("\"];\n");
                }
            }
            stack = childNodes;
        }

        if (withLinks) {
            // loop again to find all suffix links.
            sb.append("edge [color=red, style=dotted]\n");
            for (Map.Entry<Node<E, S>, Integer> entry : nodeMap.entrySet()) {
                Node n1 = entry.getKey();
                int id1 = entry.getValue();

                if (n1.hasLink()) {
                    Node n2 = n1.getLink();
                    Integer id2 = nodeMap.get(n2);
                    // if(id2 != null)
                    sb.append(id1).append(" -> ").append(id2).append(" ;\n");
                }
            }
        }
        sb.append("}");
        return (sb.toString());
    }
}
