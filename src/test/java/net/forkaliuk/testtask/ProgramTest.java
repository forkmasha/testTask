package net.forkaliuk.testtask;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class ProgramTest {

    @Test
    public void testCheckBrackets_validInput() {
        String equation = "(2+3)*(4-1)";
        boolean result = Program.checkBrackets(equation);
        assertTrue(result);
    }

    @Test
    public void testCheckBrackets_invalidInput() {
        String equation = "2+3)*(4-1)";
        boolean result = Program.checkBrackets(equation);
        assertFalse(result);
    }

    @Test
    public void testCheckExpression_validInput() {
        String equation = "2+3*(4-1)";
        boolean result = Program.checkExpression(equation);
        assertTrue(result);
    }

    @Test
    public void testCheckExpression_invalidInput() {
        String equation = "2++3*(4-1)";
        boolean result = Program.checkExpression(equation);
        assertFalse(result);
    }

    @Test
    void testCheckBrackets() {
        String equation1 = "2 * (3 + 4) - 5";
        assertTrue(Program.checkBrackets(equation1));

        String equation2 = "2 * (3 + 4 - 5";
        assertFalse(Program.checkBrackets(equation2));

        String equation3 = "2 * )3 + 4(";
        assertFalse(Program.checkBrackets(equation3));
    }
}

