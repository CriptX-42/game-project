package org.example.service;

import org.example.dominio.Producer;
import org.example.repository.ProducerRepository;

public class ProducerService {
    public static void save (Producer producer) {
        ProducerRepository.save(producer);
    }

    public static void delete (int id) {

        if(id <= 0) {
            throw new IllegalArgumentException("Invalid value for id");
        }
        ProducerRepository.delete(id);
    }
}
