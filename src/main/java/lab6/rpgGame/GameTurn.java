package lab6.rpgGame;

import java.util.Scanner;
import lab6.rpgClasses.*;

public class GameTurn {

    private final GameRules rules = new GameRules();
    private final Player enemy = new Player("Example", GameClass.BARBARIAN);
    private boolean isPlayerTurn = true;
    private final Scanner sc = new Scanner(System.in);

    public void startBattle(Player player) {
        System.out.println("The battle begins!");


        while (player.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0) {
            if (isPlayerTurn) {
                System.out.println("\nYour turn! Choose an action: [1] Attack  [2] Rest");
                String input = sc.nextLine();

                if (input.equals("1")) {
                    double dmg = rules.attackTurn(player, enemy);
                    System.out.println("You dealt " + dmg + " damage to the enemy!");
                } else if (input.equals("2")) {
                    rules.restTurn(player);
                    System.out.println("You rested and regained stamina.");
                } else {
                    System.out.println("Invalid choice. Try Again!.");
                    continue;

                }

            } else {
                if (enemy.getCurrentStamina() < enemy.getStrength()) {
                    rules.restTurn(enemy);
                    System.out.println("\nEnemy rests and regains stamina.");
                } else {
                    double dmg = rules.attackTurn(enemy, player);
                    System.out.println("\nEnemy attacks you and deals " + dmg + " damage!");
                }
            }

            isPlayerTurn = !isPlayerTurn;

            // Show current health
            System.out.printf("Your HP: %.1f | Enemy HP: %.1f%n", player.getCurrentHealth(), enemy.getCurrentHealth());
        }

        System.out.println(player.getCurrentHealth() > 0 ? "🎉 You win!" : "💀 You lost!");
    }
}
