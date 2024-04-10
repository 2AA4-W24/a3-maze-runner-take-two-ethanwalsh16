package ca.mcmaster.se2aa4.mazerunner.graphalgorithm;

import java.util.Set;

import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;

import java.util.HashSet;

public class Node {
    /* Basic data structure that is used for graph representation.
     Includes cost, surrounding nodes (allows for edge traversal),
     and tracking of id/prev for a Node's coordinates.
    */
    
    private int cost;
    private Set<Node> surroundings;
    private Coordinate id;
	private Coordinate prev;

    public Node(Coordinate c){
        this.id = c;
        this.cost = Integer.MAX_VALUE;
        this.surroundings = new HashSet<Node>();
		this.prev = new Coordinate(0,0);
    }

    public Set<Node> getSurroundings(){
        return this.surroundings;
    }

    public int getCost(){
        return this.cost;
    }

    public void setCost(int newCost){
        this.cost = newCost;
    }

    public Coordinate getId(){
        return this.id;
    }

    public void connectNodes(Node newNode){
        this.surroundings.add(newNode);
        newNode.surroundings.add(this);
    }

	public void addPrev(Coordinate current) {
		this.prev = current;
	}

	public Coordinate getPrev(){
		return this.prev;
	}

}
