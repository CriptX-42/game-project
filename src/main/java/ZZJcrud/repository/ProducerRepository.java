package ZZJcrud.repository;

import ZZJcrud.conn.ConnectionFactory;
import ZZJcrud.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepository {

    public static List<Producer> findByName (String producerName) {
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPreparedStatementFindByName(conn,producerName);
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

    private static PreparedStatement createPreparedStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = "SELECT * FROM game_store.producer WHERE name like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.format("%%%s%%", name));
        return preparedStatement;
    }

    public static void delete (int id) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement statement = createPreparedStatementDelete(conn, id);
            statement.execute();
            log.info("Linhas do banco afetadas por essa mudança, inserido '{}'", id);
        }  catch (SQLException e) {
            log.error("Erro ao tentar excluir a produtora '{}'", id, e);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection connection, Integer id) throws SQLException {
        String sql = "DELETE FROM `game_store`.`producer` WHERE (`id` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,  id);
        return preparedStatement;
    }

    public static void savePreparedStatement (Producer producer) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPrepareStatement(conn, producer);
           preparedStatement.execute();
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a produtora '{}'", producer.getName(), e);
        }

    }

    private static PreparedStatement createPrepareStatement(Connection connection, Producer producer) throws SQLException {
        String sql = "INSERT INTO `game_store`.`producer` (`name`) VALUES (?);\n".formatted(producer.getName(), producer.getId());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, producer.getName());
        return preparedStatement;
    }
}
