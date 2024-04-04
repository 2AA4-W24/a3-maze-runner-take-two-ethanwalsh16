package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;


public class BenchmarkTest {
	private static final Logger logger = LogManager.getLogger();

    @Test
    public void baseTest() {
		Benchmark benchmarker = new Benchmark();
		String benchMarkResultGraph = "";
		String benchMarkResultTremaux = "";
		String benchMarkResultRightHand = "";
		try {
			benchMarkResultGraph = benchmarker.benchmarkResults(new Maze("./examples/straight.maz.txt"), "graph");
			benchMarkResultTremaux = benchmarker.benchmarkResults(new Maze("./examples/straight.maz.txt"), "tremaux");
			benchMarkResultRightHand = benchmarker.benchmarkResults(new Maze("./examples/straight.maz.txt"), "righthand");
		} catch (FileNotFoundException e) {
			logger.error("File not found.");
		}
		String expected = "FFFF";
		assertTrue(expected.length()/benchMarkResultGraph.length()==1.0);
		assertTrue(expected.length()/benchMarkResultTremaux.length()==1.0);
		assertTrue(expected.length()/benchMarkResultRightHand.length()==1.0);
    }


}
