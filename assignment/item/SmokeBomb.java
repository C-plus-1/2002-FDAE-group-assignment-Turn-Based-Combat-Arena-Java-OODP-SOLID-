package assignment.item;

import assignment.core.Combatant;
import assignment.effect.StatusEffect;

public class SmokeBomb extends UsableItem {

    public SmokeBomb() {
        super("Smoke Bomb");
    }

    @Override
    public void use(Combatant target) {
        int originalAttack = target.getAttack();
        StatusEffect smokeEffect = new StatusEffect(2) {
            @Override
            public void apply(Combatant c) {
                c.setAttack(0);
            }

            @Override
            public void onExpire(Combatant c) {
                c.setAttack(originalAttack);
            }
        };
        target.addEffect(smokeEffect);
    }
}