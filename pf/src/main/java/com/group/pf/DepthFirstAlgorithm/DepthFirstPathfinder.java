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

    public PathResult<DFSNode> findPath(DFSNode startDFSNode, DFSNode endDFSNode, List<DFSNode> obstacles, int height, int width) {

        Grid<DFSNode> grid = gridFactory.createGrid(width, height, DFSNode.class);

        if (obstacles != null) {
            for (DFSNode obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }
        
        Set<DFSNode> visited = new LinkedHashSet<>();
        Map<DFSNode, DFSNode> parents = new HashMap<>();
        Stack<DFSNode> stack = new Stack<>();

        stack.push(startDFSNode);
        while (!stack.isEmpty()) {
            DFSNode currentDFSNode = stack.pop();
            if (visited.contains(currentDFSNode)) continue;

            visited.add(currentDFSNode);
            if (currentDFSNode.equals(endDFSNode)) {
                return new PathResult<>(reconstructPath(parents, endDFSNode), new ArrayList<>(visited));
            }

            for (Node neighborNode : currentDFSNode.getNeighbors(grid)) {
                DFSNode neighbor = (DFSNode) neighborNode;
                if (!visited.contains(neighbor) && !neighbor.isObstacle()) {
                    stack.push(neighbor);
                    parents.put(neighbor, currentDFSNode);
                }
            }
        }

        return new PathResult<>(Collections.emptyList(), new ArrayList<>(visited));
    }

    private List<DFSNode> reconstructPath(Map<DFSNode, DFSNode> parents, DFSNode endDFSNode) {
        List<DFSNode> path = new ArrayList<>();
        DFSNode current = endDFSNode;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}
