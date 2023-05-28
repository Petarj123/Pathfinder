package com.group.pf.DijkstraAlgorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class DijkstraPathfinder {
    private Grid grid;
    private Node startNode;
    private Node endNode;

    public List<Node> findPath() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();

        startNode.setDistance(0);
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode == endNode) {
                return reconstructPath();
            }
            visited.add(currentNode);
            for (Node neighbor : currentNode.getNeighbours(grid)){
                if (!visited.contains(neighbor) && !neighbor.isObstacle()){
                    int distance = currentNode.getDistance() + 1; // Assuming all edges have weight 1
                    if (distance < neighbor.getDistance()){
                        neighbor.setDistance(distance);
                        neighbor.setPrevious(currentNode);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Node> reconstructPath() {
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current);
            current = current.getPrevious();
        }
        for (Node node : path) {
            node.setPath(true);
        }
        Collections.reverse(path);
        return path;
    }
}
