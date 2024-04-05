package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactorizationTest {

    @Test
    public void baseFactorTest() {
		String path = "FFFFFLLFFRRRF";
		Factorization factorizer = new Factorization();
		String factorPath = factorizer.FactorPath(path);
		String expected = "5F 2L 2F 3R F ";
		assertTrue(factorPath.equals(expected));
    }

	@Test
	public void doubleDigitTest(){
		String path = "FFFFFFFFFRRRRRRRRRR";
		Factorization factorizer = new Factorization();
		String factorPath = factorizer.FactorPath(path);
		String expected = "9F 10R ";
		assertTrue(factorPath.equals(expected));
	}

	@Test
	public void unfactorizeTest(){
		String path = "5F 2R 3L";
		Factorization factorizer = new Factorization();
		String unfactorPath = factorizer.unfactorize(path);
		String expected = "FFFFFRRLLL";
		assertTrue(unfactorPath.equals(expected));
	}
}
