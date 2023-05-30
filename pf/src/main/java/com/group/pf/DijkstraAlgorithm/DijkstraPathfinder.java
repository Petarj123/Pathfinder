package com.group.pf.DijkstraAlgorithm;

import com.group.pf.BreadthFirstAlgorithm.BFSNode;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@RequiredArgsConstructor
public class DijkstraPathfinder {
    private final GridFactory gridFactory;

    public List<DijkstraNode> findPath(DijkstraNode startDijkstraNode, DijkstraNode endDijkstraNode) {
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
        Set<DijkstraNode> visited = new HashSet<>();
        Grid<DijkstraNode> grid = gridFactory.createGrid(50, 50, DijkstraNode.class);
        startDijkstraNode.setDistance(0);
        queue.offer(startDijkstraNode);

        while (!queue.isEmpty()) {
            DijkstraNode currentDijkstraNode = queue.poll();
            if (currentDijkstraNode == endDijkstraNode) {
                return reconstructPath(endDijkstraNode);
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

    private List<DijkstraNode> reconstructPath(DijkstraNode endDijkstraNode) {
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
