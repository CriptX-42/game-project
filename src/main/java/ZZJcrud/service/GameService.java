package ZZJcrud.service;

import ZZJcrud.domain.Game;
import ZZJcrud.domain.Producer;
import ZZJcrud.repository.GameRepository;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Log4j2
public class GameService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void buildMenu(int option) {
        switch (option) {
            case 1: findByName(); break;
            case 2: delete();
            case 3: save();
            case 4: update();
            default:
                throw new IllegalArgumentException("Not a valid option");
        }
    }

    private static void findByName() {
        System.out.println("Coloque o nome ou deixe vazio para listar tudo");
        String name = SCANNER.nextLine();

        List<Game> games = GameRepository.findByName(name);
        games.forEach(p-> System.out.printf("[%d] | - %s%n", p.getId(), p.getName()));
    }

    private static void delete() {
        findByName();
        int digit = Integer.parseInt(SCANNER.nextLine());
        GameRepository.delete(digit);

    }

    private static void save() {
        System.out.println("Digite o nome do novo game:");
        String name = SCANNER.nextLine();

        System.out.println("Dite a duração do game:");
        int duration = Integer.parseInt(SCANNER.nextLine());

        System.out.println("Dite o código da produtora:");
        int producerId = Integer.parseInt(SCANNER.nextLine());


        Game build = Game.builder().name(name).duration(duration).producer(Producer.builder().id(producerId).build()).build();
        GameRepository.savePreparedStatement(build);

    }

    private static void update() {
        System.out.println("Digite o id que do que você deseja atualizar:");
        Optional<Game> gameOptional = GameRepository.findById(Integer.parseInt(SCANNER.nextLine()));

        if(gameOptional.isEmpty()){
            System.out.println("Não encontrado");
            return;
        }
        Game gameFromDb = gameOptional.get();
        System.out.println("Coloque um novo nome ou mantenha o mesmo:");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? gameFromDb.getName() : name;

        System.out.println("Coloque o numero de duração ou mantenha o mesmo:");
        int duration = Integer.parseInt(SCANNER.nextLine());

        System.out.println("Coloque o id da produtora:");
        int producerId = Integer.parseInt(SCANNER.nextLine());

        Game build = Game.builder().id(gameFromDb.getId()).duration(duration).name(name).build();

        GameRepository.updatePreparedStatement(build);


    }
}
