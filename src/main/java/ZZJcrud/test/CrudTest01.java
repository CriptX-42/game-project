package ZZJcrud.test;

import ZZJcrud.service.ProducerService;

import java.util.Scanner;

public class CrudTest01 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true){
            producerMenu();
            int operation = Integer.parseInt(scanner.nextLine());
            ProducerService.buildMenu(operation);
            if(operation == 0) break;
        }
    }

    private static void producerMenu() {
        System.out.println("Digite o numero da operação");
        System.out.println("1. Procurar produtora");
        System.out.println("2. Deletar produtora");
        System.out.println("3. Inserir produtora");
    }
}
