package com.example.capitalauditbackend.DOA;
import java.sql.*;

public class DatabaseConnector {

    private static Connection connection;
    public void connect() {

        try {

            String url = "jdbc:sqlite:C:/Users/benan/IdeaProjects/CapitalAuditBackend/database.db";
             connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        }
        catch(SQLException e)
        {
                System.out.println(e.getMessage());
        }
    }

    public boolean ExecuteLoginQuery(String username, String password)
    {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Bind parameters
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if the result set has any rows (user found)
                return resultSet.next();
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        }
    }

    public boolean PostPaymentData(int price, String category, boolean debit_credit, boolean cleared, String date, int user_id)
    {
        String query = "INSERT INTO transactions (price, category, debit_credit, cleared, date, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Bind parameters
            statement.setInt(1, price);
            statement.setString(2, category);
            statement.setBoolean(3, debit_credit);
            statement.setBoolean(4, cleared);
            statement.setDate(5, Date.valueOf(date)); // Assuming date is in "yyyy-MM-dd" format
            statement.setInt(6, user_id);


            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkUsername(String username)
    {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Bind parameters
            statement.setString(1, username);

            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if the result set has any rows (user found)
                return resultSet.next();
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        }
    }

    public int getUserId(String username)
    {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Bind parameters
            statement.setString(1, username);

            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if the result set has any rows (user found)
                if(resultSet.next())
                {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            return 1;
        }
        return 1;
    }

    public Connection getConnection()
    {
        return DatabaseConnector.connection;
    }

    public void setConnection(Connection connection) {
        DatabaseConnector.connection = connection;
    }
}
