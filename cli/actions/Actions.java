package cli.actions;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

import cli.Utils;
import game.Engine;
import game.constants.Developpement;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Offer;
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
                System.out.print("Enter first road :");
                String[] r1 = Utils.input().split(" ");
                System.out.println("Enter second road : ");
                String[] r2 = Utils.input().split(" ");

                boolean h1 = r1[0].toUpperCase().equals("H");
                boolean h2 = r2[0].toUpperCase().equals("H");;
                int x1 = Integer.parseInt(r1[1]);
                int x2 = Integer.parseInt(r2[1]);
                int y1 = Integer.parseInt(r1[2]);
                int y2 = Integer.parseInt(r2[2]);

                if (!engine.getMap().canBuyRoad(who, h1, x1, y1) 
                ||  !engine.getMap().canBuyRoad(who, h2, x2, y2)) {
                    System.out.println("You can't take those roads");
                    return;
                }

                state.addRoad(who, h1, x1, y1);
                state.addRoad(who, h2, x2, y2);
            } break;
            case Developpement.PLENTY: {
                System.out.println("Choose two ressources : ");
                System.out.println("\tExemple :");
                System.out.println("\t\t> SHEEP SHEEP");
                String[] args = Utils.input().split(" ");
                
                int[] rsc = Offer.makeRessources(
                    Ressource.StringToInt(args[0]), 1, 
                    Ressource.StringToInt(args[1]), 1
                    );

                if ((rsc[Ressource.POINT] != 0) || 
                    (Offer.countRessources(rsc) != 2)
                ) {
                    System.out.println("Incorrect request.");
                    return ;
                }

                p.addRessources(rsc);
            } break;
            case Developpement.MONOPOLY: {
                System.out.println("Choose a type.");
                int type = Ressource.StringToInt(Utils.input());
                if (type == Ressource.POINT)
                {
                    System.out.println("Invalid type.");
                    return;
                }

                for (Player k: state.getPlayers()) if (k!=p) {
                    // steal the ressource
                    int[] rsc = k.getRessources();
                    System.out.println(
                        p+" steals "+rsc[type]+"x"+Utils.makeFirstWord(Ressource.toString(type))+
                        " to"+k+".");
                    p.getRessources()[type] += rsc[type];
                    rsc[type] = 0;
                }
            } break;
            case Developpement.POINT: {
                System.out.println("You gained one point.");
                System.out.println("Now you have " + (++p.getRessources()[Ressource.POINT]) + " points !");
            } break;            
        }

        p.useDeveloppement(card);
        System.out.println(p+" used "+Developpement.toString(card).toLowerCase()+".");
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
        // build/parse offer
        int[] r1 = new int[Ressource.nRessources];
        int[] r2 = new int[Ressource.nRessources];

        boolean begin = true;
        for (int i = 2; i < args.length; i++)
        {
            if (begin) {
                if (args[i].toLowerCase().equals("against"))
                {
                    begin = false;
                    continue;
                }

                String[] arg = args[i].split("_");
                r1[Ressource.StringToInt(arg[1])] += Integer.parseInt(arg[0]);
            } else {
                String[] arg = args[i].split("_");
                r2[Ressource.StringToInt(arg[1])] += Integer.parseInt(arg[0]);
            }
        }

        game.utils.Offer offer = new game.utils.Offer(who, Integer.parseInt(args[1]), r1, r2);

        Utils.debug("Offer was parsed properly.");

        // is valid offer
        if (!trade.canTrade(offer))
        {
            System.out.println("Invalid offer.");
            return;
        }

        // ask user
        Player p2 = state.getPlayer(offer.p2);
        System.out.println("Doe "+p2+" consent to the offer? (y/n)");
        boolean consent = false;
        if (p2.isBot())
            consent = engine.getAI().consent(p2, offer);
        else
            consent = Utils.input().toUpperCase().equals("Y");
        
        if (!consent)
        {
            System.out.println(p2+" refused your trade.");
            return;
        }

        // proceed

        trade.trade(offer);
        System.out.println("Successfull exchange.");
    }

    public void buy(int who, String[] args) {
        // build/parse offer
        int[] r1 = new int[Ressource.nRessources];
        int[] r2 = new int[Ressource.nRessources];

        boolean begin = true;
        for (int i = 1; i < args.length; i++)
        {
            if (begin) {
                if (args[i].toLowerCase().equals("against"))
                {
                    begin = false;
                    continue;
                }

                String[] arg = args[i].split("_");
                r1[Ressource.StringToInt(arg[1])] += Integer.parseInt(arg[0]);
            } else {
                r2[Ressource.StringToInt(args[i])]++;
            }
        }
        game.utils.Offer purchase = new game.utils.Offer(
            who, -1, r1, r2);

        Utils.debug("Offer was parsed properly.");

        // is valid offer

        if (!trade.canBuy(purchase)) {
            System.out.println("Transaction refused.");
            return;
        }

        // proceed

        trade.buy(purchase);
        System.out.println("Successfull transaction.");
    }

    // -------------------------------------------

    
    
}
