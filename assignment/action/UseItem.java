package assignment.action;

import assignment.core.*;
import assignment.item.Item;

public class UseItem implements Action {

    private final Item item;

    public UseItem(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext context) {
        if (item == null || item.isUsed()) {
            System.out.println("No item available to use!");
            return;
        }
        item.use(actor, target, context);
    }
}
