package ca.mcmaster.se2aa4.mazerunner.tools;

import ca.mcmaster.se2aa4.mazerunner.MazeSolver;
import ca.mcmaster.se2aa4.mazerunner.graphalgorithm.GraphAdapter;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.righthand.RightHand;
import ca.mcmaster.se2aa4.mazerunner.tremaux.Tremaux;

public class Benchmark {
	public String benchmarkResults(Maze maze, String baseline){
		MazeSolver baseSolver;
		String pathBase = "";

		switch(baseline){
			case "tremaux":
				baseSolver = new Tremaux();
				pathBase = baseSolver.solveMaze(maze);
				break;
			case "graph":
				baseSolver = new GraphAdapter();
				pathBase = baseSolver.solveMaze(maze);
				break;
			default: 
				baseSolver = new RightHand();
				pathBase = baseSolver.solveMaze(maze);	
				break;
		}

		return pathBase;
	}
	
}
