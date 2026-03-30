package assignment.action;

import assignment.core.*;
import assignment.effect.*;

public class Defend implements Action {

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext context) {
        actor.addEffect(new DefendEffect(2));
        System.out.println(actor + " uses Defend (+10 DEF for 2 turns)");
    }
}
