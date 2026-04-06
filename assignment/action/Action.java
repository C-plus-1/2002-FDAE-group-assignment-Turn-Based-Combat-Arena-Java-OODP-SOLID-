// Design the action interface to support various combat moves while ensuring extensibility 
// Implement specific actions, including BasicAttack, Defend, and class-specific SpecialSkills
// Manage action logic (eg. damage calculation formulae and cooldown tracking for skills)


package assignment.action;

import assignment.core.*;

public interface Action {
    void execute (Combatant actor, Combatant target, BattleContext context);
    
}
