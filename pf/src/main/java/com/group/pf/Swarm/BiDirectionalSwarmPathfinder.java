package com.group.pf.Swarm;

import com.group.pf.DTO.PathResult;
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

    public PathResult<SwarmNode> findPath(SwarmNode startSwarmNode, SwarmNode endSwarmNode, List<SwarmNode> obstacles, int height, int width) {
        Grid<SwarmNode> grid = gridFactory.createGrid(width, height, SwarmNode.class);
        Queue<SwarmNode> forwardQueue = new LinkedList<>();
        Queue<SwarmNode> backwardQueue = new LinkedList<>();
        Set<SwarmNode> forwardVisited = new LinkedHashSet<>();
        Set<SwarmNode> backwardVisited = new LinkedHashSet<>();
        Map<SwarmNode, SwarmNode> forwardParents = new HashMap<>();
        Map<SwarmNode, SwarmNode> backwardParents = new HashMap<>();

        if (obstacles != null) {
            for (SwarmNode obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }

        forwardQueue.offer(startSwarmNode);
        forwardVisited.add(startSwarmNode);
        backwardQueue.offer(endSwarmNode);
        backwardVisited.add(endSwarmNode);

        Set<SwarmNode> totalVisited = new LinkedHashSet<>(); // for storing all visited nodes

        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            SwarmNode forwardCurrent = forwardQueue.poll();
            List<Node> forwardNeighbors = forwardCurrent.getNeighbors(grid);

            for (Node forwardNeighborNode : forwardNeighbors) {
                SwarmNode forwardNeighbor = (SwarmNode) forwardNeighborNode;
                if (!forwardVisited.contains(forwardNeighbor) && !forwardNeighbor.isObstacle()) {
                    forwardQueue.offer(forwardNeighbor);
                    forwardVisited.add(forwardNeighbor);
                    forwardParents.put(forwardNeighbor, forwardCurrent);

                    if (backwardVisited.contains(forwardNeighbor)) {
                        return new PathResult<>(reconstructPath(forwardParents, backwardParents, forwardNeighbor), new ArrayList<>(totalVisited));
                    }
                }
            }

            totalVisited.addAll(forwardVisited); // add forwardVisited to totalVisited

            SwarmNode backwardCurrent = backwardQueue.poll();
            List<Node> backwardNeighbors = backwardCurrent.getNeighbors(grid);

            for (Node backwardNeighborNode : backwardNeighbors) {
                SwarmNode backwardNeighbor = (SwarmNode) backwardNeighborNode;
                if (!backwardVisited.contains(backwardNeighbor) && !backwardNeighbor.isObstacle()) {
                    backwardQueue.offer(backwardNeighbor);
                    backwardVisited.add(backwardNeighbor);
                    backwardParents.put(backwardNeighbor, backwardCurrent);

                    if (forwardVisited.contains(backwardNeighbor)) {
                        return new PathResult<>(reconstructPath(forwardParents, backwardParents, backwardNeighbor), new ArrayList<>(totalVisited));
                    }
                }
            }

            totalVisited.addAll(backwardVisited); // add backwardVisited to totalVisited
        }

        return new PathResult<>(Collections.emptyList(), new ArrayList<>(totalVisited)); // No path found
    }

    private List<SwarmNode> reconstructPath(Map<SwarmNode, SwarmNode> forwardParents, Map<SwarmNode, SwarmNode> backwardParents, SwarmNode meetingSwarmNode) {
        List<SwarmNode> forwardPath = new ArrayList<>();
        List<SwarmNode> backwardPath = new ArrayList<>();

        SwarmNode forwardCurrent = meetingSwarmNode;
        while (forwardCurrent != null) {
            forwardPath.add(forwardCurrent);
            forwardCurrent = forwardParents.get(forwardCurrent);
        }

        SwarmNode backwardCurrent = meetingSwarmNode;
        while (backwardCurrent != null) {
            backwardPath.add(backwardCurrent);
            backwardCurrent = backwardParents.get(backwardCurrent);
        }

        Collections.reverse(backwardPath.subList(1, backwardPath.size()));

        forwardPath.addAll(backwardPath);
        for (SwarmNode swarmNode : forwardPath) {
            swarmNode.setPath(true);
        }
        return forwardPath;
    }


}
