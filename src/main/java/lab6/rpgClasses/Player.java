package lab6.rpgClasses;

import java.util.Scanner;
import java.io.IOException;
import lab6.rpgClasses.Player;

public class Player extends Character {

    public Player(String name, GameClass gameClass) {
        setName(name);
        setStats(gameClass);
    }
}

