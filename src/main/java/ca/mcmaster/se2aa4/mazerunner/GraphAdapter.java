package ca.mcmaster.se2aa4.mazerunner;

public class GraphAdapter implements MazeSolver{
	
	@Override
	public String solveMaze(Maze maze) {
		GraphAlgorithm graphAlg = new GraphAlgorithm();
		String result = graphAlg.findShortestPath(maze);
		return result;
	}
}
