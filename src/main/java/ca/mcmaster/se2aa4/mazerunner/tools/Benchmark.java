package ca.mcmaster.se2aa4.mazerunner.tools;

import ca.mcmaster.se2aa4.mazerunner.MazeSolver;
import ca.mcmaster.se2aa4.mazerunner.graphalgorithm.GraphAdapter;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.righthand.RightHand;
import ca.mcmaster.se2aa4.mazerunner.tremaux.Tremaux;

import java.util.List;
import java.util.ArrayList;

public class Benchmark {
	// Used for running an additional maze solving method and determining benchmarking results.
	public List<Double> benchmarkResults(Maze maze, String baseline, String methodPath){
		String pathBase = "";
		double baselineStart = System.nanoTime();
		MazeSolver baseSolver;
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
		double baselineEnd = System.nanoTime();
		List<Double> results = new ArrayList<Double>();

		results.add(Math.round((baselineEnd - baselineStart)/1.0e4) / 100.0);
		double speedup = Math.round((double) pathBase.length() / (double) methodPath.length() * 100) / 100.0;
		results.add(speedup);

		return results;
	}
	
}
