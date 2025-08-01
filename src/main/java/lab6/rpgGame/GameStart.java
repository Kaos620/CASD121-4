package lab6.rpgGame;

import lab6.rpgClasses.GameClass;
import lab6.rpgClasses.Player;
import java.util.Scanner;


public class GameStart {
    public void gameStart() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter character name: ");
        String name = scanner.nextLine();

        System.out.print("Choose class (Barbarian/Warrior/Assassin): ");
        String chosen = scanner.nextLine().toLowerCase();

        GameClass selectedClass;
        switch (chosen) {
            case "1", "barbarian" -> selectedClass = GameClass.BARBARIAN;
            case "2", "warrior" -> selectedClass = GameClass.WARRIOR;
            case "3", "assassin" -> selectedClass = GameClass.ASSASSIN;
            default -> {
                System.out.println("Invalid class selected. Defaulting to Warrior.");
                selectedClass = GameClass.WARRIOR;
            }
        }

        Player player = new Player(name, selectedClass);
        System.out.printf("Character created: %s the %s%n", player.getName(), player.getGameClass());
    }
}
