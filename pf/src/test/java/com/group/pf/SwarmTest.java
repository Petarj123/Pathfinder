package com.group.pf;

import com.group.pf.Swarm.BiDirectionalSwarmPathfinder;
import com.group.pf.Swarm.ConvergentSwarmPathfinder;

import com.group.pf.Swarm.SwarmNode;
import com.group.pf.testPackage.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SwarmTest {
    @Test
    void convergentSwarm(){
        Grid<SwarmNode> grid = new Grid<>(10, 10, SwarmNode.class);
        SwarmNode startSwarmNode = grid.getNode(0, 0);
        SwarmNode endSwarmNode = grid.getNode(9, 9);
        startSwarmNode.setStart(true);
        endSwarmNode.setEnd(true);
        setRandomObstacles(grid, 20);
        ConvergentSwarmPathfinder pathfinder = new ConvergentSwarmPathfinder(grid, startSwarmNode, endSwarmNode);
        List<SwarmNode> path = pathfinder.findPath();
        printGrid(grid, path);
    }
    @Test
    void biDirectionalSwarm(){
        Grid<SwarmNode> grid = new Grid<>(10, 10, SwarmNode.class);
        SwarmNode startSwarmNode = grid.getNode(0, 0);
        SwarmNode endSwarmNode = grid.getNode(9, 9);
        startSwarmNode.setStart(true);
        endSwarmNode.setEnd(true);
        setRandomObstacles(grid, 20);
        BiDirectionalSwarmPathfinder pathfinder = new BiDirectionalSwarmPathfinder(grid, startSwarmNode, endSwarmNode);
        List<SwarmNode> path = pathfinder.findPath();
        printGrid(grid, path);
    }

    private void setRandomObstacles(Grid<SwarmNode> grid, int numObstacles) {
        int count = 0;
        while (count < numObstacles) {
            int x = (int) (Math.random() * grid.getWidth());
            int y = (int) (Math.random() * grid.getHeight());
            SwarmNode swarmNode = grid.getNode(x, y);
            if (!swarmNode.isStart() && !swarmNode.isEnd() && !swarmNode.isObstacle()) {
                swarmNode.setObstacle(true);
                count++;
            }
        }
    }
    private void printGrid(Grid<SwarmNode> grid, List<SwarmNode> path) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                SwarmNode swarmNode = grid.getNode(x, y);
                if (swarmNode.isStart()) {
                    System.out.print("1 ");
                } else if (swarmNode.isEnd()) {
                    System.out.print("2 ");
                } else if (swarmNode.isObstacle()) {
                    System.out.print("- ");
                } else if (swarmNode.isPath()) {
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
