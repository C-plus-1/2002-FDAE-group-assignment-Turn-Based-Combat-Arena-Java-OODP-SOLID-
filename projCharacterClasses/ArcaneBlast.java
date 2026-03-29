package projCharacterClasses;

public class ArcaneBlast implements StatusEffect {
    private static final int ATTACK_BUFF = 10;
    private final int attackBonus;
    private static int totalBuffApplied = 0;

    public ArcaneBlast(int killCount) {
        this.attackBonus = Math.max(0, killCount) * ATTACK_BUFF;
    }

    @Override
    public String getName() {
        return "Arcane Blast";
    }

    @Override
    public int getRemainingDuration() {
        return -1;
    }

    @Override
    public void apply(Combatant target) {
        int newAtk = target.getAttack() + attackBonus;
        totalBuffApplied += attackBonus;
        target.setAttack(newAtk);
    }

    @Override
    public void updateDuration() {
    }

    @Override
    public boolean hasExpired() {
        return false;
    }

    @Override
    public void remove(Combatant target) {
        int newAtk = target.getAttack() - totalBuffApplied;
        totalBuffApplied = 0;
        target.setAttack(newAtk);
    }
}