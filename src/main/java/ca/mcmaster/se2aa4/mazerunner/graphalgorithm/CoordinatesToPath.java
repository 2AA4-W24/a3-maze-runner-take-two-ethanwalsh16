package ca.mcmaster.se2aa4.mazerunner.graphalgorithm;

import java.util.List;

import ca.mcmaster.se2aa4.mazerunner.enums.Moves;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

public class CoordinatesToPath {

	// Converts an incoming list of coordinates from the BFS algorithm, and converts into path string.
	public String toPath(List<Coordinate> coords, Maze maze) {
		String path = "";

		Coordinate startPos = maze.getStart();
		Coordinate currentPos = new Coordinate(startPos.x(),startPos.y());
		Orientation direction = Orientation.RIGHT;

		// Iteratively cycle through coordinate list, appending to string each time.
		for(int i=0; i<coords.size(); i++){

			Coordinate nextPos = coords.get(i);

			boolean rightOf = nextPos.rightOf(currentPos,direction);
			boolean leftOf = nextPos.leftOf(currentPos, direction);
			
			// Determining if next move is turn or proceeding forward.
			if(rightOf){
				path += "RF";
				currentPos.turn(direction, Moves.RIGHT);
				direction = direction.turnRight();
			}else if(leftOf){
				path += "LF";
				currentPos.turn(direction, Moves.LEFT);
				direction = direction.turnLeft();
			}else{
				currentPos.straight(direction, Moves.FORWARD);
				path += "F";
			}

		}
		return path;
	}
}
