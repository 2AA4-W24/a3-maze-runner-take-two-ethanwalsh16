package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.graphalgorithm.GraphAdapter;
import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.righthand.RightHand;
import ca.mcmaster.se2aa4.mazerunner.tools.Benchmark;
import ca.mcmaster.se2aa4.mazerunner.tools.Factorization;
import ca.mcmaster.se2aa4.mazerunner.tools.Verifier;
import ca.mcmaster.se2aa4.mazerunner.tremaux.Tremaux;

import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

		try{

			logger.info("** Starting Maze Runner");
			logger.info("** Importing maze");

			Configuration config = configure(args);
			double readStart = System.currentTimeMillis();
			Maze maze = new Maze(config.input_filename());
			double readEnd = System.currentTimeMillis();
			double readTime = readEnd - readStart;
			Factorization factorizer = new Factorization();
			
			// Determining if user path is correct.
			if(!config.user_path().isEmpty()){

				Verifier verifier = new Verifier();
				List<Boolean> pathResults = verifier.correctPath(config.user_path());
				boolean correctEntry = pathResults.get(0);
				boolean isFactorized = pathResults.get(1);
				
				String user_path = "";
				
				if(isFactorized){
					user_path = factorizer.unfactorize(config.user_path());
					correctEntry = verifier.correctPath(user_path).get(0);
				}else{
					user_path = config.user_path();
				}

				if(correctEntry){
					String isCorrect= verifier.verifyPath(user_path, maze, maze.getStart(), maze.getEnd());
					System.out.println(isCorrect + " path");
				}else{
					logger.error("Incorrect path formatting.");
				}

			}else{
				
				MazeSolver solver;
				String method = config.method();
				String path = "";

				double solveStart;
				double solveEnd; 

				if(method.equals("tremaux")){
					solver = new Tremaux();
					solveStart = System.currentTimeMillis();
					path = solver.solveMaze(maze);
					solveEnd = System.currentTimeMillis();
				}else if(method.equals("graph")){
					solver = new GraphAdapter();
					solveStart = System.currentTimeMillis();
					path = solver.solveMaze(maze);
					solveEnd = System.currentTimeMillis();
				}else{
					solver = new RightHand();
					solveStart = System.currentTimeMillis();
					path = solver.solveMaze(maze);
					solveEnd = System.currentTimeMillis();
				}

				if(!config.benchmark().isEmpty()){
					Benchmark measurer = new Benchmark();
					double baselineStart = System.currentTimeMillis();
					String baseline = measurer.benchmarkResults(maze, config.benchmark());
					double baselineEnd = System.currentTimeMillis();

					double pathLength = path.length();
					double baselineLength = baseline.length();
					double speedup = Math.round((baselineLength / pathLength)*100) / 100.0;

					System.out.println("Maze read time: " + readTime + "ms");
					System.out.println("Solve time (method): " + Math.round((solveEnd - solveStart)*100) / 100.0 + "ms");
					System.out.println("Solve time (baseline): " + Math.round((baselineEnd - baselineStart)*100) / 100.0 + "ms");
					System.out.println("Speedup: " + speedup);

				}else{

					String factorizedPath = factorizer.FactorPath(path);
					// Returning factorized path
					System.out.println(path);
					System.out.println(factorizedPath);
				}
			}

			logger.info("** End of Maze Runner");	
			
		}catch (Exception e){
			logger.error("An error has occured");
			System.exit(1);
		}  
    }

	private static Configuration configure(String [] args) throws ParseException, FileNotFoundException{

		Options options = new Options();
		options.addOption("i", true, "Name of input maze file");
		options.addOption("p", true, "User input path to compare");
		options.addOption("method", true, "Method to solve maze (righthand or tremaux)");
		options.addOption("baseline", true, "Method for benchmarking and measuring speedup.");

        CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		String input_filename = cmd.getOptionValue("i", "");
		String user_path = cmd.getOptionValue("p", "");
		String method = cmd.getOptionValue("method", "righthand");
		String benchmark = cmd.getOptionValue("baseline", "");

		return new Configuration(input_filename,user_path,method,benchmark);	  
	}

	private record Configuration(String input_filename, String user_path, String method, String benchmark){ Configuration {} }
}
