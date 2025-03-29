package ZZJcrud.repository;

import ZZJcrud.conn.ConnectionFactory;
import ZZJcrud.domain.Game;
import ZZJcrud.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class GameRepository {

    public static List<Game> findByName (String gameName) {
        List<Game> games = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPreparedStatementFindByName(conn,gameName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");

                Producer producer = Producer.builder().name(rs.getString("producer_name")).id(rs.getInt("producer_id")).build();

                String name = rs.getString("name");
                int duration = rs.getInt("duration");
                Game game = Game.builder().id(id).name(name).duration(duration).producer(producer).build();
                games.add(game);
            }
        }  catch (SQLException e) {
            log.error("Erro ao tentar encontrar game", e);
        }
        return games;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = """
        select game.id, game.name, game.duration, game.producer_id, producer.name as 'producer_name'\s
        from game_store.game game\s
        inner join game_store.producer producer on game.producer_id = producer.id where game.name like ?;
       \s""";
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
            log.error("Erro ao tentar excluir a game '{}'", id, e);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection connection, Integer id) throws SQLException {
        String sql = "DELETE FROM `game_store`.`game` WHERE (`id` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,  id);
        return preparedStatement;
    }

    public static void savePreparedStatement (Game game) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPrepareStatement(conn, game);
           preparedStatement.execute();
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a game '{}'", game.getName(), e);
        }

    }

    private static PreparedStatement createPrepareStatement(Connection connection, Game game) throws SQLException {
        String sql = "INSERT INTO `game_store`.`game` (`name`, `duration`, `producer_id`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, game.getName());
        preparedStatement.setInt(2, game.getDuration());
        preparedStatement.setInt(3, game.getProducer().getId());
        return preparedStatement;
    }

    public static Optional<Game> findById (Integer id) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPreparedStatementFindByName(conn,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) return Optional.empty();
            Producer producer = Producer.builder().name(rs.getString("producer_name")).id(rs.getInt("producer_id")).build();

            String name = rs.getString("name");
            int duration = rs.getInt("duration");
            Game game = Game.builder().id(id).name(name).duration(duration).producer(producer).build();
              return  Optional.of(game);
        }  catch (SQLException e) {
            log.error("Erro ao tentar encontrar game", e);
        }
        return null;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection connection, Integer id) throws SQLException {
        String sql = """
        select game.id, game.name, game.duration, game.producer_id, producer.name as 'producer_name'\s
        from game_store.game game\s
        inner join game_store.producer producer on game.producer_id = producer.id where game.id = ?;
       \s""";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,  id);
        return preparedStatement;
    }


    public static void updatePreparedStatement (Game game) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = createPreparedStatementUpdate(conn, game);
            int linesAffected = preparedStatement.executeUpdate();
            log.info("Linhas do banco afetadas por essa mudança, atualizando '{}' '{}'", linesAffected, game.getId());
        }  catch (SQLException e) {
            log.error("Erro ao tentar colocar a game '{}'", game.getName(), e);
        }

    }

    private static PreparedStatement createPreparedStatementUpdate(Connection connection, Game game) throws SQLException {
        String sql = "UPDATE `game_store`.`game` SET `name` = ?, `duration` = ? WHERE (`id` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, game.getName());
        preparedStatement.setInt(2, game.getDuration());
        preparedStatement.setInt(3, game.getId());
        return preparedStatement;
    }
}
