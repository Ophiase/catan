package game.state;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cli.Utils;
import game.constants.Biome;
import game.constants.Port;
import game.utils.Fnc;

public class Map {

    private int size;
    private int sizePP;

    private int robberIndex = -1;
    private int robberPosition = -1;

    /**
     * Encodage des colonies :
     * 2 premier bits   <=> 00 vide / 01 colonie /  11 ville
     * bits qui suivent <=> index joueur
     * Exemple :
     * ....1110 on le sépare en 2: 0000011 et 11
     * Donc joueur 11(2) = 3
     *      et une ville car 11(2) = 2
    */

    private int[] dicesX;           // coordonnée X des dés
    private int[] dicesY;           // coordonnée Y des dés
    private int[][] diceIndexes;    // nombres aux dés en X;Y
    
    private int[][] biomes;         // biomes
    private int[][] colonies;       // colonies
    private int[][] roadsH;         // routes horizontals
    private int[][] roadsV;         // routes verticles
    private int[][] ports;          // ports
    
    // --------------------

    public Map(game.Config conf) {
        this.size   = conf.getSizeOfMap();
        this.sizePP = size+1;

        dicesX = new int[conf.getnCases()+conf.getnDices()];
        dicesY = new int[conf.getnCases()+conf.getnDices()];

        diceIndexes     = new int[size  ][size  ];
        biomes          = new int[size  ][size  ];
        colonies        = new int[sizePP][sizePP];
        roadsH          = new int[size  ][sizePP];
        roadsV          = new int[sizePP][size  ];
        ports           = new int[Port.nTypes][];

        // ----------------
        // fill indexes and biomes
            
        int sizeSquare = size*size;
        int[] rndArray = Fnc.randomIndexArray(sizeSquare);
        
        for (int i = 0, j = 1; i < conf.getnCases(); i++, j++) {
            if (j==Biome.nBiomes) j = 1;

            int w = rndArray[i];
            int x = Fnc.conv1dto2d_x(w, size);
            int y = Fnc.conv1dto2d_y(w, size);

            biomes      [x][y] = j;
            diceIndexes [x][y] = i + conf.getnDices();
            dicesX[i+conf.getnDices()] = x;
            dicesY[i+conf.getnDices()] = y;
        }

        // ----------------
        // fill roads

        for (int[] row: roadsH)
            Arrays.fill(row, -1);
        for (int[] row: roadsV)
            Arrays.fill(row, -1);


        // ---------------
        /* Fill ports
         *
         * Chaque port correspond à une bordure
         * Pour chaque port il y a donc 2 positions
         * de ville qui peuvent lui correspondre
         * 
         * Pour simplifier le travail
         * J'ai décidé d'avoir nDefaults instances 
         * du port par défaut (3:1)
         * Mais une seul instance des autres ports
         * (2:1) {ressource} 
         * 
         */

        ports[Port.DEFAULT] = new int[Port.nDefault*2];
        for (int i = 1; i < Port.nTypes; i++)
            ports[i] = new int[2];

        int nBorder = 4*size;
        int[] rndArray2 = Fnc.randomIndexArray(nBorder);

        for (int i = 0, defaultPointer = 0; i < nBorder; i++) {
            // Determiner le port correspondant au bord courant
            int port = Port.reduceSpace(rndArray2[i]);
            if (port == -1) continue;

            // Determiner les 2 1d-positions encodés du bord courant
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;

            int d = i/size;
            int r = i%size;
            switch (d) {
                case 0:
                    x1 = r;
                    y1 = 0;

                    x2 = x1+1;
                    y2 = y1;
                    break;
                case 1:
                    x1 = r;
                    y1 = size;

                    x2 = x1+1;
                    y2 = y1;
                    break;
                case 2:
                    x1 = 0;
                    y1 = r;

                    x2 = x1;
                    y2 = y1+1;
                break;
                case 3:
                    x1 = size;
                    y1 = r;

                    x2 = x1;
                    y2 = y1+1;
                    break;

                default :
                    throw new Error("Invalid port : i="+i+"d="+d);
            }

            // Appliquer
            if (port == Port.DEFAULT)
            {
                ports[Port.DEFAULT][defaultPointer++] = Fnc.conv2dto1d(x1, y1, size);
                ports[Port.DEFAULT][defaultPointer++] = Fnc.conv2dto1d(x2, y2, size);
            }
            else
            {
                ports[port][0] = Fnc.conv2dto1d(x1, y1, size);
                ports[port][1] = Fnc.conv2dto1d(x2, y2, size);
            }
        }

    }

    // --------------------
    // GETTERS

    public int getSize() {
        return size;
    }

    public int getSizePP() {
        return sizePP;
    }


    public int getRobberIndex() {
        return robberIndex;
    }

    public int getRobberPosition() {
        return robberPosition;
    }


    public int[] getDicesX() {
        return dicesX;
    }

    public int[] getDicesY() {
        return dicesY;
    }

    public int[][] getDiceIndexes() {
        return diceIndexes;
    }

    public int[][] getBiomes() {
        return biomes;
    }

    public int[][] getColonies() {
        return colonies;
    }

    public int[][] getRoadsH() {
        return roadsH;
    }

    public int[][] getRoadsV() {
        return roadsV;
    }

    // --------------------

    public boolean canBuyRoad(int who, boolean horizontal, int x, int y) {
        return horizontal ? canBuyRoadH(who, x, y) : canBuyRoadV(who, x, y);
    }

    private boolean canBuyRoadH(int who, int x, int y) {
        if (hasRoadH(x, y))
            return false;

        // COLONIES ADJACENTES
        if (hasColony(who, x, y)) 
            return true;
        if (hasColony(who, x+1, y)) 
            return true;
        
        // ROUTES HORIZONTALS
        if (x>0 && hasRoadH(who, x-1, y))
            return true;
        if ((x+1)<size && hasRoadH(who, x+1, y))
            return true;

        // ROUTE VERTICALES
        if (y>0)
            if (hasRoadV(who, x, y-1))
                return true;
            if (hasRoadV(who, x+1, y-1))
                return true;

        if (y<size)
            if (hasRoadV(who, x, y+1))
                return true;
            if (hasRoadV(who, x+1, y+1))
                return true;
            
        return false;
    }

    private boolean canBuyRoadV(int who, int x, int y) {
        if (hasRoadV(x, y))
            return false;

        // COLONIES ADJACENTES
        if (hasColony(who, x, y)) 
            return true;
        if (hasColony(who, x, y+1)) 
            return true;
        
        // ROUTES VERTICALES ADJACENTE
        if (y>0 && hasRoadV(who, x, y-1))
            return true;
        if ((y+1)<size && hasRoadV(who, x, y+1))
            return true;

        // ROUTE HORIZONTALES ADJACENTE
        if (x>0)
            if (hasRoadH(who, x-1, y))
                return true;
            if (hasRoadH(who, x-1, y+1))
                return true;

        if (x<size)
            if (hasRoadH(who, x, y))
                return true;
            if (hasRoadH(who, x, y+1))
                return true;
            
        return false;
    }

    public boolean canBuyColony(int who, int x, int y) {
        if (hasColony(x, y))
            return false;
        if (!hasNearPath(who, x, y))
            return false;
        if (hasNearColony(who, x, y))
            return false;
        return true;
    }

    public boolean canImproveColony(int who, int x, int y) {
        return hasColony(who, x, y) && !hasCity(who, x, y);
    }

    // --------------------
    // Map informations using bitwise operators

    private boolean hasNearPath(int who, int x, int y) {
        if ((x+1)<size && hasRoadH(who, x, y))
            return true;
        if ((y+1)<size && hasRoadV(who, x, y))
            return true;

        if (x>0 && hasRoadH(who, x, y))
            return true;
        if (y>0 && hasRoadV(who, x, y))
            return true;

        return false;
    }

    public boolean hasNearColony(int who, int x, int y) {
        if (x>0 && hasColony(who, x-1, y))
            return true;
        if (y>0 && hasColony(who, x, y-1))
            return true;
        
        if (x<size && hasColony(who, x+1, y))
            return true;
        if (y<size && hasColony(who, x, y+1))
            return true;

        return false;
    }

    public static int makeColony(boolean city, int who) {
        return (1) + ((city?1:0)<<1) + (who<<2);
    }

    public boolean hasColony(int x, int y) {
        return colonies[x][y]>0;
    }

    private boolean hasColony(int who, int x, int y) {
        return (colonies[x][y]>0) && ((colonies[x][y]>>2) == who);
    }

    private boolean hasCity(int who, int x, int y) {
        return ((colonies[x][y]&2)==2) && ((colonies[x][y]>>2) == who);
    }

    private boolean hasRoadH(int x, int y) {
        return roadsH[x][y] != -1;
    }

    private boolean hasRoadH(int who, int x, int y) {
        return roadsH[x][y] == who;
    }

    private boolean hasRoadV(int x, int y) {
        return roadsV[x][y] != -1;
    }

    private boolean hasRoadV(int who, int x, int y) {
        return roadsV[x][y] == who;
    }

    // --------------------
    // Other

    // It gives the ports availibles on 1d-encoded position to player p
    public void givePorts(int position, Player p) {
        for (int i = 0; i < ports.length; i++)
            for (int j : ports[i])
                if (j==position)    
                    p.addPort(i);
	}

    // obsolète
	public ArrayList<Integer> nearDicesIdx(int x, int y) {
        ArrayList<Integer> ndi = new ArrayList<Integer>();

        if (x < size && y < size && (diceIndexes[x][y] != -1))
            ndi.add(diceIndexes[x][y]);

        if (x > 0 && y > 0 && (diceIndexes[x-1][y-1] != -1))
            ndi.add(diceIndexes[x-1][y-1]);

        if (x > 0 && y < size && (diceIndexes[x-1][y] != -1))
            ndi.add(diceIndexes[x-1][y]);

        if (x < size && y > 0 && (diceIndexes[x][y-1] != -1))
            ndi.add(diceIndexes[x][y-1]);

		return ndi;
	}

	public void nearDicesToPlayer(Player p, int x, int y) {
        if (x < size && y < size && (diceIndexes[x][y] != -1))
            p.getDices()[x][y]++;

        if (x > 0 && y > 0 && (diceIndexes[x-1][y-1] != -1))
            p.getDices()[x-1][y-1]++;

        if (x > 0 && y < size && (diceIndexes[x-1][y] != -1))
            p.getDices()[x-1][y]++;

        if (x < size && y > 0 && (diceIndexes[x][y-1] != -1))
            p.getDices()[x][y-1]++;
	}

}
