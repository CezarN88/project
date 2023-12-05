package ro.itschool.project.exceptions.practice;

import java.sql.*;

public class DatabaseConnectionExceptionHandling {
    public static void main(String[] args) {
        String database = "jdbc:postgresql://localhost:5432/database";
        String user = "admin";
        String password = "admin";

        try (Connection connection = DriverManager.getConnection(database, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        } catch (SQLException exception) {
            System.out.println("Something went wrong.");
        }
    }
}