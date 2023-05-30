package com.group.pf.AStarAlgorithm;

import com.group.pf.main.Node;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class AStarNode extends Node implements Comparable<AStarNode>{
    private double gScore;
    private double fScore;
    private double hScore;
    private AStarNode parent;

    public AStarNode(int x, int y) {
        super(x, y);
        this.gScore = Double.POSITIVE_INFINITY;
        this.fScore = Double.POSITIVE_INFINITY;
        this.hScore = Double.POSITIVE_INFINITY;
    }


    @Override
    public int compareTo(AStarNode o) {
        return Double.compare(this.getFScore(), o.getFScore());
    }


}
