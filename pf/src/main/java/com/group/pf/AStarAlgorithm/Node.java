package com.group.pf.AStarAlgorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node implements Comparable<Node>{
    private int x;
    private int y;
    private boolean isObstacle;
    private boolean isStart;
    private boolean isEnd;
    private boolean isPath;
    private double gScore;
    private double fScore;
    private double hScore;
    private Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.isObstacle = false;
        this.isStart = false;
        this.isEnd = false;
        this.isPath = false;
        this.gScore = Double.POSITIVE_INFINITY;
        this.fScore = Double.POSITIVE_INFINITY;
        this.hScore = Double.POSITIVE_INFINITY;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.getFScore(), o.getFScore());
    }
    public List<Node> getNeighbors(Grid grid) {
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
