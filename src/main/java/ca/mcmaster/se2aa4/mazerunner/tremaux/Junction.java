package ca.mcmaster.se2aa4.mazerunner.tremaux;

import java.util.Map;

import ca.mcmaster.se2aa4.mazerunner.enums.Moves;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.maze.Coordinate;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;

import java.util.HashMap;
import java.util.Iterator;

public class Junction {
	/* Junction object, used to track intersections for Tremaux method (tiles with >2 path exits/entries)
	   Also used for entrance marking.
	*/ 
	int x;
	int y;
	int top;
	int bottom;
	int right;
	int left;

	public Junction(Coordinate c, Orientation direction, Maze maze){
		this.x = c.x();
		this.y = c.y();
		switch(direction){
			case Orientation.UP:
				this.bottom = 0;
				this.top = (maze.moveAvailable(c, direction, Moves.FORWARD)) ? 0 : Integer.MAX_VALUE;
				this.left = (maze.moveAvailable(c, direction, Moves.LEFT)) ? 0 : Integer.MAX_VALUE;
				this.right = (maze.moveAvailable(c, direction, Moves.RIGHT)) ? 0 : Integer.MAX_VALUE;
				break;
			case Orientation.DOWN:
				this.top = 0;
				this.bottom = (maze.moveAvailable(c, direction, Moves.FORWARD)) ? 0 : Integer.MAX_VALUE;
				this.right = (maze.moveAvailable(c, direction, Moves.LEFT)) ? 0 : Integer.MAX_VALUE;
				this.left = (maze.moveAvailable(c, direction, Moves.RIGHT)) ? 0 : Integer.MAX_VALUE;
				break;
			case Orientation.LEFT:
				this.right = 0;
				this.left = (maze.moveAvailable(c, direction, Moves.FORWARD)) ? 0 : Integer.MAX_VALUE;
				this.bottom = (maze.moveAvailable(c, direction, Moves.LEFT)) ? 0 : Integer.MAX_VALUE;
				this.top = (maze.moveAvailable(c, direction, Moves.RIGHT)) ? 0 : Integer.MAX_VALUE;
				break;
			case Orientation.RIGHT:
				this.left = 0;
				this.right = (maze.moveAvailable(c, direction, Moves.FORWARD)) ? 0 : Integer.MAX_VALUE;
				this.top = (maze.moveAvailable(c, direction, Moves.LEFT)) ? 0 : Integer.MAX_VALUE;
				this.bottom = (maze.moveAvailable(c, direction, Moves.RIGHT)) ? 0 : Integer.MAX_VALUE;
				break;
		}
	}

	public int x(){return this.x;}
	public int y(){return this.y;}
	public int top(){return this.top;}
	public int bottom(){return this.bottom;}
	public int left(){return this.left;}
	public int right(){return this.right;}

	// Incrementing mark on previous entry
	public void incrementPrev(Orientation direction) {
		switch(direction){
			case Orientation.UP:
				this.bottom += 1;
				break;
			case Orientation.DOWN:
				this.top += 1;
				break;
			case Orientation.LEFT:
				this.right += 1;
				break;
			case Orientation.RIGHT:
				this.left += 1;
				break;
		}
	}

	// Incrementing any other entry depending on move.
	public void incrementMove(Moves m, Orientation direction) {
		switch(direction){
			case Orientation.UP:
				if(m.equals(Moves.LEFT)){this.left+=1;}else if(m.equals(Moves.FORWARD)){this.top+=1;}else{this.right+=1;};
				break;
			case Orientation.DOWN:
				if(m.equals(Moves.LEFT)){this.right+=1;}else if(m.equals(Moves.FORWARD)){this.bottom+=1;}else{this.left+=1;};
				break;
			case Orientation.LEFT:
				if(m.equals(Moves.LEFT)){this.bottom+=1;}else if(m.equals(Moves.FORWARD)){this.left+=1;}else{this.top+=1;};
				break;
			case Orientation.RIGHT:
				if(m.equals(Moves.LEFT)){this.top+=1;}else if(m.equals(Moves.FORWARD)){this.right+=1;}else{this.bottom+=1;};
				break;
		}
	}

	// Finding direction with the least amount of marks, and returning string for recommended move.
	public String minMark(Orientation direction){
		String result = "";
		Map<String, Integer> turn_values = new HashMap<String, Integer>();
		turn_values.put("left",this.left);
		turn_values.put("right",this.right);
		turn_values.put("top",this.top);
		turn_values.put("bottom",this.bottom);
		Iterator<Map.Entry<String, Integer>> iterator = turn_values.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() == Integer.MAX_VALUE) {
                iterator.remove();
            }
        }
		int min = Integer.MAX_VALUE;
		String min_string = "";
		for (Map.Entry<String, Integer> nearbyCell : turn_values.entrySet()) {
			if(nearbyCell.getValue()<min && nearbyCell.getValue() >= 0){
				min = nearbyCell.getValue();
				min_string = nearbyCell.getKey();
			}
		}
		result = min_string;
		String move = "";
		switch(direction){
			case Orientation.UP:
				if(result.equals("left")){move="LEFT";}else if(result.equals("right")){move="RIGHT";}else if(result.equals("bottom")){move="PREV";}else{move="FORWARD";}
				break;
			case Orientation.DOWN:
				if(result.equals("left")){move="RIGHT";}else if(result.equals("right")){move="LEFT";}else if(result.equals("bottom")){move="FORWARD";}else{move="PREV";}
				break;
			case Orientation.LEFT:
				if(result.equals("left")){move="FORWARD";}else if(result.equals("right")){move="PREV";}else if(result.equals("bottom")){move="LEFT";}else{move="RIGHT";}
				break;
			case Orientation.RIGHT:
				if(result.equals("left")){move="PREV";}else if(result.equals("right")){move="FORWARD";}else if(result.equals("bottom")){move="RIGHT";}else{move="LEFT";}
				break;
		}
		return move;
	}
}
