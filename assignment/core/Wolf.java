package assignment.core;

public class Wolf extends Combatant {

    // No-arg constructor (default stats, generic name)
    public Wolf() {
        super(40, 45, 5, 35); // HP, ATK, DEF, SPD
        this.setName("Wolf"); // default name
    }

    // Constructor to assign a unique name
    public Wolf(String name) {
        this();              // call default constructor for stats
        this.setName(name);   // override the default name
    }

    @Override
    public String toString() {
        return this.getName(); // display the assigned name in menus
    }
}