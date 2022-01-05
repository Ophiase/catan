package gui.gamepanel.context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import game.utils.Fnc;
import gui.GameLoopGUI;
import gui.constants.ASCII;
import gui.gamepanel.GameScreen;

public class MapContext extends JComponent {
    
    public int contextState = 0;
    public static final int DEFAULT_STATE = 0;
    public static final int PUT_ROAD_STATE = 1;
    public static final int PUT_COLONY_STATE = 2;
    public static final int PUT_CITY_STATE = 3;
    // ------------------------------------------
    
    BufferedImage storedMapComponents;
    
    // ------------------------------------------

    GameScreen gameScreen;
    public MapContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }
    
    public void init() {
        storedMapComponents = new BufferedImage(
            gameScreen.mapImage.getWidth(),
            gameScreen.mapImage.getHeight(),
            BufferedImage.TYPE_4BYTE_ABGR);

        makeMouseListener();
        setVisible(true);
    }

    // ------------------------------------------

    MouseInputAdapter mouseInputAdapter;
    private void makeMouseListener() {
        mouseInputAdapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                applyState();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        };
    }

    public void listen() {
        this.addMouseListener(mouseInputAdapter);
    }

    public void stopListening() {
        this.removeMouseListener(mouseInputAdapter);
    }

    public void setState(int state) {
        if (state == 0)
        {
            stopListening();
            contextState = 0;
            gameScreen.gameLoop.flowing = true;

            return;
        }

        contextState = state;
        listen();

        switch(contextState) {
            case DEFAULT_STATE : {
                // nothing
            } break;
            case PUT_ROAD_STATE : {
                gameScreen.informationContext.publish("Choose a road.");
            } break;
            case PUT_COLONY_STATE : {
                gameScreen.informationContext.publish("Choose a colony.");
            } break;
            case PUT_CITY_STATE : {
                gameScreen.informationContext.publish("Choose a city.");
            } break;
        }
    }

    public void applyState() {
        switch(contextState) {
            case DEFAULT_STATE : {
                // nothing
            } break;
            case PUT_ROAD_STATE : {

                setState(DEFAULT_STATE);
            } break;
            case PUT_COLONY_STATE : {
                
                setState(DEFAULT_STATE);
            } break;
            case PUT_CITY_STATE : {
                
                setState(DEFAULT_STATE);
            } break;
        }
    }

    @Override
    public void paint(Graphics g) {
        /*
            g.setColor(
                new Color[] {Color.red, Color.green, Color.blue} [Fnc.rand(3)]);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        */

        // ----------------
        // BASE MAP

        g.drawImage(storedMapComponents, 0, 0, this.getWidth(), this.getHeight(), this);        
        
        // ----------------

        switch(contextState) {
            case DEFAULT_STATE : {

            } break;
            case PUT_ROAD_STATE : {

            } break;
            case PUT_COLONY_STATE : {
                
            } break;
            case PUT_CITY_STATE : {
                
            } break;
        }

        // ----------------
        super.paint(g);
    }
}
