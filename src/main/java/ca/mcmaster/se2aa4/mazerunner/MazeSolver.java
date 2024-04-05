package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

// Interface used to implement a variety of maze solving methods and allow for further additions later.
public interface MazeSolver {
	String solveMaze(Maze maze);
}

