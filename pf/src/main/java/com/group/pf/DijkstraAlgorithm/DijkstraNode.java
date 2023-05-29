package com.group.pf.DijkstraAlgorithm;

import com.group.pf.testPackage.Node;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DijkstraNode extends Node implements Comparable<DijkstraNode>{
    private int distance;

    public DijkstraNode(int x, int y) {
        super(x, y);
        this.distance = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(DijkstraNode o) {
        return Integer.compare(this.distance, o.distance);
    }

}
