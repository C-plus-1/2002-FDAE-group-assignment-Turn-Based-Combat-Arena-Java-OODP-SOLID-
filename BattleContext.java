package assignment.core;

import java.util.List;

public class BattleContext {

    private List<Combatant> enemies;

    public BattleContext(List<Combatant> enemies) {
        this.enemies = enemies;
    }

    public List<Combatant> getEnemies() {
        return enemies;
    }
}





