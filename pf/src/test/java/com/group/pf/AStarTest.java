package com.group.pf;

import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.main.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AStarTest {
    @Test
    void testFindPath() {
        // Create a grid


        // Set the start and end nodes
        AStarNode startAStarNode = grid.getNode(0, 0);
        AStarNode endAStarNode = grid.getNode(25, 40);
        startAStarNode.setStart(true);
        endAStarNode.setEnd(true);

        // Set random obstacles in the grid
        setRandomObstacles(grid, 200);

        // Create the A* pathfinder
        AStarPathfinder pathfinder = new AStarPathfinder();

        // Find the path
        List<AStarNode> path = pathfinder.findPath(startAStarNode, endAStarNode);

        // Print the grid
        printGrid(grid, path);

    }
    private void setRandomObstacles(Grid<AStarNode> grid, int numObstacles) {
        int count = 0;
        while (count < numObstacles) {
            int x = (int) (Math.random() * grid.getWidth());
            int y = (int) (Math.random() * grid.getHeight());
            AStarNode AStarNode = grid.getNode(x, y);
            if (!AStarNode.isStart() && !AStarNode.isEnd() && !AStarNode.isObstacle()) {
                AStarNode.setObstacle(true);
                count++;
            }
        }
    }
    private void printGrid(Grid<AStarNode> grid, List<AStarNode> path) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                AStarNode AStarNode = grid.getNode(x, y);
                if (AStarNode.isStart()) {
                    System.out.print("1 ");
                } else if (AStarNode.isEnd()) {
                    System.out.print("2 ");
                } else if (AStarNode.isObstacle()) {
                    System.out.print("- ");
                } else if (AStarNode.isPath()) {
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
