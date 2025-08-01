package lab6.rpgGame;

import lab6.rpgClasses.Character;
import java.util.Random;

public class GameRules {
    Random rand = new Random();

    /**
     * Performs an attack from attacker to defender.
     * Damage = attacker's strength
     * If attacker has enough stamina, deal damage and reduce stamina.
     * Otherwise, return 0 (miss/failure)
     */

    public int attackTurn(Character attacker, Character defender) {
        int requiredStamina = 3;
        if (attacker.getCurrentStamina() >= requiredStamina) {
            int damage = rand.nextInt(0 , attacker.getStrength());
            if (damage == attacker.getStrength()) {
                damage = attacker.getStrength() * 2;
                System.out.println("A crit has been hit! Double damage: " + damage);
                return damage;
            }else if (damage == 0){
                attacker.setCurrentStamina(attacker.getCurrentStamina() - requiredStamina);
                System.out.println("A critical failure has been hit! Lost double stamina: " + attacker.getCurrentStamina());
                return damage;
            }

            int newHealth = defender.getCurrentHealth() - damage;
            defender.setCurrentHealth(Math.max(0, newHealth)); // Prevents negative health

            attacker.setCurrentStamina(attacker.getCurrentStamina() - requiredStamina);

            return damage;
        } else {
            System.out.println(attacker.getName() + " is too tired to attack!");
            return requiredStamina;
        }
    }

    /**
     * Restores some stamina and maybe a bit of health to the attacker.
     */
    public void restTurn(Character character) {
        int staminaRestored = rand.nextInt(2, character.getCurrentStamina() * 2);
        int healthRestored = rand.nextInt(0, 11);

        int newStamina = Math.min(character.getMaxStamina(), character.getCurrentStamina() + staminaRestored);
        character.setCurrentStamina(newStamina);

        int newHealth = Math.min(character.getMaxHealth(), character.getCurrentHealth() + healthRestored);
        character.setCurrentHealth(newHealth);

    }
}

