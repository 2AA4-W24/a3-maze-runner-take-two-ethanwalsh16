package ca.mcmaster.se2aa4.mazerunner.graphalgorithm;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GraphAlgorithm {


    public String findShortestPath(Graph graph, Maze maze){
        Queue<Node> nodeQueue = new ArrayDeque<Node>();

		graph.changeCost(graph.has(new Node(maze.getStart())),0);
		graph.addPrevID(graph.has(new Node(maze.getStart())),new Node(maze.getStart()));
        for(int i=0; i<graph.getSize(); i++){
            if(graph.getNode(i).getCost() == 0){
                nodeQueue.add(graph.getNode(i));
            }
        }// Printing for now to test it.

        while(!nodeQueue.isEmpty()){
            Node current = nodeQueue.remove();
            int index = graph.has(current);

            Set<Node> surroundings = graph.getNode(index).getSurroundings();
            Iterator<Node> iterator = surroundings.iterator();

            while(iterator.hasNext()){
                int neighbourIndex = graph.has(iterator.next());
                if(graph.getNode(neighbourIndex).getCost() == Integer.MAX_VALUE){
                    graph.changeCost(neighbourIndex,graph.getNode(index).getCost()+1);
					graph.addPrevID(neighbourIndex,current);
                    nodeQueue.add(graph.getNode(neighbourIndex));
                }
            }
        }

        Node end = new Node(maze.getEnd());
		List<Coordinate> path = new ArrayList<Coordinate>();

		Coordinate iterator = end.getId();
		while(!iterator.equivalentTo(maze.getStart())){
			path.add(iterator);
			iterator = graph.getNode(graph.has(new Node(iterator))).getPrev();
		}

		Collections.reverse(path);
		CoordinatesToPath ctp = new CoordinatesToPath();
		String stringPath = ctp.toPath(path,maze);
    
        return stringPath;
    }
}
