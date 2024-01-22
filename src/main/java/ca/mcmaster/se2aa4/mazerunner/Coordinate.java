package ca.mcmaster.se2aa4.mazerunner;

public class Coordinate {
	int x;
	int y;

	public Coordinate(int xCoordinate, int yCoordinate){
		x = xCoordinate;
		y = yCoordinate;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}
}
