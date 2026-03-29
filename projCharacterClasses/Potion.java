package projCharacterClasses;

public class Potion extends UsableItem {
    private static final int HEAL_AMOUNT = 100;

    public Potion() {
        super("Potion");
    }

    @Override
    public void use(Combatant target) {
        int currentHP = target.getCurrentHP();
        int maxHP = target.getMaxHP();
        int newHP = Math.min(currentHP + HEAL_AMOUNT, maxHP);
        
        target.setCurrentHP(newHP);
    }
}
