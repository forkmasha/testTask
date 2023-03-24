package net.forkaliuk.testtask;

import java.util.Scanner;
import java.sql.*;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть математичне рівняння:");
        String equation = scanner.nextLine();

        // Перевірка коректності введення дужок
        if (!checkBrackets(equation)) {
            System.out.println("Некоректне розміщення дужок");
            return;
        }

        // Перевірка коректності введення виразу
        if (!checkExpression(equation)) {
            System.out.println("Некоректний вираз");
            return;
        }

        // Збереження рівняння у базу даних
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/equations_db", "root", "root");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO equations (equation) VALUES ('" + equation + "')");
            System.out.println("Рівняння збережено у базі даних");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Помилка при збереженні рівняння у базі даних");
            e.printStackTrace();
        }

        // Розрахунок кількості коефіцієнтів рівняння
        int numCoefficients = equation.replaceAll("[^x]", "").length();
        System.out.println("Кількість коефіцієнтів у рівнянні: " + numCoefficients);
    }

    // Перевірка коректності розміщення дужок
    public static boolean checkBrackets(String equation) {
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
    // Перевірка коректності введеного виразу
    public static boolean checkExpression(String equation) {
        String operators = "+-*/";
        for (int i = 0; i < equation.length() - 1; i++) {
            char c1 = equation.charAt(i);
            char c2 = equation.charAt(i + 1);
            if (operators.indexOf(c1) >= 0 && operators.indexOf(c2) >= 0) {
                return false;
            }
        }
        return true;
    }
}
