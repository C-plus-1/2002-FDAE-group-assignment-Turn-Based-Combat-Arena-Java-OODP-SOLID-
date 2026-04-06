package assignment.action;

import assignment.core.*;

public class UseSpecialSkill implements Action {

    private final SpecialSkill skill;
    private final boolean bypassCooldown;

    public SpecialSkill getSkill() { return skill; }

    public UseSpecialSkill(SpecialSkill skill) {
        this(skill, false);
    }

    public UseSpecialSkill(SpecialSkill skill, boolean bypassCooldown) {
        this.skill = skill;
        this.bypassCooldown = bypassCooldown;
    }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext context) {
        skill.execute(actor, target, context, bypassCooldown);
    }

    @Override
    public String toString() { return skill.getClass().getSimpleName(); }
}
