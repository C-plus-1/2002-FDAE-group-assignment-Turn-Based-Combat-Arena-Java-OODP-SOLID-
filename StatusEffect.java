package assignment.effect;

import assignment.core.Combatant;

public abstract class StatusEffect {

    protected int duration;

    public StatusEffect(int duration) {
        this.duration = duration;
    }

    public abstract void apply(Combatant target);

    public void onExpire(Combatant target) { }

    public void reduceDuration() {
        duration--;
    }

    public boolean isExpired() {
        return duration <= 0;
    }
}





