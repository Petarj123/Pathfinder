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
        // Recursive Backtracking Algorithm
        Node current = grid.getNode(0, 0);
        stack.add(current);
        while (!stack.isEmpty()) {
            visitedNodes.add(current);
            List<Node> unvisitedNeighbors = getUnvisitedNeighbors(current, grid, visitedNodes);
            if (!unvisitedNeighbors.isEmpty()) {
                Node next = unvisitedNeighbors.get(new Random().nextInt(unvisitedNeighbors.size()));
                removeWallBetweenNodes(current, next, grid);
                stack.add(next);
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
        List<Node> neighbors = node.getNeighbors(grid);
        List<Node> unvisitedNeighbors = new ArrayList<>();

        for (Node neighbor : neighbors) {
            if (!visitedNodes.contains(neighbor)) {
                unvisitedNeighbors.add(neighbor);
            }
        }

        return unvisitedNeighbors;
    }

    private void removeWallBetweenNodes(Node node1, Node node2, Grid<Node> grid) {
        int x1 = node1.getX();
        int y1 = node1.getY();
        int x2 = node2.getX();
        int y2 = node2.getY();

        // Calculate the x and y coordinates of the wall node between node1 and node2
        int wallX = (x1 + x2) / 2;
        int wallY = (y1 + y2) / 2;

        // Set the obstacle flag of the wall node to false
        Node wallNode = grid.getNode(wallX, wallY);
        wallNode.setObstacle(false);
    }
}
