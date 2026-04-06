package Boundary;

import java.util.*;
import assignment.core.*;
import assignment.action.*;
import assignment.item.*;

public class GameApp {

    public static void main(String[] args) {
        InputHandler input = new ConsoleInputHandler();
        GameView view = new GameView(input);

        view.showWelcomeScreen();

        // -----------------------
        // Player class selection
        // -----------------------
        List<String> playerClasses = Arrays.asList("Warrior", "Wizard");
        int classChoice = view.promptClassSelection(playerClasses, Arrays.asList(
                "HP:260 ATK:40 DEF:20 SPD:30",
                "HP:200 ATK:50 DEF:10 SPD:20"
        ));

        Combatant player = (classChoice == 0) ? new Warrior() : new Wizard();

        // -----------------------
        // Item selection
        // -----------------------
        List<String> itemNames = Arrays.asList("Potion", "Power Stone", "Smoke Bomb");
        List<String> itemDescs = Arrays.asList(
                "Heals 100 HP",
                "Extra use of special skill",
                "Enemies deal 0 damage this turn + next"
        );

        List<Integer> itemChoices = view.promptItemSelection(itemNames, itemDescs, 2);
        List<UsableItem> playerItems = new ArrayList<>();
        for (int choice : itemChoices) {
            switch (choice) {
                case 0 -> playerItems.add(new Potion());
                case 1 -> playerItems.add(new PowerStone());
                case 2 -> playerItems.add(new SmokeBomb());
            }
        }

        // -----------------------
        // Difficulty selection
        // -----------------------
        int levelChoice = view.promptDifficultySelection(
                Arrays.asList("Easy", "Medium", "Hard"),
                Arrays.asList("3 Goblins", "1 Goblin + 1 Wolf", "2 Goblins + 2 Wolves")
        );

        // -----------------------
        // Create enemies and backup enemies
        // -----------------------
        List<Combatant> enemies = new ArrayList<>();
        List<Combatant> backupEnemies = new ArrayList<>();

        switch (levelChoice) {
            case 0 -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                enemies.add(new Goblin("Goblin C"));
            }
            case 1 -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Wolf("Wolf A"));
                backupEnemies.add(new Wolf("Wolf B"));
                backupEnemies.add(new Wolf("Wolf C"));
            }
            case 2 -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                enemies.add(new Wolf("Wolf A"));
                enemies.add(new Wolf("Wolf B"));
                backupEnemies.add(new Goblin("Goblin C"));
                backupEnemies.add(new Wolf("Wolf C"));
            }
        }

        // -----------------------
        // Initialize BattleEngine with event listener
        // -----------------------
        List<Combatant> players = Arrays.asList(player);
        BattleEngine[] engineRef = new BattleEngine[1];
        engineRef[0] = new BattleEngine(players, enemies, backupEnemies,
            new BattleEngine.BattleEventListener() {

                @Override
                public void onRoundStart(int roundNumber) {
                    view.showMessage("\n==================================================");
                    view.showMessage("Round " + roundNumber);
                    view.showMessage("==================================================");
                }

                @Override
                public void onPlayerTurn(Combatant playerTurn, List<Combatant> enemiesTurn) {
                    view.showMessage("\n" + playerTurn.getName() + "'s turn!");

                    // Build action name list — include items if any remain
                    List<String> actionNames = new ArrayList<>(Arrays.asList("Basic Attack", "Defend", "Special Skill"));
                    for (UsableItem item : playerItems) {
                        actionNames.add("Use " + item.getName());
                    }

                    int actionIndex = view.promptActionSelection(actionNames);

                    Action chosenAction;
                    boolean needsTarget;

                    if (actionIndex == 0) {
                        chosenAction = new BasicAttack();
                        needsTarget = true;
                    } else if (actionIndex == 1) {
                        chosenAction = new Defend();
                        needsTarget = false;
                    } else if (actionIndex == 2) {
                        boolean bypass = playerTurn.hasCooldownBypass();
                        if (bypass) playerTurn.setCooldownBypass(false);

                        if (playerTurn instanceof Warrior) {
                            SpecialSkill skill = new ShieldBash();
                            chosenAction = new UseSpecialSkill(skill, bypass);
                            needsTarget = true;
                        } else {
                            SpecialSkill skill = new ArcaneBlast();
                            chosenAction = new UseSpecialSkill(skill, bypass);
                            needsTarget = false; // hits all enemies, no target needed
                        }
                    } else {
                        UsableItem selectedItem = playerItems.get(actionIndex - 3);
                        playerItems.remove(selectedItem);
                        String itemName = selectedItem.getName();
                        chosenAction = new Action() {
                            @Override
                            public void execute(Combatant actor, Combatant target, BattleContext context) {
                                selectedItem.use(actor);
                            }
                            @Override
                            public String toString() {
                                return itemName;
                            }
                        };
                        needsTarget = false;
                    }

                    // Only prompt for a target if the action requires one
                    Combatant chosenTarget = null;
                    if (needsTarget) {
                        List<String> targetNames = new ArrayList<>();
                        List<String> targetHPs = new ArrayList<>();
                        List<Combatant> aliveEnemies = new ArrayList<>();
                        for (Combatant e : enemiesTurn) {
                            if (e.isAlive()) {
                                targetNames.add(e.getName());
                                targetHPs.add(String.valueOf(e.getHp()));
                                aliveEnemies.add(e);
                            }
                        }
                        int targetIndex = view.promptTargetSelection(targetNames, targetHPs);
                        chosenTarget = aliveEnemies.get(targetIndex);
                    }

                    engineRef[0].executePlayerAction(playerTurn, chosenAction, chosenTarget);
                }

                @Override
                public void onActionExecuted(Combatant actor, Combatant target, Action action, int damage) {
                    if (target != null) {
                        int hpBefore = target.getHp() + damage;
                        int hpAfter  = target.getHp();
                        int atk      = actor.getAttack();
                        int def      = target.getDefense();
                        view.showMessage("  " + actor.getName() + " | " + action
                                + " | " + target.getName()
                                + ": HP:" + hpBefore + "/" + hpAfter
                                + " (dmg: " + atk + "-" + def + "=" + damage + ")");
                    } else {
                        view.showMessage("  " + actor.getName() + " | " + action);
                    }
                }

                @Override
                public void onCombatantEliminated(Combatant combatant) {
                    view.showElimination(combatant.getName());
                }
                
                @Override
                public void onCombatantStunned(Combatant combatant) {
                    view.showMessage("  " + combatant.getName() + " is stunned and cannot act this turn!");
                }

                @Override
                public void onRoundEnd(int roundNumber, List<Combatant> players, List<Combatant> enemies) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\nEnd of Round ").append(roundNumber).append(": ");

                    for (Combatant p : players) {
                        sb.append(p.getName())
                          .append(" HP: ").append(p.getHp())
                          .append("/").append(p.getMaxHp())
                          .append(" | ");
                    }
                    for (Combatant e : enemies) {
                        sb.append(e.getName())
                          .append(" HP: ").append(e.getHp())
                          .append(" | ");
                    }

                    // Items remaining
                    if (playerItems.isEmpty()) {
                        sb.append("No items | ");
                    } else {
                        Map<String, Integer> itemCounts = new LinkedHashMap<>();
                        for (UsableItem item : playerItems) {
                            itemCounts.merge(item.getName(), 1, Integer::sum);
                        }
                        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
                            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" | ");
                        }
                    }

                    // Special skill cooldown
                    Combatant p = players.get(0);
                    Class<?> skillClass = (p instanceof Warrior) ? ShieldBash.class : ArcaneBlast.class;
                    int cooldown = p.getCooldownValue(skillClass);
                    sb.append("Special Skills Cooldown: ").append(cooldown);

                    view.showMessage(sb.toString());
                }

                @Override
                public void onBattleEnd(boolean playerWon) {
                    if (playerWon) {
                        view.showVictoryScreen(
                            player.getHp() + "/" + player.getMaxHp(),
                            0,
                            playerItems.size() + " item(s) remaining"
                        );
                    } else {
                        view.showDefeatScreen(
                            (int) enemies.stream().filter(Combatant::isAlive).count(),
                            0
                        );
                    }
                }

                @Override
                public void onBackupSpawn(List<Combatant> newEnemies) {
                    List<String> spawnNames = new ArrayList<>();
                    for (Combatant c : newEnemies) spawnNames.add(c.getName());
                    view.showBackupSpawn(spawnNames);
                }
            }
        );

        BattleEngine engine = engineRef[0];

        // -----------------------
        // Start the battle
        // -----------------------
        engine.startBattle();

        // -----------------------
        // Post-battle
        // -----------------------
        view.waitForEnter();
        view.close();
    }

    // -----------------------
    // Helper method
    // -----------------------
    private static Combatant getFirstAlive(List<Combatant> list) {
        for (Combatant c : list) {
            if (c.isAlive()) return c;
        }
        return null;
    }
}