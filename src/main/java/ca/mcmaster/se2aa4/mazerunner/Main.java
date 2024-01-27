package ca.mcmaster.se2aa4.mazerunner;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
		try{
			logger.info("** Starting Maze Runner");
			Configuration config = configure(args);
			
			logger.info("** Importing maze");
			Maze inputMaze = new Maze(config.input_filename());
			
			// Outputting if user path is correct (if no user path flag provided, performing maze solving and returning generated path.)
			if(!config.user_path().isEmpty()){
				boolean correctEntry = Verifier.correctPath(config.user_path());
				if(correctEntry){
					String isCorrect = inputMaze.testUserPath(config.user_path());
					System.out.println(isCorrect + "path");
				}else{
					logger.error("Incorrect path formatting.");
				}
			}else{
				String[] paths = inputMaze.generatePaths(config.method());
				System.out.println(paths[1]);
			}

			logger.info("** End of Maze Runner");	
			
		}catch (Exception e){
			logger.error("An error has occured");
			e.printStackTrace();
			System.exit(1);
		}  
    }

	private static Configuration configure(String [] args) throws ParseException, FileNotFoundException{
		Options options = new Options();
		options.addOption("i", true, "Name of input maze file");
		options.addOption("p", true, "User input path to compare");
		options.addOption("method", true, "Method to solve maze (righthand or tremaux)");

        CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		String input_filename = cmd.getOptionValue("i", "");
		String user_path = cmd.getOptionValue("p", "");
		String method = cmd.getOptionValue("method", "righthand");
		return new Configuration(input_filename,user_path,method);	  
	}

	private record Configuration(String input_filename, String user_path, String method){
		Configuration {

		}
	}
}
