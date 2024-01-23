package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// To track orientation while solving the maze (F move will act differently when facing UP vs. facing LEFT)
enum Orientation {
	UP,
	RIGHT,
	DOWN,
	LEFT
}

public class Maze {

	ArrayList<ArrayList<String>> matrix;

	public Maze(String input) throws FileNotFoundException {
		matrix = Reader.read(input);
	}

	public String testUserPath(String userString){
		Coordinate[] entries = findEntries();
		String isCorrect= Verifier.verifyPath(userString, matrix, entries);
		return isCorrect;
	}

	// Creates factorized and non-factorized paths.
	public String[] generatePaths() {
		MazeSolver solver = new RightHand();
		Coordinate[] entries = findEntries();
		return solver.solveMaze(matrix,entries[0],entries[1]);
	}

	// Finds entry and exit points of a maze by scanning the first and last columns.
	public Coordinate[] findEntries(){
		ArrayList<String> startColumn = returnColumn(0);
		Coordinate entry1 = new Coordinate(0,0), entry2 = new Coordinate(0,0);
		for(int i=0; i<startColumn.size(); i++){
			if(!startColumn.get(i).equals("W")){
				entry1 = new Coordinate(0,i);
			}
		}
		ArrayList<String> endColumn = returnColumn(matrix.size()-1);
		for(int i=0; i<endColumn.size(); i++){
			if(!endColumn.get(i).equals("W")){
				entry2 = new Coordinate(matrix.get(0).size()-1,i);
			}
		}
		Coordinate[] entries = {entry1,entry2};
		return entries;
	}

	// Used to return a column of the maze (useful in finding entry points specifically)
	public ArrayList<String> returnColumn(int n){
		ArrayList<String> column = new ArrayList<String>();
		for(int i=0; i<matrix.size(); i++){
			String currentSlot = matrix.get(i).get(n);
			column.add(currentSlot);
		}
		return column;
	}
}
