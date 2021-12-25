package game.constants;

import java.security.InvalidParameterException;

public class Biome {
    public static final int DESERT  = 0;
    public static final int WOOD    = 1;
    public static final int SHEEP   = 2;
    public static final int WHEAT   = 3;
    public static final int BRICK   = 4;
    public static final int ROCK    = 5;
    public static final int nBiomes = 6;

    public static String toString(int i) {
        switch (i) {
            case 0: return "DESERT";
            case 1: return "WOOD";
            case 2: return "SHEEP";
            case 3: return "WHEAT";
            case 4: return "BRICK";
            case 5: return "ROCK";
        }

        throw new InvalidParameterException();
    }
}