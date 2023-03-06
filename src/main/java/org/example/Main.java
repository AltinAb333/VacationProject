package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/vacation_db";
        String username = "root";
        String password = "altin1234";

        // Load the JDBC driver
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load driver class: " + e);
        }

        // Connect to the database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database: " + e);
        }

        // Create a statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Unable to create statement: " + e);
        }

        // Add a new row to the table
        String sql = "INSERT INTO department (departmentId,departmentName) VALUES (1,'Pos')";
        try {
            int rowsAffected = statement.executeUpdate(sql);
            System.out.println(rowsAffected + " rows added to the table.");
        } catch (SQLException e) {
            System.out.println("Error executing INSERT statement: " + e);
        }


        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Unable to close connection: " + e);
        }
    }
}

