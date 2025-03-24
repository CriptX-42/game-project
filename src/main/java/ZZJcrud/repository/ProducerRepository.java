package ZZJcrud.repository;

import ZZJcrud.conn.ConnectionFactory;
import ZZJcrud.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
