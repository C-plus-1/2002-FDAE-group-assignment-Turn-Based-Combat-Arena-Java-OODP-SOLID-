package Boundary;

import java.util.ArrayList;
import java.util.List;



public class GameView {

    private static final int Seperator_Size = 50;
    private InputHandler input;

    public GameView(InputHandler input) {
        this.input = input;
    }

    private void printDivider() {
        System.out.println("=".repeat(Seperator_Size));
    }

    private void printSubDivider() {
        System.out.println("-".repeat(Seperator_Size));
    }

    private void showTitle(String title) {
        printDivider();
        int spaces = (Seperator_Size - title.length()) / 2;
        if (spaces < 0) spaces = 0;
        System.out.println(" ".repeat(spaces) + title);
        printDivider();
    }

    // LOADING SCREEN 

    
    public void showWelcomeScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showTitle("TURN-BASED COMBAT ARENA");
        System.out.println();
        System.out.println(" Get Ready!");
        System.out.println();
    }

  
     
    public int promptClassSelection(List<String> classNames, List<String> classStats) {
        printSubDivider();
        System.out.println(" Choose Your Class");
        printSubDivider();
        for (int i = 0; i < classNames.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + classNames.get(i));
            System.out.println("      " + classStats.get(i));
        }
        System.out.println();
        return input.getChoice("Enter choice: ", 1, classNames.size()) - 1;
    }

     
    public List<Integer> promptItemSelection(List<String> itemNames, List<String> itemDescriptions, int itemCount) {
        List<Integer> selectedItem = new ArrayList<>();
        for (int p = 1; p <= itemCount; p++) {
            printSubDivider();
            System.out.println(" Choose Item " + p + " of " + itemCount);
            printSubDivider();
            for (int i = 0; i < itemNames.size(); i++) {
                System.out.println("  [" + (i + 1) + "] " + itemNames.get(i)
                        + " - " + itemDescriptions.get(i));
            }
            System.out.println();
            int choice = input.getChoice("Enter choice: ", 1, itemNames.size()) - 1;
            selectedItem.add(choice);
        }
        return selectedItem;
    }

    
    public int promptDifficultySelection(List<String> levelNames, List<String> levelDescs) {
        printSubDivider();
        System.out.println("Select Difficulty");
        printSubDivider();
        for (int i = 0; i < levelNames.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + levelNames.get(i)
                    + " - " + levelDescs.get(i));
        }
        System.out.println();
        return input.getChoice("Enter choice: ", 1, levelNames.size()) - 1;
    }

    // GAMEPLAY 

    // Shows the round number at the start of each round.
    
    public void displayRound(int roundNumber) {
        System.out.println();
        showTitle("ROUND " + roundNumber);
    }

    //Displays all combatants and their current HP.
    
    public void showBattlefieldStatus(List<String> names, List<String> hpTexts, List<String> statuses)
    {
        printSubDivider();
        System.out.println("Battlefield Status");
        printSubDivider();
        for (int i = 0; i < names.size(); i++) {
            String statusTag = "";
            if (!statuses.get(i).isEmpty()) {
                statusTag = " " + statuses.get(i);
            }
            System.out.println("  " + names.get(i)
                    + "  |  HP: " + hpTexts.get(i) + statusTag);
        }
        System.out.println();
    }

    //Announces whose turn it is.
     
    public void announceTurn(String combatantName) {
        System.out.println();
        System.out.println(">>> " + combatantName + "'s Turn <<<");
    }

    //Shows when a status effect triggers (e.g. stun).
   
    public void showStatusEffectApplied(String combatantName, String effectMessage) {
        System.out.println("  " + combatantName + " " + effectMessage);
    }

    // Asks the player to pick an action.

    
    public int promptActionSelection(List<String> actionNames) {
        printSubDivider();
        System.out.println("Choose Action");
        printSubDivider();
        for (int i = 0; i < actionNames.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + actionNames.get(i));
        }
        System.out.println();
        return input.getChoice("Enter choice: ", 1, actionNames.size()) - 1;
    }

    //Asks the player to pick a target enemy.
     
    
    public int promptTargetSelection(List<String> targetNames, List<String> targetHPs) {
        printSubDivider();
        System.out.println("Select Target");
        printSubDivider();
        for (int i = 0; i < targetNames.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + targetNames.get(i)
                    + "  (HP: " + targetHPs.get(i) + ")");
        }
        System.out.println();
        return input.getChoice("Enter choice: ", 1, targetNames.size()) - 1;
    }

    // Asks the player which item to use.
     
    public int promptItemUse(List<String> itemNames) {
        printSubDivider();
        System.out.println("Choose Item to Use");
        printSubDivider();
        for (int i = 0; i < itemNames.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + itemNames.get(i));
        }
        System.out.println();
        return input.getChoice("Enter choice: ", 1, itemNames.size()) - 1;
    }

    // Shows what happened after an action.
    
    public void displayResult(String message) {
        System.out.println("  " + message);
    }

    // Shows when a combatant is eliminated.
     
    public void showElimination(String combatantName) {
        System.out.println(" X " + combatantName + " has been ELIMINATED!");
    }

    // Shows summary at the end of each round.
     
    public void showEndOfRound(List<String> names, List<String> hpTexts, List<String> statuses, String itemsRemaining, 
    		String cooldownText) {
        printSubDivider();
        System.out.println("End of Round");
        printSubDivider();
        for (int i = 0; i < names.size(); i++) {
            String statusText = "";
            if (!statuses.get(i).isEmpty()) {
                statusText = " " + statuses.get(i);
            }
            System.out.println("  " + names.get(i)
                    + "  |  HP: " + hpTexts.get(i) + statusText);
        }
        System.out.println("  Items: " + itemsRemaining);
        System.out.println("  Special Skill Cooldown: " + cooldownText);
        System.out.println();
    }

    // Announces enemies spawning.
   
    public void showBackupSpawn(List<String> spawnNames) {
        System.out.println();
        showTitle("NEW ENEMIES!");
        for (String name : spawnNames) {
            System.out.println("  " + name + " enters the battle!");
        }
        System.out.println();
    }

    // GAME OVER SCREENS

    //Shows the victory screen with stats.
     
    public void showVictoryScreen(String remainingHP, int totalRounds, String itemsSummary) {
        System.out.println();
        showTitle("VICTORY!");
        System.out.println("Congratulations, you have defeated all your enemies.");
        System.out.println();
        System.out.println("Remaining HP: " + remainingHP);
        System.out.println("Total Rounds: " + totalRounds);
        System.out.println("Items: " + itemsSummary);
        System.out.println();
    }

    //Shows the defeat screen with stats.
     
    public void showDefeatScreen(int enemiesRemaining, int totalRounds) {
        System.out.println();
        showTitle("DEFEAT");
        System.out.println("Defeated. Don't give up, try again!");
        System.out.println();
        System.out.println(" Enemies Remaining: " + enemiesRemaining);
        System.out.println(" Total Rounds Survived: " + totalRounds);
        System.out.println();
    }

    // Asks player what to do after game ends.
    // Returns: 0 = replay, 1 = new game, 2 = exit
     
    public int promptPostGame() {
        printSubDivider();
        System.out.println(" What would you like to do?");
        printSubDivider();
        System.out.println(" [1] Replay with the same settings");
        System.out.println(" [2] Start a new game");
        System.out.println(" [3] Exit");
        System.out.println();
        return input.getChoice("Enter choice: ", 1, 3) - 1;
    }

    //  UTILITY 

    
    public void showMessage(String message) {
        System.out.println(message);
    }

    // Waits for the player to press Enter.
    
    public void waitForEnter() {
        input.getText("Press Enter to continue...");
    }

   // Closes input resources.
    
    public void close() {
        input.close();
    }
}
