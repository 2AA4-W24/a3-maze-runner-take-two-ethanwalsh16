package ca.mcmaster.se2aa4.mazerunner.tremaux;

import java.util.List;
import java.util.ArrayList;

public class JunctionList {
	//Structure for holding all junctions
	List<Junction> junctions;

	public JunctionList(){
		junctions = new ArrayList<Junction>();
	}

	// Returning an index of an existing junction, or returning -1 if it does not exist yet.
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

	public int getSize(){
		return junctions.size();
	}
}
