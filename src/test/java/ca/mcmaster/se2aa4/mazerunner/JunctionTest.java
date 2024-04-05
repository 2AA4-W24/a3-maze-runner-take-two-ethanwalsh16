package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;


public class JunctionTest {
	private static final Logger logger = LogManager.getLogger();

    @Test
    public void incrementTest() {
		Maze maze;
		try {
			maze = new Maze("./examples/small.maz.txt");
			Junction j = new Junction(new Coordinate(1,1),Orientation.RIGHT,maze);
			j.incrementPrev(Orientation.RIGHT);
			assertEquals(j.left(),1);
		} catch (FileNotFoundException e) {
			logger.error("File not found.");
		}
    }

	@Test
	public void minMarkTest(){
		Maze maze;
		try {
			maze = new Maze("./examples/small.maz.txt");
			Junction j = new Junction(new Coordinate(1,1),Orientation.RIGHT,maze);
			j.incrementPrev(Orientation.RIGHT);
			j.incrementMove(Moves.FORWARD,Orientation.RIGHT);
			j.incrementMove(Moves.LEFT,Orientation.RIGHT);
			String min = j.minMark(Orientation.RIGHT);
			assertTrue(min.equals("PREV"));
		} catch (FileNotFoundException e) {
			logger.error("File not found.");
		}
	}


}

