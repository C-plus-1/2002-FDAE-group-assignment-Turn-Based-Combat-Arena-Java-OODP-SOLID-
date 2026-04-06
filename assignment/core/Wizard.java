package assignment.core;

public class Wizard extends Combatant {
    public Wizard() {
        super(200, 50, 10, 20);
        setName("Wizard");
    }

    @Override
    public String toString() {
        return "Wizard";
    }
}