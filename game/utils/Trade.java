package game.utils;

import java.util.Random;
import game.constants.*;

public class Trade {

    private game.state.State state;
    private Random rnd = new Random();
    
    public Trade(game.state.State state) {
        this.state = state;
    }

    // -------------------------
    // Can section

    public boolean canBuyDevelop(int who) {
        return state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.WHEAT, 1,
                Ressource.SHEEP, 1,
                Ressource.ROCK, 1
            )
        );
    }

    public boolean canBuyRoad(int who, boolean horizontal, int x, int y) {
        if (x < 0 || y < 0) 
            return false;

        if (horizontal) {
            if (
                x >= state.getMap().getSize() || 
                y >= state.getMap().getSizePP()
            ) return false;
        } else { 
            if (
                x >= state.getMap().getSizePP() || 
                y >= state.getMap().getSize()
            ) return false;
        }
        
        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1
            ))) return false;

        return state.getMap().canBuyRoad(who, horizontal, x, y);
    }

    public boolean canBuyColony(int who, int x, int y) {
        if (x < 0 || 
            y < 0 || 
            x > state.getMap().getSizePP() || 
            y > state.getMap().getSizePP()
            ) return false;

        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1,
                Ressource.SHEEP, 1,
                Ressource.WHEAT, 1
            ))) return false;

        return state.getMap().canBuyColony(who, x, y);
    }

    public boolean canImproveColony(int who, int x, int y) {
        if (x < 0 || 
        y < 0 || 
        x > state.getMap().getSizePP() || 
        y > state.getMap().getSizePP()
        ) return false;


        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessources(
                Ressource.ROCK, 4,
                Ressource.WHEAT, 2
            ))) return false;

        return state.getMap().canImproveColony(who, x, y);
    }

    public boolean canTrade(Offer offer) {
        return offer.isValidOffer(state);
    }

    // -------------------------
    // Action section

    /**
     * 
     * Warning :
     * Avant d'affection une action
     * veuillez verifier qu'elle peut être
     * effectuer via les fonctions canXXX
     * 
     */

    public void buyDevelop(int who) {
        Offer.lose(state.getPlayer(who), Offer.makeRessources(
                Ressource.WHEAT, 1,
                Ressource.SHEEP, 1,
                Ressource.ROCK, 1
            ));

        // carte developpement aleatoire
        int developpement = rnd.nextInt(Developpement.nDeveloppements);
        state.getPlayer(who).addDeveloppement(developpement);

    }

    public void buyRoad(int who, boolean horizontal, int x, int y) {
        // pay
        Offer.lose(state.getPlayer(who), Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1
            ));

        //action
        state.addRoad(who, horizontal, x, y);
    }

    public void buyColony(int who, int x, int y) {
        // pay
        Offer.lose(state.getPlayer(who), Offer.makeRessources(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1,
                Ressource.SHEEP, 1,
                Ressource.WHEAT, 1
            ));

        // action
        state.addColony(who, x, y);
    }

    public void improveColony(int who, int x, int y) {
        // pay
        Offer.lose(state.getPlayer(who), Offer.makeRessources(
            Ressource.ROCK, 4,
            Ressource.WHEAT, 2
        ));

        // action
        state.improveColony(who, x, y);
    }

    public void trade(Offer offer) {
        offer.proceed(state);
    }

}
