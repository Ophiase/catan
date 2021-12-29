package game;

import game.state.Player;
import game.state.State;
import game.utils.Trade;
import game.utils.Dices;
import game.utils.Fnc;
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
    public void play(Player bot) {
        // can improve colony ?
        // can buy colony ?
        // can buy road ?
    }

    /**This method Returns true when bot p2 consent to the offer.*/
    public boolean consent(Player bot, Offer offer) {
        if (Offer.countRessources(offer.r1) < Offer.countRessources(offer.r2))
            return false;

        return Fnc.rand(2) == 1;
    }

    /** Choose cards to abandon 
     * By default it will abandon developpements cards
     * For bots, usage of those cards is not implemented yet
    */
    public void retribution(Player bot) {
        int toAbandon = bot.nCards()/2;
    }

    /** Chose where to place the robber */
    public void steal(Player bot) {
    }
    
}
