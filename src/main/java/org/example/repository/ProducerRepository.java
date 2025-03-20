package org.example.repository;

import lombok.extern.log4j.Log4j2;
import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;

import java.sql.*;
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

    public static void saveTransaction (List<Producer> producer) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            updatePreparedStatementSaveTransaction(conn, producer);
            conn.commit();
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a produtora '{}'", e);
        }
    }

    private static void updatePreparedStatementSaveTransaction(Connection connection, List<Producer> producers) throws SQLException {
        String sql = "INSERT INTO `game_store`.`producer` (`name`) VALUES ( ? );";
        boolean shouldRollback = false;
        for (Producer producer: producers) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, producer.getName());
                ps.execute();
            }catch (SQLException e) {
                e.printStackTrace();
                shouldRollback = true;
            }
        }
        if(shouldRollback) {
            log.error("Transaction sofreu um rollback");
            connection.rollback();
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

    public static List<Producer> findByName (String producerName) {
        String sql = "SELECT * FROM game_store.producer WHERE name like '%s';".formatted("%" + producerName + "%");
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

    public static void showProducerMetadata () {
        String sql = "SELECT * FROM game_store.producer;";
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount ; i++) {

                log.info("Table name '{}'", metaData.getTableName(i));
                log.info("Column name '{}'", metaData.getColumnName(i));
                log.info("Column Size '{}'", metaData.getColumnDisplaySize(i));
                log.info("Column Type '{}'", metaData.getColumnType(i));
            }
        }  catch (SQLException e) {
            log.error("Erro ao tentar mostrar o metadata", e);
        }
    }

    public static void showDriverMetadata () {
        String sql = "SELECT * FROM game_store.producer;";
        try(Connection conn = ConnectionFactory.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            if(metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
                log.info("Supports TYPE_FORWARD_ONLY");
                if(metaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("Supports TYPE_FORWARD_ONLY && CONCUR_UPDATABLE");
                }
            }

            if(metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
                log.info("Supports TYPE_SCROLL_INSENSITIVE");
                if(metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("Supports TYPE_SCROLL_INSENSITIVE && CONCUR_UPDATABLE");
                }
            }

            if(metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
                log.info("Supports TYPE_SCROLL_SENSITIVE");
                if(metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("Supports TYPE_SCROLL_SENSITIVE && CONCUR_UPDATABLE");
                }
            }
        }  catch (SQLException e) {
            log.error("Erro ao tentar mostrar o metadata", e);
        }
    }

    public static void showTypeScrollWorking () {
        String sql = "SELECT * FROM game_store.producer;";
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql);

            log.info("É a ultima linha? {}", rs.last());
            log.info("Número da linha? {}", rs.getRow());
            log.info( Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());

            log.info("É a ultima linha? {}", rs.absolute(2));
            log.info("Número da linha? {}", rs.getRow());
            log.info( Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
        }  catch (SQLException e) {
            log.error("Erro ao tentar encontrar produtora", e);
        }
    }

    public static List<Producer> findByNameAndUpdateToUpperCase (String producerName) {
        String sql = "SELECT * FROM game_store.producer WHERE name like '%s';".formatted("%" + producerName + "%");
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                rs.updateString("name", rs.getString("name").toUpperCase());
                rs.updateRow();
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

    public static List<Producer> findByNameAndInsertWhenNotFound (String producerName) {
        String sql = "SELECT * FROM game_store.producer WHERE name like '%s';".formatted("%" + producerName + "%");
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql);

            if(!rs.next()) {
                rs.moveToInsertRow();
                rs.updateString("name", producerName);
                rs.insertRow();
                rs.next();
                rs.updateString("name", rs.getString("name").toUpperCase());
                rs.updateRow();
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

    public static List<Producer> findByNameAndPreparedStatement (String producerName) {
        String sql = "SELECT * FROM game_store.producer WHERE name like ?";
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPreparedStatement(conn,producerName);
            ResultSet rs = preparedStatement.executeQuery();
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

    public static void updatePreparedStatement (Producer producer) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = updatePreparedStatementQuery(conn, producer);
            int linesAffected = preparedStatement.executeUpdate();
            log.info("Linhas do banco afetadas por essa mudança, atualizando '{}' '{}'", linesAffected, producer.getId());
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a produtora '{}'", producer.getName(), e);
        }

    }

    public static List<Producer> findByNameAndPreparedCalleble (String producerName) {
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = callablePreparedStatementFindByName(conn, producerName);
            ResultSet rs = preparedStatement.executeQuery();
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

    private static CallableStatement callablePreparedStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = "CALL `game_store`.`sp_get_procedure_by_name`(?);";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, String.format("%" +  name + "%"));
        return callableStatement;
    }

    private static PreparedStatement updatePreparedStatementQuery(Connection connection, Producer producer) throws SQLException {
        String sql = "UPDATE `game_store`.`producer` SET `name` = ? WHERE (`id` = ?);\n".formatted(producer.getName(), producer.getId());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, producer.getName());
        preparedStatement.setString(2, producer.getId().toString());
        return preparedStatement;
    }

    private static PreparedStatement createPreparedStatement(Connection connection, String name) throws SQLException {
        String sql = "SELECT * FROM game_store.producer WHERE name like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.format("%" +  name + "%"));
        return preparedStatement;
    }
}
