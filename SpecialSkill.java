package assignment.action;

import assignment.core.*;

public abstract class SpecialSkill implements Action {

    protected int cooldown = 3;

    public int getCooldown() { return cooldown; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext context) {
        execute(actor, target, context, false);
    }

    public abstract void execute(Combatant actor, Combatant target,
                                  BattleContext context, boolean bypassCooldown);
}