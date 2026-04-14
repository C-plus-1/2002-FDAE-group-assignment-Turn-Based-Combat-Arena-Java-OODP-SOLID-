package assignment.core;

public class Goblin extends Combatant {

    public Goblin() {
        super(55, 35, 15, 25); 
        this.setName("Goblin"); 
    }

    public Goblin(String name) {
        this(); 
        this.setName(name); 
    }

    @Override
    public String toString() {
        return this.getName(); 
    }
}