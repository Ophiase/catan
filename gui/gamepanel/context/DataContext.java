package gui.gamepanel.context;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import game.Engine;
import game.constants.Developpement;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Fnc;
import gui.constants.ASCII;
import gui.gamepanel.GameScreen;

public class DataContext extends JComponent{

    private static final boolean HIDE_WHEN_BOT = true;

    // -------------------------

    GameScreen gameScreen;
    Engine engine;
    State state;
    Map map;
    public DataContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }

    public void init() {

        engine = gameScreen.engine;
        state = engine.getState();
        map = engine.getMap();
        // --------------
        setVisible(true);
    }

    // ------------------------------

    /**
     * En attendant je me contente d'afficher un gros text
     * 
     * TODO: faire un affichage plus jolie avec des images
     */
    ArrayList<String> resumeUpdate() {
        ArrayList<String> l = new ArrayList<String>();
        String sep = "-------";

        Player focus = state.getPlayer(state.getFocus());

        final String[] colors = new String[] {
            "Orange", "Blue", "Green", "Purple"
        };

        l.add("Players:");
        for (Player p : state.getPlayers())
            l.add(
                
                ASCII.padLeft("["+colors[p.getIndex()]+"]", 8, ' ') +
                ASCII.padLeft(p + " " + p.getRessource(Ressource.POINT) + "$", 15, ' ')
                
                );
        l.add(" ");
        l.add("Ressources:");
        for (int i = 1; i < Ressource.nRessources; i++)
            l.add(
                ASCII.padLeft
                (cli.Utils.makeFirstWord(Ressource.toString(i)) + " | " + 
                    ASCII.padLeft(focus.getRessource(i))
                ,12, ' '));
        l.add(" ");
        l.add("Developpements:");
        for (int i = 0; i < Developpement.nDeveloppements; i++)
            l.add(
                ASCII.padLeft
                (cli.Utils.makeFirstWord(Developpement.toString(i)) + " | " + 
                    ASCII.padLeft(focus.getDeveloppements()[i])
                , 14, ' '));

        if (HIDE_WHEN_BOT && focus.isBot())
            for (int i = 0; i < l.size(); i++)
                l.set(i, "XXXXXX");

        return l;
    }

    // ------------------------------

    @Override
    public void paint(Graphics g) {
        ArrayList<String> resume = resumeUpdate();
        
        final double sy = (double)getHeight()/(double)resume.size();
        for (int i = 0; i < resume.size();i++)
        {
            ASCII.paintText(g, this, resume.get(i), 
                0, (int)(sy*(i)), this.getWidth(), (int)(sy*(i+1))
            );
        }

        // ----------------
        super.paint(g);
    }
}
