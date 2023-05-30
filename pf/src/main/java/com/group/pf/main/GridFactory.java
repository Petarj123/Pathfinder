package com.group.pf.main;

import org.springframework.stereotype.Service;

@Service
public class GridFactory {
    public <T extends Node> Grid<T> createGrid(int width, int height, Class<T> nodeClass) {
        return new Grid<>(width, height, nodeClass);
    }
}
