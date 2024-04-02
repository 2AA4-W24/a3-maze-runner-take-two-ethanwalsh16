package ca.mcmaster.se2aa4.mazerunner;

public class Benchmark {
	public double benchmarkResults(Maze maze, String method, String baseline){
		double speedup = 0;
		MazeSolver baseSolver = new RightHand();
		MazeSolver methodSolver = new Tremaux();
		String pathBase = baseSolver.solveMaze(maze);
		String pathMethod = methodSolver.solveMaze(maze);

		
		/*
		System.out.println("METHOD: " + method);
		switch(baseline){
			case "tremaux":
				baseSolver = new Tremaux();
				pathBase = baseSolver.solveMaze(maze);
				break;
			case "graph":
				baseSolver = new GraphAlgorithm();
				pathBase = baseSolver.solveMaze(maze);
				break;
			default: 
				baseSolver = new RightHand();
				pathBase = baseSolver.solveMaze(maze);	
				break;
		}

		MazeSolver methodSolver;
		String pathMethod = "";
		
		switch(method){
			case "tremaux":
				methodSolver = new Tremaux();
				pathMethod = methodSolver.solveMaze(maze);
				break;
			case "graph":
				methodSolver = new GraphAlgorithm();
				pathMethod = methodSolver.solveMaze(maze);
				break;
			default: 
				methodSolver = new RightHand();
				pathMethod = methodSolver.solveMaze(maze);	
				break;
		}
		*/
		
		System.out.println("Base path: " + pathBase);
		System.out.println("Method path: " + pathMethod);
		speedup = pathBase.replaceAll("\\s","").length() / pathMethod.replaceAll("\\s","").length();
		return speedup;

	}
	
}
