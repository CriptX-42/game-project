package org.example.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/game_store";
        String username = "root";
        String passworld = "root";

        Connection connection;

        connection = DriverManager.getConnection(url, username, passworld);
        System.out.println(connection);
        return connection;

    }
}
