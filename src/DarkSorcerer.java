/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class DarkSorcerer extends Character {
    public DarkSorcerer(String name) {
        super(name, 200, 100, 40, 10, 3); 
        skills.put(1, new Skill("Fireball", 20, 50)); 
        skills.put(2, new Skill("Ice Shield", 25, 40)); 
    }

    @Override
    public void specialMove(Character target, int currentTurn) {
        if (currentTurn - lastSpecialUsed < specialCooldown) {
            System.out.println(this.name + "'s Fireball is still on cooldown for " +
                (specialCooldown - (currentTurn - lastSpecialUsed)) + " turn(s)!");
            return;
        }

        if (this.mana < 50) { 
            System.out.println(this.name + " doesn't have enough mana to cast Fireball! Using basic attack instead.");
            attack(target);
            return;
        }

        try {
            useMana(50);
            int damage = 60; 
            target.takeDamage(damage);
            lastSpecialUsed = currentTurn; 
            System.out.println(this.name + " casts Fireball on " + target.name + " for " + damage + " damage!");
        } catch (ManaInsufficientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void useSkill(int skillChoice, Character target) {
        Skill skill = skills.get(skillChoice);
        if (skill == null) {
            System.out.println("Invalid skill choice! Turn skipped.");
            return;
        }

        if (skillChoice == 2) {
            this.defense += 10; 
            System.out.println(this.name + " uses Ice Shield, increasing defense by 10!");
            return;
        }

        if (this.mana < skill.manaCost) { 
            System.out.println(this.name + " doesn't have enough mana for " + skill.name + "! Using basic attack instead.");
            attack(target);
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

    @Override
    protected void performSpecialMove(Character target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
