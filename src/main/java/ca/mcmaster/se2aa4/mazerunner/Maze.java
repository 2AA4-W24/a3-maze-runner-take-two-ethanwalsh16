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

	public int[][] toInt(){
		List<Integer> dimensions = this.getDimensions();
		int[][] intMaze = new int[dimensions.get(1)][dimensions.get(0)];
		for(int i=0; i<dimensions.get(1); i++){
			for(int j=0; j<dimensions.get(0); j++){
				if(matrix.get(i).get(j).equals("W")){
					intMaze[i][j] = -1;
				}else{
					intMaze[i][j] = 0;
				}
			}
		}
		return intMaze;
	}

	public boolean rightTurnAvailable(Coordinate currentPos, Orientation direction){
		boolean result = true;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.getY()+1).get(currentPos.getX()).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.getY()).get(currentPos.getX()-1).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.getY()-1).get(currentPos.getX()).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.getY()).get(currentPos.getX()+1).equals("P");
				break;
		}
		return result;
	}

	public boolean leftTurnAvailable(Coordinate currentPos, Orientation direction){
		boolean result = false;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.getY()-1).get(currentPos.getX()).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.getY()).get(currentPos.getX()+1).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.getY()+1).get(currentPos.getX()).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.getY()).get(currentPos.getX()-1).equals("P");
				break;
		}
		return result;
	}

	public boolean straightAvailable(Coordinate currentPos, Orientation direction){
		boolean result = false;
		switch(direction){
			case Orientation.RIGHT:
				result = matrix.get(currentPos.getY()).get(currentPos.getX()+1).equals("P");
				break;
			case Orientation.DOWN:
				result = matrix.get(currentPos.getY()+1).get(currentPos.getX()).equals("P");
				break;
			case Orientation.LEFT:
				result = matrix.get(currentPos.getY()).get(currentPos.getX()-1).equals("P");
				break;
			case Orientation.UP:
				result = matrix.get(currentPos.getY()-1).get(currentPos.getX()).equals("P");
				break;
		}
		return result;
	}

	public String generatePaths(String method) throws InterruptedException {
		if(method.equals("tremaux")){
			MazeSolver solver = new Tremaux();
			return solver.solveMaze(this);
		}else{
			MazeSolver solver = new RightHand();
			return solver.solveMaze(this);
		}
	}

	public String testUserPath(String userString){
		Verifier verifier = new Verifier();
		String isCorrect= verifier.verifyPath(userString, this, entryPoints);
		return isCorrect;
	}
}
