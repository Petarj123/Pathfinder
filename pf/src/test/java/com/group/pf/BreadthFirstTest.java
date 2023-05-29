package com.group.pf;


import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.BreadthFirstAlgorithm.DepthFirstPathfinder;
import com.group.pf.BreadthFirstAlgorithm.Grid;
import com.group.pf.BreadthFirstAlgorithm.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BreadthFirstTest {

    @Test
    void test() {
        Grid grid = new Grid(50, 50);
        Node startNode = grid.getNode(0, 0);
        Node endNode = grid.getNode(49, 49);
        startNode.setStart(true);
        endNode.setEnd(true);

        setRandomObstacles(grid, 500);

        BreadthFirstPathfinder pathfinder = new BreadthFirstPathfinder(grid, startNode, endNode);

        List<Node> path = pathfinder.findPath();
        System.out.println(path);
        printGrid(grid, path);
    }
    @Test
    void test1() {
        Grid grid = new Grid(10, 10);
        Node startNode = grid.getNode(0, 0);
        Node endNode = grid.getNode(9, 9);
        startNode.setStart(true);
        endNode.setEnd(true);

        setRandomObstacles(grid, 20);

        DepthFirstPathfinder pathfinder = new DepthFirstPathfinder(grid, startNode, endNode);

        List<Node> path = pathfinder.findPath();
        System.out.println(path);
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
