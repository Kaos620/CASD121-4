package lab6.rpgUI;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lab6.rpgClasses.*;
import lab6.rpgGame.GameRules;

public class GameUI extends Application {

    private Player player;
    private final Player enemy = new Player("Enemy", GameClass.BARBARIAN);
    private final GameRules rules = new GameRules();
    private boolean isPlayerTurn = true;
    private int roundCounter = 1;

    private final Label statusLabel = new Label("Welcome to the RPG Game!");
    private final TextArea battleLog = new TextArea();
    private final Button attackButton = new Button("Attack");
    private final Button restButton = new Button("Rest");

    private final Label playerLabel = new Label();
    private final Label enemyLabel = new Label();
    private final FlowPane roundTracker = new FlowPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RPG");

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

        roundTracker.setHgap(5);
        roundTracker.setPadding(new Insets(0, 10, 0, 0));
        roundTracker.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox(20, playerLabel, statusLabel, enemyLabel, roundTracker);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));

        HBox actionButtons = new HBox(10, attackButton, restButton);
        actionButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, header, battleLog, actionButtons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        attackButton.setOnAction(e -> handlePlayerAction("attack"));
        restButton.setOnAction(e -> handlePlayerAction("rest"));

        updateBattleStatus("The battle begins!");
        updateCharacterNameColors();

        return new Scene(layout, 600, 400);
    }

    private void handlePlayerAction(String action) {
        if (!isPlayerTurn || player.getCurrentHealth() <= 0 || enemy.getCurrentHealth() <= 0) return;

        if (action.equals("attack")) {
            int dmg = rules.attackTurn(player, enemy);
            updateBattleStatus("You attacked the enemy for " + dmg + " damage.");
        } else if (action.equals("rest")) {
            rules.restTurn(player);
            updateBattleStatus("You rested and regained stamina.");
        }

        isPlayerTurn = false;
        updateCharacterNameColors();
        updateBattleStatus("Enemy's turn...");
        handleEnemyTurn();
    }

    private void handleEnemyTurn() { //Enemy turn is always next to yours, most of the checks are here
        PauseTransition pause = new PauseTransition(Duration.seconds(2)); // To make it look like the machine is taking time to think
        pause.setOnFinished(e -> {
            if (enemy.getCurrentStamina() < 3) {
                rules.restTurn(enemy);
                updateBattleStatus("Enemy rests and regains stamina.");
            } else {
                int dmg = rules.attackTurn(enemy, player);
                updateBattleStatus("Enemy attacked you for " + dmg + " damage.");
            }

            updateBattleStatus("Your HP: " + player.getCurrentHealth() + " | Enemy HP: " + enemy.getCurrentHealth());
            updateBattleStatus("Your Stamina: " + player.getCurrentStamina() + " | Enemy Stamina: " + enemy.getCurrentStamina());

            roundCounter++;
            addRoundIndicator();

            isPlayerTurn = true;
            updateCharacterNameColors();

            if (player.getCurrentHealth() <= 0 || enemy.getCurrentHealth() <= 0) {
                endGame();
            }
        });
        pause.play();
    }

    private void updateCharacterNameColors() { // Green is health, Yellow is hurt and Red is critical
        Color playerColor = getHealthColor(player);
        Color enemyColor = getHealthColor(enemy);

        playerLabel.setText(player.getName());
        playerLabel.setTextFill(playerColor);

        enemyLabel.setText(enemy.getName());
        enemyLabel.setTextFill(enemyColor);
    }

    private Color getHealthColor(Player p) {
        double percent = getHealthPercentage(p);
        if (percent <= 30) return Color.RED;
        if (percent <= 60) return Color.GOLD;
        return Color.LIMEGREEN;
    }

    private double getHealthPercentage(Player p) { // To help with the update of the health indicator by hp percentage
        return ((double) p.getCurrentHealth() / p.getMaxHealth()) * 100;
    }


    private void addRoundIndicator() { //Round indicator is supposed to count the number of rounds
        Circle circle = new Circle(5, Color.GRAY);
        roundTracker.getChildren().add(circle);
    }

    private void updateBattleStatus(String message) {
        battleLog.appendText("Round " + roundCounter + ": " + message + "\n");
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
