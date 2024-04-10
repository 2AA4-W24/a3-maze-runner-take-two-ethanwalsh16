package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.tools.Benchmark;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;


public class BenchmarkTest {
	private static final Logger logger = LogManager.getLogger();

    @Test
    void baseTest() {
		Benchmark benchmarker = new Benchmark();
		List<Double> benchMarkResultGraph = new ArrayList<Double>();
		List<Double> benchMarkResultTremaux = new ArrayList<Double>();
		List<Double> benchMarkResultRightHand = new ArrayList<Double>();
		try {
			benchMarkResultGraph = benchmarker.benchmarkResults(new Maze("./examples/straight.maz.txt"), "graph", "FFFF");
			benchMarkResultTremaux = benchmarker.benchmarkResults(new Maze("./examples/straight.maz.txt"), "tremaux", "FFFF");
			benchMarkResultRightHand = benchmarker.benchmarkResults(new Maze("./examples/straight.maz.txt"), "righthand","FFFF");
		} catch (FileNotFoundException e) {
			logger.error("File not found.");
		}
		assertTrue(benchMarkResultGraph.get(1)-1.0<1e-6);
		assertTrue(benchMarkResultTremaux.get(1)-1.0<1e-6);
		assertTrue(benchMarkResultRightHand.get(1)-1.0<1e-6);
    }


}
