/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class Tank extends Character {
    public Tank(String name) {
        super(name, 450, 80, 25, 30, 3); 
        skills.put(1, new Skill("Body Slam", 5, 20));
        skills.put(2, new Skill("Fortify", 10, 30));
        skills.put(3, new Skill("Crippling Blow", 15, 45));
    }

    @Override
    public void specialMove(Character target, int currentTurn) throws SpecialMoveCooldownException {
        if (currentTurn - lastSpecialUsed < specialCooldown) {
        System.out.println(this.name + "'s special move is still on cooldown.");
        throw new SpecialMoveCooldownException(this.name + "'s special move is on cooldown for " +
            (specialCooldown - (currentTurn - lastSpecialUsed)) + " more turn(s).");
    }


        try {
            useMana(30);
            int rawDamage = 70; 
            int reducedDamage = Math.max(0, rawDamage - target.defense); 
            target.takeDamage(reducedDamage);
            lastSpecialUsed = currentTurn; 
            System.out.println(this.name + " uses Earthquake on " + target.name + " for " + reducedDamage + " damage!");
        } catch (ManaInsufficientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void performSpecialMove(Character target) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
