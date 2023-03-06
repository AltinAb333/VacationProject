package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private Connection con = null;

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vacation_db", "root", "altin1234");
            return con;
        } catch (Exception e) {
            throw e;
        }


    }
}
