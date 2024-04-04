package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GraphAlgorithm {

    private Graph BuildNodes(Maze maze){

        List<Integer> dimensions = maze.getDimensions();
        Graph graph = new Graph();

        int xDim = dimensions.get(0);
        int yDim = dimensions.get(1);

        for(int i=1; i<yDim-1; i++){
            for(int j=1; j<xDim-1; j++){

                Coordinate currentPos = new Coordinate(j,i);
                
				// Determining neighbours of node.
				if(maze.isTile(j,i) && maze.inBounds(currentPos)){
					Node newNode = new Node(currentPos);
					graph.addNode(newNode);
				}
            }
		}
		Node start = new Node(maze.getStart());
		graph.addNode(start);
		Node end = new Node( maze.getEnd());
		graph.addNode(end);
        
        return graph;
        
    }

	private void connectGraph(Graph graph, Maze maze){
		for(int i=0; i<graph.getSize(); i++){
			Coordinate currentPos = graph.getNode(i).getId();
			Boolean disableLeft = false;
			Boolean disableRight = false;
			if(currentPos.equivalentTo(maze.getEnd())){
				disableRight = true;
			}
			if(currentPos.equivalentTo(maze.getStart())){
				disableLeft = true;
			}
			if(maze.isTile(currentPos.x(),currentPos.y()-1)){
				Coordinate up = new Coordinate(currentPos.x(),currentPos.y()-1);
				graph.getNode(graph.has(new Node(currentPos))).connectNodes(graph.getNode(graph.has(new Node(up))));
			}
			if(maze.isTile(currentPos.x(),currentPos.y()+1)){
				Coordinate down = new Coordinate(currentPos.x(),currentPos.y()+1);
				graph.getNode(graph.has(new Node(currentPos))).connectNodes(graph.getNode(graph.has(new Node(down))));
			}
			if(!disableRight){
				if(maze.isTile(currentPos.x()+1,currentPos.y())){
					Coordinate right = new Coordinate(currentPos.x()+1,currentPos.y());
					graph.getNode(graph.has(new Node(currentPos))).connectNodes(graph.getNode(graph.has(new Node(right))));
				}
			}
			if(!disableLeft){
				if(maze.isTile(currentPos.x()-1,currentPos.y())){
					Coordinate left = new Coordinate(currentPos.x()-1,currentPos.y());
					graph.getNode(graph.has(new Node(currentPos))).connectNodes(graph.getNode(graph.has(new Node(left))));
				}
			}
		}
	}

    public String findShortestPath(Maze maze){
        Graph graph = BuildNodes(maze);
		connectGraph(graph,maze);
        Queue<Node> nodeQueue = new ArrayDeque<Node>();

		graph.changeCost(graph.has(new Node(maze.getStart())),0);
		graph.addPrev(graph.has(new Node(maze.getStart())),new Node(maze.getStart()));
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
					graph.addPrev(neighbourIndex,current);
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
