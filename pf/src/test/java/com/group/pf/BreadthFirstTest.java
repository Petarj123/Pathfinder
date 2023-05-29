package com.group.pf;


import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.BreadthFirstAlgorithm.DepthFirstPathfinder;

import com.group.pf.BreadthFirstAlgorithm.BFSNode;
import com.group.pf.testPackage.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BreadthFirstTest {

    @Test
    void test() {
        Grid<BFSNode> grid = new Grid<>(50, 50, BFSNode.class);
        BFSNode startBFSNode = grid.getNode(0, 0);
        BFSNode endBFSNode = grid.getNode(49, 49);
        startBFSNode.setStart(true);
        endBFSNode.setEnd(true);

        setRandomObstacles(grid, 500);

        BreadthFirstPathfinder pathfinder = new BreadthFirstPathfinder(grid, startBFSNode, endBFSNode);

        List<BFSNode> path = pathfinder.findPath();

        printGrid(grid, path);
    }
    @Test
    void test1() {
        Grid<BFSNode> grid = new Grid<>(10, 10, BFSNode.class);
        BFSNode startBFSNode = grid.getNode(0, 0);
        BFSNode endBFSNode = grid.getNode(9, 9);
        startBFSNode.setStart(true);
        endBFSNode.setEnd(true);

        setRandomObstacles(grid, 20);

        DepthFirstPathfinder pathfinder = new DepthFirstPathfinder(grid, startBFSNode, endBFSNode);

        List<BFSNode> path = pathfinder.findPath();
        printGrid(grid, path);
    }

    private void setRandomObstacles(Grid<BFSNode> grid, int numObstacles) {
        int count = 0;
        while (count < numObstacles) {
            int x = (int) (Math.random() * grid.getWidth());
            int y = (int) (Math.random() * grid.getHeight());
            BFSNode BFSNode = grid.getNode(x, y);
            if (!BFSNode.isStart() && !BFSNode.isEnd() && !BFSNode.isObstacle()) {
                BFSNode.setObstacle(true);
                count++;
            }
        }
    }

    private void printGrid(Grid<BFSNode> grid, List<BFSNode> path) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                BFSNode BFSNode = grid.getNode(x, y);
                if (BFSNode.isStart()) {
                    System.out.print("1 ");
                } else if (BFSNode.isEnd()) {
                    System.out.print("2 ");
                } else if (BFSNode.isObstacle()) {
                    System.out.print("- ");
                } else if (BFSNode.isPath()) {
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
