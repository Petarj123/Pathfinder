package com.group.pf.AStarAlgorithm;

import com.group.pf.testPackage.Grid;
import com.group.pf.testPackage.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Data
@AllArgsConstructor
public class AStarPathfinder {
    private Grid grid;
    private AStarNode startAStarNode;
    private AStarNode endAStarNode;

    public List<AStarNode> findPath() {
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(AStarNode::getFScore));
        Set<AStarNode> closedSet = new HashSet<>();

        startAStarNode.setGScore(0);
        startAStarNode.setHScore(calculateHScore(startAStarNode, endAStarNode));
        startAStarNode.setFScore(startAStarNode.getGScore() + startAStarNode.getHScore());
        openSet.add(startAStarNode);

        while (!openSet.isEmpty()) {
            AStarNode currentAStarNode = openSet.poll();

            if (currentAStarNode == endAStarNode) {
                return reconstructPath(currentAStarNode);
            }

            closedSet.add(currentAStarNode);

            for (Node neighborNode : currentAStarNode.getNeighbors(grid)) {
                AStarNode neighbor = (AStarNode) neighborNode;
                if (closedSet.contains(neighbor) || neighbor.isObstacle()) {
                    continue;
                }

                double tentativeGScore = currentAStarNode.getGScore() + calculateGScore(currentAStarNode, neighbor);

                if (!openSet.contains(neighbor) || tentativeGScore < neighbor.getGScore()) {
                    neighbor.setParent(currentAStarNode);
                    neighbor.setGScore(tentativeGScore);
                    neighbor.setHScore(calculateHScore(neighbor, endAStarNode));
                    neighbor.setFScore(neighbor.getGScore() + neighbor.getHScore());

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
    private List<AStarNode> reconstructPath(AStarNode currentAStarNode) {
        List<AStarNode> path = new ArrayList<>();
        while (currentAStarNode != null) {
            path.add(currentAStarNode);
            currentAStarNode = currentAStarNode.getParent();
        }
        for (AStarNode AStarNode : path) {
            AStarNode.setPath(true);
        }
        Collections.reverse(path);
        return path;
    }
    private double calculateGScore(AStarNode parent, AStarNode current) {
        // Calculate and return the gScore based on the parent node and current node
        double distance = distance(parent, current);
        return parent.getGScore() + distance;
    }

    private double calculateHScore(AStarNode current, AStarNode goal) {
        // Calculate and return the hScore based on the current node and goal node
        int dx = Math.abs(current.getX() - goal.getX());
        int dy = Math.abs(current.getY() - goal.getY());
        return Math.sqrt(dx * dx + dy * dy);
    }
    private double distance(AStarNode AStarNode1, AStarNode AStarNode2) {
        // Calculate and return the distance between two nodes
        int dx = Math.abs(AStarNode1.getX() - AStarNode2.getX());
        int dy = Math.abs(AStarNode1.getY() - AStarNode2.getY());
        return Math.sqrt(dx * dx + dy * dy);
    }
}
