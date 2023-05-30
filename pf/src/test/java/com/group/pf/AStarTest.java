package com.group.pf;

import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AStarTest {
    @Test
    void testFindPath() {
        // Create a grid
        GridFactory gridFactory = new GridFactory();

        // Set the start and end nodes
        AStarNode startAStarNode = new AStarNode(0, 0);
        AStarNode endAStarNode = new AStarNode(9, 9);
        startAStarNode.setStart(true);
        endAStarNode.setEnd(true);


        // Create the A* pathfinder
        AStarPathfinder pathfinder = new AStarPathfinder(gridFactory);

        // Find the path
        List<AStarNode> path = pathfinder.findPath(startAStarNode, endAStarNode, null);

        System.out.println(path);
        // Print the grid
        printGrid(path);

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
    private void printGrid(List<AStarNode> path) {
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                AStarNode AStarNode = path.get(x);
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
