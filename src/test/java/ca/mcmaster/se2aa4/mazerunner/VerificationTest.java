package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

public class VerificationTest {

	private static final Logger logger = LogManager.getLogger();

	@Test
	public void verifyPath(){
		Verifier verifier = new Verifier();
		Maze maze;
		try {
			maze = new Maze("./examples/straight.maz.txt");
			String result = verifier.verifyPath("FFFF",maze,maze.getStart(),maze.getEnd());
			System.out.println(result);
			assertTrue(result.equals("correct"));
		} catch (FileNotFoundException e) {
			logger.error("File not found");
		}	
	}

	@Test
	public void validPath(){
		Verifier verifier = new Verifier();
		List<Boolean> results = verifier.correctPath("FRFLFRRRRF");
		// Valid path
		assertTrue(results.get(0));
		// Not factorized
		assertFalse(results.get(1));
	}
}
