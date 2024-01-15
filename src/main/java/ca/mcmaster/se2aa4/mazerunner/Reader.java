package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reader {

	private static final Logger logger = LogManager.getLogger();

	public static void read(String input_filename) throws FileNotFoundException{
		BufferedReader reader = new BufferedReader(new FileReader(input_filename));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				for (int idx = 0; idx < line.length(); idx++) {
					if (line.charAt(idx) == '#') {
						System.out.print("WALL ");
					} else if (line.charAt(idx) == ' ') {
						System.out.print("PASS ");
					}
				}
				System.out.print(System.lineSeparator());
			}
		} catch (IOException e) {
			logger.error("/!\\ An error has occured /!\\");
		}
	}
}
