package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodes;

	public Graph(){
		nodes = new ArrayList<Node>();
	}

    public void addNode(Node n){
        nodes.add(n);
    }

    public Node getNode(int index){
        return nodes.get(index);
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
}
