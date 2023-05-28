package com.group.pf;

import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.AStarAlgorithm.Grid;
import com.group.pf.AStarAlgorithm.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AStarTest {
    @Test
    void testFindPath() {
        // Create a grid
        Grid grid = new Grid(50, 50);

        // Set the start and end nodes
        Node startNode = grid.getNode(0, 0);
        Node endNode = grid.getNode(49, 49);
        startNode.setStart(true);
        endNode.setEnd(true);

        // Set random obstacles in the grid
        setRandomObstacles(grid, 200);

        // Create the A* pathfinder
        AStarPathfinder pathfinder = new AStarPathfinder(grid, startNode, endNode);

        // Find the path
        List<Node> path = pathfinder.findPath();

        // Print the grid
        printGrid(grid, path);

    }
    private void setRandomObstacles(Grid grid, int numObstacles) {
        int count = 0;
        while (count < numObstacles) {
            int x = (int) (Math.random() * grid.getWidth());
            int y = (int) (Math.random() * grid.getHeight());
            Node node = grid.getNode(x, y);
            if (!node.isStart() && !node.isEnd() && !node.isObstacle()) {
                node.setObstacle(true);
                count++;
            }
        }
    }
    private void printGrid(Grid grid, List<Node> path) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Node node = grid.getNode(x, y);
                if (node.isStart()) {
                    System.out.print("1 ");
                } else if (node.isEnd()) {
                    System.out.print("2 ");
                } else if (node.isObstacle()) {
                    System.out.print("- ");
                } else if (node.isPath()) {
                    System.out.print("* ");
                } else {
                    System.out.print("4 ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
