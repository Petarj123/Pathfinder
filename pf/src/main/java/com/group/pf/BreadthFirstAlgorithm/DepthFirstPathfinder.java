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
public class DepthFirstPathfinder {
    private Grid<BFSNode> grid;
    private BFSNode startBFSNode;
    private BFSNode endBFSNode;

    public List<BFSNode> findPath() {
        List<BFSNode> path = new ArrayList<>();

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
