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

    public boolean PostPaymentData(String jsonString)
    {

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

    public Connection getConnection()
    {
        return DatabaseConnector.connection;
    }

    public void setConnection(Connection connection) {
        DatabaseConnector.connection = connection;
    }
}
