package lab6.rpgClasses;

import lab6.rpgClasses.Character;

public enum GameClass {
    BARBARIAN(150, 10, 20),
    WARRIOR(100, 15, 15),
    ASSASSIN(80, 30, 10);

    private final int maxHealth;
    private final int maxStamina;
    private final int strength;

    GameClass(int maxHealth, int maxStamina, int strength) {
        this.maxHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.strength = strength;
    }

    public int getMaxHealth() { return maxHealth; }
    public int getMaxStamina() { return maxStamina; }
    public int getStrength() { return strength; }
}

