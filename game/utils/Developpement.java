package game.utils;

import java.security.InvalidParameterException;

public class Developpement {
    public static final int KNIGHT      = 0; // lets the player move the robber
    public static final int ROAD        = 1; // player can place 2 roads as if they just built them
    public static final int PLENTY      = 2; // the player can draw 2 resource cards of their choice from the bank
    public static final int MONOPOLY    = 3; // player can claim all resource cards of a specific declared type
    public static final int POINT       = 4; // 1 additional point of victory
    public static final int nDeveloppements = 5;

    static String toString(int i) {
        switch (i) {
            case 0: return "KNIGHT";
            case 1: return "ROAD";
            case 2: return "PLENTY";
            case 3: return "MONOPOLY";
            case 4: return "POINT";
        }

        throw new InvalidParameterException();
    }
}
