package assignment.item;

import assignment.core.*;

public abstract class Item {

    protected boolean used = false;

    public boolean isUsed() {
        return used;
    }

    public abstract void use(Combatant actor, Combatant target, BattleContext context);
}
