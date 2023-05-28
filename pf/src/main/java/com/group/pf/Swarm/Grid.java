package com.group.pf.Swarm;

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
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Node(i, j);
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
