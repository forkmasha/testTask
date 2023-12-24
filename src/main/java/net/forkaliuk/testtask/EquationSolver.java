package net.forkaliuk.testtask;

import java.util.List;
import java.util.Scanner;

public class EquationSolver {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/equations_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static final String INVALID_EQUATION_MESSAGE = "Некоректне введення рівняння";
    private static final String SAVE_EQUATION_ERROR_MESSAGE = "Помилка при збереженні рівняння у базі даних";
    private static final String SAVE_ROOT_ERROR_MESSAGE = "Помилка при збереженні кореня у базі даних";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            processEquation(scanner);
        }
    }

    private static void processEquation(Scanner scanner) {
        System.out.println("Введіть математичне рівняння:");
        String equation = scanner.nextLine();

        EquationValidator equationValidator = new EquationValidator();

        if (!equationValidator.isValid(equation)) {
            System.out.println(INVALID_EQUATION_MESSAGE);
            return;
        }

        EquationProcessor equationProcessor = new EquationProcessor(DB_URL, DB_USER, DB_PASSWORD);

        if (!equationProcessor.saveEquation(equation)) {
            System.out.println(SAVE_EQUATION_ERROR_MESSAGE);
            return;
        }

        int numCoefficients = equationProcessor.calculateCoefficients(equation);
        System.out.println("Кількість коефіцієнтів у рівнянні: " + numCoefficients);

        saveRoot(scanner, equationProcessor, equation);

        processRootSearch(scanner, equationProcessor);
    }

    private static void saveRoot(Scanner scanner, EquationProcessor equationProcessor, String equation) {
        System.out.println("Введіть корінь для даного рівняння:");
        double root = scanner.nextDouble();

        if (!equationProcessor.saveRoot(equation, root)) {
            System.out.println(SAVE_ROOT_ERROR_MESSAGE);
            System.exit(1);
        }

        System.out.println("Корінь успішно збережено в базі даних");
    }

    private static void processRootSearch(Scanner scanner, EquationProcessor equationProcessor) {
        System.out.println("Введіть корінь для пошуку рівнянь:");
        double specifiedRoot = scanner.nextDouble();

        List<String> equationsWithSpecifiedRoot = equationProcessor.findEquationsByRoot(specifiedRoot);
        System.out.println("Рівняння з вказаним коренем " + specifiedRoot + ": " + equationsWithSpecifiedRoot);

        List<String> equationsWithSingleRoot = equationProcessor.findEquationsWithSingleRoot();
        System.out.println("Рівняння з єдиним коренем: " + equationsWithSingleRoot);
    }
}
