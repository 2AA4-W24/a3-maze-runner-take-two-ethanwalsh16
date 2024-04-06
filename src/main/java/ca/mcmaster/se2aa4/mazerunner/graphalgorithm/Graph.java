package ca.mcmaster.se2aa4.mazerunner.graphalgorithm;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodes;

	public Graph(){
		nodes = new ArrayList<Node>();
	}

    public void addNode(Node n){
        if(this.has(n) == -1){
            nodes.add(n);
        }
    }

    public int getSize(){
        return nodes.size();
    }

    public Node getNode(int index){
        return nodes.get(index);
    }

    public void addNeighbour(int index, Node n){
		if(this.has(n) == -1){
			this.getNode(index).connectNodes(n);
		}
    }

    public int has(Node n){
		for(int i =0; i<nodes.size(); i++){
			Node curr = nodes.get(i);
			if(curr.getId().equivalentTo(n.getId())){
				return i;
			}
		}
		return -1;
	}

    public void changeCost(int index, int cost){
        this.getNode(index).setCost(cost);
    }

	public void addPrevID(int neighbourIndex, Node current) {
		this.getNode(neighbourIndex).addPrev(current.getId());
	}
}
