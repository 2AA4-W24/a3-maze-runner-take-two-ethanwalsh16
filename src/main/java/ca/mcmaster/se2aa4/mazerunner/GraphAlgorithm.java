package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.Iterator;

public class GraphAlgorithm {

    private Graph BuildGraph(Maze maze){
        List<Integer> dimensions = maze.getDimensions();
        Graph graph = new Graph();
        Orientation direction = Orientation.UP; // for ease of setting up surroundings.
        int xDim = dimensions.get(0);
        int yDim = dimensions.get(1);
        for(int i=1; i<yDim-1; i++){
            for(int j=1; j<xDim-1; j++){
                Coordinate currentPos = new Coordinate(j,i);
                Node newNode = new Node(currentPos);
                int index = graph.has(newNode);

                Boolean canAdd = false;
                Boolean canAddUp = false;
                Boolean canAddDown = false;
                Boolean canAddRight = false;
                Boolean canAddLeft = false;
                Node upNode = new Node(new Coordinate(j,i-1));
                Node rightNode = new Node(new Coordinate(j+1,i));
                Node leftNode = new Node(new Coordinate(j-1,i));
                Node downNode = new Node(new Coordinate(j,i+1));
                
                // Finding current node to deal with (to avoid duplicates.)
                if(maze.isTile(j,i) && maze.inBounds(currentPos)){
                    canAdd = (index == -1) ? true : false;
                    if(maze.straightAvailable(currentPos, direction)){
                        int upIndex = graph.has(upNode);
                        if(upIndex == -1){
                            canAddUp = true;
                        }else{
                            graph.addNeighbour(upIndex,newNode);
                        }
                    }
                    if(maze.rightTurnAvailable(currentPos,direction)){
                        int rightIndex = graph.has(rightNode);
                        if(rightIndex == -1){
                            canAddRight = true;
                        }else{
                            graph.addNeighbour(rightIndex,newNode);
                        }
                    }
                    if(maze.leftTurnAvailable(currentPos,direction)){
                        int leftIndex = graph.has(leftNode);
                        if(leftIndex == -1){
                            canAddLeft = true;
                        }else{
                            graph.addNeighbour(leftIndex,newNode);
                        }
                    }
                    if(maze.prevAvailable(currentPos,direction)){
                        int downIndex = graph.has(downNode);
                        if(downIndex == -1){
                            canAddDown = true;
                        }else{
                            graph.addNeighbour(downIndex,newNode);
                        }
                    }
                    if(canAdd){
                        if(canAddUp){
                            newNode.connectNodes(upNode);
                            graph.addNode(upNode);
                        }if(canAddDown){
                            newNode.connectNodes(downNode);
                            graph.addNode(downNode);
                        }if(canAddLeft){
                            newNode.connectNodes(leftNode);
                            graph.addNode(leftNode);                            
                        }if(canAddRight){
                            newNode.connectNodes(rightNode);
                            graph.addNode(rightNode);                      
                        }
                        graph.addNode(newNode);
                    }
                }
            }
        }
        Node end = new Node(maze.getEnd());
        int endIndex = graph.has(end);
        if(endIndex == -1){
            Node leftOfEnd = new Node(new Coordinate(maze.getEnd().X()-1,maze.getEnd().Y()));
            int lIndex = graph.has(leftOfEnd);
            graph.addNeighbour(lIndex,end);
            graph.addNode(end);
        }

        Node start = new Node(maze.getStart());
        int startIndex = graph.has(start);
        if(startIndex == -1){
            Node rightOfStart = new Node(new Coordinate(maze.getEnd().X()+1,maze.getEnd().Y()));
            int rIndex = graph.has(rightOfStart);
            graph.addNeighbour(rIndex,start);
            start.setCost(0);
            graph.addNode(start);
        }else{
            graph.changeCost(graph.has(start),0);
        }
        
        System.out.println("Total size: " + graph.getSize());
        for(int i=0; i<graph.getSize(); i++){
            graph.getNode(i).print();
        }
        return graph;
        
    }

    public String findShortestPath(Maze maze){
        Graph graph = BuildGraph(maze);
        Queue<Node> nodeQueue = new ArrayDeque<Node>();
        
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
                    nodeQueue.add(graph.getNode(neighbourIndex));
                }
            }
        }

        Node end = new Node(maze.getEnd());
        int endIndex = graph.has(end);
        System.out.println("Length of shortest path to end node: " + graph.getNode(endIndex).getCost());
    
        System.out.println("Now completed:");
        return "Empty path, not implemented yet.";
    }
}
