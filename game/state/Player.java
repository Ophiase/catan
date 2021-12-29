package game.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import game.constants.*;
import game.utils.Fnc;

public class Player {
    
    private int         index;
    private boolean     isBot;
    private String      name;
    private int[]       ressources      = new int       [Ressource.nRessources];
    private int[]       developpements  = new int       [Developpement.nDeveloppements];
    private boolean[]   ports           = new boolean   [Port.nTypes];
    
    private Set<Integer> dicesIdx       = new HashSet<Integer>();
    private int[][] dices;
    private ArrayList<Integer> roadH    = new ArrayList<Integer>();
    private ArrayList<Integer> roadV    = new ArrayList<Integer>();
    private ArrayList<Integer> colonies = new ArrayList<Integer>();
    private ArrayList<Integer> cities   = new ArrayList<Integer>();

    // ---------------

    public Player(int index, boolean isBot, int mapSize) {
        this.index = index;
        this.isBot = isBot;
        this.name = (isBot ? "Bot" : "Player" ) + " (" + index + ")";
        this.dices = new int[mapSize][mapSize];

        ressources[Ressource.POINT] = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
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

    public int[][] getDices() {
        return dices;
    }

    public Set<Integer> getDicesIdx() { return dicesIdx; }

    public ArrayList<Integer> getRoadH() {
        return roadH;
    }

    public ArrayList<Integer> getRoadV() {
        return roadV;
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

    public void addRessources(int[] rsc) {
        for (int i = 0; i < rsc.length; i++)
            ressources[i] += rsc[i];
    }

    // ---------------

    public int[] getDeveloppements() {
        return developpements;
    }

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
        return Fnc.arrSum(developpements) + Fnc.arrSum(ressources) - ressources[Ressource.POINT];
    }

}
