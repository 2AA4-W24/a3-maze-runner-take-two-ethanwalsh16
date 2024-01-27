package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;

public class Verifier {
	// Confirming the method is suitable to verify (only contains correct characters)
	public static boolean correctPath(String userPath){
		boolean correctChars = true;
		for(int i=0; i<userPath.length(); i++){
			if(userPath.charAt(i) != 'F' && userPath.charAt(i) != 'R' && userPath.charAt(i) != 'L'){
				correctChars = false;
				return correctChars;
			}
		}
		return correctChars;
	}
	
	// Testing user entered path to see if it is a valid maze solution (requires a true value from correctPath in order to be run).
	public static String verifyPath(String userPath, ArrayList<ArrayList<String>> matrix, Coordinate[] entries){

		// Tracking position and orientation for moving through the maze.
		Coordinate currentPos = entries[0];
		Coordinate endPoint = entries[1];
		Orientation direction = Orientation.RIGHT;
		String result = "Incorrect";
		userPath = userPath.replaceAll("\\s","");
		// Testing if left to right method works
		result = iteration(direction, currentPos, endPoint, userPath, matrix);
		if(result != "Correct"){
			// If not, also testing path as right to left.
			currentPos = entries[1];
			endPoint = entries[0];
			direction = Orientation.LEFT;
			result = iteration(direction, currentPos, endPoint, userPath, matrix);
		}
		return result.toLowerCase();
	}

	// Method for handling left to right and right to left path verification.
	public static String iteration(Orientation direction, Coordinate currentPos, Coordinate endPoint, String userPath, ArrayList<ArrayList<String>> matrix){
		String result = "Incorrect";
		for(int i=0; i<userPath.length(); i++){
			switch(direction){
				case RIGHT:
					if(userPath.charAt(i) == 'F'){
						// Check for path, then move
						if(matrix.get(currentPos.getY()).get(currentPos.getX()+1).equals("W")){
							return result;
						}else{
							currentPos.setX(currentPos.getX()+1);
						}
					}else if(userPath.charAt(i) == 'R'){
						direction = Orientation.DOWN;
					}else if(userPath.charAt(i) == 'L'){
						direction = Orientation.UP;
					}
					break;
				case DOWN:
					if(userPath.charAt(i) == 'F'){
						// Check for path, then move
						if(matrix.get(currentPos.getY()+1).get(currentPos.getX()).equals("W")){
							return result;
						}else{
							currentPos.setY(currentPos.getY()+1);
						}
					}else if(userPath.charAt(i) == 'R'){
						direction = Orientation.LEFT;
					}else if(userPath.charAt(i) == 'L'){
						direction = Orientation.RIGHT;
					}
					break;
				case LEFT:
					if(userPath.charAt(i) == 'F'){
						// Check for path, then move
						if(matrix.get(currentPos.getY()).get(currentPos.getX()-1).equals("W")){
							return result;
						}else{
							currentPos.setX(currentPos.getX()-1);
						}
					}else if(userPath.charAt(i) == 'R'){
						direction = Orientation.UP;
					}else if(userPath.charAt(i) == 'L'){
						direction = Orientation.DOWN;
					}
					break;
				case UP:
					if(userPath.charAt(i) == 'F'){
						// Check for path, then increase
						if(matrix.get(currentPos.getY()-1).get(currentPos.getX()).equals("W")){
							return result;
						}else{
							currentPos.setY(currentPos.getY()-1);
						}
					}else if(userPath.charAt(i) == 'R'){
						direction = Orientation.RIGHT;
					}else if(userPath.charAt(i) == 'L'){
						direction = Orientation.LEFT;
					}
					break;
			}
			if(Coordinate.equivalentTo(currentPos,endPoint)){
				result = "Correct";
				break;
			}
		}
		return result;
	}
}
