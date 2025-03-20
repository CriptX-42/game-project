package test;

import lombok.extern.log4j.Log4j2;
import org.example.dominio.Producer;
import org.example.service.ProducerRowSetService;
import org.example.service.ProducerService;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ConnectionFactoryTest02 {
    public static void main(String[] args) {
//        Producer microsoft = Producer.builder().id(1).name("Microsoft").build();
//        ProducerRowSetService.updateJdbcRowSet(microsoft);
//        log.info("============================================");
//        List<Producer> rockstar = ProducerRowSetService.findByNameJdbcRowSet("");
//        log.info(rockstar);


        Producer ea = Producer.builder().name("EA").build();
        Producer kojima = Producer.builder().name("Kojima").build();
        Producer rockstarNorth = Producer.builder().name("Rockstar North").build();

        ProducerService.saveTransaction(List.of(ea, kojima, rockstarNorth));
    }
}
