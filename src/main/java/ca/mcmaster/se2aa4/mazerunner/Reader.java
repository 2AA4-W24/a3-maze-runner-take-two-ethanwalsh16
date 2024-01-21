package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class Reader {

	private static final Logger logger = LogManager.getLogger();

	public static ArrayList<ArrayList<String>> read(String input_filename) throws FileNotFoundException{
		BufferedReader reader = new BufferedReader(new FileReader(input_filename));	
		String line;
		ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
		int i=0;
		try {
			while ((line = reader.readLine()) != null) {
				matrix.add(new ArrayList<String>());
				for (int idx = 0; idx < line.length(); idx++) {
					if (line.charAt(idx) == '#') {
						System.out.print("WALL ");
						matrix.get(i).add("W");
					} else if (line.charAt(idx) == ' ') {
						System.out.print("PASS ");
						matrix.get(i).add("P");
					}
				}
				System.out.print(System.lineSeparator());
				i++;
			}
		} catch (IOException e) {
			logger.error("/!\\ An error has occured /!\\");
		}
		return matrix;
	}
}
