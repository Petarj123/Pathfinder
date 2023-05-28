package com.group.pf.BreadthFirstAlgorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class BreadthFirstPathfinder {
    private Grid grid;
    private Node startNode;
    private Node endNode;

    public List<Node> findPath() {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parents = new HashMap<>();

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode == endNode) {
                return reconstructPath(parents);
            }

            for (Node neighbor : currentNode.getNeighbors(grid)) {
                if (!visited.contains(neighbor) && !neighbor.isObstacle()) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parents.put(neighbor, currentNode);
                }
            }
        }
        return Collections.emptyList();
    }
    private List<Node> reconstructPath(Map<Node, Node> parents) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }
        for (Node node : path){
            node.setPath(true);
        }
        Collections.reverse(path);
        return path;
    }
}
