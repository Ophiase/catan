package gui.gamepanel.context;

import javax.swing.JComponent;

import gui.gamepanel.GameScreen;

public class PartyDataContext extends JComponent{

    GameScreen gameScreen;
    public PartyDataContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
}
