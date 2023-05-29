package com.group.pf.Swarm;

import com.group.pf.testPackage.Grid;
import com.group.pf.testPackage.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
@AllArgsConstructor
public class ConvergentSwarmPathfinder {
    private Grid<SwarmNode> grid;
    private SwarmNode startSwarmNode;
    private SwarmNode endSwarmNode;

    public List<SwarmNode> findPath() {
        List<SwarmNode> path = new ArrayList<>();

        // Create the forward swarm from the start node
        List<SwarmNode> forwardSwarm = new ArrayList<>();
        forwardSwarm.add(startSwarmNode);

        // Create the backward swarm from the end node
        List<SwarmNode> backwardSwarm = new ArrayList<>();
        backwardSwarm.add(endSwarmNode);

        // Initialize convergence flag
        boolean isConverged = false;

        while (!isConverged) {
            // Move the forward swarm towards the backward swarm
            for (SwarmNode swarmNode : forwardSwarm) {
                // Move the swarmNode towards the neighboring swarmNode with the lowest distance from the end swarmNode
                SwarmNode nextSwarmNode = getNeighborWithLowestDistanceToEnd(swarmNode);
                if (nextSwarmNode != null) {
                    swarmNode.setPrevious(nextSwarmNode);
                    swarmNode.setPath(true);
                    if (backwardSwarm.contains(nextSwarmNode)) {
                        // The forward swarm has met the backward swarm, convergence reached
                        isConverged = true;
                        break;
                    }
                }
            }

            // Move the backward swarm towards the forward swarm
            for (SwarmNode swarmNode : backwardSwarm) {
                // Move the swarmNode towards the neighboring swarmNode with the lowest distance from the start swarmNode
                SwarmNode nextSwarmNode = getNeighborWithLowestDistanceToStart(swarmNode);
                if (nextSwarmNode != null) {
                    swarmNode.setPrevious(nextSwarmNode);
                    swarmNode.setPath(true);
                    if (forwardSwarm.contains(nextSwarmNode)) {
                        // The backward swarm has met the forward swarm, convergence reached
                        isConverged = true;
                        break;
                    }
                }
            }
        }

        // Combine the paths from the start node to the meeting point and from the end node to the meeting point
        SwarmNode meetingPoint = findMeetingPoint(forwardSwarm, backwardSwarm);
        List<SwarmNode> forwardPath = reconstructPath(startSwarmNode, meetingPoint);
        List<SwarmNode> backwardPath = reconstructPath(endSwarmNode, meetingPoint);

        // Combine the forward and backward paths
        path.addAll(forwardPath);
        path.addAll(backwardPath);

        return path;
    }
    private SwarmNode findMeetingPoint(List<SwarmNode> forwardSwarm, List<SwarmNode> backwardSwarm) {
        // Find the first common node between the forward and backward swarms
        for (SwarmNode forwardSwarmNode : forwardSwarm) {
            if (backwardSwarm.contains(forwardSwarmNode)) {
                return forwardSwarmNode;
            }
        }
        return null; // Meeting point not found
    }

    private SwarmNode getNeighborWithLowestDistanceToStart(SwarmNode swarmNode) {
        List<Node> neighbors = swarmNode.getNeighbors(grid);
        SwarmNode neighborWithLowestDistance = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node neighborNode : neighbors) {
            SwarmNode neighbor = (SwarmNode) neighborNode;
            if (neighbor.getDistanceToStart() < lowestDistance) {
                neighborWithLowestDistance = neighbor;
                lowestDistance = neighbor.getDistanceToStart();
            }
        }

        return neighborWithLowestDistance;
    }

    private SwarmNode getNeighborWithLowestDistanceToEnd(SwarmNode swarmNode) {
        List<Node> neighbors = swarmNode.getNeighbors(grid);
        SwarmNode neighborWithLowestDistance = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node neighborNode : neighbors) {
            SwarmNode neighbor = (SwarmNode) neighborNode;
            if (neighbor.getDistanceToEnd() < lowestDistance) {
                neighborWithLowestDistance = neighbor;
                lowestDistance = neighbor.getDistanceToEnd();
            }
        }

        return neighborWithLowestDistance;
    }

    private List<SwarmNode> reconstructPath(SwarmNode startSwarmNode, SwarmNode endSwarmNode) {
        List<SwarmNode> path = new ArrayList<>();
        SwarmNode current = endSwarmNode;

        while (current != null && current != startSwarmNode) {
            path.add(current);
            current = (SwarmNode) current.getPrevious();
        }

        // Add the start node to the path
        if (current == startSwarmNode) {
            path.add(startSwarmNode);
        }

        // Reverse the path to get the correct order
        Collections.reverse(path);
        for (SwarmNode swarmNode : path) {
            swarmNode.setPath(true);
        }
        return path;
    }

}
