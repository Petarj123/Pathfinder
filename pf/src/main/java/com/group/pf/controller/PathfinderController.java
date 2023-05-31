package com.group.pf.controller;

import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.BreadthFirstAlgorithm.BFSNode;
import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.BreadthFirstAlgorithm.DepthFirstPathfinder;
import com.group.pf.DTO.Coordinates;
import com.group.pf.DTO.PathfinderRequest;
import com.group.pf.DijkstraAlgorithm.DijkstraNode;
import com.group.pf.DijkstraAlgorithm.DijkstraPathfinder;
import com.group.pf.Swarm.BiDirectionalSwarmPathfinder;
import com.group.pf.Swarm.SwarmNode;
import com.group.pf.main.Grid;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PathfinderController {
    private final GridFactory gridFactory;
    private final AStarPathfinder aStarPathfinder;
    private final DijkstraPathfinder dijkstraPathfinder;
    private final BreadthFirstPathfinder breadthFirstPathfinder;
    private final DepthFirstPathfinder depthFirstPathfinder;
    private final BiDirectionalSwarmPathfinder biDirectionalSwarmPathfinder;

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

    @PostMapping("/pathfinder/aStar")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Coordinates> findPathAStar(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        AStarNode startNode = new AStarNode(startCoord.x(), startCoord.y());
        startNode.setStart(true);
        AStarNode endNode = new AStarNode(endCoord.x(), endCoord.y());
        endNode.setEnd(true);
        List<AStarNode> obstacleNodes = obstacleCoords.stream()
                .map(coordinates -> new AStarNode(coordinates.x(), coordinates.y()))
                .toList();
        setObstacles(obstacleNodes);

        List<AStarNode> path = aStarPathfinder.findPath(startNode, endNode, obstacleNodes);

        // Extract the coordinates from the path nodes
        System.out.println(path);
        return convertNodeToCoordinate(path);
    }
    @PostMapping("/pathfinder/dijkstra")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Coordinates> findPathDijkstra(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        DijkstraNode startNode = new DijkstraNode(startCoord.x(), startCoord.y());
        DijkstraNode endNode = new DijkstraNode(endCoord.x(), endCoord.y());
        startNode.setStart(true);
        endNode.setEnd(true);

        List<DijkstraNode> obstacleNodes = obstacleCoords.stream()
                .map(coordinates -> new DijkstraNode(coordinates.x(), coordinates.y()))
                .toList();
        setObstacles(obstacleNodes);
        List<DijkstraNode> path = dijkstraPathfinder.findPath(startNode, endNode, obstacleNodes);

        // Extract the coordinates from the path nodes
        System.out.println(path);
        return convertNodeToCoordinate(path);
    }
    @PostMapping("/pathfinder/breadthFirst")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Coordinates> findPathBreadthFirst(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        BFSNode startNode = new BFSNode(startCoord.x(), startCoord.y());
        BFSNode endNode = new BFSNode(endCoord.x(), endCoord.y());
        startNode.setStart(true);
        endNode.setEnd(true);

        List<BFSNode> obstacleNodes = obstacleCoords.stream()
               .map(coordinates -> new BFSNode(coordinates.x(), coordinates.y()))
               .toList();
        setObstacles(obstacleNodes);

        List<BFSNode> path = breadthFirstPathfinder.findPath(startNode, endNode, obstacleNodes);
        System.out.println(path);
        return convertNodeToCoordinate(path);
    }
    @PostMapping("/pathfinder/depthFirst")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Coordinates> findPathDepthFirst(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        BFSNode startNode = new BFSNode(startCoord.x(), startCoord.y());
        BFSNode endNode = new BFSNode(endCoord.x(), endCoord.y());
        startNode.setStart(true);
        endNode.setEnd(true);

        List<BFSNode> obstacleNodes = obstacleCoords.stream()
                .map(coordinates -> new BFSNode(coordinates.x(), coordinates.y()))
                .toList();
        setObstacles(obstacleNodes);

        List<BFSNode> path = depthFirstPathfinder.findPath(startNode, endNode, obstacleNodes);
        System.out.println(path);
        return convertNodeToCoordinate(path);
    }
    @PostMapping("/pathfinder/biDirectionalSwarm")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Coordinates> findPathBiDirectionalSwarm(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        SwarmNode startNode = new SwarmNode(startCoord.x(), startCoord.y());
        SwarmNode endNode = new SwarmNode(endCoord.x(), endCoord.y());
        startNode.setStart(true);
        endNode.setEnd(true);

        List<SwarmNode> obstacleNodes = obstacleCoords.stream()
              .map(coordinates -> new SwarmNode(coordinates.x(), coordinates.y()))
              .toList();
        setObstacles(obstacleNodes);

        List<SwarmNode> path = biDirectionalSwarmPathfinder.findPath(startNode, endNode, obstacleNodes);
        return convertNodeToCoordinate(path);
    }
    private void setObstacles(List<? extends Node> obstacles){
        for (Node node : obstacles){
            node.setObstacle(true);
        }
    }
    private List<Coordinates> convertNodeToCoordinate(List<? extends Node> path){
        return path.stream()
                .map(node -> new Coordinates(node.getX(), node.getY()))
                .toList();
    }

}
