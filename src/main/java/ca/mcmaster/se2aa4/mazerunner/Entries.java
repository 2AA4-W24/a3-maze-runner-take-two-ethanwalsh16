package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

public class Entries {
	// Finds entry and exit points of a maze by scanning the first and last columns.
	public Coordinate[] findEntries(ArrayList<ArrayList<String>> matrix){
		ArrayList<String> startColumn = returnColumn(0,matrix);
		Coordinate entry1 = new Coordinate(0,0), entry2 = new Coordinate(0,0);
		for(int i=0; i<startColumn.size(); i++){
			if(!startColumn.get(i).equals("W")){
				entry1 = new Coordinate(0,i);
			}
		}
		ArrayList<String> endColumn = returnColumn((matrix.get(0).size()-1),matrix);
		for(int i=0; i<endColumn.size(); i++){
			if(!endColumn.get(i).equals("W")){
				entry2 = new Coordinate(matrix.get(0).size()-1,i);
			}
		}
		Coordinate[] entries = {entry1,entry2};
		return entries;
	}

	// Used to return a column of the maze (useful in finding entry points specifically)
	public ArrayList<String> returnColumn(int n, ArrayList<ArrayList<String>> matrix){
		ArrayList<String> column = new ArrayList<String>();
		for(int i=0; i<matrix.size(); i++){
			String currentSlot = matrix.get(i).get(n);
			column.add(currentSlot);
		}
		return column;
	}
}
