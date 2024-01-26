package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

public class Tremaux implements MazeSolver {
	// Tremaux method, to be potentially implemented as bonus option for project.
	@Override
	public String[] solveMaze(ArrayList<ArrayList<String>> maze, Coordinate entry1, Coordinate entry2) {
		String path = "";
		Orientation direction = Orientation.RIGHT;
		Coordinate currentPosition = entry1;
		Coordinate endPosition = entry2;
		ArrayList<ArrayList<Integer>> intMaze = convertToInteger(maze);
		while(!Coordinate.equivalentTo(endPosition,currentPosition)){
			switch(direction){
				case RIGHT:
					// If two options available from current junction (e.g. straight and right), mark as a junction.
					int rTurn = (intMaze.get(currentPosition.getY()+1).get(currentPosition.getX()) >= 0) ? 1 : 0;
					int sPath = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1) >= 0) ? 1 : 0;
					int lTurn = (intMaze.get(currentPosition.getY()-1).get(currentPosition.getX()) >= 0) ? 1 : 0;

					// If it is a junction
					if((rTurn + sPath + lTurn >= 2)){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX());
						intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
						
						// Storing values of junction entrances
						int prev = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
						int right = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
						int straight = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
						int left = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());

						// If only previous entrance is marked, make a turn
						if(prev == 1 && (right == 0 && left == 0 && left == 0)){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
								intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
								currentPosition.setY(currentPosition.getY()-1);
								path += "LF";
								direction = Orientation.UP;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
								intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
								currentPosition.setY(currentPosition.getY()+1);
								path += "RF";
								direction = Orientation.DOWN;
							// Checking for straight ability
							}else{
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
								currentPosition.setX(currentPosition.getX()+1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
							intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
							currentPosition.setX(currentPosition.getX()-1);
							path += "RRF";
							direction = Orientation.LEFT;
						}
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
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
					}	
				case DOWN:
					break;
				case LEFT:
					break;
				case UP:
					break;
			}
		}

		/* Idea for going right: check if currently in an intersection (can turn at least two of left/right/straight)
		 * If so, mark intersection at entrance you just came from.
		 * Use first applicable rule below
		 * (If only entrance where you came from is marked) pick entrance at random.
		 * (If entrance came from not marked twice) pick entrance you just came from (go back on path)
		 * pick entrance with fewest marks (zero, else one)
		 */
		
		// Obtaining factorized path prior to returning both results.
		String factorizedPath = Factorization.FactorPath(path);
		String[] paths = {path, factorizedPath};
		System.out.println("Tremaux path: " + path);
		return paths;
	}

	public ArrayList<ArrayList<Integer>> convertToInteger(ArrayList<ArrayList<String>> maze){
		ArrayList<ArrayList<Integer>> intMaze = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<maze.get(0).size(); i++){
			intMaze.add(new ArrayList<Integer>());
			for(int j=0; j<maze.size(); j++){
				if(maze.get(i).get(j).equals("W")){
					intMaze.get(i).add(-1);
				}else{
					intMaze.get(i).add(0);
				}
			}
		}
		return null;
	}	
}

