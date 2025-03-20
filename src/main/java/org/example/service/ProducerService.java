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

    public static void saveTransaction (List<Producer> producers) {
        ProducerRepository.saveTransaction(producers);
    }

    public static List<Producer> findByName (String name) {
        return ProducerRepository.findByName(name);
    }

    public static void showProducerMetaData () {
        ProducerRepository.showProducerMetadata();
    }

    public static void showDriverMetadata () {
        ProducerRepository.showDriverMetadata();
    }

    public static void showTypeScrollWorking () {
        ProducerRepository.showTypeScrollWorking();
    }

    public static List<Producer> findByNameAndUpdateToUpperCase (String name) {
       return ProducerRepository.findByNameAndUpdateToUpperCase(name);
    }

    public static List<Producer> findByNameAndInsertWhenNotFound (String name) {
        return ProducerRepository.findByNameAndInsertWhenNotFound(name);
    }

    public static void updatePreparedStatement (Producer producer) {
        ProducerRepository.updatePreparedStatement(producer);
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
