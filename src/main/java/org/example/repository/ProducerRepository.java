package org.example.repository;

import lombok.extern.log4j.Log4j2;
import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepository {
    public static void save (Producer producer) {
        String sql = "INSERT INTO `game_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            int linesAffected = statement.executeUpdate(sql);
            log.info("Linhas do banco afetadas por essa mudança, inserido '{}' '{}'", linesAffected, producer.getName());
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a produtora '{}'", producer.getName(), e);
        }
    }

    public static void update (Producer producer) {
        String sql = "UPDATE `game_store`.`producer` SET `name` = '%s' WHERE (`id` = '%d');\n".formatted(producer.getName(), producer.getId());
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            int linesAffected = statement.executeUpdate(sql);
            log.info("Linhas do banco afetadas por essa mudança, atualizando '{}' '{}'", linesAffected, producer.getId());
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a produtora '{}'", producer.getName(), e);
        }
    }

    public static List<Producer> findAll () {
        String sql = "SELECT id, name FROM game_store.producer;";
        log.info("Encontrado todos os produtores '{}' '{}'");
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Producer producer = Producer.builder().id(id).name(name).build();
                producers.add(producer);
            }
        }  catch (SQLException e) {
            log.error("Erro ao tentar encontrar produtora", e);
        }
        return producers;
    }

    public static void delete (int id) {
        String sql = "DELETE FROM `game_store`.`producer` WHERE (`id` = ('%d'));".formatted(id);
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            int linesAffected = statement.executeUpdate(sql);
            log.info("Linhas do banco afetadas por essa mudança, inserido '{}' '{}'", linesAffected, id);
        }  catch (SQLException e) {
            log.error("Erro ao tentar excluir a produtora '{}'", id, e);
        }
    }
}
