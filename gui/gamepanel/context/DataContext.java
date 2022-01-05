package gui.gamepanel.context;

import javax.swing.JComponent;

import gui.gamepanel.GameScreen;

public class DataContext extends JComponent{

    GameScreen gameScreen;
    public DataContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
}
