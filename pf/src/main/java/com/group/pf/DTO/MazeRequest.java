package com.group.pf.DTO;

public record MazeRequest(Coordinates startNode, Coordinates endNode, int height, int width) {
}
