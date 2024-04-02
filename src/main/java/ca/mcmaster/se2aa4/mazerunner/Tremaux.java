package ca.mcmaster.se2aa4.mazerunner;

public class Tremaux implements MazeSolver {
	// Tremaux maze solving method, implemented as bonus option for assignment 1.
	@Override
	public String solveMaze(Maze maze) {

		Coordinate startPos = maze.getStart();
		Coordinate endPos = maze.getEnd();
		Orientation direction = Orientation.RIGHT;

		System.out.println("Marking junctions...");
		JunctionList junctions = markJunctions(startPos, endPos, maze, direction);
		System.out.println("Now carving path...");
		String path = createPath(junctions, startPos, endPos, maze, direction);
		System.out.println("Completed path.");
		System.out.println("Path: " + path);
		return path;
	}

	private JunctionList markJunctions(Coordinate startPos, Coordinate endPos, Maze maze, Orientation input_dir){
		JunctionList junctions = new JunctionList();
		Coordinate currentPos = new Coordinate(startPos.X(),startPos.Y());
		Orientation direction = input_dir;
		while(!currentPos.equivalentTo(endPos)){
			System.out.println("Current Position: " + currentPos.toString());
			if(currentPos.X() == 0){
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
						direction = direction.turnRight();
					// Checking for right turn availability
					}else if(maze.leftTurnAvailable(currentPos, direction)){
						current.incrementMove(Moves.LEFT, direction);
						currentPos.turn(direction,Moves.LEFT);
						direction = direction.turnLeft();
					// Checking for straight ability
					}else if(maze.straightAvailable(currentPos, direction)){
						current.incrementMove(Moves.FORWARD, direction);
						currentPos.straight(direction, Moves.FORWARD);	
					}
				}
				// Returning on original path
				else if(prev == 1){
					current.incrementPrev(direction);
					currentPos.straight(direction,Moves.UTURN);
					direction = direction.opposite();
				}
				// Otherwise selecting lowest flagged option (path travelled 0 times is ideal, then 1)
				else{
					String min_string = current.minMark(direction);
					switch(min_string){
						case "LEFT":
							current.incrementMove(Moves.LEFT,direction);
							currentPos.turn(direction,Moves.LEFT);
							direction = direction.turnLeft();
							break;
						case "RIGHT":
							current.incrementMove(Moves.RIGHT, direction);
							currentPos.turn(direction,Moves.RIGHT);
							direction = direction.turnRight();
							break;
						case "FORWARD":
							current.incrementMove(Moves.FORWARD, direction);
							currentPos.straight(direction, Moves.FORWARD);
							break;
						case "PREV":
							current.incrementPrev(direction);
							currentPos.straight(direction,Moves.UTURN);
							direction = direction.opposite();
							break;
					}
					
				}

			}else{
				// Traditional right hand method until junction is reached again.
				if(maze.rightTurnAvailable(currentPos,direction)){
					currentPos.turn(direction,Moves.RIGHT);
					direction = direction.turnRight();
				}else if(maze.straightAvailable(currentPos,direction)){
					currentPos.straight(direction,Moves.FORWARD);
				}else if(maze.leftTurnAvailable(currentPos,direction)){
					currentPos.turn(direction,Moves.LEFT);
					direction = direction.turnLeft();
				}else{
					currentPos.straight(direction,Moves.UTURN);
					direction = direction.opposite();
				}
			}
		}
		return junctions;
	}
	
	private String createPath(JunctionList junctions, Coordinate startPos, Coordinate endPos, Maze maze, Orientation input_dir){
		
		Coordinate currentPos = new Coordinate(startPos.X(),startPos.Y());
		Orientation direction = input_dir;
		String path = "";

		while(!currentPos.equivalentTo(endPos)){
			if(currentPos.X() == 0){
				path += "F";
				currentPos.straight(direction,Moves.FORWARD);
				continue;
			}
			if(isJunction(currentPos, maze, direction) && maze.inBounds(currentPos)){
				Junction j = new Junction(currentPos, direction, maze);
				Junction current;
				int index = junctions.has(j);
				current = junctions.get(index);
				
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

				if(straight == 1){
					path += "F";
					currentPos.straight(direction,Moves.FORWARD);
				}else if(right == 1){
					path += "RF";
					currentPos.turn(direction,Moves.RIGHT);
					direction = direction.turnRight();
				}else if(left == 1){
					path += "LF";
					currentPos.turn(direction,Moves.LEFT);
					direction = direction.turnLeft();
				}else if(prev == 1){
					path += "RRF";
					currentPos.straight(direction,Moves.UTURN);
					direction = direction.opposite();
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
}

