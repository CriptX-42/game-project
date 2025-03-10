package org.example.service;

import org.example.dominio.Producer;
import org.example.repository.ProducerRepository;

import java.util.List;

public class ProducerService {
    public static void save (Producer producer) {
        ProducerRepository.save(producer);
    }

    public static void update (Producer producer) {
        requireValidId(producer.getId());
        ProducerRepository.update(producer);
    }

    public static List<Producer> listProducer () {
        return ProducerRepository.findAll();
    }

    public static List<Producer> findByName (String name) {
        return ProducerRepository.findByName(name);
    }

    public static void delete (Integer id) {
        requireValidId(id);
        ProducerRepository.delete(id);
    }

    private static void requireValidId(Integer id) {
        if (id == null && id <= 0) {
            throw new IllegalArgumentException("Invalid value for id");
        }
    }
}
