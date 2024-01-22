package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;

public class Factorization {

	public static ArrayList<String> FactorPath(String input){
		ArrayList<String> factorizedPath = new ArrayList<String>();
		char runner = input.charAt(0);
		int count = 0;
		for(int i=0; i<input.length()-1; i++){
			if(input.charAt(i) == input.charAt(i+1)){
				count += 1;
			}else{
				if(count>1){
					factorizedPath.add(count + String.valueOf(input.charAt(i)));
				}else if (count > 0){
					factorizedPath.add(String.valueOf(input.charAt(i)));
				}
				count = 0;
			}
		}
		return factorizedPath;
	}
}
