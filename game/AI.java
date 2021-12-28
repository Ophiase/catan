package game;

import game.state.Player;
import game.state.State;
import game.utils.Trade;
import game.utils.Dices;
import game.utils.Offer;

/**
 * Artificial intelligence
 * This class contains methods that
 * define how bots should behave.
 * 
 */

public class AI {

    private State state;
    private Trade trade;
    private Dices dices;

    public AI(State state, Trade trade, Dices dices) {
        this.state = state;
        this.trade = trade;
        this.dices = dices;
    }

    /** TODO: echanges spontanés */
    public void play() {
    }

    /**This method Returns true when bot p2 consent to the offer.*/
    public boolean consent(Player bot, Offer offer) {
        return false;
    }

    /** Choose cards to abandon */
    public void retribution(Player bot) {

    }

    /** Chose where to place the robber */
    public void steal(Player bot) {
    }
    
}
