package assignment.core;

public class Warrior extends Combatant {
    public Warrior() {
        super(260, 40, 20, 30);
        setName("Warrior");
    }

    @Override
    public String toString() {
        return "Warrior";
    }
}