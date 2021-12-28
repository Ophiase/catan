package game.constants;

import java.security.InvalidParameterException;

public class Developpement {
    public static final int KNIGHT      = 0; // lets the player move the robber
    public static final int ROAD        = 1; // player can place 2 roads as if they just built them
    public static final int PLENTY      = 2; // the player can draw 2 resource cards of their choice from the bank
    public static final int MONOPOLY    = 3; // player can claim all resource cards of a specific declared type
    public static final int POINT       = 4; // 1 additional point of victory
    public static final int nDeveloppements = 5;

    public static String[] names = new String[] {
        "KNIGHT", "ROAD", "PLENTY", "MONOPOLY", "POINT"
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
