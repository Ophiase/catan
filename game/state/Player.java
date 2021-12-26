package game.state;

import java.util.ArrayList;

import game.constants.*;
import game.utils.Fnc;

public class Player {
    
    private int         index;
    private boolean     isBot;
    private int[]       ressources      = new int       [Ressource.nRessources];
    private int[]       developpements  = new int       [Developpement.nDeveloppements];
    private boolean[]   ports           = new boolean   [Port.nTypes];
    
    private ArrayList<Integer> colonies  = new ArrayList<Integer>();
    private ArrayList<Integer> cities    = new ArrayList<Integer>();

    // ---------------

    public Player(int index, boolean isBot) {
        this.isBot = isBot;

        ressources[Ressource.POINT] = 2;
    }

    public int getIndex() {
        return index;
    }

    public boolean isBot() {
        return isBot;
    }

    // ---------------

    public ArrayList<Integer> getColonies() {
        return colonies;
    }

    public ArrayList<Integer> getCities() {
        return cities;
    }

    // ---------------

    public boolean hasRessources (int[] askedRessources) {
        for (int i = 0; i < askedRessources.length; i++)
            if (askedRessources[i] > ressources[i])
                return false;

        return true;
    }

    public int[] getRessources() {
        return ressources;
    }

    public int getRessource(int index) {
        return ressources[index];
    }

    // ---------------

    public boolean hasDeveloppement(int developpementAsked) {
        return developpements[developpementAsked] > 0;
    }

    public void useDeveloppement(int developpement) {
        developpements[developpement] --;
    }

    public void addDeveloppement(int developpement) {
        developpements[developpement] ++;
    }

    // ---------------

    public boolean hasPort(int p) {
        return ports[p];
    }

    public void addPort(int p) {
        ports[p] = true;
    }

    // ---------------

    public int nCards() {
        int nCards = 0;

        // on compte à 1 car la ressource point ne compte pas
        for (int i = 1; i < ressources.length; i++)
            nCards += ressources[i];

        for (int i = 0; i < developpements.length; i++)
            nCards += developpements.length;

        return nCards;
    }

}
