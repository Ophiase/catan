package gui.gamepanel.context;

import java.awt.*;
import javax.swing.*;

import javax.swing.JComponent;

import game.utils.Fnc;
import gui.constants.ASCII;
import gui.gamepanel.GameScreen;

public class InformationContext extends JComponent {

    GameScreen gameScreen;
    private String text;

    public InformationContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.text = "";
        setVisible(false);
    }
    
    public void init() {
        setVisible(true);
    }

    public void publish(String text) {
        this.text = text;
        cli.Utils.info(text);
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        /*
        g.setColor(
            new Color[] {Color.red, Color.green, Color.blue} [Fnc.rand(3)]);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        */
        // ----------------
        
        ASCII.paintText(g, this, 
            text, getWidth(), getHeight()
        );
        
        // ----------------
        super.paint(g);
    }
}
