package com.group.pf.DijkstraAlgorithm;

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
public class DijkstraPathfinder {
    private final GridFactory gridFactory;

    public PathResult<DijkstraNode> findPath(DijkstraNode startDijkstraNode, DijkstraNode endDijkstraNode, List<DijkstraNode> obstacles, int height, int width) {
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
        Set<DijkstraNode> visited = new LinkedHashSet<>();
        Grid<DijkstraNode> grid = gridFactory.createGrid(width, height, DijkstraNode.class);

        // Set obstacles
        if (obstacles != null) {
            for (DijkstraNode node : obstacles) {
                grid.setObstacle(node.getX(), node.getY(), true);
            }
        }

        startDijkstraNode.setDistance(0);
        queue.offer(startDijkstraNode);

        while (!queue.isEmpty()) {
            DijkstraNode currentDijkstraNode = queue.poll();

            if (currentDijkstraNode.equals(endDijkstraNode)) {
                return new PathResult<>(reconstructPath(currentDijkstraNode), new ArrayList<>(visited));
            }

            visited.add(currentDijkstraNode);

            for (Node neighborNode : currentDijkstraNode.getNeighbors(grid)) {
                DijkstraNode neighbor = (DijkstraNode) neighborNode;
                if (!visited.contains(neighbor) && !neighbor.isObstacle()) {
                    int distance = currentDijkstraNode.getDistance() + 1;

                    if (distance < neighbor.getDistance()) {
                        neighbor.setDistance(distance);
                        neighbor.setPrevious(currentDijkstraNode);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return new PathResult<>(Collections.emptyList(), new ArrayList<>(visited));
    }


    private List<DijkstraNode> reconstructPath(DijkstraNode endDijkstraNode) {
        List<DijkstraNode> path = new ArrayList<>();
        DijkstraNode current = endDijkstraNode;
        System.out.println("Current node: " + current.getX() + " " + current.getY());
        while (current.getPrevious() != null) {
            path.add(current);
            current = (DijkstraNode) current.getPrevious();
        }
        path.add(current);

        for (DijkstraNode dijkstraNode : path) {
            dijkstraNode.setPath(true);
        }

        Collections.reverse(path);
        return path;
    }
}
