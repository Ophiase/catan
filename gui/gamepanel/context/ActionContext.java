package gui.gamepanel.context;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import game.utils.Fnc;
import gui.gamepanel.GameScreen;

public class ActionContext extends JComponent{

    GameScreen gameScreen;
    public ActionContext(GameScreen gameScreen) {
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
