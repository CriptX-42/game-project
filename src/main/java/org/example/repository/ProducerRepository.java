package org.example.repository;

import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProducerRepository {

    public static void Save (Producer producer) {
        String sql = "INSERT INTO `game_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
