package ca.mcmaster.se2aa4.mazerunner.maze;

import java.util.ArrayList;
import java.util.List;

public class Entries {
	// Finds entry and exit points of a maze by scanning the first and last columns.
	public List<Coordinate> findEntries(ArrayList<ArrayList<String>> matrix){

		List<String> startColumn = returnColumn(0,matrix);
		Coordinate entry1 = new Coordinate(0,0);
		Coordinate entry2 = new Coordinate(0,0);

		for(int i=0; i<startColumn.size(); i++){
			if(!startColumn.get(i).equals("W")){
				entry1 = new Coordinate(0,i);
			}
		}

		List<String> endColumn = returnColumn((matrix.get(0).size()-1),matrix);
		
		for(int i=0; i<endColumn.size(); i++){
			if(!endColumn.get(i).equals("W")){
				entry2 = new Coordinate(matrix.get(0).size()-1,i);
			}
		}

		List<Coordinate> entries = new ArrayList<>();
		entries.add(entry1);
		entries.add(entry2);
		return entries;
	}

	// Used to return a column of the maze (useful in finding entry points specifically)
	public ArrayList<String> returnColumn(int n, ArrayList<ArrayList<String>> matrix){
		ArrayList<String> column = new ArrayList<>();
		for(int i=0; i<matrix.size(); i++){
			String currentSlot = matrix.get(i).get(n);
			column.add(currentSlot);
		}
		return column;
	}
}
