package com.example.expensemanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/expense";
    private static final String USER = "root";
    private static final String PASS = "MySql13th13;";

    public void createTransaction(Transaction transaction) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO `transaction` (`inout`, `cause`, `amount`, `description`, `date`, `iduser`) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getInout());
            ps.setString(2, transaction.getCause());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.setDate(5, java.sql.Date.valueOf(transaction.getDate()));
            ps.setInt(6, transaction.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM transaction order by date desc";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String inout = rs.getString("inout");
                String cause = rs.getString("cause");
                Double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                java.sql.Date date = rs.getDate("date");
                int userId = rs.getInt("iduser");

                Transaction transaction = new Transaction(id, inout, cause, amount, description, date.toLocalDate(), userId);
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return transactionList;
    }

    public static void updateTransaction(Transaction transaction) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "UPDATE transaction SET `inout` = ?, cause = ?, amount = ?, description = ?, `date` = ?, iduser = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getInout());
            ps.setString(2, transaction.getCause());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.setDate(5, java.sql.Date.valueOf(transaction.getDate()));
            ps.setInt(6, transaction.getUserId());
            ps.setInt(7, transaction.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void deleteTransaction(int transactionId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM transaction WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    public static List<ExpenseAnalyzer> analyzeExpenses(int lastMonths) {



        // SQL query to select cause and sum of amount for transactions in the last n months
        String query = "SELECT cause, SUM(amount) AS somme " +
                "FROM transaction " +
                "WHERE `inout` = 'OUT' " +
                "  AND `date` >= DATE_SUB(CURRENT_DATE(), INTERVAL ? MONTH) " +
                "GROUP BY cause " +
                "ORDER BY somme DESC";

        List<ExpenseAnalyzer> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the number of months
            preparedStatement.setInt(1, lastMonths);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the results
                while (resultSet.next()) {
                    String cause = resultSet.getString("cause");
                    double sum = resultSet.getDouble("somme");
                    // Create a new TransactionResult object and add it to the list
                    results.add(new ExpenseAnalyzer(cause, sum));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}