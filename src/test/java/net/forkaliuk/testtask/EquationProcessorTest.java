package net.forkaliuk.testtask;

import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;

public class EquationProcessorTest {

    private EquationProcessor equationProcessor;

    @Before
    public void setUp() {
        equationProcessor = new EquationProcessor("jdbc:mysql://localhost:3306/equations_db", "root", "root");
    }

    @Test
    public void saveEquationTest1() {
        assertTrue(equationProcessor.saveEquation("2*x + 3 = 5"));
    }
    @Test
    public void saveEquationTest2() {
        assertTrue(equationProcessor.saveEquation("2x^2 + 3x - 5 = 0"));
    }
    @Test
    public void saveRootTest1() {
        equationProcessor.saveEquation("2x^2 + 3x - 5 = 0");
        assertTrue(equationProcessor.saveRoot("2x^2 + 3x - 5 = 0", 1.0));
    }
    @Test
    public void saveRootTest2() {
        assertTrue(equationProcessor.saveRoot("2*x + 3 = 5", 2.0));
    }
    @Test
    public void calculateCoefficientsTest1() {
        assertEquals(2, equationProcessor.calculateCoefficients("2*x + 3 = 5"));
    }
    @Test
    public void calculateCoefficientsTest2() {
        assertEquals(3, equationProcessor.calculateCoefficients("2x^2 + 3x - 5 = 0"));
    }
    @Test
    public void findEquationsByRootTest() {
        equationProcessor.saveEquation("x^2 - 4 = 0");
        equationProcessor.saveRoot("x^2 - 4 = 0", 2.0);

        List<String> equations = equationProcessor.findEquationsByRoot(2.0);
        assertTrue(equations.contains("x^2 - 4 = 0"));
    }
}
