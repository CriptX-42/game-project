package org.example.service;

import org.example.dominio.Producer;
import org.example.repository.ProducerRepositoryRowSet;

import java.util.List;

public class ProducerRowSetService {
    public static List<Producer> findByNameJdbcRowSet(String name) {
        return ProducerRepositoryRowSet.findByNameJdbcRowSet(name);
    }

    public static void updateJdbcRowSet(Producer producer) {
        ProducerRepositoryRowSet.updateJdbcRowSet(producer);
    }
}
