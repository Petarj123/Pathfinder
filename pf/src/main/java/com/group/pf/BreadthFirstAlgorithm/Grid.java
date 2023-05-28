package com.group.pf.BreadthFirstAlgorithm;

import lombok.Data;

@Data
public class Grid {
    private int width;
    private int height;
    private Node[][] grid;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Node[width][height];
        initializeGrid();
    }
    private void initializeGrid() {
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Node(x, y);
            }
        }
    }
    public void setObstacle(int x, int y, boolean obstacle) {
        grid[x][y].setObstacle(obstacle);
    }
    public void setStart(int x, int y, boolean start) {
        grid[x][y].setStart(start);
    }
    public void setEnd(int x, int y, boolean end) {
        grid[x][y].setEnd(end);
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
