package com.group.pf.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.BreadthFirstAlgorithm.DepthFirstPathfinder;
import com.group.pf.DTO.Coordinates;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AStarNode> findPath(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        AStarNode startNode = new AStarNode(startCoord.x(), startCoord.y());
        startNode.setStart(true);
        System.out.println(startNode.isStart());
        AStarNode endNode = new AStarNode(endCoord.x(), endCoord.y());
        endNode.setEnd(true);
        System.out.println(endNode.isEnd());
        List<AStarNode> obstacleNodes = obstacleCoords.stream()
                .map(coordinates -> new AStarNode(coordinates.x(), coordinates.y()))
                .toList();
        for (AStarNode node : obstacleNodes){
            node.setObstacle(true);
        }
        List<AStarNode> path = aStarPathfinder.findPath(startNode, endNode, obstacleNodes);
        System.out.println(path);
        return null;
    }
}
