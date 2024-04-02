package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

	Entries entryFinder = new Entries();
	ArrayList<ArrayList<String>> matrix;
	List<Coordinate> entryPoints;
	Reader reader = new Reader();

	public Maze(String file_input) throws FileNotFoundException{
		matrix = reader.read(file_input);
		entryPoints = entryFinder.findEntries(matrix); 	
	}

	public Coordinate getStart(){
		return this.entryPoints.get(0);
	}

	public Coordinate getEnd(){
		return this.entryPoints.get(1);
	}

	public List<Integer> getDimensions(){
		List<Integer> dimensions = new ArrayList<Integer>();
		dimensions.add(matrix.get(0).size()); // Adding x dimension
		dimensions.add(matrix.size()); // Adding y dimension
		return dimensions;
	}

	public boolean rightTurnAvailable(Coordinate currentPos, Orientation direction){
		boolean result = true;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.Y()+1).get(currentPos.X()).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.Y()).get(currentPos.X()-1).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.Y()-1).get(currentPos.X()).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.Y()).get(currentPos.X()+1).equals("P");
				break;
		}
		return result;
	}

	public boolean leftTurnAvailable(Coordinate currentPos, Orientation direction){
		boolean result = false;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.Y()-1).get(currentPos.X()).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.Y()).get(currentPos.X()+1).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.Y()+1).get(currentPos.X()).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.Y()).get(currentPos.X()-1).equals("P");
				break;
		}
		return result;
	}

	public boolean straightAvailable(Coordinate currentPos, Orientation direction){
		boolean result = false;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.Y()).get(currentPos.X()+1).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.Y()+1).get(currentPos.X()).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.Y()).get(currentPos.X()-1).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.Y()-1).get(currentPos.X()).equals("P");
				break;
		}
		return result;
	}

	public boolean prevAvailable(Coordinate currentPos, Orientation direction){
		boolean result = false;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.Y()).get(currentPos.X()-1).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.Y()-1).get(currentPos.X()).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.Y()).get(currentPos.X()+1).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.Y()+1).get(currentPos.X()).equals("P");
				break;
		}
		return result;
	}

	

	public boolean inBounds(Coordinate currentPos) {
		List<Integer> dimensions = this.getDimensions();
		if((1 <= currentPos.X() && currentPos.X() < dimensions.get(0)-1) && (1 <= currentPos.Y() && currentPos.Y() < dimensions.get(1)-1)){
			return true;
		}else{
			return false;
		}
	}
}
