package com.group.pf.Swarm;

import com.group.pf.BreadthFirstAlgorithm.BFSNode;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class BiDirectionalSwarmPathfinder {
    private final GridFactory gridFactory;

    public List<SwarmNode> findPath(SwarmNode startSwarmNode, SwarmNode endSwarmNode, List<SwarmNode> obstacles, int height, int width) {
        Grid<SwarmNode> grid = gridFactory.createGrid(width, height, SwarmNode.class);
        // Initialize the forward and backward queues
        Queue<SwarmNode> forwardQueue = new LinkedList<>();
        Queue<SwarmNode> backwardQueue = new LinkedList<>();

        // Initialize the forward and backward visited sets
        Set<SwarmNode> forwardVisited = new HashSet<>();
        Set<SwarmNode> backwardVisited = new HashSet<>();

        // Initialize the forward and backward parents maps
        Map<SwarmNode, SwarmNode> forwardParents = new HashMap<>();
        Map<SwarmNode, SwarmNode> backwardParents = new HashMap<>();

        if (obstacles != null) {
            for (SwarmNode obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }

        // Enqueue the start node in the forward queue
        forwardQueue.offer(startSwarmNode);
        forwardVisited.add(startSwarmNode);

        // Enqueue the end node in the backward queue
        backwardQueue.offer(endSwarmNode);
        backwardVisited.add(endSwarmNode);

        // Perform bidirectional traversal until a meeting point is found
        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            // Expand the forward search
            SwarmNode forwardCurrent = forwardQueue.poll();
            List<Node> forwardNeighbors = forwardCurrent.getNeighbors(grid);

            for (Node forwardNeighborNode : forwardNeighbors) {
                SwarmNode forwardNeighbor = (SwarmNode) forwardNeighborNode;
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
            SwarmNode backwardCurrent = backwardQueue.poll();
            assert backwardCurrent != null;
            List<Node> backwardNeighbors = backwardCurrent.getNeighbors(grid);

            for (Node backwardNeighborNode : backwardNeighbors) {
                SwarmNode backwardNeighbor = (SwarmNode) backwardNeighborNode;
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

    private List<SwarmNode> reconstructPath(Map<SwarmNode, SwarmNode> forwardParents, Map<SwarmNode, SwarmNode> backwardParents, SwarmNode meetingSwarmNode) {
        List<SwarmNode> forwardPath = new ArrayList<>();
        List<SwarmNode> backwardPath = new ArrayList<>();

        // Reconstruct the forward path
        SwarmNode forwardCurrent = meetingSwarmNode;
        while (forwardCurrent != null) {
            forwardPath.add(forwardCurrent);
            forwardCurrent = forwardParents.get(forwardCurrent);
        }

        // Reconstruct the backward path
        SwarmNode backwardCurrent = meetingSwarmNode;
        while (backwardCurrent != null) {
            backwardPath.add(backwardCurrent);
            backwardCurrent = backwardParents.get(backwardCurrent);
        }

        // Reverse the backward path (excluding the meeting node)
        Collections.reverse(backwardPath.subList(1, backwardPath.size()));

        // Combine the forward and backward paths
        forwardPath.addAll(backwardPath);
        for (SwarmNode swarmNode : forwardPath) {
            swarmNode.setPath(true);
        }
        return forwardPath;
    }


}
