package com.group.pf.DTO;

import java.util.List;

public record PathfinderRequest(List<Coordinates> start, List<Coordinates> end,List<Coordinates> obstacles) {

}
