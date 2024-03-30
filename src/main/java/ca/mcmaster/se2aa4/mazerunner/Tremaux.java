package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tremaux implements MazeSolver {
	// Tremaux maze solving method, implemented as bonus option for assignment 1.

	// Note 2: For every maze except for the direct.maz.txt, the algorithm works and generates a correct path. I believe that this
	// error is caused by the 2x2 open grid on lines 4-5, columns 5-6 being treated as a junction, when it would be more efficient
	// to follow the right wall and exit the maze. Due to time constraint it was unable to be fixed.

	@Override
	public String solveMaze(Maze maze) {

		String path = "";
		
		Coordinate startPosition = maze.getStart();
		Coordinate currentPosition = new Coordinate(startPosition.getX(),startPosition.getY());
		Coordinate endPosition = maze.getEnd();
		Orientation direction = Orientation.RIGHT;

		List<Integer> dimensions = maze.getDimensions();
		int[][] intMaze = new int[dimensions.get(0)][dimensions.get(1)];
		intMaze = maze.toInt();

		while(!currentPosition.equivalentTo(endPosition)){

			int rTurn = 0, sPath = 0, lTurn = 0, uTurn = 0;

			if(!currentPosition.equivalentTo(startPosition)){
				rTurn = (maze.rightTurnAvailable(currentPosition, direction)) ? 1 : 0;
				sPath = (maze.straightAvailable(currentPosition, direction)) ? 1 : 0;
				lTurn = (maze.leftTurnAvailable(currentPosition, direction)) ? 1 : 0;
			}

			int turnSum = rTurn + sPath + lTurn + uTurn;

			if(turnSum >= 3){
				int prevVal = 0;
				switch(direction){
					case Orientation.DOWN:
						prevVal = intMaze[currentPosition.getY()-1][currentPosition.getX()];
						break;
					case Orientation.LEFT:
						prevVal = intMaze[currentPosition.getY()][currentPosition.getX()+1];
						break;
					case Orientation.UP:
						prevVal = intMaze[currentPosition.getY()+1][currentPosition.getX()];
						break;
					case Orientation.RIGHT:
						prevVal = intMaze[currentPosition.getY()][currentPosition.getX()-1];
						break;
				}
				//NEED METHOD HERE
				// get IntMaze at previous place

			}
		}
		//ArrayList<ArrayList<Integer>> intMaze = convertToInteger(maze);
		/*
		while(currentPosition.equivalentTo(endPosition)){
			switch(direction){
				case RIGHT:
					// If three options (including straight and turn around) are available, junction has been reached.
					int rTurn = 0, sPath = 0, lTurn = 0, pTurn = 0;
					if(!Coordinate.equivalentTo(currentPosition,entry1)){
						rTurn = (intMaze.get(currentPosition.getY()+1).get(currentPosition.getX()) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPosition.getY()-1).get(currentPosition.getX()) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1) >= 0) ? 1 : 0;
					}
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
						intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();

						// Storing values of junction entrances
						int prev = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
						int right = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
						int straight = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
						int left = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);

						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
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
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
								currentPosition.setX(currentPosition.getX()+1);
								path += "F";
							}
						// Returning on original path
						}else if(prev == 1){
							prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
							intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
							currentPosition.setX(currentPosition.getX()-1);
							path += "RRF";
							direction = Orientation.LEFT;
						}else{
							// Otherwise selecting lowest flagged option (path travelled 0 times is ideal, then 1)
							int min = Integer.MAX_VALUE;
							String min_string = "";
							for (Map.Entry<String, Integer> nearbyCell : surroundingCells.entrySet()) {
								if(nearbyCell.getValue()<min && nearbyCell.getValue() >= 0){
									min = nearbyCell.getValue();
									min_string = nearbyCell.getKey();
								}
							}
							switch(min_string){
								case "right":
									prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()+1);
									path += "RF";
									direction = Orientation.DOWN;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
									currentPosition.setX(currentPosition.getX()+1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()-1);
									path += "LF";
									direction = Orientation.UP;
									break;
								case "prev":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
									currentPosition.setX(currentPosition.getX()-1);
									path += "RRF";
									direction = Orientation.LEFT;
									break;
							}
						}
						surroundingCells.clear();
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
					
					break;	
				case DOWN:
					rTurn = 0; sPath = 0; lTurn = 0; pTurn = 0;
					if(!Coordinate.equivalentTo(currentPosition,entry1)){
						rTurn = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPosition.getY()+1).get(currentPosition.getX()) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPosition.getY()-1).get(currentPosition.getX()) >= 0) ? 1 : 0;
					}
					// If two options available from current junction (e.g. straight and right), mark as a junction.
					// If it is a junction
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
						intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();
						// Storing values of junction entrances
						int prev = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
						int right = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
						int straight = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
						int left = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);

						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
								currentPosition.setX(currentPosition.getX()+1);
								path += "LF";
								direction = Orientation.RIGHT;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
								currentPosition.setX(currentPosition.getX()-1);
								path += "RF";
								direction = Orientation.LEFT;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
								intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
								currentPosition.setY(currentPosition.getY()+1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
							intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
							currentPosition.setY(currentPosition.getY()-1);
							path += "RRF";
							direction = Orientation.UP;
						}else{
							int min = Integer.MAX_VALUE;
							String min_string = "";
							for (Map.Entry<String, Integer> nearbyCell : surroundingCells.entrySet()) {
								if(nearbyCell.getValue()<min && nearbyCell.getValue() >= 0){
									min = nearbyCell.getValue();
									min_string = nearbyCell.getKey();
								}
							}
							switch(min_string){
								case "right":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
									currentPosition.setX(currentPosition.getX()-1);
									path += "RF";
									direction = Orientation.LEFT;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()+1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
									currentPosition.setX(currentPosition.getX()+1);
									path += "LF";
									direction = Orientation.RIGHT;
									break;
								case "prev":
									prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()-1);
									path += "RRF";
									direction = Orientation.UP;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
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
					}
					break;
				case LEFT:
					rTurn = 0; sPath = 0; lTurn = 0; pTurn = 0;
					if(!Coordinate.equivalentTo(currentPosition,entry1)){
						// If two options available from current junction (e.g. straight and right), mark as a junction.
						rTurn = (intMaze.get(currentPosition.getY()-1).get(currentPosition.getX()) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPosition.getY()+1).get(currentPosition.getX()) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1) >= 0) ? 1 : 0;
					}
					// If it is a junction
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
						intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();
						// Storing values of junction entrances
						int prev = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
						int right = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
						int straight = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
						int left = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);

						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
								intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
								currentPosition.setY(currentPosition.getY()+1);
								path += "LF";
								direction = Orientation.DOWN;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
								intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
								currentPosition.setY(currentPosition.getY()-1);
								path += "RF";
								direction = Orientation.UP;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
								currentPosition.setX(currentPosition.getX()-1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
							intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
							currentPosition.setX(currentPosition.getX()+1);
							path += "RRF";
							direction = Orientation.RIGHT;
						}else{
							int min = Integer.MAX_VALUE;
							String min_string = "";
							for (Map.Entry<String, Integer> nearbyCell : surroundingCells.entrySet()) {
								if(nearbyCell.getValue()<min && nearbyCell.getValue() >= 0){
									min = nearbyCell.getValue();
									min_string = nearbyCell.getKey();
								}
							}
							switch(min_string){
								case "right":
									prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()-1);
									path += "RF";
									direction = Orientation.UP;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
									currentPosition.setX(currentPosition.getX()-1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()+1);
									path += "LF";
									direction = Orientation.DOWN;
									break;
								case "prev":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
									currentPosition.setX(currentPosition.getX()+1);
									path += "RRF";
									direction = Orientation.RIGHT;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
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
					break;
				case UP:
					rTurn = 0; sPath = 0; lTurn = 0; pTurn = 0;
					if(!Coordinate.equivalentTo(currentPosition,entry1)){
						// If two options available from current junction (e.g. straight and right), mark as a junction.
						rTurn = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPosition.getY()-1).get(currentPosition.getX()) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPosition.getY()+1).get(currentPosition.getX()) >= 0) ? 1 : 0;
					}
					// If it is a junction
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
						intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();
						// Storing values of junction entrances
						int prev = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
						int right = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
						int straight = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
						int left = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);
						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
								currentPosition.setX(currentPosition.getX()-1);
								path += "LF";
								direction = Orientation.LEFT;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
								intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
								currentPosition.setX(currentPosition.getX()+1);
								path += "RF";
								direction = Orientation.RIGHT;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
								intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
								currentPosition.setY(currentPosition.getY()-1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
							intMaze.get(currentPosition.getY()+1).set(currentPosition.getX()-1,prevVal+1);
							currentPosition.setY(currentPosition.getY()+1);
							path += "RRF";
							direction = Orientation.DOWN;
						}else{
							int min = Integer.MAX_VALUE;
							String min_string = "";
							for (Map.Entry<String, Integer> nearbyCell : surroundingCells.entrySet()) {
								if(nearbyCell.getValue()<min && nearbyCell.getValue() >= 0){
									min = nearbyCell.getValue();
									min_string = nearbyCell.getKey();
								}
							}
							switch(min_string){
								case "right":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()+1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()+1,prevVal+1);
									currentPosition.setX(currentPosition.getX()+1);
									path += "RF";
									direction = Orientation.RIGHT;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPosition.getY()-1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()-1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()-1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPosition.getY()).get(currentPosition.getX()-1);
									intMaze.get(currentPosition.getY()).set(currentPosition.getX()-1,prevVal+1);
									currentPosition.setX(currentPosition.getX()-1);
									path += "LF";
									direction = Orientation.LEFT;
									break;
								case "prev":
									prevVal = intMaze.get(currentPosition.getY()+1).get(currentPosition.getX());
									intMaze.get(currentPosition.getY()+1).set(currentPosition.getX(),prevVal+1);
									currentPosition.setY(currentPosition.getY()+1);
									path += "RRF";
									direction = Orientation.DOWN;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
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
					}
					break;
			}
		}
		*/
		return path;
	}	
}

