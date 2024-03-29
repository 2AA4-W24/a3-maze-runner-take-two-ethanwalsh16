package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

	Entries entryFinder = new Entries();
	ArrayList<ArrayList<String>> matrix;
	List<Coordinate> entryPoints;

	public Maze(String file_input) throws FileNotFoundException{
		matrix = Reader.read(file_input);
		entryPoints = entryFinder.findEntries(matrix); 	
	}

	public Coordinate getStart(){
		return this.entryPoints.get(0);
	}

	public Coordinate getEnd(){
		return this.entryPoints.get(1);
	}

	public boolean rightTurnAvailable(Coordinate currentPos, Orientation direction){
		System.out.println("Position: " + currentPos.toString());
		System.out.println("DIRECTION: " + direction);
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

	public List<String> generatePaths(String method) throws InterruptedException {
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
		String isCorrect= verifier.verifyPath(userString, matrix, entryPoints);
		return isCorrect;
	}
}
