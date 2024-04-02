package ca.mcmaster.se2aa4.mazerunner;

public class RightHand implements MazeSolver {
	// Specific solver iteration, using right hand algorithm.
	// Enum for tracking orientation within maze, as 2D array coordinates will vary

	@Override
	public String solveMaze(Maze maze){
		String path = "";
		// To monitor current location, as well as end point (assuming end is right entry)
		Coordinate currentPos = maze.getStart();
		Coordinate endPos = maze.getEnd();
		Orientation direction = Orientation.RIGHT;

		while(!currentPos.equivalentTo(endPos)){
			// Different process depending on orientation.
			if(maze.rightTurnAvailable(currentPos,direction)){
				path += "RF";
				currentPos.turn(direction,Moves.RIGHT);
				direction = direction.turnRight();

			}else if(maze.straightAvailable(currentPos,direction)){
				path += "F";
				currentPos.straight(direction,Moves.FORWARD);
			
			}else if(maze.leftTurnAvailable(currentPos,direction)){
				path += "LF";
				currentPos.turn(direction,Moves.LEFT);
				direction = direction.turnLeft();

			}else{
				path += "RRF";
				currentPos.straight(direction,Moves.UTURN);
				direction = direction.opposite();
			}
		}
		return path;
	}
}
