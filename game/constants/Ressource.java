package game.constants;

import java.security.InvalidParameterException;

import game.state.Player;

public class Ressource {
    public static final int POINT  = 0;
    public static final int WOOD   = 1;
    public static final int SHEEP  = 2;
    public static final int WHEAT  = 3;
    public static final int BRICK  = 4;
    public static final int ROCK   = 5;
    public static final int nRessources = 6;

    public static String[] names = new String[] {
        "POINT", "WOOD", "SHEEP", "WHEAT", "BRICK", "ROCK"
    };

    public static String toString(int i) {
        return names[i];
    }

    public static int StringToInt(String str) {
        str = str.toUpperCase();

        for (int i = 0; i < names.length; i++)
            if (names[i].equals(str))
                return i;

        throw new InvalidParameterException(); 
    }

    public static int priceOf(int[] r2, Player player) {
        int which = -1;
        for (int i = 0; i < nRessources; i++)
            if (r2[i] == 1)
                which = i;

        if (which==-1) 
            throw new Error("Price of invalid ressource.");

        // ------------------

        if (player.hasPort(which))
            return 2;
        if (player.hasPort(Port.DEFAULT))
            return 3;
        return 4;
    }

    // --------------------------------

    public static String resume() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < names.length; i++)
        {
            s.append(names[i]);
            s.append(" (");
            s.append(i);
            s.append("), ");
        }

        return s.toString();
    }
}
