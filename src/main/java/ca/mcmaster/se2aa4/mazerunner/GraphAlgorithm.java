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
                Coordinate currentPos = new Coordinate(i,j);
                Node n = new Node(currentPos);
                int index = graph.has(n);
                Node current;
                // Finding current node to deal with (to avoid duplicates.)
                if(index == -1){
                    graph.addNode(n);
                    current = n;
                }else{
                    current = graph.getNode(index);
                }
                if(maze.isTile(i,j) && maze.inBounds(currentPos)){
                    if(maze.straightAvailable(currentPos,direction)){
                        Coordinate up = new Coordinate(i-1,j);
                        Node upNode = new Node(up);
                        int iUp = graph.has(upNode);
                        if(iUp == -1){
                            graph.addNode(upNode);
                        }else{
                            upNode = graph.getNode(iUp);
                        }
                        upNode.connectNodes(current);
                        current.connectNodes(upNode);
                    }
                    if(maze.rightTurnAvailable(currentPos,direction)){
                        Coordinate right = new Coordinate(i,j+1);
                        Node rightNode = new Node(right);
                        int iRight = graph.has(rightNode);
                        if(iRight == -1){
                            graph.addNode(rightNode);
                        }else{
                            rightNode = graph.getNode(iRight);
                        }
                        rightNode.connectNodes(current);
                        current.connectNodes(rightNode);
                    }
                    if(maze.leftTurnAvailable(currentPos,direction)){
                        Coordinate left = new Coordinate(i,j-1);
                        Node leftNode= new Node(left);
                        int iLeft = graph.has(leftNode);
                        if(iLeft == -1){
                            graph.addNode(leftNode);
                        }else{
                            leftNode = graph.getNode(iLeft);
                        }
                        leftNode.connectNodes(current);
                        current.connectNodes(leftNode);
                    }
                    if(maze.prevAvailable(currentPos,direction)){
                        Coordinate down = new Coordinate(i+1,j);
                        Node downNode = new Node(down);
                        int iDown = graph.has(downNode);
                        if(iDown == -1){
                            graph.addNode(downNode);
                        }else{
                            downNode = graph.getNode(iDown);
                        }
                        downNode.connectNodes(current);
                        current.connectNodes(downNode);
                    }
                }
            }
            // Add entry and end points at the end.
            Coordinate startPos = maze.getStart();
            Coordinate endPos = maze.getEnd();
            Node start = new Node(startPos);
            Node end = new Node(endPos);
            Node right = new Node(new Coordinate(startPos.X()+1, startPos.Y()));
            Node left = new Node(new Coordinate(endPos.X()-1, endPos.Y()));

            int rightIndex = graph.has(right);
            right = graph.getNode(rightIndex);
            right.connectNodes(start);
            start.connectNodes(right);

            int leftIndex = graph.has(left);
            left = graph.getNode(leftIndex);
            left.connectNodes(end);
            end.connectNodes(left);
            
            graph.addNode(start);
            graph.addNode(end);
        }
        return graph;
    }

    public String findShortestPath(Maze maze){
        Graph graph = BuildGraph(maze);
        graph.toString(); // Printing for now to test it.
        return "Empty path, not implemented yet.";
    }
}
