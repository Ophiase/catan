package game.utils;

import java.security.InvalidParameterException;
import game.constants.*;

public class Offer {
    public int p1;
    public int p2; // -1 = bank
    public int[] r1;
    public int[] r2;

    public Offer(int p1, int p2, int[] r1, int[] r2) {
        this.p1 = p1;
        this.p2 = p2;
        this.r1 = r1;
        this.r2 = r2;
    }

    // exemple: makeRessource(Ressource.WOOD, 2, Ressource.STONE, 3)
    public static int[] makeRessources (int... data) {
        if ((data.length&1) == 1) throw new InvalidParameterException();

        int[] ressources = new int[Ressource.nRessources];

        for (int i = 0; i < data.length; i+=2)
            ressources[data[i]] += data[i+1];

        return ressources;
    }

    public boolean isValidOffer(game.state.State state) {
        if (state.getFocus() != p1)
            return false;
        
        if (p2 == p1)
            return false;
        
        if (p2 < -1 || p2 > 3)
            return false;
        
        if (!state.getPlayer(p1).hasRessources(r1))
            return false;
        
        if (p2!=-1 && !state.getPlayer(p2).hasRessources(r2))
            return false;

        return true;
    }
}
