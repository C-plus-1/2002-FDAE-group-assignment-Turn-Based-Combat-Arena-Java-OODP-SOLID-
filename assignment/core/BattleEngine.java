package assignment.core;

import java.util.*;
import assignment.action.*;
import assignment.effect.*;

public class BattleEngine {

    private List<Combatant> players;
    private List<Combatant> enemies;
    private List<Combatant> backupEnemies;

    private BattleContext context;
    private int round = 1;

    private BattleEventListener eventListener;

    private Action pendingPlayerAction = null;
    private Combatant pendingPlayerTarget = null;

    public BattleEngine(List<Combatant> players,
                        List<Combatant> enemies,
                        List<Combatant> backupEnemies,
                        BattleEventListener listener) {
        this.players = new ArrayList<>(players);
        this.enemies = new ArrayList<>(enemies);
        this.backupEnemies = backupEnemies != null ? new ArrayList<>(backupEnemies) : new ArrayList<>();
        this.eventListener = listener;

        List<Combatant> allCombatants = new ArrayList<>();
        allCombatants.addAll(players);
        allCombatants.addAll(enemies);
        this.context = new BattleContext(enemies, listener);
    }

        public void startBattle() {
        while (!isBattleOver()) {
            if (eventListener != null) eventListener.onRoundStart(round);

            List<Combatant> turnOrder = new ArrayList<>();
            turnOrder.addAll(players);
            turnOrder.addAll(enemies);
            turnOrder.sort((a, b) -> b.getSpeed() - a.getSpeed());

            for (Combatant combatant : turnOrder) {
                if (!combatant.isAlive()) continue;

                if (combatant.isStunned()) {
                    if (eventListener != null) eventListener.onCombatantStunned(combatant);
                    continue;
                }

                if (players.contains(combatant)) {
                    if (eventListener != null)
                        eventListener.onPlayerTurn(combatant, enemies);

                    while (pendingPlayerAction == null) {
                        try { Thread.sleep(50); } catch (InterruptedException e) {}
                    }

                    executeAction(combatant, pendingPlayerAction, pendingPlayerTarget);
                    pendingPlayerAction = null;
                    pendingPlayerTarget = null;
                    continue;
                }

                Combatant target = selectPlayerTarget();
                if (target != null) executeAction(combatant, new BasicAttack(), target);

                if (isBattleOver()) break;
            }

            if (eventListener != null) eventListener.onRoundEnd(round, players, enemies);
            endRound();
            round++;

            if (!backupEnemies.isEmpty() && enemies.stream().allMatch(c -> !c.isAlive())) {
                spawnBackupEnemies();
            }
        }

        notifyBattleEnd();
    }

    public void executePlayerAction(Combatant player, Action action, Combatant target) {
        this.pendingPlayerAction = action;
        this.pendingPlayerTarget = target;
    }

    private void executeAction(Combatant actor, Action action, Combatant target) {
    int hpBefore = (target != null) ? target.getHp() : 0;
    action.execute(actor, target, context);
    int damage = (target != null) ? Math.max(0, hpBefore - target.getHp()) : 0;

    if (!(action instanceof UseSpecialSkill && ((UseSpecialSkill) action).getSkill() instanceof ArcaneBlast)) {
        if (eventListener != null)
            eventListener.onActionExecuted(actor, target, action, damage);

        if (target != null && !target.isAlive()) {
            notifyElimination(target);
        }
    } else {
        for (Combatant enemy : context.getEnemies()) {
            if (!enemy.isAlive()) notifyElimination(enemy);
        }
    }
}

    private void spawnBackupEnemies() {
    enemies.removeIf(c -> !c.isAlive());
    enemies.addAll(backupEnemies);
    backupEnemies.clear();
    this.context = new BattleContext(enemies, eventListener);
    notifyBackupSpawn(enemies);
}

    private boolean isBattleOver() {
        boolean playersAlive = players.stream().anyMatch(Combatant::isAlive);
        boolean enemiesAlive = enemies.stream().anyMatch(Combatant::isAlive);
        return !(playersAlive && enemiesAlive);
    }

    private Combatant selectPlayerTarget() {
        return players.stream().filter(Combatant::isAlive).findFirst().orElse(null);
    }

    private void notifyElimination(Combatant combatant) {
        if (eventListener != null) eventListener.onCombatantEliminated(combatant);
    }

    private void notifyBattleEnd() {
        boolean playerWon = enemies.stream().allMatch(c -> !c.isAlive());
        if (eventListener != null) eventListener.onBattleEnd(playerWon);
    }
    
    public int getRound() {
        return round;
    }

    private void endRound() {
        for (Combatant c : players) {
            c.applyEffects();
            c.reduceCooldowns();
        }
        for (Combatant c : enemies) {
            c.applyEffects();
            c.reduceCooldowns();
        }
    }

    private void notifyBackupSpawn(List<Combatant> newEnemies) {
        if (eventListener != null) eventListener.onBackupSpawn(newEnemies);
    }
    

    public interface BattleEventListener {
        void onRoundStart(int roundNumber);
        void onPlayerTurn(Combatant player, List<Combatant> enemies);
        void onActionExecuted(Combatant actor, Combatant target, Action action, int damage);
        void onCombatantEliminated(Combatant combatant);
        void onCombatantStunned(Combatant combatant);
        void onRoundEnd(int roundNumber, List<Combatant> players, List<Combatant> enemies);
        void onBattleEnd(boolean playerWon);
        void onBackupSpawn(List<Combatant> newEnemies);
    }
}
