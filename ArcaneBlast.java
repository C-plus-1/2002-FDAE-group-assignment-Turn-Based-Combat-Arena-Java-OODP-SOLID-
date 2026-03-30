package assignment.action;

import assignment.core.*;

public class ArcaneBlast extends SpecialSkill {

    @Override
    public void execute(Combatant actor, Combatant target,
                        BattleContext context, boolean bypassCooldown) {

        if (!bypassCooldown && actor.isOnCooldown(this.getClass())) {
            System.out.println("Arcane Blast is on cooldown!");
            return;
        }

        for (Combatant enemy : context.getEnemies()) {
            if (!enemy.isAlive()) continue;

            int damage = DamageCalculator.calculate(actor, enemy);
            enemy.takeDamage(damage);
            System.out.println("Arcane Blast hits " + enemy + " → " + damage + " damage");

            if (!enemy.isAlive()) {
                actor.increaseAttack(10);
                System.out.println("Enemy defeated! " + actor + " gains +10 ATK");
            }
        }

        if (!bypassCooldown) {
            actor.setCooldown(this.getClass(), cooldown);
        }
    }
}
