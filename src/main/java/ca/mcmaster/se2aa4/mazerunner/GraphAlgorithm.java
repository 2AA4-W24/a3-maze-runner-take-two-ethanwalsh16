package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

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
                        System.out.println("HERE: " + leftIndex);
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
                            upNode.connectNodes(newNode);
                            System.out.println("ADDING UP NODE : " + upNode.getId() + " JI: " + j + ", " + i);
                            graph.addNode(upNode);
                        }if(canAddDown){
                            newNode.connectNodes(downNode);
                            downNode.connectNodes(newNode);
                            System.out.println("ADDING DOWN NODE : " + downNode.getId() + " JI: " + j + ", " + i);
                            graph.addNode(downNode);
                        }if(canAddLeft){
                            newNode.connectNodes(leftNode);
                            leftNode.connectNodes(newNode);
                            System.out.println("ADDING LEFT NODE : " + leftNode.getId() + " JI: " + j + ", " + i);
                            graph.addNode(leftNode);
                        }if(canAddRight){
                            newNode.connectNodes(rightNode);
                            rightNode.connectNodes(newNode);
                            System.out.println("ADDING RIGHT NODE : " + rightNode.getId() + " JI: " + j + ", " + i);
                            graph.addNode(rightNode);
                        }
                        graph.addNode(newNode);
                    }
                }
            }
        }
        return graph;
    }

    public String findShortestPath(Maze maze){
        Graph graph = BuildGraph(maze);
        System.out.println("Printing graph:");
        System.out.println("SIZE: " + graph.getSize());
        for(int i=0; i<graph.getSize(); i++){
            graph.getNode(i).print();
        }// Printing for now to test it.
        return "Empty path, not implemented yet.";
    }
}
