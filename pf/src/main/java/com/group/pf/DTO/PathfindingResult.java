package com.group.pf.DTO;

import java.util.List;

public record PathfindingResult(List<Coordinates> path, List<Coordinates> visited) {
}
