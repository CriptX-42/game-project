package org.example.repository;

import lombok.extern.log4j.Log4j2;
import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class ProducerRepository {
    public static void Save (Producer producer) {
        String sql = "INSERT INTO `game_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            int linesAffected = statement.executeUpdate(sql);
            log.info("Linhas do banco afetadas por essa mudança {}", linesAffected);
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
