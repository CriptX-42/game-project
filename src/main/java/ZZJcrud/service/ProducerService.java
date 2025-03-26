package ZZJcrud.service;

import ZZJcrud.domain.Producer;
import ZZJcrud.repository.ProducerRepository;
import lombok.extern.log4j.Log4j2;
import org.example.conn.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

@Log4j2
public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void buildMenu(int option) {
        switch (option) {
            case 1: findByName(); break;
            case 2: delete();
            case 3: save();
            default:
                throw new IllegalArgumentException("Not a valid option");
        }
    }

    private static void findByName() {
        System.out.println("Coloque o nome ou deixe vazio para listar tudo");
        String name = SCANNER.nextLine();

        List<Producer> producers = ProducerRepository.findByName(name);
        producers.forEach(p-> System.out.printf("[%d] | - %s%n", p.getId(), p.getName()));
    }

    private static void delete() {
        findByName();
        int digit = Integer.parseInt(SCANNER.nextLine());
        ProducerRepository.delete(digit);

    }

    private static void save() {
        System.out.println("Digite o nome da nova produtora:");
        String name = SCANNER.nextLine();
        Producer build = Producer.builder().name(name).build();
        ProducerRepository.savePreparedStatement(build);

    }
}
