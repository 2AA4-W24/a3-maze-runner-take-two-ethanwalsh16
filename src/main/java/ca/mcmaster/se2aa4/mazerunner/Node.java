package ca.mcmaster.se2aa4.mazerunner;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Node {
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

	public boolean equivalentTo(Node n1){
		return n1.getId().equivalentTo(n1.getId());
	}

    public int getCost(){
        return this.cost;
    }

    public void incCost(){
        this.cost += 1;
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
