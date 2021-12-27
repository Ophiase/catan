package cli;

import java.util.Scanner;

import game.Engine;

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
        System.out.print("> "); sc.nextLine();

        int score = engine.getDices().roll();
        System.out.println("Dices gave : " + score);
        
        if (score == engine.getDices().getRobberDice()) { // DEFAULT = 7

            // retribution

        } else {
            engine.getState().collect(score);
        }

        while (true) {
            System.out.println("Enter an action. Type help to have more Information.");
            System.out.print("> ");
            String[] args = sc.nextLine().split(" ");
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
                        actions.use();
                        break;
                    case "pick" :
                        actions.pick();
                        break;

                    // ----------------

                    case "road":
                        actions.road();
                        break;
                    case "colony":
                        actions.colony();
                        break;
                    case "city":
                        actions.city();
                        break;

                    // ----------------
                    
                    case "trade":
                        actions.trade();
                        break;
                    case "buy":
                        actions.buy();
                        break;

                    // ----------------

                    case "end":
                        return;
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
        
        int score = engine.getDices().roll();
        System.out.println("Dices gave : " + score);
        
        if (score == engine.getDices().getRobberDice()) { // DEFAULT = 7

            // retribution

        } else {
            engine.getState().collect(score);
        }

        boolean interupt = engine.getAI().play();
    }
}
