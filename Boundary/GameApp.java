package Boundary;


public class GameApp {

    public static void main(String[] args) {

        
        InputHandler inputReader = new ConsoleInputHandler();
        GameView view = new GameView(inputReader);

       
        view.showWelcomeScreen();
        view.showMessage("  [Boundary layer is ready]");
        view.showMessage("  Waiting for BattleEngine integration...");
        view.close();
    }
}
