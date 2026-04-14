package assignment.item;
import assignment.core.Combatant;

public class Potion extends UsableItem {
    private static final int HEAL_AMOUNT = 100;

    public Potion() {
        super("Potion");
    }

    @Override
    public void use(Combatant target) {
        target.heal(HEAL_AMOUNT);
        System.out.println(target + " healed " + HEAL_AMOUNT + " HP!");
    }
}
