package test;

import lombok.extern.log4j.Log4j2;
import org.example.dominio.Producer;
import org.example.service.ProducerRowSetService;

import java.util.List;

@Log4j2
public class ConnectionFactoryTest02 {
    public static void main(String[] args) {
        List<Producer> rockstar = ProducerRowSetService.findByNameJdbcRowSet("ROCKSTAR");
        log.info(rockstar);
    }
}
