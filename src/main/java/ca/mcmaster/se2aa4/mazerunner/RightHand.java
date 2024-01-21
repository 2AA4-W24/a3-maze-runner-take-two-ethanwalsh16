package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

public class RightHand implements MazeSolver {

	@Override
	public String solveMaze(ArrayList<ArrayList<String>> maze) {
		return maze.get(0).get(0);
	}
	
}
