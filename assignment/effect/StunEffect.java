package assignment.effect;

import assignment.core.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);   
    }

    @Override
    public void apply(Combatant target) {
        target.setStunned(true);
        System.out.println(target + " is stunned and cannot act!");
    }

    @Override
    public void onExpire(Combatant target) {
        target.setStunned(false);
    }
}