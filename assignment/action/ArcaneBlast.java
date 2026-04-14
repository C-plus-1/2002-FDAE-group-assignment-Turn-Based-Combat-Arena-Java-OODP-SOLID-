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

            int hpBefore = enemy.getHp();
            int damage = DamageCalculator.calculate(actor, enemy);
            enemy.takeDamage(damage);

            // Notify per enemy hit
            if (context.getEventListener() != null) {
                context.getEventListener().onActionExecuted(actor, enemy, this, damage);
            }

            if (!enemy.isAlive()) {
                actor.increaseAttack(10);
            }
        }

        if (!bypassCooldown) {
            actor.setCooldown(this.getClass(), cooldown);
        }
    }

    @Override
    public String toString() { return "Arcane Blast"; }
}
