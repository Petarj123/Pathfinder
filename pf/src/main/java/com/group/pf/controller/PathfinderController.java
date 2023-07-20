package com.group.pf.controller;

import com.group.pf.AStarAlgorithm.AStarNode;
import com.group.pf.AStarAlgorithm.AStarPathfinder;
import com.group.pf.BreadthFirstAlgorithm.BFSNode;
import com.group.pf.BreadthFirstAlgorithm.BreadthFirstPathfinder;
import com.group.pf.DTO.*;
import com.group.pf.DepthFirstAlgorithm.DFSNode;
import com.group.pf.DepthFirstAlgorithm.DepthFirstPathfinder;
import com.group.pf.DijkstraAlgorithm.DijkstraNode;
import com.group.pf.DijkstraAlgorithm.DijkstraPathfinder;
import com.group.pf.Swarm.BiDirectionalSwarmPathfinder;
import com.group.pf.Swarm.SwarmNode;
import com.group.pf.main.GridFactory;
import com.group.pf.main.Maze;
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
    private final DijkstraPathfinder dijkstraPathfinder;
    private final BreadthFirstPathfinder breadthFirstPathfinder;
    private final BiDirectionalSwarmPathfinder biDirectionalSwarmPathfinder;
    private final DepthFirstPathfinder depthFirstPathfinder;
    private final Maze maze;

    @GetMapping("/home")
    public String home(Model model) {
        AStarPathfinder aStarPathfinder = new AStarPathfinder(gridFactory);
        DijkstraPathfinder dijkstraPathfinder = new DijkstraPathfinder(gridFactory);
        BreadthFirstPathfinder breadthFirstPathfinder = new BreadthFirstPathfinder(gridFactory);
        BiDirectionalSwarmPathfinder biDirectionalSwarmPathfinder = new BiDirectionalSwarmPathfinder(gridFactory);
        DepthFirstPathfinder depthFirstPathfinder = new DepthFirstPathfinder(gridFactory);
        model.addAttribute("AStar Algorithm", aStarPathfinder);
        model.addAttribute("Dijkstra Algorithm", dijkstraPathfinder);
        model.addAttribute("Breadth First Algorithm", breadthFirstPathfinder);
        model.addAttribute("Bidirectional Swarm Algorithm", biDirectionalSwarmPathfinder);
        model.addAttribute("Depth First Algorithm", depthFirstPathfinder);
        return "pathfinder";
    }

    @PostMapping("/pathfinder/aStar")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PathfindingResult findPathAStar(@RequestBody PathfinderRequest requestBody) {
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

        PathResult<AStarNode> pathResult = aStarPathfinder.findPath(startNode, endNode, obstacleNodes, requestBody.height(), requestBody.width());
        List<Coordinates> pathCoordinates = convertNodeToCoordinate(pathResult.path());
        List<Coordinates> visitedCoordinates = convertNodeToCoordinate(pathResult.visited());
        return new PathfindingResult(pathCoordinates, visitedCoordinates);
    }

    @PostMapping("/pathfinder/dijkstra")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PathfindingResult findPathDijkstra(@RequestBody PathfinderRequest requestBody) {
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

        PathResult<DijkstraNode> pathResult = dijkstraPathfinder.findPath(startNode, endNode, obstacleNodes, requestBody.height(), requestBody.width());

        // Extract the coordinates from the path nodes and visited nodes
        List<Coordinates> path = convertNodeToCoordinate(pathResult.path());
        List<Coordinates> visited = convertNodeToCoordinate(pathResult.visited());

        return new PathfindingResult(path, visited);
    }

    @PostMapping("/pathfinder/breadthFirst")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PathfindingResult findPathBreadthFirst(@RequestBody PathfinderRequest requestBody) {
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

        PathResult<BFSNode> pathResult = breadthFirstPathfinder.findPath(startNode, endNode, obstacleNodes, requestBody.height(), requestBody.width());

        List<Coordinates> pathCoords = convertNodeToCoordinate(pathResult.path());
        List<Coordinates> visitedCoords = convertNodeToCoordinate(pathResult.visited());

        return new PathfindingResult(pathCoords, visitedCoords);
    }
    @PostMapping("/pathfinder/depthFirst")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PathfindingResult findPathDepthFirst(@RequestBody PathfinderRequest requestBody) {
        List<Coordinates> startCoords = requestBody.start();
        List<Coordinates> endCoords = requestBody.end();
        List<Coordinates> obstacleCoords = requestBody.obstacles();

        Coordinates startCoord = startCoords.get(0);
        Coordinates endCoord = endCoords.get(0);

        DFSNode startNode = new DFSNode(startCoord.x(), startCoord.y());
        DFSNode endNode = new DFSNode(endCoord.x(), endCoord.y());
        startNode.setStart(true);
        endNode.setEnd(true);

        List<DFSNode> obstacleNodes = obstacleCoords.stream()
                .map(coordinates -> new DFSNode(coordinates.x(), coordinates.y()))
                .collect(Collectors.toList());

        PathResult<DFSNode> pathResult = depthFirstPathfinder.findPath(startNode, endNode, obstacleNodes, requestBody.height(), requestBody.width());

        List<Coordinates> pathCoords = convertNodeToCoordinate(pathResult.path());
        List<Coordinates> visitedCoords = convertNodeToCoordinate(pathResult.visited());

        return new PathfindingResult(pathCoords, visitedCoords);
    }
    @PostMapping("/pathfinder/biDirectionalSwarm")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PathfindingResult findPathBiDirectionalSwarm(@RequestBody PathfinderRequest requestBody) {
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

        PathResult<SwarmNode> pathResult = biDirectionalSwarmPathfinder.findPath(startNode, endNode, obstacleNodes, requestBody.height(), requestBody.width());

        // Extract the coordinates from the path nodes and visited nodes
        List<Coordinates> path = convertNodeToCoordinate(pathResult.path());
        List<Coordinates> visited = convertNodeToCoordinate(pathResult.visited());

        return new PathfindingResult(path, visited);
    }
    // TODO FIX THIS
    @PostMapping("/pathfinder/maze")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Coordinates> generateMaze(@RequestBody MazeRequest requestBody) {
        System.out.println(maze.generateMaze(requestBody.height(), requestBody.width()));
        return maze.generateMaze(requestBody.height(), requestBody.width());
    }

    private void setObstacles(List<? extends Node> obstacles) {
        for (Node node : obstacles) {
            node.setObstacle(true);
        }
    }

    private List<Coordinates> convertNodeToCoordinate(List<? extends Node> path) {
        return path.stream()
                .map(node -> new Coordinates(node.getX(), node.getY()))
                .toList();
    }

}
