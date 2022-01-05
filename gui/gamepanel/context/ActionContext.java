package gui.gamepanel.context;

import javax.swing.JComponent;

import gui.gamepanel.GameScreen;

public class ActionContext extends JComponent{

    GameScreen gameScreen;
    public ActionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
}
