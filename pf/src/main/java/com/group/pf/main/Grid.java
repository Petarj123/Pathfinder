package com.group.pf.main;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

@Getter
@Setter
public class Grid<T extends Node> {
    private Class<T> nodeClass;
    private T[][] grid;
    private int width;
    private int height;

    @SuppressWarnings("unchecked")
    public Grid(int width, int height, Class<T> nodeClass) {
        this.width = width;
        this.height = height;
        this.nodeClass = nodeClass;
        this.grid = (T[][]) Array.newInstance(nodeClass, width, height);
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = createNode(x, y);
            }
        }
    }

    public void setObstacle(int x, int y, boolean obstacle) {
        grid[x][y].setObstacle(obstacle);
    }

    public T getNode(int x, int y) {
        if (isWithinBounds(x, y)) {
            return grid[x][y];
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private T createNode(int x, int y) {
        try {
            Constructor<T> constructor = nodeClass.getConstructor(int.class, int.class);
            return constructor.newInstance(x, y);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create node", e);
        }
    }

    protected boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
