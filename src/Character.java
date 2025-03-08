/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
import java.util.HashMap;

abstract class Character {
    protected String name;
    protected int health;
    protected int mana;
    protected int attackPower;
    protected int defense;
    protected HashMap<Integer, Skill> skills = new HashMap<>();

    protected int specialCooldown; 
    protected int lastSpecialUsed; 

    public Character(String name, int health, int mana, int attackPower, int defense, int specialCooldown) {
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.attackPower = attackPower;
        this.defense = defense;
        this.specialCooldown = specialCooldown;
        this.lastSpecialUsed = -specialCooldown;
    }

    public void specialMove(Character target, int currentTurn) throws SpecialMoveCooldownException {
        if (currentTurn - lastSpecialUsed < specialCooldown) {
            System.out.println(this.name + "'s special move is on cooldown for " +
                (specialCooldown - (currentTurn - lastSpecialUsed)) + " more turn(s).");
            return; 
        }

        performSpecialMove(target);

        lastSpecialUsed = currentTurn;
    }

    protected abstract void performSpecialMove(Character target);

    public void attack(Character target) {
        int rawDamage = this.attackPower;
        int reducedDamage = Math.max(0, rawDamage - target.defense);
        target.takeDamage(reducedDamage);
        System.out.println(this.name + " attacks " + target.name + " for " + reducedDamage + " damage!");
    }

    public void useSkill(int skillChoice, Character target) {
        Skill skill = skills.get(skillChoice);
        if (skill == null) {
            System.out.println("Invalid skill choice! Turn skipped.");
            return;
        }

        try {
            useMana(skill.manaCost);
            int rawDamage = skill.damage;
            int reducedDamage = Math.max(0, rawDamage - target.defense);
            target.takeDamage(reducedDamage);
            System.out.println(this.name + " uses " + skill.name + " on " + target.name + " for " + reducedDamage + " damage!");
        } catch (ManaInsufficientException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displaySkills() {
        skills.forEach((key, skill) -> 
            System.out.println(key + ". " + skill.name + " (-" + skill.manaCost + " Mana)")
        );
    }

    public void takeDamage(int damage) {
        int reducedAmount = Math.max(0, damage - this.defense);
        this.health = Math.max(0, this.health - reducedAmount);
        System.out.println(this.name + " takes " + reducedAmount + " damage (reduced by " + (damage - reducedAmount) + " due to defense).");
    }

    public void useMana(int amount) throws ManaInsufficientException {
        if (this.mana >= amount) {
            this.mana -= amount;
            System.out.println(this.name + " used " + amount + " mana.");
        } else {
            throw new ManaInsufficientException(this.name + " has insufficient mana!");
        }
    }

    public void defendAndRegenMana() {
        System.out.println(this.name + " prepares to defend and regenerate mana.");
        this.defense += 5;  
        this.mana = Math.min(100, this.mana + 10);  
        this.health = Math.max(0, this.health - 5); 
        System.out.println(this.name + " regains 20 Mana, loses 5 Health, and increases defense temporarily.");
    }

    public void resetDefense() {
        if (this.defense > 20) { 
            this.defense -= 5;
            System.out.println(this.name + "'s defense returns to normal.");
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

} 
