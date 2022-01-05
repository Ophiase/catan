package gui.gamepanel.context;

import java.awt.*;
import javax.swing.*;

import game.Engine;
import game.state.State;
import game.utils.Fnc;
import gui.constants.ASCII;
import gui.gamepanel.GameScreen;

/**
 * Header Context :
 * It shows: Focus, Number of Participants, Time
 */
public class PartyDataContext extends JComponent{

    GameScreen gameScreen;
    
    Engine engine;
    State state;

    String nParticipants;
    
    public PartyDataContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }
    
    public void init() {
        this.engine = gameScreen.engine;
        this.state = engine.getState();
        this.nParticipants = "Participants: " + engine.getState().getnPlayers();
        
        setVisible(true);
    }


    @Override
    public void paint(Graphics g) {
        final double sx = getWidth();
        final double sy = getHeight();

        // ----------------
        /*
        g.setColor(
            new Color[] {Color.red, Color.green, Color.blue} [Fnc.rand(3)]);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        */
        // ----------------

        ASCII.paintText(g, this, 
            "Focus: " + state.getPlayer(state.getFocus()), 
            (int)(sx*0.05), 0, (int)(sx*0.3), (int)sy
        );

        ASCII.paintText(g, this, 
            nParticipants, 
            (int)(sx*0.65),0, (int)(sx*0.85), (int)sy
        );

        ASCII.paintText(g, this, 
            "Time: "+state.getTime(), 
            (int)(sx*0.85),0, (int)(sx*0.95), (int)sy
        );

        // ----------------
        super.paint(g);
    }
}
