package assignment.action;

import assignment.core.*;

public interface Action {
    void execute (Combatant actor, Combatant target, BattleContext context);
    
}
