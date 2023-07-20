package com.group.pf.BreadthFirstAlgorithm;

import com.group.pf.DTO.PathResult;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@RequiredArgsConstructor
public class BreadthFirstPathfinder {
    private final GridFactory gridFactory;

    public PathResult<BFSNode> findPath(BFSNode startBFSNode, BFSNode endBFSNode, List<BFSNode> obstacles, int height, int width) {
        Queue<BFSNode> queue = new LinkedList<>();
        Set<BFSNode> visited = new LinkedHashSet<>();
        Map<BFSNode, BFSNode> parents = new HashMap<>();
        Grid<BFSNode> grid = gridFactory.createGrid(width, height, BFSNode.class);

        if (obstacles != null) {
            for (BFSNode obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }

        queue.offer(startBFSNode);
        visited.add(startBFSNode);

        while (!queue.isEmpty()) {
            BFSNode currentBFSNode = queue.poll();
            if (currentBFSNode.equals(endBFSNode)) {
                return new PathResult<>(reconstructPath(parents, endBFSNode), new ArrayList<>(visited));
            }

            for (Node neighborNode : currentBFSNode.getNeighbors(grid)) {
                BFSNode neighbor = (BFSNode) neighborNode;
                if (!visited.contains(neighbor) && !neighbor.isObstacle()) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parents.put(neighbor, currentBFSNode);
                }
            }
        }

        return new PathResult<>(Collections.emptyList(), new ArrayList<>(visited));
    }

    private List<BFSNode> reconstructPath(Map<BFSNode, BFSNode> parents, BFSNode endBFSNode) {
        List<BFSNode> path = new ArrayList<>();
        BFSNode current = endBFSNode;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        for (BFSNode node : path) {
            node.setPath(true);
        }

        Collections.reverse(path);
        return path;
    }

}
