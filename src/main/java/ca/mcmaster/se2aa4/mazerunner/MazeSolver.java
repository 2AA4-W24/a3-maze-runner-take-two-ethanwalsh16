package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

public interface MazeSolver {
	String solveMaze(ArrayList<ArrayList<String>> maze, Coordinate entry1, Coordinate entry2);
}

