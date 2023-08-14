package com.group.pf.main;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Node {
    private int x;
    private int y;
    private boolean isStart;
    private boolean isEnd;
    private boolean isObstacle;
    private boolean isPath;
    private Node previous;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.isStart = false;
        this.isEnd = false;
        this.isObstacle = false;
        this.isPath = false;
        this.previous = null;
    }

    public List<Node> getNeighbors(Grid<?> grid) {
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
    public List<Node> getNeighborsMaze(Grid<?> grid) {
        List<Node> neighbors = new ArrayList<>();

        int currentX = getX();
        int currentY = getY();

        // Check the top neighbor (2 cells away)
        if (grid.isWithinBounds(currentX, currentY - 2)) {
            neighbors.add(grid.getNode(currentX, currentY - 2));
        }

        // Check the bottom neighbor (2 cells away)
        if (grid.isWithinBounds(currentX, currentY + 2)) {
            neighbors.add(grid.getNode(currentX, currentY + 2));
        }

        // Check the left neighbor (2 cells away)
        if (grid.isWithinBounds(currentX - 2, currentY)) {
            neighbors.add(grid.getNode(currentX - 2, currentY));
        }

        // Check the right neighbor (2 cells away)
        if (grid.isWithinBounds(currentX + 2, currentY)) {
            neighbors.add(grid.getNode(currentX + 2, currentY));
        }

        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
