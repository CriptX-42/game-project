package org.example;

import org.example.conn.ConnectionFactory;
import org.example.dominio.Producer;
import org.example.repository.ProducerRepository;

class App
{
    public static void main( String[] args )
    {
        Producer rockstar = Producer.builder().name("Rockstar").build();
        ProducerRepository.Save(rockstar);
    }
}
