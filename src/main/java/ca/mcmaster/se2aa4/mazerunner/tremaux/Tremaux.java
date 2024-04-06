package ca.mcmaster.se2aa4.mazerunner.tremaux;

import ca.mcmaster.se2aa4.mazerunner.MazeSolver;
import ca.mcmaster.se2aa4.mazerunner.enums.Moves;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

public class Tremaux implements MazeSolver {
	// Tremaux maze solving method, implemented as bonus option for assignment 1.
	@Override
	public String solveMaze(Maze maze) {

		Coordinate startPos = maze.getStart();
		Coordinate endPos = maze.getEnd();
		Orientation direction = Orientation.RIGHT;

		JunctionList junctions = markJunctions(startPos, endPos, maze, direction);
		String path = createPath(junctions, startPos, endPos, maze, direction);
		return path;
	}

	private JunctionList markJunctions(Coordinate startPos, Coordinate endPos, Maze maze, Orientation input_dir){
		JunctionList junctions = new JunctionList();
		Coordinate currentPos = new Coordinate(startPos.x(),startPos.y());
		Orientation direction = input_dir;
		while(!currentPos.equivalentTo(endPos)){
			if(currentPos.x() == 0){
				currentPos.straight(direction, Moves.FORWARD);
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
					// Checking for right turn availability
					if(maze.moveAvailable(currentPos, direction, Moves.RIGHT)){
						current.incrementMove(Moves.RIGHT, direction);
						currentPos.turn(direction, Moves.RIGHT);
						direction = direction.turnRight();
					// Checking for left turn availability
					}else if(maze.moveAvailable(currentPos, direction, Moves.LEFT)){
						current.incrementMove(Moves.LEFT, direction);
						currentPos.turn(direction, Moves.LEFT);
						direction = direction.turnLeft();
					// Checking for straight ability
					}else if(maze.moveAvailable(currentPos, direction, Moves.FORWARD)){
						current.incrementMove(Moves.FORWARD, direction);
						currentPos.straight(direction, Moves.FORWARD);	
					}
				}
				// Returning on original path
				else if(prev == 1){
					current.incrementPrev(direction);
					currentPos.straight(direction, Moves.UTURN);
					direction = direction.opposite();
				}
				// Otherwise selecting lowest flagged option (path travelled 0 times is ideal, then 1)
				else{
					String min_string = current.minMark(direction);
					switch(min_string){
						case "LEFT":
							current.incrementMove(Moves.LEFT,direction);
							currentPos.turn(direction, Moves.LEFT);
							direction = direction.turnLeft();
							break;
						case "RIGHT":
							current.incrementMove(Moves.RIGHT, direction);
							currentPos.turn(direction, Moves.RIGHT);
							direction = direction.turnRight();
							break;
						case "FORWARD":
							current.incrementMove(Moves.FORWARD, direction);
							currentPos.straight(direction, Moves.FORWARD);
							break;
						case "PREV":
							current.incrementPrev(direction);
							currentPos.straight(direction, Moves.UTURN);
							direction = direction.opposite();
							break;
					}
					
				}

			}else{
				// Traditional right hand method until junction is reached again.
				if(maze.moveAvailable(currentPos,direction, Moves.RIGHT)){
					currentPos.turn(direction, Moves.RIGHT);
					direction = direction.turnRight();
				}else if(maze.moveAvailable(currentPos,direction, Moves.FORWARD)){
					currentPos.straight(direction, Moves.FORWARD);
				}else if(maze.moveAvailable(currentPos,direction, Moves.LEFT)){
					currentPos.turn(direction, Moves.LEFT);
					direction = direction.turnLeft();
				}else{
					currentPos.straight(direction, Moves.UTURN);
					direction = direction.opposite();
				}
			}
		}
		return junctions;
	}
	
	private String createPath(JunctionList junctions, Coordinate startPos, Coordinate endPos, Maze maze, Orientation input_dir){
		
		Coordinate currentPos = new Coordinate(startPos.x(),startPos.y());
		Orientation direction = input_dir;
		String path = "";

		while(!currentPos.equivalentTo(endPos)){
			if(currentPos.x() == 0){
				path += "F";
				currentPos.straight(direction, Moves.FORWARD);
				continue;
			}
			if(isJunction(currentPos, maze, direction) && maze.inBounds(currentPos)){
				Junction j = new Junction(currentPos, direction, maze);
				Junction current;
				int index = junctions.has(j);
				current = junctions.get(index);
				
				int right = 0, left = 0, straight = 0;
				switch(direction){
					case Orientation.DOWN:
						right = current.left(); 
						left = current.right(); 
						straight = current.bottom();
						break;
					case Orientation.LEFT:
						right = current.top(); 
						left = current.bottom(); 
						straight = current.left();
						break;
					case Orientation.RIGHT: 
						right = current.bottom(); 
						left = current.top(); 
						straight = current.right();
						break;
					case Orientation.UP:
						right = current.right(); 
						left = current.left(); 
						straight = current.top();
						break;
				}

				if(straight == 1){
					path += "F";
					currentPos.straight(direction, Moves.FORWARD);
				}else if(right == 1){
					path += "RF";
					currentPos.turn(direction, Moves.RIGHT);
					direction = direction.turnRight();
				}else if(left == 1){
					path += "LF";
					currentPos.turn(direction, Moves.LEFT);
					direction = direction.turnLeft();
				}
			}else{
				// Traditional right hand method until junction is reached again.
				if(maze.moveAvailable(currentPos,direction, Moves.RIGHT)){
					path += "RF";
					currentPos.turn(direction, Moves.RIGHT);
					direction = direction.turnRight();
				}else if(maze.moveAvailable(currentPos,direction, Moves.FORWARD)){
					path += "F";
					currentPos.straight(direction, Moves.FORWARD);
				}else if(maze.moveAvailable(currentPos,direction, Moves.LEFT)){
					path += "LF";
					currentPos.turn(direction, Moves.LEFT);
					direction = direction.turnLeft();
				}	
			}
		}
		return path;
	}

	private boolean isJunction(Coordinate c, Maze maze, Orientation direction){
		boolean result = false;
		int count = 0;
		if(maze.moveAvailable(c,direction, Moves.LEFT)){count++;};
		if(maze.moveAvailable(c,direction, Moves.RIGHT)){count++;};
		if(maze.moveAvailable(c,direction, Moves.FORWARD)){count++;};
		if(maze.moveAvailable(c, direction, Moves.UTURN)){count++;};
		if(count >= 3){result = true;}
		return result;
	}
}

