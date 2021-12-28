package cli;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

import game.Engine;
import game.constants.Developpement;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.utils.Fnc;
import game.utils.Offer;

/**
 * This class defines the how a loop/time unit of the game should pass.
 */
public class GameLoop {

    CLI cli;

    Engine engine;
    Scanner sc;
    cli.actions.Actions actions;
    
    public GameLoop(CLI cli) {
        this.cli = cli;
        this.engine  = cli.engine;
        this.sc = cli.sc;

        actions = new cli.actions.Actions(engine, sc);
    }

    // --------------------------------

    /**
     * This function represent a time unit of the game.
     */
    public boolean gameloop() {
        Utils.clear();

        int     time  = engine.getState().getTime();
        int     focus = engine.getState().getFocus();
        boolean isBot = engine.getState().focusOnBot();

        Utils.delim();
        System.out.println();
        System.out.println("Time : " + time);
        System.out.println("Focus : " + (isBot ? "Bot" : "Player") + " (" + focus + ")");
        System.out.println();
        cli.resume.resume();
        System.out.println();

        // -------------------------------------

        if (isBot)
            botTurn();
        else
            playerTurn();

        // -------------------------------------

        int won = engine.won();
        if (won != -1) {
            System.out.println("Player " + won + " won !!!");
            return false;
        }

        engine.endTurn();

        return true;
    }

    // --------------------------------

    private void playerTurn() {
        int who = engine.getState().getFocus();
        
        System.out.println("Enter any key to roll");
        Utils.input();
        int score = dicesRoll(who);

        while (true) {
            System.out.println("Enter an action. Type help to have more Information.");
            String[] args = Utils.input().split(" ");

            try {
                if (args.length > 0) switch(args[0]) {

                    case "resume":
                        cli.resume.resume();
                        break;

                    // ----------------

                    case "inventory":
                        cli.resume.showInventory(who);
                        break;
                    case "use" :
                        actions.use(who, Developpement.StringToInt(args[1]));
                        break;
                    case "pick" :
                        actions.pick(who);
                        break;

                    // ----------------

                    case "road":
                        actions.road(
                            who, 
                            args[0], 
                            Integer.parseInt(args[1]), 
                            Integer.parseInt(args[2]));
                        break;
                    case "colony":
                        actions.colony(
                            who,
                            Integer.parseInt(args[0]), 
                            Integer.parseInt(args[1]));
                        break;
                    case "city":
                        actions.city(
                            who,
                            Integer.parseInt(args[0]), 
                            Integer.parseInt(args[1]));
                        break;

                    // ----------------
                    
                    case "trade":
                        actions.trade(who, args);
                        break;
                    case "buy":
                        actions.buy(who, args);
                        break;

                    // ----------------

                    case "end":
                        return;
                    case "metadata":
                        cli.resume.metadata();
                        break;
                    case "help":
                        Utils.clear();
                        Utils.help();
                        break;
                    case "exit":
                        Utils.exit();
                }
            } catch (Exception e) { Utils.error(); }
        }
    }

    private void botTurn() {
        int who = engine.getState().getFocus();
        int score = dicesRoll(who);
        engine.getAI().play();
    }

    private int dicesRoll(int who) {
        int score = engine.getDices().roll();
        System.out.println("Dices gave : " + score);
        
        if (score == engine.getDices().getRobberDice()) { // DEFAULT = 7
            retribution();
            steal(who);
        } else {
            engine.getState().collect(score);
        }

        return score;
    }


    /**
     * After a 7 at dices.
     * Each player that have above 7 cards 
     * has to abandon half of them.
     */
    private void retribution() {
        for (Player p: engine.getState().getPlayers()) if (p.nCards() > engine.getDices().getRobberDice()) {
            if (p.isBot())
            {
                engine.getAI().retribution(p);
                continue;
            }

            boolean asking = true;

            while (asking)
            {
                try {
                    System.out.println("Choose "+(p.nCards()/2)+" cards to abandon");
                    System.out.println("\texemple: ");
                    System.out.println("\t\t> KNIGHT ROCK SHEEP");
                    
                    String[] args = Utils.input().split(" ");
                    ArrayList<Integer> rsc = new ArrayList<Integer>();
                    ArrayList<Integer> devs = new ArrayList<Integer>();
                    
                    if (args.length != (p.nCards()/2))
                        throw new Exception();

                    for (int i = 0, j = 0; i < args.length; i++)
                    {
                        int[] parse = cardToInt(args[i]);
                        if (parse[1]==0)
                            rsc.add(parse[0]);
                        else
                            devs.add(parse[1]);
                    }

                    for (int i : rsc)
                        p.getRessources()[i]--;
                    for (int i : devs)
                        p.getDeveloppements()[i]--;

                    asking = false;
                } catch (Exception e) {}
            }

        }
    }

    /**
     * Determines if a card is valid and its type.
     * output[0] = type of the card
     * output[1] = 0 for ressources / 1 for developpement
     */
    private int[] cardToInt(String str) {
        try {
            return new int[] { Ressource.StringToInt(str), 0};
        } catch(Exception e) {}
        try {
            return new int[] { Developpement.StringToInt(str), 1};
        } catch(Exception e) {}

        throw new InvalidParameterException();
    }

    /**
     * After a 7 at dices
     * Player choose where to put the robber.
     * And get steal a ressource.
     */
    private void steal(int who) {
        Player currentPlayer = engine.getState().getPlayer(who);
        if (currentPlayer.isBot())
        {
            engine.getAI().steal(currentPlayer);
            return;
        }

        while (true) try {
            // move robber
            System.out.println("Put the robber somewhere. {x} {y}");
            String[] args = Utils.input().split(" ");
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            if (!engine.getMap().canMoveRobber(x, y))
                throw new Exception();

            Map map = engine.getMap();
            map.moveRobber(x, y);
            int idx = map.getRobberIndex();

            // choose a victim
            ArrayList<Player> victims = new ArrayList<Player>();
            for(Player p: engine.getState().getPlayers())
                if (p != currentPlayer && p.getDicesIdx().contains(idx))
                    victims.add(p);

            if (victims.isEmpty())
                return;

            // stole him
            Player victim = victims.get(Fnc.rand(victims.size()));
            int[] rsc = victim.getRessources();
            int[] priority = Fnc.randomIndexArray(rsc.length-1);
            for (int i = 0, r = 0; i < priority.length; i++)
                if (rsc[r=(priority[i]+1)]!=0)
                {
                    rsc[r]--;
                    currentPlayer.getRessources()[r]++;
                    
                    System.out.println(currentPlayer+" stole "+Ressource.toString(r).toLowerCase()+" to "+victim+".");
                    return;
                }

            return;
        } catch (Exception e) {}
    }
}
