package com.group.pf.AStarAlgorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Data
@AllArgsConstructor
public class AStarPathfinder {
    private Grid grid;
    private Node startNode;
    private Node endNode;

    public List<Node> findPath() {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFScore));
        Set<Node> closedSet = new HashSet<>();

        startNode.setGScore(0);
        startNode.setHScore(calculateHScore(startNode, endNode));
        startNode.setFScore(startNode.getGScore() + startNode.getHScore());
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (currentNode == endNode) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode);

            for (Node neighbor : currentNode.getNeighbors(grid)) {
                if (closedSet.contains(neighbor) || neighbor.isObstacle()) {
                    continue;
                }

                double tentativeGScore = currentNode.getGScore() + calculateGScore(currentNode, neighbor);

                if (!openSet.contains(neighbor) || tentativeGScore < neighbor.getGScore()) {
                    neighbor.setParent(currentNode);
                    neighbor.setGScore(tentativeGScore);
                    neighbor.setHScore(calculateHScore(neighbor, endNode));
                    neighbor.setFScore(neighbor.getGScore() + neighbor.getHScore());

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }
    private List<Node> reconstructPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        return path;
    }
    public double calculateGScore(Node parent, Node current) {
        // Calculate and return the gScore based on the parent node and current node
        double distance = distance(parent, current);
        return parent.getGScore() + distance;
    }

    public double calculateFScore(Node current, Node goal) {
        // Calculate and return the fScore based on the current node and goal node
        return calculateGScore(current.getParent(), current) + calculateHScore(current, goal);
    }

    public double calculateHScore(Node current, Node goal) {
        // Calculate and return the hScore based on the current node and goal node
        int dx = Math.abs(current.getX() - goal.getX());
        int dy = Math.abs(current.getY() - goal.getY());
        return Math.sqrt(dx * dx + dy * dy);
    }
    public double distance(Node node1, Node node2) {
        // Calculate and return the distance between two nodes
        int dx = Math.abs(node1.getX() - node2.getX());
        int dy = Math.abs(node1.getY() - node2.getY());
        return Math.sqrt(dx * dx + dy * dy);
    }
}
