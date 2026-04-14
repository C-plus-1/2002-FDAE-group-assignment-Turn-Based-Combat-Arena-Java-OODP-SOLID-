package assignment.core;

public class DamageCalculator {

    public static int calculate(Combatant attacker, Combatant target) {
        return Math.max(0, attacker.getAttack() - target.getDefense());
    }

    public static String getFormula(Combatant attacker, Combatant target) {
        return attacker.getAttack() + "−" + target.getDefense() + "=" + calculate(attacker, target);
    }
}
