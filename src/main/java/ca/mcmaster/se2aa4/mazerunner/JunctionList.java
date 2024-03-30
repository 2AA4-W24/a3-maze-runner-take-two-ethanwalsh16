package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

public class JunctionList {
	List<Junction> junctions;

	public JunctionList(){
		junctions = new ArrayList<Junction>();
	}

	public int has(Junction in_junction){
		for(int i =0; i<junctions.size(); i++){
			Junction j = junctions.get(i);
			if(j.x() == in_junction.x() && j.y() == in_junction.y()){
				return i;
			}
		}
		return -1;
	}

	public void add(Junction j){
		junctions.add(j);
	}

	public Junction get(int index){
		return junctions.get(index);
	}
}
