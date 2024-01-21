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
			logger.info("** Importing maze");
			Configuration config = configure(args);

			Reader.read(config.input_filename());
			Maze inputMaze = new Maze("./examples/mvp.maz.txt");
			String path = inputMaze.createPath();
			logger.info("**** Path is: " + path);
			logger.info("** End of Maze Runner");
 
			/* 
			if(!config.user_path().isEmpty()){
				String pathsEqual = Comparer.comparePath(path, config.user_path());
				System.out.println(pathsEqual);
			}
			*/	
			
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

        CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		String input_filename = cmd.getOptionValue("i", "");
		String user_path = cmd.getOptionValue("p", "");
		
		return new Configuration(input_filename,user_path);	  
	}

	private record Configuration(String input_filename, String user_path){
		Configuration {

		}
	}
}
