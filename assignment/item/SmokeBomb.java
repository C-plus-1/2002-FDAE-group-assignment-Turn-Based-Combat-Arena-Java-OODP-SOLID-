package assignment.item;

import assignment.core.Combatant;
import assignment.effect.StatusEffect;

/**
 * SmokeBomb: Enemy attacks do 0 damage for current and next turn.
 */
public class SmokeBomb extends UsableItem {

    public SmokeBomb() {
        super("Smoke Bomb");
    }

    @Override
    public void use(Combatant target) {

        // Create a simple status effect lasting 2 turns
        StatusEffect smokeEffect = new StatusEffect(2) {
            @Override
            public void apply(Combatant c) {
                // Set attack to 0 for duration
                c.setAttack(0); // Temporary debuff
            }

            @Override
            public void onExpire(Combatant c) {
                // Restore attack after effect expires (or recalc if needed)
                // Could be implemented as needed in Combatant
            }
        };

        target.addEffect(smokeEffect);
    }
}
