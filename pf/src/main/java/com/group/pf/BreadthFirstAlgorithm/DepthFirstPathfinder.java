package com.group.pf.BreadthFirstAlgorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class DepthFirstPathfinder {
    private Grid grid;
    private Node startNode;
    private Node endNode;

    public List<Node> findPath() {
        List<Node> path = new ArrayList<>();

        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            visited.add(currentNode);

            if (currentNode.isEnd()) {
                // Path found
                path = reconstructPath(startNode, currentNode);
                break;
            }

            List<Node> neighbors = currentNode.getNeighbors(grid);
            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    (neighbor).setPrevious(currentNode);
                }
            }
        }

        return path;
    }

    private List<Node> reconstructPath(Node startNode, Node endNode) {
        LinkedList<Node> path = new LinkedList<>();
        Node currentNode = endNode;

        while (currentNode != startNode) {
            path.addFirst(currentNode);
            currentNode = currentNode.getPrevious();
        }

        path.addFirst(startNode);

        for (Node node : path) {
            node.setPath(true);
        }

        return new ArrayList<>(path);
    }


}
