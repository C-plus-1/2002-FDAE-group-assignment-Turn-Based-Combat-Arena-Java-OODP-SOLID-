package assignment.action;

import assignment.core.*;

public class UseSpecialSkill implements Action {

    private final SpecialSkill skill;
    private final boolean bypassCooldown;

    // Normal use — respects cooldown
    public UseSpecialSkill(SpecialSkill skill) {
        this(skill, false);
    }

    // PowerStone use — bypasses cooldown check AND does not set cooldown
    public UseSpecialSkill(SpecialSkill skill, boolean bypassCooldown) {
        this.skill = skill;
        this.bypassCooldown = bypassCooldown;
    }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext context) {
        skill.execute(actor, target, context, bypassCooldown);
    }
}
