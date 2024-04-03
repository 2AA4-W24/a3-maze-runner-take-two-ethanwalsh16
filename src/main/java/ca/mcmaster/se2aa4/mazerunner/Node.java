package ca.mcmaster.se2aa4.mazerunner;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Node {
    private int cost;
    private Set<Node> surroundings;
    private Coordinate id;

    public Node(Coordinate c){
        this.id = c;
        this.cost = Integer.MAX_VALUE;
        this.surroundings = new HashSet<Node>();
    }

    public Set<Node> getSurroundings(){
        return this.surroundings;
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

    public void print(){
        System.out.println("ID: " + this.id + "Cost: " + this.cost);
        System.out.println("Surroundings");
        Iterator<Node> surroundingsIterator = surroundings.iterator();
        while(surroundingsIterator.hasNext()){
            Node n = surroundingsIterator.next();
            System.out.println(n.getId().toString());
        }
    }

}
