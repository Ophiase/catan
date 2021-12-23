package game.utils;

import java.util.Random;

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
            Offer.makeRessource(
                Ressource.WHEAT, 1,
                Ressource.SHEEP, 1,
                Ressource.ROCK, 1
            )
        );
    }

    public boolean canBuyRoad(int who) {
        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessource(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1
            ))) return false;

        // ETC ...

        return true;
    }

    public boolean canBuyColony(int who) {
        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessource(
                Ressource.BRICK, 1,
                Ressource.WOOD, 1,
                Ressource.SHEEP, 1,
                Ressource.WHEAT, 1
            ))) return false;

        // ETC ...

        return true;
    }

    public boolean canImproveColony(int who) {
        if (!state.getPlayer(who).hasRessources(
            Offer.makeRessource(
                Ressource.ROCK, 4,
                Ressource.WHEAT, 2
            ))) return false;

        // ETC ...

        return true;
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
        int[] r = state.getPlayer(who).getRessources();

        r[Ressource.WHEAT]--;
        r[Ressource.SHEEP]--;
        r[Ressource.ROCK]--;

        // carte developpement aleatoire
        int developpement = rnd.nextInt(Developpement.nDeveloppements);
        state.getPlayer(who).addDeveloppement(developpement);

    }

    public void buyRoad(int who) {
        
    }

    public void buyColony(int who) {
        
    }

    public void improveColony(int who) {
        
    }

    public void trade(Offer offer) {
        
    }

}
