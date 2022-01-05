package gui.gamepanel.context;

import java.awt.*;
import javax.swing.*;

import game.utils.Fnc;
import gui.gamepanel.GameScreen;

public class DataContext extends JComponent{

    GameScreen gameScreen;
    public DataContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }

    public void init() {
        setVisible(true);
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(
            new Color[] {Color.red, Color.green, Color.blue} [Fnc.rand(3)]);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        // ----------------

        // ----------------
        super.paint(g);
    }
}
