package com.group.pf.BreadthFirstAlgorithm;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node {
    private int x;
    private int y;
    private boolean isStart;
    private boolean isEnd;
    private boolean isObstacle;
    private boolean isPath;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.isStart = false;
        this.isEnd = false;
        this.isObstacle = false;
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
