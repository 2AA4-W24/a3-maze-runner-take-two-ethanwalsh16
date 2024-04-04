package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class CoordinatesToPath {

	public String toPath(List<Coordinate> coords, Maze maze) {
		String path = "";

		Coordinate startPos = maze.getStart();
		Coordinate currentPos = new Coordinate(startPos.x(),startPos.y());
		Orientation direction = Orientation.RIGHT;

		for(int i=0; i<coords.size(); i++){

			Coordinate nextPos = coords.get(i);

			boolean rightOf = nextPos.rightOf(currentPos,direction);
			boolean leftOf = nextPos.leftOf(currentPos, direction);
			
			if(rightOf){
				path += "RF";
				currentPos.turn(direction,Moves.RIGHT);
				direction = direction.turnRight();
			}else if(leftOf){
				path += "LF";
				currentPos.turn(direction,Moves.LEFT);
				direction = direction.turnLeft();
			}else{
				currentPos.straight(direction,Moves.FORWARD);
				path += "F";
			}
		}
		return path;
	}
}
