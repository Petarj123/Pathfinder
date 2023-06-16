package com.group.pf.DijkstraAlgorithm;

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
    public List<DijkstraNode> findPath(DijkstraNode startDijkstraNode, DijkstraNode endDijkstraNode, List<DijkstraNode> obstacles, int height, int width) {
        if (startDijkstraNode == null || endDijkstraNode == null || height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
        boolean[][] visited = new boolean[height][width];
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
                return reconstructPath(currentDijkstraNode); // Return the reconstructed path
            }
            visited[currentDijkstraNode.getY()][currentDijkstraNode.getX()] = true;
            for (Node neighborNode : currentDijkstraNode.getNeighbors(grid)) {
                DijkstraNode neighbor = (DijkstraNode) neighborNode;
                if (neighbor.isObstacle()) {
                    System.out.println(neighbor);
                }
                if (!visited[neighbor.getY()][neighbor.getX()] && !neighbor.isObstacle()) {
                    int distance = currentDijkstraNode.getDistance() + 1;
                    if (distance < neighbor.getDistance()) {
                        neighbor.setDistance(distance);
                        neighbor.setPrevious(currentDijkstraNode);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList(); // Return an empty list if no path is found
    }
    private List<DijkstraNode> reconstructPath(DijkstraNode endDijkstraNode) {
        List<DijkstraNode> path = new ArrayList<>();
        DijkstraNode current = endDijkstraNode;
        while (current != null) {
            path.add(current);
            current.setPath(true);
            current = (DijkstraNode) current.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }
}
