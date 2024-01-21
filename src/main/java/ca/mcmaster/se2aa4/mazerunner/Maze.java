package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Maze {

	ArrayList<ArrayList<String>> matrix;

	public Maze(String input) throws FileNotFoundException {
		matrix = Reader.read(input);
	}

	public String generatePath() {
		MazeSolver solver = new RightHand();
		return solver.solveMaze(matrix);
	}
}
