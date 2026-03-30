package assignment.core;

public class DamageCalculator {

    public static int calculate( Combatant attacker, Combatant target){
        return Math.max(0, attacker.getAttack() - target.getDefense());
    }
    
}

