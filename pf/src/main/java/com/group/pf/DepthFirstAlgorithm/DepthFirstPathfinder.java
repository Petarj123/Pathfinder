package com.group.pf.DepthFirstAlgorithm;

import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.DTO.PathResult;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DepthFirstPathfinder {
    private final GridFactory gridFactory;

    public PathResult<Node> findPath(Node startNode, Node endNode, List<Node> obstacles, int height, int width) {

        Grid<Node> grid = gridFactory.createGrid(width, height, Node.class);

        if (obstacles != null) {
            for (Node obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }


        Set<Node> visited = new LinkedHashSet<>();
        Map<Node, Node> parents = new HashMap<>();
        Stack<Node> stack = new Stack<>();

        stack.push(startNode);
        while (!stack.isEmpty()) {
            Node currentDFSNode = stack.pop();
            if (visited.contains(currentDFSNode)) continue;

            visited.add(currentDFSNode);
            if (currentDFSNode.equals(endNode)) {
                return new PathResult<>(reconstructPath(parents, endNode), new ArrayList<>(visited));
            }

            for (Node neighbor : currentDFSNode.getNeighbors(grid)) {
                if (!visited.contains(neighbor) && !neighbor.isObstacle()) {
                    stack.push(neighbor);
                    parents.put(neighbor, currentDFSNode);
                }
            }
        }

        return new PathResult<>(Collections.emptyList(), new ArrayList<>(visited));
    }

    private List<Node> reconstructPath(Map<Node, Node> parents, Node endDFSNode) {
        List<Node> path = new ArrayList<>();
        Node current = endDFSNode;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}
