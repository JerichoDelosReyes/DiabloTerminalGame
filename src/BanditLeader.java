/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class BanditLeader extends Character {
    public BanditLeader(String name) {
        super(name, 250, 60, 60, 15, 3); 
        skills.put(1, new Skill("Cleave", 10, 20)); 
        skills.put(2, new Skill("Berserk", 30, 60)); 
    }

    @Override
    public void specialMove(Character target, int currentTurn) {
        if (this.mana <= 0) {
            System.out.println(this.name + " is out of mana! Using basic attack.");
            attack(target);
            return;
        }

        if (currentTurn - lastSpecialUsed < specialCooldown) {
            System.out.println(this.name + "'s Berserk is still on cooldown for " +
                (specialCooldown - (currentTurn - lastSpecialUsed)) + " turn(s)!");
            attack(target);
            return;
        }

        int damage = this.attackPower * 2; 
        target.takeDamage(damage);
        lastSpecialUsed = currentTurn;
        System.out.println(this.name + " uses Berserk on " + target.name + " for " + damage + " damage!");
    }

    @Override
    public void useSkill(int skillChoice, Character target) {
        Skill skill = skills.get(skillChoice);
        if (skill == null) {
            System.out.println("Invalid skill choice! Turn skipped.");
            return;
        }

        if (this.mana <= 0 && skillChoice != 1) { 
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
    