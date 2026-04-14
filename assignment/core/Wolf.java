package assignment.core;

public class Wolf extends Combatant {

    public Wolf() {
        super(40, 45, 5, 35); 
        this.setName("Wolf"); 
    }

    public Wolf(String name) {
        this();            
        this.setName(name);  
    }

    @Override
    public String toString() {
        return this.getName(); 
    }
}