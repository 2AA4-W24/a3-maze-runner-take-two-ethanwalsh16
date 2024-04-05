package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.graphalgorithm.GraphAdapter;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

public class GraphAlgTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Test
	void baseSolve(){
		MazeSolver solver = new GraphAdapter();
		try {
			Maze m = new Maze("./examples/small.maz.txt");
			String path = solver.solveMaze(m);
			String expected = "FLFRFFLFFFFFFRFFFFRFFLFFRFFLF";
			assertEquals(expected,path);
		} catch (FileNotFoundException e) {
			logger.error("File not found");
		}
	}
}
