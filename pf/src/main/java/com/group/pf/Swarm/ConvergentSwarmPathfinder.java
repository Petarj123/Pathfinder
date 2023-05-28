package com.group.pf.Swarm;

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
    private Grid grid;
    private Node startNode;
    private Node endNode;

    public List<Node> findPath() {
        List<Node> path = new ArrayList<>();

        // Create the forward swarm from the start node
        List<Node> forwardSwarm = new ArrayList<>();
        forwardSwarm.add(startNode);

        // Create the backward swarm from the end node
        List<Node> backwardSwarm = new ArrayList<>();
        backwardSwarm.add(endNode);

        // Initialize convergence flag
        boolean isConverged = false;

        while (!isConverged) {
            // Move the forward swarm towards the backward swarm
            for (Node node : forwardSwarm) {
                // Move the node towards the neighboring node with the lowest distance from the end node
                Node nextNode = getNeighborWithLowestDistanceToEnd(node);
                if (nextNode != null) {
                    node.setPrevious(nextNode);
                    node.setPath(true);
                    if (backwardSwarm.contains(nextNode)) {
                        // The forward swarm has met the backward swarm, convergence reached
                        isConverged = true;
                        break;
                    }
                }
            }

            // Move the backward swarm towards the forward swarm
            for (Node node : backwardSwarm) {
                // Move the node towards the neighboring node with the lowest distance from the start node
                Node nextNode = getNeighborWithLowestDistanceToStart(node);
                if (nextNode != null) {
                    node.setPrevious(nextNode);
                    node.setPath(true);
                    if (forwardSwarm.contains(nextNode)) {
                        // The backward swarm has met the forward swarm, convergence reached
                        isConverged = true;
                        break;
                    }
                }
            }
        }

        // Combine the paths from the start node to the meeting point and from the end node to the meeting point
        Node meetingPoint = findMeetingPoint(forwardSwarm, backwardSwarm);
        List<Node> forwardPath = reconstructPath(startNode, meetingPoint);
        List<Node> backwardPath = reconstructPath(endNode, meetingPoint);

        // Combine the forward and backward paths
        path.addAll(forwardPath);
        path.addAll(backwardPath);

        return path;
    }
    private Node findMeetingPoint(List<Node> forwardSwarm, List<Node> backwardSwarm) {
        // Find the first common node between the forward and backward swarms
        for (Node forwardNode : forwardSwarm) {
            if (backwardSwarm.contains(forwardNode)) {
                return forwardNode;
            }
        }
        return null; // Meeting point not found
    }

    private Node getNeighborWithLowestDistanceToStart(Node node) {
        List<Node> neighbors = node.getNeighbours(grid);
        Node neighborWithLowestDistance = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node neighbor : neighbors) {
            if (neighbor.getDistanceToStart() < lowestDistance) {
                neighborWithLowestDistance = neighbor;
                lowestDistance = neighbor.getDistanceToStart();
            }
        }

        return neighborWithLowestDistance;
    }

    private Node getNeighborWithLowestDistanceToEnd(Node node) {
        List<Node> neighbors = node.getNeighbours(grid);
        Node neighborWithLowestDistance = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node neighbor : neighbors) {
            if (neighbor.getDistanceToEnd() < lowestDistance) {
                neighborWithLowestDistance = neighbor;
                lowestDistance = neighbor.getDistanceToEnd();
            }
        }

        return neighborWithLowestDistance;
    }

    private List<Node> reconstructPath(Node startNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null && current != startNode) {
            path.add(current);
            current = current.getPrevious();
        }

        // Add the start node to the path
        if (current == startNode) {
            path.add(startNode);
        }

        // Reverse the path to get the correct order
        Collections.reverse(path);
        for (Node node : path) {
            node.setPath(true);
        }
        return path;
    }

}
