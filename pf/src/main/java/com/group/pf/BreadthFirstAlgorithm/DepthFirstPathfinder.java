package com.group.pf.BreadthFirstAlgorithm;

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
public class DepthFirstPathfinder {
    private final GridFactory gridFactory;

    public List<BFSNode> findPath(BFSNode startBFSNode) {
        List<BFSNode> path = new ArrayList<>();
        Grid<BFSNode> grid = gridFactory.createGrid(50, 50, BFSNode.class);
        Stack<BFSNode> stack = new Stack<>();
        Set<BFSNode> visited = new HashSet<>();

        stack.push(startBFSNode);

        while (!stack.isEmpty()) {
            BFSNode currentBFSNode = stack.pop();
            visited.add(currentBFSNode);

            if (currentBFSNode.isEnd()) {
                // Path found
                path = reconstructPath(startBFSNode, currentBFSNode);
                break;
            }

            List<Node> neighborNodes = currentBFSNode.getNeighbors(grid);
            for (Node node : neighborNodes) {
                BFSNode neighbor = (BFSNode) node;
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    neighbor.setPrevious(currentBFSNode);
                }
            }
        }

        return path;
    }

    private List<BFSNode> reconstructPath(BFSNode startBFSNode, BFSNode endBFSNode) {
        LinkedList<BFSNode> path = new LinkedList<>();
        BFSNode currentBFSNode = endBFSNode;

        while (currentBFSNode != startBFSNode) {
            path.addFirst(currentBFSNode);
            currentBFSNode = (BFSNode) currentBFSNode.getPrevious();
        }

        path.addFirst(startBFSNode);

        for (BFSNode BFSNode : path) {
            BFSNode.setPath(true);
        }

        return new ArrayList<>(path);
    }

}
