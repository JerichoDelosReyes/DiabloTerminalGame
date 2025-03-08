/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class ForestBeast extends Character {
    public ForestBeast(String name) {
        super(name, 350, 50, 50, 10, 3);
        skills.put(1, new Skill("Stomp", 10, 30));
        skills.put(2, new Skill("Roar", 20, 50));
    }

    @Override
    public void specialMove(Character target, int currentTurn) {
        if (currentTurn - lastSpecialUsed < specialCooldown) {
            System.out.println(this.name + "'s Roar is still on cooldown. Turn will not be counted.");
            return; 
        }

        if (this.mana <= 0) {
            System.out.println(this.name + " is out of mana! Using basic attack instead.");
            attack(target); 
            return;
        }

        target.defense -= 5; 
        lastSpecialUsed = currentTurn;
        System.out.println(this.name + " uses Roar! " + target.name + "'s defense decreases by 5 for the next turn!");
    }

    @Override
    public void useSkill(int skillChoice, Character target) {
        Skill skill = skills.get(skillChoice);
        if (skill == null) {
            System.out.println("Invalid skill choice! Turn skipped.");
            return;
        }

        if (this.mana <= 0 && skillChoice != 2) { 
            System.out.println(this.name + " is out of mana! Using basic attack instead.");
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
