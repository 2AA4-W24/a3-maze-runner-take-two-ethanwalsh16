package ca.mcmaster.se2aa4.mazerunner;

public class Coordinate {
	// Class to handle coordinates, used for moving through mazes and finding solution path.
	int x;
	int y;

	public Coordinate(int xCoordinate, int yCoordinate){
		x = xCoordinate;
		y = yCoordinate;
	}

	public int X(){
		return this.x;
	}

	public int Y(){
		return this.y;
	}

	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}

	// Comparator function, used to determine whether path is at end of maze.
	public boolean equivalentTo(Coordinate c){
		if((this.X() == c.X()) && (this.Y() == c.Y())){
			return true;
		}else{
			return false;
		}
	}

	public void turn(Orientation o, Moves m){
		int mult = m.equals(Moves.LEFT) ? 1 : -1;
		switch(o){
			case Orientation.DOWN:
				this.x += 1*mult;
				break;
			case Orientation.UP:
				this.x += (-1)*mult;
				break;
			case Orientation.LEFT:
				this.y += mult;
				break;
			case Orientation.RIGHT:
				this.y += (-1)*mult;
				break;
		}
	}

	public void straight(Orientation o, Moves m){
		int mult = m.equals(Moves.FORWARD) ? 1 : -1;
		switch(o){
			case Orientation.DOWN:
				this.y += mult;
				break;
			case Orientation.UP:
				this.y += (-1)*mult;
				break;
			case Orientation.LEFT:
				this.x += (-1)*mult;
				break;
			case Orientation.RIGHT:
				this.x += mult;
				break;
		}
	}
}
