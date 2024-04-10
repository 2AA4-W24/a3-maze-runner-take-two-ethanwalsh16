package ca.mcmaster.se2aa4.mazerunner.maze;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.se2aa4.mazerunner.Reader;
import ca.mcmaster.se2aa4.mazerunner.enums.Orientation;
import ca.mcmaster.se2aa4.mazerunner.enums.Moves;

public class Maze {
	
	// Primary class for storing and exploring the maze.
	private ArrayList<ArrayList<String>> matrix;
	private List<Coordinate> entryPoints;

	Entries entryFinder = new Entries();
	Reader reader = new Reader();

	/*  Modifiers for determining if an adjacent coordinate is suitable for a move to
		Depending on move, will use one of these lists to check adjacent coordinates.
		lMods for left, rMods for right, sMods for straight, and pMods for prev/u-turn.
	*/
	private int[] lMods = {-1,0,0,1,1,0,0,-1};
	private int[] rMods = {1,0,0,-1,-1,0,0,1};
	private int[] sMods = {0,1,1,0,0,-1,-1,0};
	private int[] pMods = {0,-1,-1,0,0,1,1,0};

	public Maze(String file_input) throws FileNotFoundException{
		matrix = reader.read(file_input);
		entryPoints = entryFinder.findEntries(matrix); 	
	}

	// Returns starting coordinate of maze
	public Coordinate getStart(){
		return this.entryPoints.get(0);
	}

	// Returns ending coordinate of maze
	public Coordinate getEnd(){
		return this.entryPoints.get(1);
	}
	public boolean isTile(int x, int y){
		return matrix.get(y).get(x).equals("P");
	}

	public List<Integer> getDimensions(){
		List<Integer> dimensions = new ArrayList<Integer>();
		dimensions.add(matrix.get(0).size()); // Adding x dimension
		dimensions.add(matrix.size()); // Adding y dimension
		return dimensions;
	}

	// Determining if move is available.
	public boolean moveAvailable(Coordinate currentPos, Orientation direction, Moves move){
		
		int[] mods = new int[8];
		if(move.equals(Moves.LEFT)){
			mods = lMods;
		}else if(move.equals(Moves.RIGHT)){
			mods = rMods;
		}else if(move.equals(Moves.FORWARD)){
			mods = sMods;
		}else{
			mods = pMods;
		}

		boolean result = false;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.y()+mods[0]).get(currentPos.x()+mods[1]).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.y()+mods[2]).get(currentPos.x()+mods[3]).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.y()+mods[4]).get(currentPos.x()+mods[5]).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.y()+mods[6]).get(currentPos.x()+mods[7]).equals("P");
				break;
		}
		return result;
	}

	// Determining if current coordinate is within bounds of the maze.
	public boolean inBounds(Coordinate currentPos) {
		List<Integer> dimensions = this.getDimensions();
		if((1 <= currentPos.x() && currentPos.x() < dimensions.get(0)-1) && (1 <= currentPos.y() && currentPos.y() < dimensions.get(1)-1)){
			return true;
		}else{
			return false;
		}
	}
}
