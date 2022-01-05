package gui.gamepanel.context;

import javax.swing.JComponent;

import gui.gamepanel.GameScreen;

public class InteractionContext extends JComponent{

    GameScreen gameScreen;
    public InteractionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
}
