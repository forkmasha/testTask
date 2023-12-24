package net.forkaliuk.testtask;

import org.junit.jupiter.api.Test;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


public class EquationValidatorTest {

    @Test
    public void validEquationTest() {
        EquationValidator validator = new EquationValidator();
        assertTrue(validator.isValid("2*(x+5+х)+5=10"));
    }

    @Test
    public void invalidEquationTest() {
        EquationValidator validator = new EquationValidator();
        assertFalse(validator.isValid("2*x + (3-1) = 4)"));
    }

    @Test
    public void equationWithConsecutiveOperatorsTest() {
        EquationValidator validator = new EquationValidator();
        assertFalse(validator.isValid("2*x ++ (3-1) = 4"));
    }

    @Test
    public void equationWithConsecutiveOperators2Test() {
        EquationValidator validator = new EquationValidator();
        assertTrue(validator.isValid("4x*-7 = 4"));
    }

    @Test
    public void equationWithEmptyBracketsTest() {
        EquationValidator validator = new EquationValidator();
        assertFalse(validator.isValid("2*x + () = 4"));
    }
}