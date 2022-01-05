package gui.gamepanel.context;

import javax.swing.JComponent;

import gui.gamepanel.GameScreen;

public class InformationContext extends JComponent {

    GameScreen gameScreen;
    public InformationContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
}
