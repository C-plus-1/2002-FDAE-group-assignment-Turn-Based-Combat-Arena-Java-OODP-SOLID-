package projCharacterClasses;

public interface StatusEffect {

    public String getName();
    public int getRemainingDuration();
    public void apply(Combatant target);
    public void updateDuration();
    public boolean hasExpired();
    public void remove(Combatant target);

}