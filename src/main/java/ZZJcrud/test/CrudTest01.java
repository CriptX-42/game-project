package ZZJcrud.test;

import ZZJcrud.service.GameService;
import ZZJcrud.service.ProducerService;

import java.util.Scanner;

public class CrudTest01 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true){
            menu();
            int operation = Integer.parseInt(scanner.nextLine());
            if(operation == 0) break;

            switch (operation) {
                case 1 -> {
                    producerMenu();
                    int operationProducer = Integer.parseInt(scanner.nextLine());
                    ProducerService.buildMenu(operationProducer);
                }
                case 2 -> {
                    gameMenu();
                    int operationAnime = Integer.parseInt(scanner.nextLine());
                    GameService.buildMenu(operationAnime);
                }
            }
        }
    }

    private  static  void menu() {
        System.out.println("Digite o numero da operação");
        System.out.println("1. Produtora");
        System.out.println("2. Game");
    }

    private static void producerMenu() {
        System.out.println("Digite o numero da operação");
        System.out.println("1. Procurar produtora");
        System.out.println("2. Deletar produtora");
        System.out.println("3. Inserir produtora");
        System.out.println("4. Alterar produtora");
    }

    private static void gameMenu() {
        System.out.println("Digite o numero da operação");
        System.out.println("1. Procurar produtora");
        System.out.println("2. Deletar produtora");
        System.out.println("3. Inserir produtora");
        System.out.println("4. Alterar produtora");
    }
}
