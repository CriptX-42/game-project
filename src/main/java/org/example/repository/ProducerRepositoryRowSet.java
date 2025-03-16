package org.example.repository;

import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;
import org.example.listener.CustomRowSetListener;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducerRepositoryRowSet {
    public static List<Producer> findByNameJdbcRowSet(String name) {
        String sql = "SELECT * FROM game_store.producer WHERE name like ?";
        List<Producer> producers = new ArrayList<>();
        try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setString(1, String.format("%%%s%%", name));
            jrs.execute();

            while (jrs.next()){
                Producer build = Producer.builder()
                        .id(jrs.getInt("id"))
                        .name(jrs.getString("name"))
                        .build();
                producers.add(build);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static void updateJdbcRowSet(Producer producer) {
        String sql = "SELECT * FROM game_store.producer WHERE (`id` = ?);";
        try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setInt(1, producer.getId());
            jrs.execute();
            if (!jrs.next()) return;
            jrs.updateString("name", producer.getName());
            jrs.updateRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateJdbcCachedRowSet(Producer producer) {
        String sql = "SELECT * FROM producer WHERE (`id` = ?);";
        try(CachedRowSet cachedRowSet = ConnectionFactory.getJdbcCachedRowSet();
            Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            cachedRowSet.setCommand(sql);
            cachedRowSet.setInt(1, producer.getId());
            cachedRowSet.execute(connection);
            if (!cachedRowSet.next()) return;
            cachedRowSet.updateString("name", producer.getName());
            cachedRowSet.updateRow();
            cachedRowSet.acceptChanges();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
