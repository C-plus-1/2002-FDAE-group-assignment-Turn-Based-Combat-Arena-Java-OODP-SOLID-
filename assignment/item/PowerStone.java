package assignment.item;

import assignment.core.Combatant;
import assignment.action.ShieldBash;
import assignment.action.ArcaneBlast;

public class PowerStone extends UsableItem {

    public PowerStone() {
        super("Power Stone");
    }

    @Override
    public void use(Combatant target) {
        target.setCooldownBypass(true);
        // Clear the cooldown immediately so it shows 0 right away
        target.setCooldown(ShieldBash.class, 0);
        target.setCooldown(ArcaneBlast.class, 0);
        System.out.println(target.getName() + " used Power Stone! Next special skill will bypass cooldown.");
    }
}