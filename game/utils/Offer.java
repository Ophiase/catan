package game.utils;

import java.security.InvalidParameterException;
import game.constants.*;
import game.state.Player;

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

    // -------------------------------------

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
        
        if (p2 < 0 || p2 >= state.getnPlayers())
            return false;
        
        if (!state.getPlayer(p1).hasRessources(r1))
            return false;
        
        if (p2!=-1 && !state.getPlayer(p2).hasRessources(r2))
            return false;

        return true;
    }

    public boolean isValidPurchase(game.state.State state) {
        if (state.getFocus() != p1)
            return false;
        
        if (-1 != p2)
            return false;

        if (!state.getPlayer(p1).hasRessources(r1))
            return false;

        if (countRessources(r2) != 1)
            return false;

        if (r2[0] != 0)
            return false;

        // -----------

        if (countRessources(r1) != Ressource.priceOf(r2, state.getPlayer(p1)))
            return false;

        // -----------
        
        return true;
    }

    private static int countRessources(int[] ressources) {
        return Fnc.arrSum(ressources);
    }

    // -------------------------------------
    // ACTIONS

    public void proceed(game.state.State state) {
        give(state, p1, p2, r1);
        give(state, p2, p1, r2);
    }

    public static void give(game.state.State state, int from, int to, int[] r) {
        if (from != -1)
            lose(state.getPlayer(from), r);
        
        if (to != -1)
            receive(state.getPlayer(to), r);
    }

    public static void receive(Player p, int[] r) {
        for (int i = 0; i < r.length; i++)
            p.getRessources()[i] += r[i];
    }

    public static void lose(Player p, int[] r) {
        for (int i = 0; i < r.length; i++)
            p.getRessources()[i] -= r[i];
    }
}
