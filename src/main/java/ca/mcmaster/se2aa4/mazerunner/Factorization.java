package ca.mcmaster.se2aa4.mazerunner;

public class Factorization {

	public static String FactorPath(String input){
		System.out.println("LENGTH: " + input.length());
		String factorizedPath = "";
		String current = "";
		for(int i=0; i<input.length(); i++){
			if(current.isEmpty()){
				current += input.charAt(i);
			}else if (input.charAt(i) == current.charAt(0)){
				current += input.charAt(i);
			}else{
				if(!current.isEmpty()){
					if(current.length() > 1){
						factorizedPath += current.length() + String.valueOf(current.charAt(0)) + " ";
					}else{
						factorizedPath += String.valueOf(current.charAt(0)) + " ";
					}
				}
				current = String.valueOf(input.charAt(i));
			}
		}
		if(current.length() > 1){
			factorizedPath += current.length() + String.valueOf(current.charAt(0)) + " ";
		}else{
			factorizedPath += String.valueOf(input.charAt(input.length()-1)) + " ";
		}
		return factorizedPath;
	}
}
