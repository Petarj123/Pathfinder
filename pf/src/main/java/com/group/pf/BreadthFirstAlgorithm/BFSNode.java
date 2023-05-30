package com.group.pf.BreadthFirstAlgorithm;


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
public class BFSNode extends Node {

    public BFSNode(int x, int y) {
        super(x, y);
    }

}
