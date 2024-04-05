package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

public class RightHandTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Test
	void baseSolve(){
		MazeSolver solver = new RightHand();
		try {
			Maze m = new Maze("./examples/small.maz.txt");
			String path = solver.solveMaze(m);
			String expected = "FRFRRFFRFFRFFRRFFFFRFFRFFFFRRFFRFFFFRFFRFFRRFFLFFLFFFFRFFRFFRRFFFFRFFRFFRRFFRFFRFFFFRFFLFFRFFLF";
			assertEquals(expected,path);
		} catch (FileNotFoundException e) {
			logger.error("File not found");
		}
	}
}
