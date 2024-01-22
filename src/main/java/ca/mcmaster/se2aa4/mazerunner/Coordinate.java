package ca.mcmaster.se2aa4.mazerunner;

public class Coordinate {
	int x;
	int y;

	public Coordinate(int xCoordinate, int yCoordinate){
		x = xCoordinate;
		y = yCoordinate;
	}

	public int getX(Coordinate c){
		return c.x;
	}

	public int getY(Coordinate c){
		return c.y;
	}
}
