package assignment.effect;

import assignment.core.Combatant;

public class DefendEffect extends StatusEffect {

    private static final int BONUS = 10;

    public DefendEffect(int duration) {
        super(duration);
    }

    @Override
    public void apply(Combatant target) {
        // Called once on the turn it is added — grant the bonus immediately
        target.increaseDefense(BONUS);
    }

    @Override
    public void onExpire(Combatant target) {
        // Called by StatusEffect when duration hits 0 — take the bonus back
        target.increaseDefense(-BONUS);
    }
}