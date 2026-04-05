package assignment.core;

import java.util.*;
import assignment.action.*;
import assignment.effect.*;

public class BattleEngine {

    private List<Combatant> players;
    private List<Combatant> enemies;
    private List<Combatant> backupEnemies;

    private TurnOrderStrategy turnOrderStrategy;

    private BattleContext context;
    private int round = 1;

    public BattleEngine(List<Combatant> players,
                        List<Combatant> enemies,
                        List<Combatant> backupEnemies,
                        TurnOrderStrategy strategy,
                        BattleContext context) {

        this.players = players;
        this.enemies = enemies;
        this.backupEnemies = backupEnemies;
        this.turnOrderStrategy = strategy;
        this.context = context;
    }

    // =========================
    // MAIN BATTLE LOOP
    // =========================
    public void startBattle() {

        System.out.println("=== BATTLE START ===");

        while (!isBattleOver()) {

            System.out.println("\n--- Round " + round + " ---");

            List<Combatant> turnOrder = getAllAliveCombatants();
            turnOrder = turnOrderStrategy.determineOrder(turnOrder);

            for (Combatant c : turnOrder) {

                if (!c.isAlive()) continue;

                // Apply effects + cooldowns (CORRECT for your Combatant)
                c.applyEffects();
                c.reduceCooldowns();

                if (!c.isAlive()) continue;

                if (c.isStunned()) {
                    System.out.println(c + " is stunned and skips turn.");
                    continue;
                }

                takeTurn(c);

                if (isBattleOver()) break;
            }

            handleBackupSpawn();

            round++;
        }

        printResult();
    }

    // =========================
    // TURN LOGIC
    // =========================
    private void takeTurn(Combatant c) {

        if (players.contains(c)) {
            playerTurn(c);
        } else {
            enemyTurn(c);
        }
    }

    private void playerTurn(Combatant player) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\nPlayer Turn: " + player);
        System.out.println("1. Basic Attack");

        int choice = sc.nextInt();

        if (choice == 1) {
            Combatant target = getFirstAlive(enemies);
            if (target != null) {
                Action action = new BasicAttack();
                action.execute(player, target, context);
            }
        }
    }

    private void enemyTurn(Combatant enemy) {

        Combatant target = getFirstAlive(players);

        if (target != null) {
            Action action = new BasicAttack();
            action.execute(enemy, target, context);
        }
    }

    // =========================
    // BACKUP SPAWN
    // =========================
    private void handleBackupSpawn() {

        boolean allEnemiesDead = enemies.stream().noneMatch(Combatant::isAlive);

        if (allEnemiesDead && backupEnemies != null && !backupEnemies.isEmpty()) {

            System.out.println(">>> Backup enemies have arrived!");

            enemies.addAll(backupEnemies);
            backupEnemies.clear();
        }
    }

    // =========================
    // HELPERS
    // =========================
    private List<Combatant> getAllAliveCombatants() {

        List<Combatant> all = new ArrayList<>();

        for (Combatant c : players)
            if (c.isAlive()) all.add(c);

        for (Combatant c : enemies)
            if (c.isAlive()) all.add(c);

        return all;
    }

    private Combatant getFirstAlive(List<Combatant> list) {
        for (Combatant c : list)
            if (c.isAlive()) return c;
        return null;
    }

    private boolean isBattleOver() {

        boolean playersAlive = players.stream().anyMatch(Combatant::isAlive);
        boolean enemiesAlive = enemies.stream().anyMatch(Combatant::isAlive);

        return !(playersAlive && enemiesAlive);
    }

    private void printResult() {

        boolean playersAlive = players.stream().anyMatch(Combatant::isAlive);

        System.out.println("\n=== BATTLE END ===");

        if (playersAlive) {
            System.out.println("Victory!");
        } else {
            System.out.println("Defeat!");
        }
    }

    // =========================
    // TURN ORDER STRATEGY (DIP)
    // =========================
    public interface TurnOrderStrategy {
        List<Combatant> determineOrder(List<Combatant> combatants);
    }

    // Speed-based implementation
    public static class SpeedBasedTurnOrder implements TurnOrderStrategy {
        @Override
        public List<Combatant> determineOrder(List<Combatant> combatants) {
            combatants.sort((a, b) -> b.getSpeed() - a.getSpeed());
            return combatants;
        }
    }
}
