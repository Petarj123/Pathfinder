package com.group.pf.Swarm;

import com.group.pf.DijkstraAlgorithm.Grid;
import com.group.pf.DijkstraAlgorithm.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class BiDirectionalSwarmPathfinder {
    private Grid grid;
    private Node startNode;
    private Node endNode;

    public List<Node> findPath() {
        // Initialize the forward and backward queues
        Queue<Node> forwardQueue = new LinkedList<>();
        Queue<Node> backwardQueue = new LinkedList<>();

        // Initialize the forward and backward visited sets
        Set<Node> forwardVisited = new HashSet<>();
        Set<Node> backwardVisited = new HashSet<>();

        // Initialize the forward and backward parents maps
        Map<Node, Node> forwardParents = new HashMap<>();
        Map<Node, Node> backwardParents = new HashMap<>();

        // Enqueue the start node in the forward queue
        forwardQueue.offer(startNode);
        forwardVisited.add(startNode);

        // Enqueue the end node in the backward queue
        backwardQueue.offer(endNode);
        backwardVisited.add(endNode);

        // Perform bidirectional traversal until a meeting point is found
        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            // Expand the forward search
            Node forwardCurrent = forwardQueue.poll();
            List<Node> forwardNeighbors = forwardCurrent.getNeighbours(grid);

            for (Node forwardNeighbor : forwardNeighbors) {
                if (!forwardVisited.contains(forwardNeighbor) && !forwardNeighbor.isObstacle()) {
                    forwardQueue.offer(forwardNeighbor);
                    forwardVisited.add(forwardNeighbor);
                    forwardParents.put(forwardNeighbor, forwardCurrent);

                    // Check if the forward neighbor has been visited by the backward search
                    if (backwardVisited.contains(forwardNeighbor)) {
                        // Path found! Reconstruct and return the path
                        return reconstructPath(forwardParents, backwardParents, forwardNeighbor);
                    }
                }
            }

            // Expand the backward search
            Node backwardCurrent = backwardQueue.poll();
            assert backwardCurrent != null;
            List<Node> backwardNeighbors = backwardCurrent.getNeighbours(grid);

            for (Node backwardNeighbor : backwardNeighbors) {
                if (!backwardVisited.contains(backwardNeighbor) && !backwardNeighbor.isObstacle()) {
                    backwardQueue.offer(backwardNeighbor);
                    backwardVisited.add(backwardNeighbor);
                    backwardParents.put(backwardNeighbor, backwardCurrent);

                    // Check if the backward neighbor has been visited by the forward search
                    if (forwardVisited.contains(backwardNeighbor)) {
                        // Path found! Reconstruct and return the path
                        return reconstructPath(forwardParents, backwardParents, backwardNeighbor);
                    }
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }
    private List<Node> reconstructPath(Map<Node, Node> forwardParents, Map<Node, Node> backwardParents, Node meetingNode) {
        List<Node> forwardPath = new ArrayList<>();
        List<Node> backwardPath = new ArrayList<>();

        // Reconstruct the forward path
        Node forwardCurrent = meetingNode;
        while (forwardCurrent != null) {
            forwardPath.add(forwardCurrent);
            forwardCurrent = forwardParents.get(forwardCurrent);
        }

        // Reconstruct the backward path
        Node backwardCurrent = meetingNode;
        while (backwardCurrent != null) {
            backwardPath.add(backwardCurrent);
            backwardCurrent = backwardParents.get(backwardCurrent);
        }

        // Reverse the backward path (excluding the meeting node)
        Collections.reverse(backwardPath.subList(1, backwardPath.size()));

        // Combine the forward and backward paths
        forwardPath.addAll(backwardPath);
        for (Node node : forwardPath) {
            node.setPath(true);
        }
        return forwardPath;
    }


}
