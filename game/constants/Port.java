package game.constants;

import java.security.InvalidParameterException;

public class Port {
    public static final int DEFAULT  = 0;
    public static final int WOOD   = 1;
    public static final int SHEEP  = 2;
    public static final int WHEAT  = 3;
    public static final int BRICK  = 4;
    public static final int ROCK   = 5;
    public static final int nTypes = 6;

    public static final int nDefault = 3;
    public static final int nPorts = nTypes + (-1 + nDefault);

    
    public static String[] names = new String[] {
        "DEFAULT", "WOOD", "SHEEP", "WHEAT", "BRICK", "ROCK"
    };

    public static String toString(int i) {
        return names[i];
    }

    public static int StringToInt(String str) {
        for (int i = 0; i < nTypes; i++)
            if (names[i].equals(str))
                return i;

        throw new InvalidParameterException(); 
    }

    public static int reduceSpace (int i) {
        // cette fonction est utile pour generer des ports
        // à l'aide de nombres aléatoires entre 0 et nPorts
        
        if (i >= nPorts) 
            return -1;

        return i >= nTypes ? DEFAULT : i;
    }
}
