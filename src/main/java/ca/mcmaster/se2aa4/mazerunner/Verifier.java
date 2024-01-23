package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;

public class Verifier {
	
	public static String verifyPath(String userPath, ArrayList<ArrayList<String>> matrix, Coordinate[] entries){
		Coordinate startPoint = entries[0];
		Coordinate endPoint = entries[1];
		
		for(int i=0; i<userPath.length(); i++){

		}

		return "";
	}

	public static boolean correctPath(String userPath){
		boolean correctChars = true;
		for(int i=0; i<userPath.length(); i++){
			if(userPath.charAt(i) != 'F' || userPath.charAt(i) != 'R' || userPath.charAt(i) != 'L'){
				correctChars = false;
				return correctChars;
			}
		}
		return correctChars;
	}
}
