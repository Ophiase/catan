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

    public static String toString(int i) {
        switch (i) {
            case 0: return "DEFAULT";
            case 1: return "WOOD";
            case 2: return "SHEEP";
            case 3: return "WHEAT";
            case 4: return "BRICK";
            case 5: return "ROCK";
        }

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
