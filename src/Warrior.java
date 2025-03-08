/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
class Warrior extends Character {
    public Warrior(String name) {
        super(name, 400, 100, 35, 15, 3); 
        skills.put(1, new Skill("Slash", 5, 25));
        skills.put(2, new Skill("Shield Bash", 10, 35));
        skills.put(3, new Skill("Poison Strike", 15, 50));
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
            int rawDamage = 80; 
            int reducedDamage = Math.max(0, rawDamage - target.defense); 
            target.takeDamage(reducedDamage);
            lastSpecialUsed = currentTurn;
            System.out.println(this.name + " uses Berserker Rage on " + target.name + " for " + reducedDamage + " damage!");
        } catch (ManaInsufficientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void performSpecialMove(Character target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
