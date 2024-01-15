package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
		Options options = new Options();
		options.addOption("i", true, "Name of input maze file");
		options.addOption("p", true, "User input path to compare");
        CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			String input_filename = cmd.getOptionValue("i", "");
			String user_path = cmd.getOptionValue("p", "");
            logger.info("**** Reading the maze from file " + input_filename);
			Reader.read(input_filename);
			logger.info("**** Computing path");
			// Later on, for bonus option, an additional flag will be needed here for tremaux vs righthand
			String generated_path = MazeParser.createPath();
			if(user_path.isEmpty()){
				logger.info(generated_path);
			}else{
				logger.info(Comparer.comparePath(generated_path, user_path));
			}
        	logger.info("** End of MazeRunner");
			
			         
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }      
    }
}
