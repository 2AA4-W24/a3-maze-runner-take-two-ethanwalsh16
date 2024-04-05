package ca.mcmaster.se2aa4.mazerunner;

public class Coordinate {
	// Class to handle coordinates, used for moving through mazes and finding solution path.
	int x;
	int y;

	public Coordinate(int xCoordinate, int yCoordinate){
		x = xCoordinate;
		y = yCoordinate;
	}

	public int x(){
		return this.x;
	}

	public int y(){
		return this.y;
	}

	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}

	// Comparator function, used to determine whether path is at end of maze.
	public boolean equivalentTo(Coordinate c){
		return (this.x() == c.x()) && (this.y() == c.y());
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

	public boolean rightOf(Coordinate currentPos, Orientation direction) {
		switch(direction){
			case Orientation.UP:
				return (this.x() - 1 == currentPos.x());
			case Orientation.RIGHT:
				return (this.y() - 1 == currentPos.y());
			case Orientation.DOWN:
				return (this.x() + 1 == currentPos.x());
			case Orientation.LEFT:
				return (this.y() + 1 == currentPos.y());
			default:
				return false;
		}
	}

	public boolean leftOf(Coordinate currentPos, Orientation direction) {
		switch(direction){
			case Orientation.UP:
				return (this.x() + 1 == currentPos.x());
			case Orientation.RIGHT:
				return (this.y() + 1 == currentPos.y());
			case Orientation.DOWN:
				return (this.x() - 1 == currentPos.x());
			case Orientation.LEFT:
				return (this.y() - 1 == currentPos.y());
			default:
				return false;
		}
	}
}
