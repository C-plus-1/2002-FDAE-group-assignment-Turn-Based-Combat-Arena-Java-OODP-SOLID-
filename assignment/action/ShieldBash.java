package assignment.action;

import assignment.core.*;
import assignment.effect.StunEffect;

public class ShieldBash extends SpecialSkill {

    @Override
    public void execute(Combatant actor, Combatant target,
                        BattleContext context, boolean bypassCooldown) {

        if (!bypassCooldown && actor.isOnCooldown(this.getClass())) {
            System.out.println("Shield Bash is on cooldown!");
            return;
        }

        int damage = DamageCalculator.calculate(actor, target);
        target.takeDamage(damage);
        target.addEffect(new StunEffect(2));

        if (!bypassCooldown) {
            actor.setCooldown(this.getClass(), cooldown);
        }
    }
    @Override
    public String toString() { return "Shield Bash"; }
}