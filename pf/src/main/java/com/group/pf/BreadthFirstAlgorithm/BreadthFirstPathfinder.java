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

    public PathResult<Node> findPath(Node startNode, Node endNode, List<Node> obstacles, int height, int width) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new LinkedHashSet<>();
        Map<Node, Node> parents = new HashMap<>();
        Grid<Node> grid = gridFactory.createGrid(width, height, Node.class);

        if (obstacles != null) {
            for (Node obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode.equals(endNode)) {
                return new PathResult<>(reconstructPath(parents, endNode), new ArrayList<>(visited));
            }

            for (Node neighbor : currentNode.getNeighbors(grid)) {
                if (!visited.contains(neighbor) && !neighbor.isObstacle()) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parents.put(neighbor, currentNode);
                }
            }
        }

        return new PathResult<>(Collections.emptyList(), new ArrayList<>(visited));
    }

    private List<Node> reconstructPath(Map<Node, Node> parents, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        for (Node node : path) {
            node.setPath(true);
        }

        Collections.reverse(path);
        return path;
    }

}
