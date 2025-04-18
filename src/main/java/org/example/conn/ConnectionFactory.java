package org.example.conn;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/game_store";
        String username = "root";
        String password = "root";

        Connection connection;

        connection = DriverManager.getConnection(url, username, password);
        System.out.println(connection);
        return connection;

    }

    public static JdbcRowSet getJdbcRowSet() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/game_store";
        String username = "root";
        String password = "root";

        JdbcRowSet jdbcRowSet = RowSetProvider.newFactory().createJdbcRowSet();
        jdbcRowSet.setUrl(url);
        jdbcRowSet.setUsername(username);
        jdbcRowSet.setPassword(password);
        return jdbcRowSet;

    }

    public static CachedRowSet getJdbcCachedRowSet() throws SQLException {
        return RowSetProvider.newFactory().createCachedRowSet();
    }
}

