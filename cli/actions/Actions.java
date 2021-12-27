package cli.actions;

import java.security.InvalidParameterException;
import java.util.Scanner;

import cli.Utils;
import game.Engine;
import game.constants.Developpement;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Trade;

public class Actions {

    Scanner sc;

    Engine engine;
    State state;
    Map map;
    Trade trade;

    public Actions(Engine engine, Scanner sc) {
        this.sc = sc;
        this.engine = engine;
        this.state = engine.getState();
        this.map = state.getMap();
        this.trade = engine.getTrade();
    }

    // ----------------------------------------
    // Interface

    public void use(int who, int card) {
        Player p = state.getPlayer(who);

        if (!p.hasDeveloppement(card))
        {
            System.out.println("You don't have this card.");
            return;
        }

        System.out.println("You use the card : " + Utils.makeFirstWord(Developpement.toString(card)));
        switch (card) {
            case Developpement.KNIGHT: {
                System.out.println("Choose {x} {y}.");
                String[] args = Utils.input().split(" ");
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);

                if (!map.canMoveRobber(x, y))
                    throw new Error();
                
                map.moveRobber(x, y);
            } break;
            case Developpement.ROAD: {

            } break;
            case Developpement.PLENTY: {
            
            } break;
            case Developpement.MONOPOLY: {

            } break;
            case Developpement.POINT: {
                System.out.println("You gained one point.");
                System.out.println("Now you have " + (++p.getRessources()[Ressource.POINT]) + " points !");
            } break;            
        }
    }

    public void pick(int who) {
        if (!trade.canBuyDevelop(who))
        {
            System.out.println("You don't have enough ressources.");
            return;
        }

        int dev = trade.buyDevelop(who);
        System.out.println("You obtains : " + Utils.makeFirstWord(Developpement.toString(dev)) + ".");
    }

    public void road(int who, String horizontalString, int x, int y) {
        boolean horizontal = false;

        if (horizontalString.toUpperCase().equals("H"))
            horizontal = true;
        else if (!horizontalString.toUpperCase().equals("V"))
        {
            System.out.println("Invalid command. Try Again.");
            return;
        }

        if (!trade.canBuyRoad(who, horizontal, x, y))
        {
            System.out.println("You cannot buy this road.");
            return;
        }

        trade.buyRoad(who, horizontal, x, y);
    }

    public void colony(int who, int x, int y) {
        if (!trade.canBuyColony(who, x, y))
        {
            System.out.println("You cannot buy this colony.");
            return;
        }

        trade.buyColony(who, x, y);
    }

    public void city(int who, int x, int y) {
        if (!trade.canImproveColony(who, x, y))
        {
            System.out.println("You cannot buy this city.");
            return;
        }

        trade.improveColony(who, x, y);
    }

    public void trade(int who, String[] args) {

    }

    public void buy(int who, String[] args) {
        
    }

    // -------------------------------------------

    
    
}
