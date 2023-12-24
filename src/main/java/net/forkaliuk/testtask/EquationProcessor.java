package net.forkaliuk.testtask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquationProcessor {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public EquationProcessor(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        createEquationsTable();
    }

    private void createEquationsTable() {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS equations (id INT AUTO_INCREMENT PRIMARY KEY, equation VARCHAR(255), root DOUBLE)";
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean saveEquation(String equation) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {
            String insertSQL = "INSERT INTO equations (equation) VALUES ('" + equation + "')";
            stmt.executeUpdate(insertSQL);
            System.out.println("Рівняння збережено у базі даних");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveRoot(String equation, double root) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE equations SET root = ? WHERE equation = ?")) {
            pstmt.setDouble(1, root);
            pstmt.setString(2, equation);
           pstmt.executeUpdate();
            System.out.println("Корінь збережено у базі даних");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int calculateCoefficients(String equation) {
        int coefficientCount = 0;
        String[] terms = equation.split("[\\-\\+]");
        for (String term : terms) {
            String variablePart = term.replaceAll("[^a-zA-Z]", "");
            if (variablePart.equals("x") || variablePart.isEmpty()) {
                coefficientCount++;
            }
        }

        return coefficientCount;
    }
    public List<String> findEquationsByRoot(double root) {
        List<String> equations = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT equation FROM equations WHERE root = ?")) {
            pstmt.setDouble(1, root);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                equations.add(resultSet.getString("equation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equations;
    }

    public List<String> findEquationsWithSingleRoot() {
        List<String> equations = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {
            String selectSQL = "SELECT equation FROM equations WHERE root IS NOT NULL GROUP BY equation HAVING COUNT(root) = 1";
            ResultSet resultSet = stmt.executeQuery(selectSQL);
            while (resultSet.next()) {
                equations.add(resultSet.getString("equation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equations;
    }
}