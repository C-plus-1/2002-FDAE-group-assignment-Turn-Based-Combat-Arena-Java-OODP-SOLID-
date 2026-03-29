package projCharacterClasses;

public class Stun implements StatusEffect {
    private int remainingDuration;
    private static final int STUN_DURATION = 2;

    public Stun() {
        this.remainingDuration = STUN_DURATION;
    }

    @Override
    public String getName() {
        return "Stun";
    }

    @Override
    public void apply(Combatant target) {
        target.setStunned(true);
    }

    @Override
    public void updateDuration() {
        this.remainingDuration--;
    }

    @Override
    public boolean hasExpired() {
        return remainingDuration <= 0;
    }

    @Override
    public void remove(Combatant target) {
        target.setStunned(false);
    }

    @Override
    public int getRemainingDuration() {
        return remainingDuration;
    }
}