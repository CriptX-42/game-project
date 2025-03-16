package org.example;

import lombok.extern.log4j.Log4j2;
import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;
import org.example.repository.ProducerRepository;
import org.example.service.ProducerService;

import java.util.List;

@Log4j2
class App
{
    public static void main( String[] args )
    {
        Producer rockstar = Producer.builder().name("Rockstar").build();
        Producer producerToUpdate = Producer.builder().id(1).name("EA Games").build();
//
//        ProducerService.save(rockstar);
//
//        ProducerService.delete(5);
//
//        ProducerService.update(producerToUpdate);
//
//        List<Producer> producers = ProducerService.listProducer();
//        log.info(producers);

//        List<Producer> producers = ProducerService.findByName("Rockstar");
        // log.info(producers);

//        ProducerService.showProducerMetaData();

//        ProducerService.showDriverMetadata();

//        List<Producer> rockstar1 = ProducerService.findByNameAndUpdateToUpperCase("Rockstar");
//
//         log.info("Produtoras encontradas '{}'", rockstar1);

//        List<Producer> nintendo = ProducerService.findByNameAndInsertWhenNotFound("Nintendo");
//
//        log.info("Produtoras encontradas '{}'", nintendo);

        Producer producerToUpdate2 = Producer.builder().id(2).name("EA Games").build();
        ProducerService.updatePreparedStatement(producerToUpdate2);

    }
}
