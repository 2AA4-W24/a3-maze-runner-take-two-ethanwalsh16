package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
	
	@Test
	public void turnTest(){
		Coordinate base = new Coordinate(0,0);
		base.turn(Orientation.RIGHT,Moves.RIGHT);
		Coordinate expected = new Coordinate(0,1);
		assertTrue(base.equivalentTo(expected));
		base = new Coordinate(0,0);
		base.turn(Orientation.RIGHT,Moves.LEFT);
		expected = new Coordinate(0,-1);
		assertTrue(base.equivalentTo(expected));
	}

	@Test
	public void inequivalency(){
		Coordinate base = new Coordinate(1,1);
		Coordinate c2 = new Coordinate(2,2);
		assertFalse(base.equivalentTo(c2));
	}

	@Test
	public void equivalency(){
		Coordinate base = new Coordinate(1,1);
		Coordinate c2 = new Coordinate(1,1);
		assertTrue(base.equivalentTo(c2));
	}
}
