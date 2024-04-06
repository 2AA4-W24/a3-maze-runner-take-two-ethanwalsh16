package ca.mcmaster.se2aa4.mazerunner.righthand;

import ca.mcmaster.se2aa4.mazerunner.MazeSolver;
import ca.mcmaster.se2aa4.mazerunner.enums.Moves;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

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
			if(maze.moveAvailable(currentPos,direction, Moves.RIGHT)){
				path += "RF";
				currentPos.turn(direction, Moves.RIGHT);
				direction = direction.turnRight();

			}else if(maze.moveAvailable(currentPos,direction, Moves.FORWARD)){
				path += "F";
				currentPos.straight(direction, Moves.FORWARD);
			
			}else if(maze.moveAvailable(currentPos,direction, Moves.LEFT)){
				path += "LF";
				currentPos.turn(direction, Moves.LEFT);
				direction = direction.turnLeft();

			}else{
				path += "RRF";
				currentPos.straight(direction, Moves.UTURN);
				direction = direction.opposite();
			}
		}
		return path;
	}
}
