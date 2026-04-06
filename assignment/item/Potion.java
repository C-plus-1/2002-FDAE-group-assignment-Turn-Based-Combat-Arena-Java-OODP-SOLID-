package assignment.item;
import assignment.core.Combatant;

public class Potion extends UsableItem {
    private static final int HEAL_AMOUNT = 100;

    public Potion() {
        super("Potion");
    }

    @Override
    public void use(Combatant target) {
        // Use the existing heal method from Combatant
        target.heal(HEAL_AMOUNT);

        // Optional: print feedback to the console
        System.out.println(target + " healed " + HEAL_AMOUNT + " HP!");
    }
}
