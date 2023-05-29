package com.group.pf.DijkstraAlgorithm;

import com.group.pf.testPackage.Grid;
import com.group.pf.testPackage.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class DijkstraPathfinder {
    private Grid<DijkstraNode> grid;
    private DijkstraNode startDijkstraNode;
    private DijkstraNode endDijkstraNode;

    public List<DijkstraNode> findPath() {
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
        Set<DijkstraNode> visited = new HashSet<>();

        startDijkstraNode.setDistance(0);
        queue.offer(startDijkstraNode);

        while (!queue.isEmpty()) {
            DijkstraNode currentDijkstraNode = queue.poll();
            if (currentDijkstraNode == endDijkstraNode) {
                return reconstructPath();
            }
            visited.add(currentDijkstraNode);
            for (Node neighborNodes : currentDijkstraNode.getNeighbors(grid)){
                DijkstraNode neighbor = (DijkstraNode) neighborNodes;
                if (!visited.contains(neighbor) && !neighbor.isObstacle()){
                    int distance = currentDijkstraNode.getDistance() + 1; // Assuming all edges have weight 1
                    if (distance < neighbor.getDistance()){
                        neighbor.setDistance(distance);
                        neighbor.setPrevious(currentDijkstraNode);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<DijkstraNode> reconstructPath() {
        List<DijkstraNode> path = new ArrayList<>();
        DijkstraNode current = endDijkstraNode;

        while (current != null) {
            path.add(current);
            current = (DijkstraNode) current.getPrevious();
        }
        for (DijkstraNode dijkstraNode : path) {
            dijkstraNode.setPath(true);
        }
        Collections.reverse(path);
        return path;
    }
}
