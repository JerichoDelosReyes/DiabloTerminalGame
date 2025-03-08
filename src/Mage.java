/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class Mage extends Character {
    public Mage(String name) {
        super(name, 350, 180, 20, 5, 4); 
        skills.put(1, new Skill("Firebolt", 5, 40));
        skills.put(2, new Skill("Ice Spike", 10, 50));
        skills.put(3, new Skill("Blind", 15, 60));
    }

    @Override
    public void specialMove(Character target, int currentTurn) throws SpecialMoveCooldownException {
        if (currentTurn - lastSpecialUsed < specialCooldown) {
            throw new SpecialMoveCooldownException(this.name + "'s special move is on cooldown for " +
                (specialCooldown - (currentTurn - lastSpecialUsed)) + " more turn(s).");
        }

        try {
            useMana(50);
            int rawDamage = 105; 
            int reducedDamage = Math.max(0, rawDamage - target.defense);
            target.takeDamage(reducedDamage);
            lastSpecialUsed = currentTurn; 
            System.out.println(this.name + " uses Meteor on " + target.name + " for " + reducedDamage + " damage!");
        } catch (ManaInsufficientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void performSpecialMove(Character target) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
