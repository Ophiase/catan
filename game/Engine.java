package game;

import game.state.*;
import game.utils.*;

public class Engine {
    
    // GAME DATA
    private Config conf;

    private Map map;
    private State state;
    private Dices dices;
    private Trade trade;


    // LAUNCH THE GAME
    public Engine(Config conf) {
        this.conf   = conf;

        this.dices  = new Dices(conf);
        this.map    = new Map(conf);
        this.state  = new State(conf, map);
        this.trade  = new Trade(state);

        // ----------------

    }

    // ---------------------------

    public Config getConf() {
        return conf;
    }

    public Map getMap() {
        return map;
    }

    public State getState() {
        return state;
    }

    public Dices getDices() {
        return dices;
    }

    public Trade getTrade() {
        return trade;
    }

    // ---------------------------
    
}
