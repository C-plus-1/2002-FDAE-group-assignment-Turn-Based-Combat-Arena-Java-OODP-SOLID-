package assignment.core;

import java.util.*;

import assignment.action.Action;
import assignment.effect.StatusEffect;
import assignment.factory.ActionFactory;
import assignment.factory.EnemyFactory;
import assignment.boundary.InputHandler;

public class BattleEngine {

    private Combatant player;
    private List<Combatant> enemies;
    private BattleContext context;

    private ActionFactory actionFactory;
    private EnemyFactory enemyFactory;
    private InputHandler input;

    private boolean backupSpawned = false;
    private int round = 1;

    public BattleEngine(Combatant player,
                        List<Combatant> enemies,
                        ActionFactory actionFactory,
                        EnemyFactory enemyFactory,
                        InputHandler input) {

        this.player = player;
        this.enemies = enemies;
        this.context = new BattleContext(enemies);

        this.actionFactory = actionFactory;
        this.enemyFactory = enemyFactory;
        this.input = input;
    }

    public void startBattle() {
        System.out.println("=== Battle Start ===");

        while (!isGameOver()) {
            System.out.println("\n--- Round " + round + " ---");

            List<Combatant> turnOrder = getTurnOrder();

            for (Combatant c : turnOrder) {

                if (!c.isAlive()) continue;

                applyStatusEffects(c);

                if (c.isStunned()) {
                    System.out.println(c + " is stunned and skips turn!");
                    continue;
                }

                if (c == player) {
                    handlePlayerTurn();
                } else {
                    handleEnemyTurn(c);
                }

                if (isGameOver()) break;
            }

            handleBackupSpawn();
            round++;
        }

        endGame();
    }

    private List<Combatant> getTurnOrder() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(enemies);
        all.sort((a, b) -> b.getSpeed() - a.getSpeed());
        return all;
    }

    private void handlePlayerTurn() {

        System.out.println("\nChoose Action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");

        int choice = input.getChoice("Enter choice: ", 1, 4);

        Action action = actionFactory.createAction(choice);

        Combatant target = (choice == 1 || choice == 4)
                ? selectEnemy()
                : player;

        action.execute(player, target, context);
    }

    private void handleEnemyTurn(Combatant enemy) {
        Action attack = actionFactory.createAction(1); // BasicAttack
        attack.execute(enemy, player, context);
    }

    private Combatant selectEnemy() {
        List<Combatant> aliveEnemies = new ArrayList<>();

        for (Combatant e : enemies) {
            if (e.isAlive()) aliveEnemies.add(e);
        }

        for (int i = 0; i < aliveEnemies.size(); i++) {
            System.out.println((i + 1) + ". " + aliveEnemies.get(i));
        }

        int choice = input.getChoice("Select target: ", 1, aliveEnemies.size());
        return aliveEnemies.get(choice - 1);
    }

    private void applyStatusEffects(Combatant c) {
        for (StatusEffect effect : c.getEffects()) {
            effect.apply(c);
        }
    }

    private void handleBackupSpawn() {
        if (!backupSpawned && enemies.stream().noneMatch(Combatant::isAlive)) {

            System.out.println("Backup enemies incoming!");
            enemies.addAll(enemyFactory.createBackupEnemies());

            backupSpawned = true;
        }
    }

    private boolean isGameOver() {
        return !player.isAlive() || enemies.stream().noneMatch(Combatant::isAlive);
    }

    private void endGame() {
        if (player.isAlive()) {
            System.out.println("Victory! Rounds: " + round);
        } else {
            System.out.println("Defeat... Rounds survived: " + round);
        }
    }
}
