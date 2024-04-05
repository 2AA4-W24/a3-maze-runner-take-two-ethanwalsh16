package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class GraphAlgTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Test
	public void baseSolve(){
		MazeSolver solver = new GraphAdapter();
		try {
			Maze m = new Maze("./examples/small.maz.txt");
			String path = solver.solveMaze(m);
			String expected = "FLFRFFLFFFFFFRFFFFRFFLFFRFFLF";
			assertTrue(path.equals(expected));
		} catch (FileNotFoundException e) {
			logger.error("File not found");
		}
	}
}
