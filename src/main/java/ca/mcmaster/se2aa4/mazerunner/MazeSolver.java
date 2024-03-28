package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

// Interface used to implement a variety of maze solving methods and allow for further additions later.
public interface MazeSolver {
	List<String> solveMaze(ArrayList<ArrayList<String>> maze, Coordinate entry1, Coordinate entry2);
}

