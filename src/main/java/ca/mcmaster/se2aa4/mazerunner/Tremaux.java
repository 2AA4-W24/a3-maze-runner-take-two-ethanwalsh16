package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

public class Tremaux implements MazeSolver {
	// Tremaux maze solving method, implemented as bonus option for assignment 1.

	// Note 2: For every maze except for the direct.maz.txt, the algorithm works and generates a correct path. I believe that this
	// error is caused by the 2x2 open grid on lines 4-5, columns 5-6 being treated as a junction, when it would be more efficient
	// to follow the right wall and exit the maze. Due to time constraint it was unable to be fixed.
	private boolean isJunction(Coordinate c, Maze maze, Orientation direction){
		boolean result = false;
		int count = 0;
		if(maze.leftTurnAvailable(c,direction)){count++;};
		if(maze.rightTurnAvailable(c,direction)){count++;};
		if(maze.straightAvailable(c,direction)){count++;};
		if(maze.prevAvailable(c, direction)){count++;};
		if(count >= 3){result = true;}
		return result;
	}

	@Override
	public String solveMaze(Maze maze) {

		String path = "";
		
		Coordinate startPos = maze.getStart();
		Coordinate currentPos = new Coordinate(startPos.getX(),startPos.getY());
		Coordinate endPos = maze.getEnd();
		Orientation direction = Orientation.RIGHT;

		JunctionList junctions = new JunctionList();

		while(!currentPos.equivalentTo(endPos)){
			path += "F";
			currentPos.straight(direction,Moves.FORWARD);
			System.out.println(currentPos.toString());
			if(isJunction(currentPos, maze, direction)){
				Junction j = new Junction(currentPos, direction, maze);
				System.out.println(j.toString());
				int index = junctions.has(j);
				Junction current;
				if(index == -1){
					junctions.add(j);
					current = j;
				}else{
					junctions.get(index).incrementPrev(direction.opposite());
					current = junctions.get(index);
				}

				int prev = 0, right = 0, left = 0, straight = 0;

				switch(direction){
					case Orientation.DOWN:
						prev = current.top(); 
						right = current.left(); 
						left = current.right(); 
						straight = current.bottom();
						break;
					case Orientation.LEFT:
						prev = current.right(); 
						right = current.top(); 
						left = current.bottom(); 
						straight = current.left();
						break;
					case Orientation.RIGHT:
						prev = current.left(); 
						right = current.bottom(); 
						left = current.top(); 
						straight = current.right();
						break;
					case Orientation.UP:
						prev = current.bottom(); 
						right = current.right(); 
						left = current.left(); 
						straight = current.top();
						break;
				}

				if(prev == 1 && (right <= 0 && left <= 0 && straight <= 0)){
					// Checking for left turn availability
					if(maze.leftTurnAvailable(currentPos, direction)){
						current.incrementMove(Moves.LEFT, direction);
						currentPos.turn(direction,Moves.LEFT);
						path += "LF";
						direction = direction.turnLeft();
					// Checking for right turn availability
					}else if(maze.rightTurnAvailable(currentPos, direction)){
						current.incrementMove(Moves.RIGHT, direction);
						currentPos.turn(direction,Moves.RIGHT);
						path += "RF";
						direction = direction.turnRight();
					// Checking for straight ability
					}else if(maze.straightAvailable(currentPos, direction)){
						current.incrementMove(Moves.FORWARD, direction);
						currentPos.straight(direction, Moves.FORWARD);
						path += "F";
					}
				}
				// Returning on original path
				else if(prev == 1){
					current.incrementPrev(direction.opposite());
					currentPos.straight(direction,Moves.UTURN);
					path += "RRF";
					direction = direction.opposite();
				}
				// Otherwise selecting lowest flagged option (path travelled 0 times is ideal, then 1)
				else{
					String min_string = current.minMark(direction);
					switch(min_string){
						case "LEFT":
							current.incrementMove(Moves.LEFT,direction);
							currentPos.turn(direction,Moves.LEFT);
							path += "LF";
							direction = direction.turnLeft();
							break;
						case "RIGHT":
							current.incrementMove(Moves.RIGHT, direction);
							currentPos.turn(direction,Moves.RIGHT);
							path += "RF";
							direction = direction.turnRight();
							break;
						case "FORWARD":
							current.incrementMove(Moves.FORWARD, direction);
							currentPos.straight(direction, Moves.FORWARD);
							path += "F";
							break;
						case "PREV":
							current.incrementPrev(direction.opposite());
							currentPos.straight(direction,Moves.UTURN);
							path += "RRF";
							direction = direction.opposite();
							break;
					}
					
				}
			}else{
				// Traditional right hand method until junction is reached again.
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
					direction = direction.opposite();
				}
			}
			System.out.println(path);
		}
		//ArrayList<ArrayList<Integer>> intMaze = convertToInteger(maze);
		/*
		while(currentPos.equivalentTo(endPos)){
			switch(direction){
				case RIGHT:
					// If three options (including straight and turn around) are available, junction has been reached.
					int rTurn = 0, sPath = 0, lTurn = 0, pTurn = 0;
					if(!Coordinate.equivalentTo(currentPos,entry1)){
						rTurn = (intMaze.get(currentPos.getY()+1).get(currentPos.getX()) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPos.getY()).get(currentPos.getX()+1) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPos.getY()-1).get(currentPos.getX()) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPos.getY()).get(currentPos.getX()-1) >= 0) ? 1 : 0;
					}
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
						intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();

						// Storing values of junction entrances
						int prev = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
						int right = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
						int straight = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
						int left = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);

						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
								intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
								currentPos.setY(currentPos.getY()-1);
								path += "LF";
								direction = Orientation.UP;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
								intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
								currentPos.setY(currentPos.getY()+1);
								path += "RF";
								direction = Orientation.DOWN;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
								intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
								currentPos.setX(currentPos.getX()+1);
								path += "F";
							}
						// Returning on original path
						}else if(prev == 1){
							prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
							intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
							currentPos.setX(currentPos.getX()-1);
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
									prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
									intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()+1);
									path += "RF";
									direction = Orientation.DOWN;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
									currentPos.setX(currentPos.getX()+1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
									intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()-1);
									path += "LF";
									direction = Orientation.UP;
									break;
								case "prev":
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
									currentPos.setX(currentPos.getX()-1);
									path += "RRF";
									direction = Orientation.LEFT;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
						// If right turn available, do as such
						if(!maze.get(currentPos.getY()+1).get(currentPos.getX()).equals("W")){
							currentPos.setY(currentPos.getY()+1);
							path += "RF";
							direction = Orientation.DOWN;
						}
						// If no right, go straight
						else if(!maze.get(currentPos.getY()).get(currentPos.getX()+1).equals("W")){
							currentPos.setX(currentPos.getX()+1);
							path += "F";
						}
						// If no straight, go left
						else if(!maze.get(currentPos.getY()-1).get(currentPos.getX()).equals("W")){
							currentPos.setY(currentPos.getY()-1);
							path += "LF";
							direction = Orientation.UP;
						// If all else fails, go back (appending RRF to represent 180 degree turn, then a move forward).
						}else{
							currentPos.setX(currentPos.getX()-1);
							path += "RRF";
							direction = Orientation.LEFT;
						}
					}
					
					break;	
				case DOWN:
					rTurn = 0; sPath = 0; lTurn = 0; pTurn = 0;
					if(!Coordinate.equivalentTo(currentPos,entry1)){
						rTurn = (intMaze.get(currentPos.getY()).get(currentPos.getX()-1) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPos.getY()+1).get(currentPos.getX()) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPos.getY()).get(currentPos.getX()+1) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPos.getY()-1).get(currentPos.getX()) >= 0) ? 1 : 0;
					}
					// If two options available from current junction (e.g. straight and right), mark as a junction.
					// If it is a junction
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
						intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();
						// Storing values of junction entrances
						int prev = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
						int right = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
						int straight = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
						int left = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);

						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
								intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
								currentPos.setX(currentPos.getX()+1);
								path += "LF";
								direction = Orientation.RIGHT;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
								intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
								currentPos.setX(currentPos.getX()-1);
								path += "RF";
								direction = Orientation.LEFT;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
								intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
								currentPos.setY(currentPos.getY()+1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
							intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
							currentPos.setY(currentPos.getY()-1);
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
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
									currentPos.setX(currentPos.getX()-1);
									path += "RF";
									direction = Orientation.LEFT;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
									intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()+1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
									currentPos.setX(currentPos.getX()+1);
									path += "LF";
									direction = Orientation.RIGHT;
									break;
								case "prev":
									prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
									intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()-1);
									path += "RRF";
									direction = Orientation.UP;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
						if(!maze.get(currentPos.getY()).get(currentPos.getX()-1).equals("W")){
							currentPos.setX(currentPos.getX()-1);
							path += "RF";
							direction = Orientation.LEFT;
						}
						else if(!maze.get(currentPos.getY()+1).get(currentPos.getX()).equals("W")){
							currentPos.setY(currentPos.getY()+1);
							path += "F";
						}
						else if(!maze.get(currentPos.getY()).get(currentPos.getX()+1).equals("W")){
							currentPos.setX(currentPos.getX()+1);
							path += "LF";
							direction = Orientation.RIGHT;
						}else{
							currentPos.setY(currentPos.getY()-1);
							path += "RRF";
							direction = Orientation.UP;
						}
					}
					break;
				case LEFT:
					rTurn = 0; sPath = 0; lTurn = 0; pTurn = 0;
					if(!Coordinate.equivalentTo(currentPos,entry1)){
						// If two options available from current junction (e.g. straight and right), mark as a junction.
						rTurn = (intMaze.get(currentPos.getY()-1).get(currentPos.getX()) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPos.getY()).get(currentPos.getX()-1) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPos.getY()+1).get(currentPos.getX()) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPos.getY()).get(currentPos.getX()+1) >= 0) ? 1 : 0;
					}
					// If it is a junction
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
						intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();
						// Storing values of junction entrances
						int prev = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
						int right = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
						int straight = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
						int left = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);

						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
								intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
								currentPos.setY(currentPos.getY()+1);
								path += "LF";
								direction = Orientation.DOWN;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
								intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
								currentPos.setY(currentPos.getY()-1);
								path += "RF";
								direction = Orientation.UP;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
								intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
								currentPos.setX(currentPos.getX()-1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
							intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
							currentPos.setX(currentPos.getX()+1);
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
									prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
									intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()-1);
									path += "RF";
									direction = Orientation.UP;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
									currentPos.setX(currentPos.getX()-1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
									intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()+1);
									path += "LF";
									direction = Orientation.DOWN;
									break;
								case "prev":
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
									currentPos.setX(currentPos.getX()+1);
									path += "RRF";
									direction = Orientation.RIGHT;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
						if(!maze.get(currentPos.getY()-1).get(currentPos.getX()).equals("W")){
							currentPos.setY(currentPos.getY()-1);
							path += "RF";
							direction = Orientation.UP;
						}
						else if(!maze.get(currentPos.getY()).get(currentPos.getX()-1).equals("W")){
							currentPos.setX(currentPos.getX()-1);
							path += "F";
						}
						else if(!maze.get(currentPos.getY()+1).get(currentPos.getX()).equals("W")){
							currentPos.setY(currentPos.getY()+1);
							path += "LF";
							direction = Orientation.DOWN;
						}else{
							currentPos.setX(currentPos.getX()+1);
							path += "RRF";
							direction = Orientation.RIGHT;
						}
						break;
					}
					break;
				case UP:
					rTurn = 0; sPath = 0; lTurn = 0; pTurn = 0;
					if(!Coordinate.equivalentTo(currentPos,entry1)){
						// If two options available from current junction (e.g. straight and right), mark as a junction.
						rTurn = (intMaze.get(currentPos.getY()).get(currentPos.getX()+1) >= 0) ? 1 : 0;
						sPath = (intMaze.get(currentPos.getY()-1).get(currentPos.getX()) >= 0) ? 1 : 0;
						lTurn = (intMaze.get(currentPos.getY()).get(currentPos.getX()-1) >= 0) ? 1 : 0;
						pTurn = (intMaze.get(currentPos.getY()+1).get(currentPos.getX()) >= 0) ? 1 : 0;
					}
					// If it is a junction
					if((rTurn + sPath + lTurn + pTurn) >= 3){
						// Increment previous entrance to mark accordingly.
						int prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
						intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
						Map<String, Integer> surroundingCells = new HashMap<String, Integer>();
						// Storing values of junction entrances
						int prev = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
						int right = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
						int straight = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
						int left = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
						surroundingCells.put("right",right);
						surroundingCells.put("ahead",straight);
						surroundingCells.put("left",left);
						surroundingCells.put("prev",prev);
						// If only previous entrance is marked, make a turn
						if(prev >= 1 && right <= 0 && straight <= 0 && left <= 0){
							// Checking for left turn availability
							if(lTurn == 1){
								prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
								intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
								currentPos.setX(currentPos.getX()-1);
								path += "LF";
								direction = Orientation.LEFT;
							// Checking for right turn availability
							}else if(rTurn == 1){
								prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
								intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
								currentPos.setX(currentPos.getX()+1);
								path += "RF";
								direction = Orientation.RIGHT;
							// Checking for straight ability
							}else if(sPath == 1){
								prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
								intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
								currentPos.setY(currentPos.getY()-1);
								path += "F";
							}
						}else if(prev == 1){
							prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
							intMaze.get(currentPos.getY()+1).set(currentPos.getX()-1,prevVal+1);
							currentPos.setY(currentPos.getY()+1);
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
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()+1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()+1,prevVal+1);
									currentPos.setX(currentPos.getX()+1);
									path += "RF";
									direction = Orientation.RIGHT;
									break;
								case "ahead":
									prevVal = intMaze.get(currentPos.getY()-1).get(currentPos.getX());
									intMaze.get(currentPos.getY()-1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()-1);
									path += "F";
									break;
								case "left":
									prevVal = intMaze.get(currentPos.getY()).get(currentPos.getX()-1);
									intMaze.get(currentPos.getY()).set(currentPos.getX()-1,prevVal+1);
									currentPos.setX(currentPos.getX()-1);
									path += "LF";
									direction = Orientation.LEFT;
									break;
								case "prev":
									prevVal = intMaze.get(currentPos.getY()+1).get(currentPos.getX());
									intMaze.get(currentPos.getY()+1).set(currentPos.getX(),prevVal+1);
									currentPos.setY(currentPos.getY()+1);
									path += "RRF";
									direction = Orientation.DOWN;
									break;
							}
						}
						surroundingCells.clear();
					}else{
						// Handling the case it is not a junction, where has to move forward or turn.
						if(!maze.get(currentPos.getY()).get(currentPos.getX()+1).equals("W")){
							currentPos.setX(currentPos.getX()+1);
							path += "RF";
							direction = Orientation.RIGHT;
						}
						else if(!maze.get(currentPos.getY()-1).get(currentPos.getX()).equals("W")){
							currentPos.setY(currentPos.getY()-1);
							path += "F";
						}
						else if(!maze.get(currentPos.getY()).get(currentPos.getX()-1).equals("W")){
							currentPos.setX(currentPos.getX()-1);
							path += "LF";
							direction = Orientation.LEFT;
						}else{
							currentPos.setY(currentPos.getY()+1);
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

