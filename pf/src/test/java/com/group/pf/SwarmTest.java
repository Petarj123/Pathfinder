package com.group.pf;

import com.group.pf.Swarm.ConvergentSwarmPathfinder;
import com.group.pf.Swarm.Grid;
import com.group.pf.Swarm.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SwarmTest {
    @Test
    void convergentSwarm(){
        Grid grid = new Grid(10, 10);
        Node startNode = grid.getNode(0, 0);
        Node endNode = grid.getNode(9, 9);
        startNode.setStart(true);
        endNode.setEnd(true);
        setRandomObstacles(grid, 20);
        ConvergentSwarmPathfinder pathfinder = new ConvergentSwarmPathfinder(grid, startNode, endNode);
        List<Node> path = pathfinder.findPath();
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