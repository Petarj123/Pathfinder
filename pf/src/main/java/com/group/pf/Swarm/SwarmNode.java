package com.group.pf.Swarm;


import com.group.pf.testPackage.Node;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SwarmNode extends Node implements Comparable<SwarmNode>{

    private int distanceToStart;
    private int distanceToEnd;

    public SwarmNode(int x, int y) {
        super(x, y);
        this.distanceToStart = Integer.MAX_VALUE;
        this.distanceToEnd = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(SwarmNode o) {
        return Integer.compare(this.distanceToStart + this.distanceToEnd, o.distanceToStart + o.distanceToEnd);
    }
    public void calculateDistanceToStart(SwarmNode startSwarmNode) {
        this.distanceToStart = calculateDistance(startSwarmNode);
    }

    public void calculateDistanceToEnd(SwarmNode endSwarmNode) {
        this.distanceToEnd = calculateDistance(endSwarmNode);
    }

    private int calculateDistance(SwarmNode swarmNode) {
        // Calculate the Manhattan distance between this swarmNode and the given swarmNode
        return Math.abs(this.getX() - swarmNode.getX()) + Math.abs(this.getY() - swarmNode.getY());
    }
}
