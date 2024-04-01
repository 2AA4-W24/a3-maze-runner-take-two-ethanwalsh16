package ca.mcmaster.se2aa4.mazerunner;

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
		Coordinate currentPos = new Coordinate(startPos.X(),startPos.Y());
		Coordinate endPos = maze.getEnd();
		Orientation direction = Orientation.RIGHT;

		JunctionList junctions = new JunctionList();
		
		while(!currentPos.equivalentTo(endPos)){
			System.out.println("Current Position: " + currentPos.toString());
			if(currentPos.X() == 0){
				path += "F";
				currentPos.straight(direction,Moves.FORWARD);
				continue;
			}
			if(isJunction(currentPos, maze, direction) && maze.inBounds(currentPos)){
				Junction j = new Junction(currentPos, direction, maze);
				Junction current;
				int index = junctions.has(j);
				if(index == -1){
					j.incrementPrev(direction);
					junctions.add(j);
					current = j;
				}else{
					junctions.get(index).incrementPrev(direction);
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

				if(prev == 1 && ((right <= 0 || right == Integer.MAX_VALUE) && (left <= 0 || left == Integer.MAX_VALUE) && (straight <= 0 || straight == Integer.MAX_VALUE))){
					// Checking for left turn availability
					if(maze.rightTurnAvailable(currentPos, direction)){
						current.incrementMove(Moves.RIGHT, direction);
						currentPos.turn(direction,Moves.RIGHT);
						path += "RF";
						direction = direction.turnRight();
					// Checking for right turn availability
					}else if(maze.leftTurnAvailable(currentPos, direction)){
						current.incrementMove(Moves.LEFT, direction);
						currentPos.turn(direction,Moves.LEFT);
						path += "LF";
						direction = direction.turnLeft();
					// Checking for straight ability
					}else if(maze.straightAvailable(currentPos, direction)){
						current.incrementMove(Moves.FORWARD, direction);
						currentPos.straight(direction, Moves.FORWARD);	
						path += "F";
					}
				}
				// Returning on original path
				else if(prev == 1){
					current.incrementPrev(direction);
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
							current.incrementPrev(direction);
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
			System.out.println("Current path: " + path);
		}
		
		return path;
	}	
}

