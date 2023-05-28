package com.group.pf.DijkstraAlgorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node implements Comparable<Node>{
    private int x;
    private int y;
    private int distance;
    private boolean isStart;
    private boolean isEnd;
    private boolean isObstacle;
    private boolean isPath;
    private Node previous;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.distance = Integer.MAX_VALUE;
        this.isStart = false;
        this.isEnd = false;
        this.isObstacle = false;
        this.isPath = false;
        this.previous = null;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.distance, o.distance);
    }
    public List<Node> getNeighbours(Grid grid){
        List<Node> neighbors = new ArrayList<>();
        int currentX = getX();
        int currentY = getY();
        // Check the top neighbor
        if (grid.isWithinBounds(currentX, currentY - 1)) {
            neighbors.add(grid.getNode(currentX, currentY - 1));
        }

        // Check the bottom neighbor
        if (grid.isWithinBounds(currentX, currentY + 1)) {
            neighbors.add(grid.getNode(currentX, currentY + 1));
        }

        // Check the left neighbor
        if (grid.isWithinBounds(currentX - 1, currentY)) {
            neighbors.add(grid.getNode(currentX - 1, currentY));
        }

        // Check the right neighbor
        if (grid.isWithinBounds(currentX + 1, currentY)) {
            neighbors.add(grid.getNode(currentX + 1, currentY));
        }

        return neighbors;
    }
}
