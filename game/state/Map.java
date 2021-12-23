package game.state;

/**
 * TODO : 
 * 
 * Emplacement du voleur
 * Emplacement des ports
 * Emplacement des maisons
 * Emplacement des routes
 * Biomes
 * 
 */

public class Map {

    // --------------------

    private int size;

    private int fiefIndex;
    private int fiefX;
    private int fiefY;

    private int[][] biomes; // biomes
    private int[][] houses; // maisons
    private int[][] roadsH; // routes horizontals
    private int[][] roadsV; // routes verticles
    
    // --------------------

    public Map(game.Config conf) {
        this.size = conf.getSizeOfMap();

        init();
    }

    private void init() {

    }

    // --------------------

    
}
