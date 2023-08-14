package com.group.pf.main;

import com.group.pf.DTO.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Maze {
    private final GridFactory gridFactory;

    public List<Coordinates> generateMaze(int height, int width) {
        Grid<Node> grid = gridFactory.createGrid(width, height, Node.class);
        List<Node> stack = new ArrayList<>();
        Set<Node> visitedNodes = new HashSet<>();

        // Initialize the grid with walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid.getNode(x, y).setObstacle(true);
            }
        }

        // Start at a random initial node
        Node current = grid.getNode(new Random().nextInt(width), new Random().nextInt(height));
        current.setObstacle(false);
        stack.add(current);

        while (!stack.isEmpty()) {
            visitedNodes.add(current);
            List<Node> unvisitedNeighbors = getUnvisitedNeighbors(current, grid, visitedNodes);
            if (!unvisitedNeighbors.isEmpty()) {
                Node next = unvisitedNeighbors.get(new Random().nextInt(unvisitedNeighbors.size()));
                removeWallBetweenNodes(current, next, grid);
                stack.add(current);
                next.setObstacle(false);
                current = next;
            } else {
                current = stack.remove(stack.size() - 1);
            }
        }

        // Convert maze nodes to coordinates
        List<Coordinates> mazeCoordinates = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid.getNode(x, y).isObstacle()) {
                    mazeCoordinates.add(new Coordinates(x, y));
                }
            }
        }
        return mazeCoordinates;
    }

    private List<Node> getUnvisitedNeighbors(Node node, Grid<Node> grid, Set<Node> visitedNodes) {
        List<Node> neighbors = node.getNeighborsMaze(grid);
        List<Node> unvisitedNeighbors = new ArrayList<>();

        for (Node neighbor : neighbors) {
            if (!visitedNodes.contains(neighbor)) {
                unvisitedNeighbors.add(neighbor);
            }
        }
        return unvisitedNeighbors;
    }

    private void removeWallBetweenNodes(Node node1, Node node2, Grid<Node> nodeGrid) {
        int dx = node2.getX() - node1.getX();
        int dy = node2.getY() - node1.getY();

        int x = node1.getX() + dx/2;
        int y = node1.getY() + dy/2;

        node1.setObstacle(false);
        node2.setObstacle(false);

        // Get the wall node between node1 and node2 and set its obstacle flag to false
        Node wallNode = nodeGrid.getNode(x, y);
        if (wallNode != null) {
            wallNode.setObstacle(false);
        }
    }
}

