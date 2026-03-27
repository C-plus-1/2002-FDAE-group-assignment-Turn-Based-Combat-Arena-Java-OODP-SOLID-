package projCharacterClasses;

public class Potion extends UsableItem {

    public Potion() {
        super("Potion");
    }

    @Override
    public void use(Combatant target) {
        //target.heal(100);
    }
}
