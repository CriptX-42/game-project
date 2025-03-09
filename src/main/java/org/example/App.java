package org.example;

import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;
import org.example.repository.ProducerRepository;
import org.example.service.ProducerService;

class App
{
    public static void main( String[] args )
    {
        Producer rockstar = Producer.builder().name("Rockstar").build();
        Producer producerToUpdate = Producer.builder().id(1).name("EA Games").build();
//        ProducerService.save(rockstar);
//        ProducerService.delete(5);
        ProducerService.update(producerToUpdate);
    }
}
