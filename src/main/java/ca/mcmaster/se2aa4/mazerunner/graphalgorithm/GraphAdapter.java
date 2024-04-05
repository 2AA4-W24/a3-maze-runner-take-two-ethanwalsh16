package ca.mcmaster.se2aa4.mazerunner.graphalgorithm;

import java.util.List;

import ca.mcmaster.se2aa4.mazerunner.MazeSolver;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

public class GraphAdapter implements MazeSolver{
	
	@Override
	public String solveMaze(Maze maze) {
		Graph g = BuildNodes(maze);
		connectGraph(g,maze);
		GraphAlgorithm graphAlg = new GraphAlgorithm();
		String result = graphAlg.findShortestPath(g,maze);
		return result;
	}

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
}
