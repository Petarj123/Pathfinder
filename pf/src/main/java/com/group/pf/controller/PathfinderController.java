package com.group.pf.controller;

import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.BreadthFirstAlgorithm.DepthFirstPathfinder;
import com.group.pf.DTO.PathfinderRequest;
import com.group.pf.DijkstraAlgorithm.DijkstraPathfinder;
import com.group.pf.Swarm.BiDirectionalSwarmPathfinder;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PathfinderController {
    private final GridFactory gridFactory;
    private final AStarPathfinder aStarPathfinder;
    @GetMapping("/home")
    public String home(Model model) {
        Grid<Node> grid = gridFactory.createGrid(50, 50, Node.class);
        AStarPathfinder aStarPathfinder = new AStarPathfinder(gridFactory);
        DijkstraPathfinder dijkstraPathfinder = new DijkstraPathfinder(gridFactory);
        BreadthFirstPathfinder breadthFirstPathfinder = new BreadthFirstPathfinder(gridFactory);
        DepthFirstPathfinder depthFirstPathfinder = new DepthFirstPathfinder(gridFactory);
        BiDirectionalSwarmPathfinder biDirectionalSwarmPathfinder = new BiDirectionalSwarmPathfinder(gridFactory);
        System.out.println(Arrays.deepToString(grid.getGrid()));
        model.addAttribute("grid", grid.getGrid());
        model.addAttribute("AStar Algorithm", aStarPathfinder);
        model.addAttribute("Dijkstra Algorithm", dijkstraPathfinder);
        model.addAttribute("Breadth First Algorithm", breadthFirstPathfinder);
        model.addAttribute("Depth First Algorithm", depthFirstPathfinder);
        model.addAttribute("Bidirectional Swarm Algorithm", biDirectionalSwarmPathfinder);
        return "pathfinder";
    }
    @PostMapping("/pathfinder")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AStarNode> findPath(@RequestBody PathfinderRequest request) {
        int startX = request.startX();
        int startY = request.startY();
        int endX = request.endX();
        int endY = request.endY();
        System.out.println(startX);
        System.out.println(startY);
        System.out.println(endX);
        System.out.println(endY);
        AStarNode aStarStartNode = new AStarNode(startX, startY);
        AStarNode aStarEndNode = new AStarNode(endX, endY);

        return aStarPathfinder.findPath(aStarStartNode, aStarEndNode);
    }
}
