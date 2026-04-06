package assignment.core;

public class Goblin extends Combatant {

    // No-arg constructor (default stats, generic name)
    public Goblin() {
        super(55, 35, 15, 25); // HP, ATK, DEF, SPD
        this.setName("Goblin"); // set default name
    }

    // New constructor to assign a unique name
    public Goblin(String name) {
        this(); // call default constructor for stats
        this.setName(name); // override the default name
    }

    @Override
    public String toString() {
        return this.getName(); // return the assigned name
    }
}