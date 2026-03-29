package projCharacterClasses;

public class SmokeBomb extends UsableItem {

    public SmokeBomb() {
        super("Smoke Bomb");
    }

    @Override
    public void use(Combatant target) {
        target.setAttack(0);
    }
}
