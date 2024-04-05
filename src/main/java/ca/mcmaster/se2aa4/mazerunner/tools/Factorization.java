package ca.mcmaster.se2aa4.mazerunner.tools;

public class Factorization {
	// Used to convert paths from unfactorized (e.g. FFFFF) to factorized (e.g. 5F)
	public String FactorPath(String input){
		
		String factorizedPath = "";
		String current = "";
		for(int i=0; i<input.length(); i++){
			// Adding new character to tracker
			if(current.isEmpty()){
				current += input.charAt(i);
			}else if (input.charAt(i) == current.charAt(0)){
				current += input.charAt(i);
			}else{
				if(!current.isEmpty()){
					// Adding direction, with coefficient if more than one.
					if(current.length() > 1){
						factorizedPath += current.length() + String.valueOf(current.charAt(0)) + " ";
					}else{
						factorizedPath += String.valueOf(current.charAt(0)) + " ";
					}
				}
				current = String.valueOf(input.charAt(i));
			}
		}
		// Adding last trailing instruction, as it is not picked up by for loop
		if(current.length() > 1){
			factorizedPath += current.length() + String.valueOf(current.charAt(0)) + " ";
		}else{
			factorizedPath += String.valueOf(input.charAt(input.length()-1)) + " ";
		}
		return factorizedPath;
	}

	// Used for conversion of factorized paths to unfactorized (e.g. 5F -> FFFFF)
	public String unfactorize(String userPath){
		userPath = userPath.replaceAll("\\s","");
		String newPath = "";
		boolean lastCaught = false;
		for(int i=0; i<userPath.length()-1; i++){
			// Finding numbers (while loop to handle numbers with more than one digit (e.g. 10))
			if(Character.isDigit(userPath.charAt(i))){
				int j = 0;
				String num = "";
				while(j+i < userPath.length()-1 && Character.isDigit(userPath.charAt(j+i))){
					num += userPath.charAt(j+i);
					j++;
				}
				newPath += String.valueOf(userPath.charAt(i+j)).repeat(Integer.valueOf(num));
				i += j;
			}else{
				newPath += String.valueOf(userPath.charAt(i));
			}
			// Handling last case, as last letter may not be picked up by loop. If it is this is true.
			if(i == userPath.length()-1){
				lastCaught = true;
			}
		}
		// Picking up case of last letter being missed by initial loop, and appending to path.
		if(!lastCaught){
			newPath += String.valueOf(userPath.charAt(userPath.length()-1));
		}
		return newPath;
	}
}
