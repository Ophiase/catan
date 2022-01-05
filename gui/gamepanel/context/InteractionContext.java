package gui.gamepanel.context;

import java.awt.*;
import javax.swing.*;

import game.utils.Fnc;
import gui.gamepanel.GameScreen;

public class InteractionContext extends JComponent{

    public int contextState;

    private static final int DEFAULT_STATE = 0;
    private static final int TRADE_WAIT_STATE = 1;
    private static final int PROPOSE_TRADE_STATE = 2;

    // --------------------------------------

    GameScreen gameScreen;
    public InteractionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }
    
    public void init() {
        setVisible(true);
    }


    @Override
    public void paint(Graphics g) {
        /*
            g.setColor(
                new Color[] {Color.red, Color.green, Color.blue} [Fnc.rand(3)]);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        */
        // ----------------

        switch(contextState) {
            case DEFAULT_STATE : {

            } break;
            case TRADE_WAIT_STATE : {

            } break;
            case PROPOSE_TRADE_STATE : {
                
            }
        }

        // ----------------
        super.paint(g);
    }
}
