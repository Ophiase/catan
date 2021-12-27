package game;

import game.state.State;
import game.utils.Trade;
import game.utils.Dices;

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
    
}
