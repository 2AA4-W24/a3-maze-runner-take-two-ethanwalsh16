package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.tools.Factorization;

import static org.junit.jupiter.api.Assertions.*;

public class FactorizationTest {

    @Test
    void baseFactorTest() {
		String path = "FFFFFLLFFRRRF";
		Factorization factorizer = new Factorization();
		String factorPath = factorizer.factorPath(path);
		String expected = "5F 2L 2F 3R F ";
		assertEquals(expected,factorPath);
    }

	@Test
	void doubleDigitTest(){
		String path = "FFFFFFFFFRRRRRRRRRR";
		Factorization factorizer = new Factorization();
		String factorPath = factorizer.factorPath(path);
		String expected = "9F 10R ";
		assertEquals(expected,factorPath);
	}

	@Test
	void unfactorizeTest(){
		String path = "5F 2R 3L";
		Factorization factorizer = new Factorization();
		String unfactorPath = factorizer.unfactorize(path);
		String expected = "FFFFFRRLLL";
		assertEquals(expected,unfactorPath);
	}
}
