package com.group.pf.BreadthFirstAlgorithm;

import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@RequiredArgsConstructor
@Log4j2
public class BreadthFirstPathfinder {
    private final GridFactory gridFactory;

    public List<BFSNode> findPath(BFSNode startBFSNode, BFSNode endBFSNode, List<BFSNode> obstacles, int height, int width) {
        if (startBFSNode == null || endBFSNode == null || height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Queue<BFSNode> queue = new ArrayDeque<>();
        BitSet visited = new BitSet(height * width);
        int[] parents = new int[height * width];
        Arrays.fill(parents, -1);
        Grid<BFSNode> grid = gridFactory.createGrid(width, height, BFSNode.class);

        if (obstacles != null) {
            for (BFSNode obstacle : obstacles) {
                grid.setObstacle(obstacle.getX(), obstacle.getY(), true);
            }
        }
        int startIdx = startBFSNode.getX() + startBFSNode.getY() * width;
        int endIdx = endBFSNode.getX() + endBFSNode.getY() * width;
        queue.offer(startBFSNode);
        visited.set(startIdx);

        while (!queue.isEmpty()) {
            BFSNode currentBFSNode = queue.poll();
            int currentIdx = currentBFSNode.getX() + currentBFSNode.getY() * width;
            if (currentBFSNode.isObstacle()) {
                log.debug("Node {} {}", currentBFSNode.getX(), currentBFSNode.getY());
            }
            if (currentIdx == endIdx) {
                return reconstructPath(parents, endBFSNode, width);
            }

            for (Node neighborNode : currentBFSNode.getNeighbors(grid)) {
                BFSNode neighbor = (BFSNode) neighborNode;
                int neighborIdx = neighbor.getX() + neighbor.getY() * width;
                if (!visited.get(neighborIdx) && !neighbor.isObstacle()) {
                    queue.offer(neighbor);
                    visited.set(neighborIdx);
                    parents[neighborIdx] = currentIdx;
                }
            }
        }
        return Collections.emptyList();
    }

    private List<BFSNode> reconstructPath(int[] parents, BFSNode endBFSNode, int width) {
        List<BFSNode> path = new ArrayList<>();
        BFSNode current = endBFSNode;
        while (current != null) {
            path.add(current);
            int currentIdx = current.getX() + current.getY() * width;
            current = parents[currentIdx] == -1 ? null : new BFSNode(parents[currentIdx] % width, parents[currentIdx] / width);
        }
        for (BFSNode bfsNode : path) {
            bfsNode.setPath(true);
        }
        for (int i = 0, j = path.size() - 1; i < j; i++, j--) {
            Collections.swap(path, i, j);
        }
        return path;
    }

}
