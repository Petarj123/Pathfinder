package com.group.pf.DijkstraAlgorithm;

import com.group.pf.AStarAlgorithm.AStarNode;
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

    public List<DijkstraNode> findPath(DijkstraNode startDijkstraNode, DijkstraNode endDijkstraNode, List<DijkstraNode> obstacles) {
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
        Set<DijkstraNode> visited = new HashSet<>();
        Grid<DijkstraNode> grid = gridFactory.createGrid(50, 50, DijkstraNode.class);

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

            visited.add(currentDijkstraNode);

            for (Node neighborNode : currentDijkstraNode.getNeighbors(grid)) {
                DijkstraNode neighbor = (DijkstraNode) neighborNode;
                if (neighbor.isObstacle()){
                    System.out.println(neighbor);
                }
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

        return Collections.emptyList(); // Return an empty list if no path is found
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
