/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class Duelist extends Character {
    public Duelist(String name) {
        super(name, 375, 120, 40, 10, 3); 
        skills.put(1, new Skill("Quick Strike", 5, 25));
        skills.put(2, new Skill("Flurry", 10, 50));
        skills.put(3, new Skill("Paralyzing Strike", 15, 60));
    }

    @Override
    public void specialMove(Character target, int currentTurn) throws SpecialMoveCooldownException {
        if (currentTurn - lastSpecialUsed < specialCooldown) {
            System.out.println(this.name + "'s special move is still on cooldown.");
            throw new SpecialMoveCooldownException(this.name + "'s special move is on cooldown for " +
                (specialCooldown - (currentTurn - lastSpecialUsed)) + " more turn(s).");
        }


        try {
            useMana(20);
            int rawDamage = 85; 
            int reducedDamage = Math.max(0, rawDamage - target.defense); 
            target.takeDamage(reducedDamage);
            lastSpecialUsed = currentTurn;
            System.out.println(this.name + " uses Fatal Blow on " + target.name + " for " + reducedDamage + " damage!");
        } catch (ManaInsufficientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void performSpecialMove(Character target) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
