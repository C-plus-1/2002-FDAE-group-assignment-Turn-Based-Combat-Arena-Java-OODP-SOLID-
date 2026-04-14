package assignment.effect;

import assignment.core.Combatant;

public class DefendEffect extends StatusEffect {

    private static final int BONUS = 10;

    public DefendEffect(int duration) {
        super(duration);
    }

    @Override
    public void apply(Combatant target) {
        target.increaseDefense(BONUS);
    }

    @Override
    public void onExpire(Combatant target) {
        target.increaseDefense(-BONUS);
    }
}