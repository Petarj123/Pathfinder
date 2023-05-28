package com.group.pf.DijkstraAlgorithm;

import lombok.Data;

@Data
public class Grid {
    private Node[][] grid;
    private int width;
    private int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Node[width][height];
        initializerGrid();
    }

    private void initializerGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Node(x, y);
            }
        }
    }
    public Node getNode(int x, int y){
        if (isWithinBounds(x, y)){
            return grid[x][y];
        } else {
            return null;
        }
    }
    protected boolean isWithinBounds(int x, int y){
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
