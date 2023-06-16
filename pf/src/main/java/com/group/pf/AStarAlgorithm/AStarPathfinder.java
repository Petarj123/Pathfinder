package com.group.pf.AStarAlgorithm;

import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AStarPathfinder {
    private final GridFactory gridFactory;

    public List<AStarNode> findPath(AStarNode startAStarNode, AStarNode endAStarNode, List<AStarNode> obstacles, int height, int width) {
        if (startAStarNode == null || endAStarNode == null || height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        Grid<AStarNode> grid = gridFactory.createGrid(width, height, AStarNode.class);
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(AStarNode::getFScore));
        Set<AStarNode> closedSet = new HashSet<>();
        for (AStarNode node : obstacles) {
            grid.setObstacle(node.getX(), node.getY(), true);
        }
        updateScores(startAStarNode, endAStarNode, 0);
        openSet.add(startAStarNode);
        while (!openSet.isEmpty()) {
            AStarNode currentAStarNode = openSet.poll();
            if (currentAStarNode.equals(endAStarNode)) {
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
                    updateScores(neighbor, endAStarNode, tentativeGScore);
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
    private void updateScores(AStarNode node, AStarNode goal, double gScore) {
        node.setGScore(gScore);
        node.setHScore(calculateHScore(node, goal));
        node.setFScore(node.getGScore() + node.getHScore());
    }
}
