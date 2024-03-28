package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

	Entries entryFinder = new Entries();
	ArrayList<ArrayList<String>> matrix;
	List<Coordinate> entryPoints;

	public Maze(String file_input) throws FileNotFoundException{
		matrix = Reader.read(file_input);
		entryPoints = entryFinder.findEntries(matrix); 	
	}

	public List<String> generatePaths(String method) {
		if(method.equals("tremaux")){
			MazeSolver solver = new Tremaux();
			return solver.solveMaze(matrix,entryPoints.get(0),entryPoints.get(1));
		}else{
			MazeSolver solver = new RightHand();
			return solver.solveMaze(matrix,entryPoints.get(0),entryPoints.get(1));
		}
	}

	public String testUserPath(String userString){
		Verifier verifier = new Verifier();
		String isCorrect= verifier.verifyPath(userString, matrix, entryPoints);
		return isCorrect;

	}
}
