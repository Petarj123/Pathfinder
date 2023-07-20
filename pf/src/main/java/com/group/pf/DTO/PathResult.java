package com.group.pf.DTO;

import com.group.pf.main.Node;

import java.util.List;

public record PathResult<T extends Node>(List<T> path, List<T> visited) {
}
