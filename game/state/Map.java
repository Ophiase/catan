package game.state;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import game.constants.Biome;
import game.utils.Fnc;

public class Map {

    private int size;
    private int sizePP;

    private int fiefIndex;
    private int fiefX;
    private int fiefY;

    
    /**
     * Encodage des colonies :
     * 2 premier bits   <=> 00 vide / 01 colonie /  11 ville
     * bits qui suivent <=> index joueur
     * Exemple :
     * ....1110 on le sépare en 2: 0000011 et 11
     * Donc joueur 11(2) = 3
     *      et une ville car 11(2) = 2
    */

    private int[][] diceIndexes;    // nombres aux dés
    private int[][] biomes;         // biomes
    private int[][] colonies;       // colonies
    private int[][] roadsH;         // routes horizontals
    private int[][] roadsV;         // routes verticles
    
    // --------------------

    public Map(game.Config conf) {
        this.size   = conf.getSizeOfMap();
        this.sizePP = size+1;

        diceIndexes     = new int[size  ][size  ];
        biomes          = new int[size  ][size  ];
        colonies        = new int[sizePP][sizePP];
        roadsH          = new int[size  ][sizePP];
        roadsV          = new int[sizePP][size  ];

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
        }

        // ----------------
        // fill roads

        for (int[] row: roadsH)
            Arrays.fill(row, -1);
        for (int[] row: roadsV)
            Arrays.fill(row, -1);

    }

    // --------------------
    // GETTERS

    public int getSize() {
        return size;
    }

    public int getSizePP() {
        return sizePP;
    }


    public int getFiefIndex() {
        return fiefIndex;
    }

    public int getFiefX() {
        return fiefX;
    }

    public int getFiefY() {
        return fiefY;
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
        return false;
    }

    public boolean canImproveColony(int who, int x, int y) {
        return false;
    }

    // --------------------
    // Map informations using bitwise operators

    private boolean hasColony(int who, int x, int y) {
        return (colonies[x][y]>0) && ((colonies[x][y]>>2) == who);
    }

    private boolean hasCity(int who, int x, int y) {
        return ((colonies[x][y]&2)==2) && ((colonies[x][y]>>2) == who);
    }

    private boolean hasRoadH(int who, int x, int y) {
        return roadsH[x][y] == who;
    }

    private boolean hasRoadV(int who, int x, int y) {
        return roadsV[x][y] == who;
    }

    // --------------------

}
