package com.group.pf.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

public record PathfinderRequest(List<Coordinates> start, List<Coordinates> end,List<Coordinates> obstacles) {

}
