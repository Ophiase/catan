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

    public static String[] names = new String[] {
        "DESERT", "WOOD", "SHEEP", "WHEAT", "BRICK", "ROCK"
    };

    public static String toString(int i) {
        return names[i];
    }

    public static int StringToInt(String str) {
        for (int i = 0; i < nBiomes; i++)
            if (names[i].equals(str))
                return i;

        throw new InvalidParameterException(); 
    }
}