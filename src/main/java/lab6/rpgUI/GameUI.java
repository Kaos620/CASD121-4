package lab6.rpgUI;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import lab6.rpgClasses.*;
import lab6.rpgGame.GameRules;

public class GameUI extends Application {

    private Player player;
    private final Player enemy = new Player("Enemy", GameClass.BARBARIAN);
    private final GameRules rules = new GameRules();
    private boolean isPlayerTurn = true;

    private final Label statusLabel = new Label("Welcome to the RPG Game!");
    private final TextArea battleLog = new TextArea();
    private final Button attackButton = new Button("Attack");
    private final Button restButton = new Button("Rest");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RPG battle simulation");

        Scene menuScene = createMenuScene(primaryStage);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private Scene createMenuScene(Stage stage) {
        Label nameLabel = new Label("Enter your character name:");
        TextField nameField = new TextField();

        Label classLabel = new Label("Choose a class:");
        ComboBox<String> classBox = new ComboBox<>();
        classBox.getItems().addAll("Barbarian", "Warrior", "Assassin");
        classBox.getSelectionModel().selectFirst();

        Button startButton = new Button("Start Game");

        VBox menuLayout = new VBox(10, nameLabel, nameField, classLabel, classBox, startButton);
        menuLayout.setAlignment(Pos.CENTER);

        startButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String chosenClass = classBox.getValue().toLowerCase();

            GameClass selectedClass = switch (chosenClass) {
                case "barbarian" -> GameClass.BARBARIAN;
                case "assassin" -> GameClass.ASSASSIN;
                default -> GameClass.WARRIOR;
            };

            player = new Player(name, selectedClass);
            stage.setScene(createBattleScene(stage));
        });

        return new Scene(menuLayout, 400, 300);
    }

    private Scene createBattleScene(Stage stage) {
        battleLog.setEditable(false);
        battleLog.setPrefHeight(200);

        HBox actionButtons = new HBox(10, attackButton, restButton);
        actionButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, statusLabel, battleLog, actionButtons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(10));

        attackButton.setOnAction(e -> handlePlayerAction("attack"));
        restButton.setOnAction(e -> handlePlayerAction("rest"));

        updateBattleStatus("The battle begins!");

        return new Scene(layout, 500, 350);
    }

    private void handlePlayerAction(String action) {
        if (!isPlayerTurn || player.getCurrentHealth() <= 0 || enemy.getCurrentHealth() <= 0) return;

        if (action.equals("attack")) {
            int dmg = rules.attackTurn(player, enemy);
            updateBattleStatus("You attacked the enemy for " + dmg + " damage.");
            updateBattleStatus("The enemy's current health is " + enemy.getCurrentHealth() + "!");
        } else if (action.equals("rest")) {
            rules.restTurn(player);
            updateBattleStatus("You rested and regained stamina.");
        }

        isPlayerTurn = false;
        updateBattleStatus("Enemy's turn...");
        handleEnemyTurn();
    }

    private void handleEnemyTurn() {

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(1));
        pause.setOnFinished(e -> {
            if (enemy.getCurrentStamina() < 10) {
                rules.restTurn(enemy);
                updateBattleStatus("Enemy rests and regains stamina.");
                updateBattleStatus("Current health: " + enemy.getCurrentHealth()+ " Current stamina: " + enemy.getCurrentStamina() );
            } else {
                int dmg = rules.attackTurn(enemy, player);
                updateBattleStatus("Enemy attacked you for " + dmg + " damage.");
            }

            updateBattleStatus("Your HP: " + player.getCurrentHealth() + "Enemy HP: "+ enemy.getCurrentHealth());
            isPlayerTurn = true;

            if (player.getCurrentHealth() <= 0 || enemy.getCurrentHealth() <= 0) {
                endGame();
            }

        });
        pause.play();
    }

    private void updateBattleStatus(String message) {
        battleLog.appendText(message + "\n");
    }

    private void endGame() {
        attackButton.setDisable(true);
        restButton.setDisable(true);
        String result = player.getCurrentHealth() > 0 ? "You win!" : "You lost!";
        updateBattleStatus(result);
        statusLabel.setText("Game Over");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
