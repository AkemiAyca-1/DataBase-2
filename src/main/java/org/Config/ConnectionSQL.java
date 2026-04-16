package org.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {

    private Connection connection;

    public ConnectionSQL() {
        try {
            System.out.println("Connecting to the DataBase.");
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ToDoListEnglish",
                    "root",
                    "Hola123!");

            System.out.println("Connection Successful!.");
        } catch (SQLException sqlException) {
            System.err.println("Error SQL: " + sqlException.getMessage());
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Driver not found: " + classNotFoundException.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
