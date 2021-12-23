package game.state;

import game.constants.*;
public class Player {
    
    private int     index;
    private boolean isBot;
    private int[]   ressources      = new int[Ressource.nRessources];
    private int[]   developpements  = new int[Developpement.nDeveloppements];

    // ---------------

    public Player(int index, boolean isBot) {
        this.isBot = isBot;

        ressources[Ressource.POINT] = 2;
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
