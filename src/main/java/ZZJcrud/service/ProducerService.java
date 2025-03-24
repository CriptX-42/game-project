package ZZJcrud.service;

import ZZJcrud.domain.Producer;
import ZZJcrud.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;

public class ProducerService {
    private static Scanner scanner = new Scanner(System.in);

    public static void buildMenu(int option) {
        switch (option) {
            case 1: findByName(); break;
            default:
                throw new IllegalArgumentException("Not a valid option");
        }
    }

    private static void findByName() {
        System.out.println("Coloque o nome ou deixe vazio para listar tudo");
        String name = scanner.nextLine();

        List<Producer> producers = ProducerRepository.findByName(name);
        for (int i = 0; i < producers.size() ; i++) {
            System.out.printf("[%d] - %s%n", i, producers.get(i).getName());
        }

    }
}
