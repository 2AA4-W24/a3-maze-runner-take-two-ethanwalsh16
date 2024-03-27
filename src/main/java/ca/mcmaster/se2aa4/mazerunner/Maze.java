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
	private Entries entryFinder = new Entries();

	public Maze(String input) throws FileNotFoundException {
		matrix = new ArrayList<ArrayList<String>>();
		matrix = Reader.read(input);
	}

	public String testUserPath(String userString){
		Coordinate[] entries = entryFinder.findEntries(matrix);
		String isCorrect= Verifier.verifyPath(userString, matrix, entries);
		return isCorrect;
	}

	// Creates factorized and non-factorized paths.
	public String[] generatePaths(String method) {
		Coordinate[] entries = entryFinder.findEntries(matrix);
		if(method.equals("tremaux")){
			MazeSolver solver = new Tremaux();
			return solver.solveMaze(matrix,entries[0],entries[1]);
		}else{
			MazeSolver solver = new RightHand();
			return solver.solveMaze(matrix,entries[0],entries[1]);
		}
	}
}
