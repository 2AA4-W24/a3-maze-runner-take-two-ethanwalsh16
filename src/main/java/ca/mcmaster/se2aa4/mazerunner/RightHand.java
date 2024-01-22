package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;


enum Orientation {
	UP,
	RIGHT,
	DOWN,
	LEFT
}

public class RightHand implements MazeSolver {

	@Override
	public String solveMaze(ArrayList<ArrayList<String>> maze, Coordinate entry1, Coordinate entry2) {
		String path = "";
		Coordinate currentPosition = entry1;
		Coordinate endPosition = entry2;
		Orientation direction = Orientation.RIGHT;
		while(!Coordinate.equivalentTo(endPosition,currentPosition)){
			switch (direction){
				case RIGHT:
					if(!maze.get(currentPosition.getY()+1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()+1);
						path += "RF";
						direction = Orientation.DOWN;
					}
					else if(!maze.get(currentPosition.getY()).get(currentPosition.getX()+1).equals("W")){
						currentPosition.setX(currentPosition.getX()+1);
						path += "F";
					}
					else if(!maze.get(currentPosition.getY()-1).get(currentPosition.getX()).equals("W")){
						currentPosition.setY(currentPosition.getY()-1);
						path += "LF";
						direction = Orientation.UP;
					}else{
						currentPosition.setX(currentPosition.getX()-1);
						path += "RRF";
						direction = Orientation.LEFT;
					}
					break;
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
			// Example for going right
			// If you can go right, go right (need to access one row lower at same x (y+1),(x))
			// Append RF to path string
			// If you cannot go right, check to see if you can go straight (check (x,y+1))
			// If you cannot go right or straight, go left (check (x,y-1))
			// If you cannot go left, go back on the path you came.
		}
		return path;
	}
	
}
