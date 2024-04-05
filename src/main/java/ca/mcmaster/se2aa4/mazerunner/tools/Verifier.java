package ca.mcmaster.se2aa4.mazerunner.tools;

import java.util.List;

import ca.mcmaster.se2aa4.mazerunner.enums.Moves;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

import java.util.ArrayList;

public class Verifier {
	// Confirming the method is suitable to verify (only contains correct characters)
	public List<Boolean> correctPath(String userPath){
		boolean correctChars = true;
		boolean factorized = false;
		for(int i=0; i<userPath.length(); i++){
			if(Character.isDigit(userPath.charAt(i))){
				factorized = true;
				break;
			}
		}
		for(int i=0; i<userPath.length(); i++){
			if(userPath.charAt(i) != 'F' && userPath.charAt(i) != 'R' && userPath.charAt(i) != 'L'){
				correctChars = false;
				break;
			}
		}
		List<Boolean> results = new ArrayList<Boolean>();
		results.add(correctChars);
		results.add(factorized);
		return results;
	}
	
	// Testing user entered path to see if it is a valid maze solution (requires a true value from correctPath in order to be run).
	public String verifyPath(String userPath, Maze maze, Coordinate entry1, Coordinate entry2){
		
		// Tracking position and orientation for moving through the maze.
		Coordinate currentPos = new Coordinate(entry1.x(), entry1.y());
		Coordinate endPoint = entry2;
		Orientation direction = Orientation.RIGHT;
		String result = "Incorrect";
		userPath = userPath.replaceAll("\\s","");
		// Verifying path from Left to right.
		result = iteration(direction, currentPos, endPoint, userPath, maze);
		if(!result.equals("Correct")){
			// If not, also verifying path as right to left.
			currentPos = new Coordinate(entry2.x(), entry2.y());
			endPoint = entry1;
			direction = Orientation.LEFT;
			result = iteration(direction, currentPos, endPoint, userPath, maze);
		}
		return result.toLowerCase();
	}

	// Method for handling left to right and right to left path verification.
	public String iteration(Orientation direction, Coordinate currentPos, Coordinate endPoint, String userPath, Maze maze){
		String result = "Incorrect";
		for(int i=0; i<userPath.length(); i++){
			if(userPath.charAt(i) == 'F'){
				if(!maze.straightAvailable(currentPos,direction)){
					return result;
				}else{
					currentPos.straight(direction,Moves.FORWARD);
				}
			}else if(userPath.charAt(i) == 'R'){
				direction = direction.turnRight();
			}else if(userPath.charAt(i) == 'L'){
				direction = direction.turnLeft();
			}
			
			if(currentPos.equivalentTo(endPoint)){
				result = "Correct";
				break;
			}
		}
		return result;
	}
}
