package com.group.pf;

import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.DTO.PathResult;
import com.group.pf.DijkstraAlgorithm.DijkstraNode;
import com.group.pf.DijkstraAlgorithm.DijkstraPathfinder;
import com.group.pf.main.GridFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AlgoTest {
    @Test
    void testAStarFindPath() {
        // Create a grid
        GridFactory gridFactory = new GridFactory();

        // Set the start and end nodes
        AStarNode startAStarNode = new AStarNode(0, 0);
        AStarNode endAStarNode = new AStarNode(1, 1);
        startAStarNode.setStart(true);
        endAStarNode.setEnd(true);


        // Create the A* pathfinder
        AStarPathfinder pathfinder = new AStarPathfinder(gridFactory);

        // Find the path
        PathResult<AStarNode> path = pathfinder.findPath(startAStarNode, endAStarNode, null, 50, 50);

        for (AStarNode node : path) {
            System.out.println("Node " + node.getX() + " " + node.getY());
        }

    }
    @Test
    void testDijkstra(){
        // Create a grid
        GridFactory gridFactory = new GridFactory();

        // Set the start and end nodes
        DijkstraNode startAStarNode = new DijkstraNode(0, 0);
        DijkstraNode endAStarNode = new DijkstraNode(30, 30);
        startAStarNode.setStart(true);
        endAStarNode.setEnd(true);

        DijkstraPathfinder pathfinder = new DijkstraPathfinder(gridFactory);
        List<DijkstraNode> path = pathfinder.findPath(startAStarNode, endAStarNode, null, 50, 50);
        for (DijkstraNode node : path){
            if (node != null){
                System.out.println("Node " + node.getX() + " " + node.getY());
            }
        }
    }
    @Test
    void testBFS(){
        // Create a grid
        GridFactory gridFactory = new GridFactory();

        // Set the start and end nodes
        Node startAStarNode = new Node(0, 0);
        Node endAStarNode = new Node(30, 30);
        startAStarNode.setStart(true);
        endAStarNode.setEnd(true);

        BreadthFirstPathfinder pathfinder = new BreadthFirstPathfinder(gridFactory);
        List<Node> path = pathfinder.findPath(startAStarNode, endAStarNode, null, 50, 50);
        for (Node node : path){
            if (node!= null){
                System.out.println("Node " + node.getX() + " " + node.getY());
            }
        }
    }
}
