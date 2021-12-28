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

    public boolean play() {
        return false;
    }

    /**
     * This method Returns true when bot p2 consent to the offer.
     * 
     * @param p2 Bot
     */
    public boolean consent(Player p2, Offer offer) {
        return false;
    }
    
}
