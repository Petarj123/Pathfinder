package com.group.pf;

import com.group.pf.DijkstraAlgorithm.DijkstraNode;
import com.group.pf.DijkstraAlgorithm.DijkstraPathfinder;
import com.group.pf.Swarm.SwarmNode;
import com.group.pf.testPackage.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DijkstraTest {

    @Test
    void dijkstraTest(){
        Grid<DijkstraNode> grid = new Grid<>(10, 10, DijkstraNode.class);
        DijkstraNode startNode = grid.getNode(0, 0);
        DijkstraNode endNode = grid.getNode(9, 9);
        startNode.setStart(true);
        endNode.setEnd(true);
        setRandomObstacles(grid, 10);
        DijkstraPathfinder dijkstra = new DijkstraPathfinder(grid, startNode, endNode);
        List<DijkstraNode> path = dijkstra.findPath();
        printGrid(grid, path);
    }

    private void setRandomObstacles(Grid<DijkstraNode> grid, int numObstacles) {
        int count = 0;
        while (count < numObstacles) {
            int x = (int) (Math.random() * grid.getWidth());
            int y = (int) (Math.random() * grid.getHeight());
            DijkstraNode swarmNode = grid.getNode(x, y);
            if (!swarmNode.isStart() && !swarmNode.isEnd() && !swarmNode.isObstacle()) {
                swarmNode.setObstacle(true);
                count++;
            }
        }
    }
    private void printGrid(Grid<DijkstraNode> grid, List<DijkstraNode> path) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                DijkstraNode swarmNode = grid.getNode(x, y);
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
