package ca.mcmaster.se2aa4.mazerunner;

public class Junction {
	int x;
	int y;
	int top;
	int bottom;
	int right;
	int left;

	public Junction(Coordinate c, Orientation direction, Maze maze){
		this.x = c.getX();
		this.y = c.getY();
		this.top = 0;
		this.bottom = 0;
		this.right = 0;
		this.left = 0;
		/*
		switch(direction){
			case Orientation.UP:
				this.bottom = 0;
				this.top = (maze.straightAvailable(c, direction)) ? 0 : -1;
				this.left = (maze.leftTurnAvailable(c, direction)) ? 0 : -1;
				this.right = (maze.rightTurnAvailable(c, direction)) ? 0 : -1;
				break;
			case Orientation.DOWN:
				this.top = 0;
				this.bottom = (maze.straightAvailable(c, direction)) ? 0 : -1;
				this.right = (maze.leftTurnAvailable(c, direction)) ? 0 : -1;
				this.left = (maze.rightTurnAvailable(c, direction)) ? 0 : -1;
				break;
			case Orientation.LEFT:
				this.right = 0;
				this.left = (maze.straightAvailable(c, direction)) ? 0 : -1;
				this.bottom = (maze.leftTurnAvailable(c, direction)) ? 0 : -1;
				this.top = (maze.rightTurnAvailable(c, direction)) ? 0 : -1;
				break;
			case Orientation.RIGHT:
				this.left = 0;
				this.right = (maze.straightAvailable(c, direction)) ? 0 : -1;
				this.top = (maze.leftTurnAvailable(c, direction)) ? 0 : -1;
				this.bottom = (maze.rightTurnAvailable(c, direction)) ? 0 : -1;
				break;
		}
		 */
	}

	public int x(){return this.x;}
	public int y(){return this.y;}
	public int top(){return this.top;}
	public int bottom(){return this.bottom;}
	public int left(){return this.left;}
	public int right(){return this.right;}

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

	public String minMark(Orientation direction){
		String result = "";
		if(this.left <= this.right && this.left <= this.top && this.left <= this.bottom){
			result = "LEFT";
		}else if(this.right <= this.left && this.right <= this.top && this.right <= this.bottom){
			result = "RIGHT";
		}else if(this.top <= this.left && this.top <= this.bottom && this.top <= this.right){
			result = "FORWARD";
		}else{
			result = "PREV";
		}
		String move = "";
		switch(direction){
			case Orientation.UP:
				move = result;
				break;
			case Orientation.DOWN:
				if(result.equals("LEFT")){move="RIGHT";}else if(result.equals("RIGHT")){move="LEFT";}else if(result.equals("FORWARD")){move="PREV";}else{move="PREV";}
				break;
			case Orientation.LEFT:
				if(result.equals("LEFT")){move="FORWARD";}else if(result.equals("RIGHT")){move="PREV";}else if(result.equals("FORWARD")){move="RIGHT";}else{move="LEFT";}
				break;
			case Orientation.RIGHT:
				if(result.equals("LEFT")){move="PREV";}else if(result.equals("RIGHT")){move="FORWARD";}else if(result.equals("FORWARD")){move="LEFT";}else{move="RIGHT";}
				break;
		}
		return move;
	}

	// For easy debugging, remove later.
	public String toString(){
		return "TOP: " + this.top + " BOTTOM: " + this.bottom + " LEFT: " + this.left + " RIGHT: " + this.right;

	}
}
