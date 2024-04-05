package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.enums.Moves;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.tremaux.Junction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;


public class JunctionTest {
	private static final Logger logger = LogManager.getLogger();

    @Test
    void incrementTest() {
		Maze maze;
		try {
			maze = new Maze("./examples/small.maz.txt");
			Junction j = new Junction(new Coordinate(1,1),Orientation.RIGHT,maze);
			j.incrementPrev(Orientation.RIGHT);
			assertEquals(1,j.left());
		} catch (FileNotFoundException e) {
			logger.error("File not found.");
		}
    }

	@Test
	void minMarkTest(){
		Maze maze;
		try {
			maze = new Maze("./examples/small.maz.txt");
			Junction j = new Junction(new Coordinate(1,1),Orientation.RIGHT,maze);
			j.incrementPrev(Orientation.RIGHT);
			j.incrementMove(Moves.FORWARD,Orientation.RIGHT);
			j.incrementMove(Moves.LEFT,Orientation.RIGHT);
			String min = j.minMark(Orientation.RIGHT);
			assertEquals("PREV",min);
		} catch (FileNotFoundException e) {
			logger.error("File not found.");
		}
	}


}

