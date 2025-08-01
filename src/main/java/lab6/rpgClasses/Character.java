package lab6.rpgClasses;

public abstract class Character {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int maxStamina;
    private int currentStamina;
    private int strength;
    private GameClass gameClass;

    public void setName(String name) { this.name = name; }
    public void setStats(GameClass gameClass) {
        this.gameClass = gameClass;
        this.maxHealth = gameClass.getMaxHealth();
        this.currentHealth = gameClass.getMaxHealth();
        this.maxStamina = gameClass.getMaxStamina();
        this.currentStamina = gameClass.getMaxStamina();
        this.strength = gameClass.getStrength();
    }

    public int getStrength() { return strength; }
    public int getMaxHealth() { return maxHealth; }
    public int getMaxStamina() { return maxStamina; }
    public String getName() { return name; }
    public GameClass getGameClass() { return gameClass; }

    public int getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentStamina() { return currentStamina; }
    public void setCurrentStamina(int currentStamina) {
        this.currentStamina = currentStamina;
    }
}
