package com.group.pf.BreadthFirstAlgorithm;

import com.group.pf.testPackage.Grid;
import com.group.pf.testPackage.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class BreadthFirstPathfinder {
    private Grid<BFSNode> grid;
    private BFSNode startBFSNode;
    private BFSNode endBFSNode;

    public List<BFSNode> findPath() {
        Queue<BFSNode> queue = new LinkedList<>();
        Set<BFSNode> visited = new HashSet<>();
        Map<BFSNode, BFSNode> parents = new HashMap<>();

        queue.offer(startBFSNode);
        visited.add(startBFSNode);

        while (!queue.isEmpty()) {
            BFSNode currentBFSNode = queue.poll();

            if (currentBFSNode == endBFSNode) {
                return reconstructPath(parents);
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
        return Collections.emptyList();
    }
    private List<BFSNode> reconstructPath(Map<BFSNode, BFSNode> parents) {
        List<BFSNode> path = new ArrayList<>();
        BFSNode current = endBFSNode;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }
        for (BFSNode BFSNode : path){
            BFSNode.setPath(true);
        }
        Collections.reverse(path);
        return path;
    }
}
