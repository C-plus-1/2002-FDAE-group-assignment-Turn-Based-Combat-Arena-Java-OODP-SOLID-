package assignment.core;

import java.util.List;
import assignment.action.Action;

public class BattleContext {

    private List<Combatant> enemies;
    private BattleEngine.BattleEventListener eventListener;

    public BattleContext(List<Combatant> enemies, BattleEngine.BattleEventListener eventListener) {
        this.enemies = enemies;
        this.eventListener = eventListener;
    }

    public List<Combatant> getEnemies() { return enemies; }

    public BattleEngine.BattleEventListener getEventListener() { return eventListener; }
}