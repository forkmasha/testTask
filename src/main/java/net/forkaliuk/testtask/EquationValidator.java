package net.forkaliuk.testtask;

public class EquationValidator {
    public boolean isValid(String equation) {
        return checkBrackets(equation) && checkExpression(equation);
    }

    private boolean checkBrackets(String equation) {
        int numOpenBrackets = 0;
        int numCloseBrackets = 0;
        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);
            if (c == '(') {
                numOpenBrackets++;
            } else if (c == ')') {
                numCloseBrackets++;
            }
            if (numCloseBrackets > numOpenBrackets) {
                return false;
            }
        }
        return numOpenBrackets == numCloseBrackets;
    }

    private boolean checkExpression(String equation) {
        String operators = "+-*/";


        for (int i = 0; i < equation.length() - 1; i++) {
            char currentChar = equation.charAt(i);
            char nextChar = equation.charAt(i + 1);

            if (isOperator(currentChar, operators)) {
                if (hasConsecutiveOperators(nextChar, currentChar,operators)) {
                    return false;
                }
            }
            if (isEmptyBrackets(currentChar, nextChar)) {
                return false;
            }
        }

        if (endsWithNonNegativeOperator(equation, operators)) {
            return false;
        }

        if (startsWithNonNegativeOperator(equation, operators)) {
            return false;
        }

        return true;
    }

    private boolean isOperator(char c, String operators) {
        return operators.indexOf(c) >= 0;
    }

    private boolean hasConsecutiveOperators(char nextChar, char currentChar, String operators) {
        if (currentChar == '-') {
            return isOperator(nextChar, operators);
        } else {
            return isOperator(nextChar, operators) && nextChar != '-';
        }
    }

    private boolean isEmptyBrackets(char currentChar, char nextChar) {
        return currentChar == '(' && nextChar == ')';
    }

    private boolean endsWithNonNegativeOperator(String equation, String operators) {
        char lastChar = equation.charAt(equation.length() - 1);
        return isOperator(lastChar, operators) && lastChar != '-';
    }

    private boolean startsWithNonNegativeOperator(String equation, String operators) {
        char firstChar = equation.charAt(0);
        return isOperator(firstChar, operators) && firstChar != '-';
    }
}

