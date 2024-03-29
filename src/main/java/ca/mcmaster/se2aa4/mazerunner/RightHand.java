package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;


public class RightHand implements MazeSolver {
	// Specific solver iteration, using right hand algorithm.
	// Enum for tracking orientation within maze, as 2D array coordinates will vary

	@Override
	public List<String> solveMaze(Maze maze){
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
				direction = direction.turnRight();
				direction = direction.turnRight();

			}
		}
		// Obtaining factorized path prior to returning both results.
		String factorizedPath = Factorization.FactorPath(path);
		List<String> paths = new ArrayList<String>();
		paths.add(path);
		paths.add(factorizedPath);
		return paths;
		/* 
			switch (direction){
				case RIGHT:
					// If right turn available, do as such
					if(!maze.get(currentPosition.getY()+1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()+1);
						path += "RF";
						direction = Orientation.DOWN;
					}
					// If no right, go straight
					else if(!maze.get(currentPosition.getY()).get(currentPosition.getX()+1).equals("W")){
						currentPosition.setX(currentPosition.getX()+1);
						path += "F";
					}
					// If no straight, go left
					else if(!maze.get(currentPosition.getY()-1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()-1);
						path += "LF";
						direction = Orientation.UP;
					// If all else fails, go back (appending RRF to represent 180 degree turn, then a move forward).
					}else{
						currentPosition.setX(currentPosition.getX()-1);
						path += "RRF";
						direction = Orientation.LEFT;
					}
					break;
				// Cases UP, DOWN, and LEFT all have the same decision logic of RIGHT (go right -> go straight -> go left -> turn around).
				case UP:
					if(!maze.get(currentPosition.getY()).get(currentPosition.getX()+1).equals("W")){
						currentPosition.setX(currentPosition.getX()+1);
						path += "RF";
						direction = Orientation.RIGHT;
					}
					else if(!maze.get(currentPosition.getY()-1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()-1);
						path += "F";
					}
					else if(!maze.get(currentPosition.getY()).get(currentPosition.getX()-1).equals("W")){
						currentPosition.setX(currentPosition.getX()-1);
						path += "LF";
						direction = Orientation.LEFT;
					}else{
						currentPosition.setY(currentPosition.getY()+1);
						path += "RRF";
						direction = Orientation.DOWN;
					}
					break;
				case DOWN:
					if(!maze.get(currentPosition.getY()).get(currentPosition.getX()-1).equals("W")){
						currentPosition.setX(currentPosition.getX()-1);
						path += "RF";
						direction = Orientation.LEFT;
					}
					else if(!maze.get(currentPosition.getY()+1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()+1);
						path += "F";
					}
					else if(!maze.get(currentPosition.getY()).get(currentPosition.getX()+1).equals("W")){
						currentPosition.setX(currentPosition.getX()+1);
						path += "LF";
						direction = Orientation.RIGHT;
					}else{
						currentPosition.setY(currentPosition.getY()-1);
						path += "RRF";
						direction = Orientation.UP;
					}
					break;
				case LEFT:
					if(!maze.get(currentPosition.getY()-1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()-1);
						path += "RF";
						direction = Orientation.UP;
					}
					else if(!maze.get(currentPosition.getY()).get(currentPosition.getX()-1).equals("W")){
						currentPosition.setX(currentPosition.getX()-1);
						path += "F";
					}
					else if(!maze.get(currentPosition.getY()+1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()+1);
						path += "LF";
						direction = Orientation.DOWN;
					}else{
						currentPosition.setX(currentPosition.getX()+1);
						path += "RRF";
						direction = Orientation.RIGHT;
					}
					break;
			}
		}
		*/
	}
	
	
}
