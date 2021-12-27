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
        for (int i = 0; i < nDeveloppements; i++)
            if (names[i].equals(str))
                return i;

        throw new InvalidParameterException(); 
    }
}
