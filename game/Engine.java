package game;

import game.state.*;
import game.utils.*;

public class Engine {
    
    // GAME DATA
    private Map map;
    private State state;
    private Dices dices;


    // LAUNCH THE GAME
    public Engine(Config conf) {

        map = new Map(conf);
        state = new State(conf);
        dices = new Dices(conf);

        // ----------------

    }
    
}
