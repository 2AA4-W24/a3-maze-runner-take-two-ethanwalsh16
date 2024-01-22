package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;

enum Orientation {
	UP,
	RIGHT,
	DOWN,
	LEFT
}

public class Maze {

	ArrayList<ArrayList<String>> matrix;

	public Maze(String input) throws FileNotFoundException {
		matrix = Reader.read(input);
	}

	public String generatePath() {
		MazeSolver solver = new RightHand();
		Coordinate[] entries = findEntries();
		System.out.println("SIZE: " + matrix.size() + ", " + matrix.get(0).size());
		System.out.println("ENTRY 1: " + entries[0].getX() + ", " + entries[0].getY());
		System.out.println("ENTRY 2: " + entries[1].getX() + ", " + entries[1].getY());
		return solver.solveMaze(matrix);
	}

	public Coordinate[] findEntries(){
		ArrayList<String> startColumn = returnColumn(0);
		Coordinate entry1 = new Coordinate(0,0), entry2 = new Coordinate(0,0);
		for(int i=0; i<startColumn.size(); i++){
			if(!startColumn.get(i).equals("W")){
				entry1 = new Coordinate(0,i);
			}
		}
		ArrayList<String> endColumn = returnColumn(matrix.size()-1);
		for(int i=0; i<endColumn.size(); i++){
			if(!endColumn.get(i).equals("W")){
				entry2 = new Coordinate(matrix.get(0).size()-1,i);
			}
		}
		Coordinate[] entries = {entry1,entry2};
		return entries;
	}

	public ArrayList<String> returnColumn(int n){
		ArrayList<String> column = new ArrayList<String>();
		for(int i=0; i<matrix.size(); i++){
			String currentSlot = matrix.get(i).get(n);
			column.add(currentSlot);
		}
		return column;
	}
}
