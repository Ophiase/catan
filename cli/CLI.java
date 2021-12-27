package cli;

import java.util.Scanner;

import game.Config;
import game.Engine;
import game.state.Player;
import cli.actions.*;
import cli.visuals.*;

public class CLI {

    public static Scanner sc = new Scanner(System.in);

    public CLI() {
        while (true) {
            Utils.clear();

            play();

            System.out.println();
            System.out.println("Do you want to play again ? (y/n)");
            String r = sc.nextLine();

            if (r.isEmpty() || !(r.toLowerCase().charAt(0) == 'y'))
                break;
        }
    }

    // --------------------------------

    Config config;
    Engine engine;

    Put put;
    Resume resume;

    // --------------------------------

    private void play() {
        initConfig();
        initEngine();

        GameLoop gl = new GameLoop(this);
        while (gl.gameloop());
    }

    private void initConfig() {
        boolean validConfig = false;
        
        while (!validConfig)
        {
            System.out.println(Utils.readAll("config_helper.txt"));
            Utils.delim();
            System.out.println("Enter your config :");
            System.out.print("> ");
            String arg = sc.nextLine();

            try {
                if (arg.startsWith("exit"))
                    Utils.exit();
                
                if (arg.isBlank()) {
                    System.out.println("You choose default config.");
                    config = Config.DEFAULT();
                } else if (arg.startsWith("preset")) {

                    switch (arg.split(" ")[1].toUpperCase()) {                        
                        case "DEFAULT": config = Config.DEFAULT(); break;
                        default: throw new Exception();
                    }
                
                } else if (arg.startsWith("config")) {
                    
                    String[] args = arg.split(" ");
                    config = new Config(
                        Integer.parseInt(args[0]), 
                        Integer.parseInt(args[1]), 
                        Integer.parseInt(args[2]), 
                        Integer.parseInt(args[3])
                        );

                } else { throw new Exception(); }

                validConfig = true;
            }

            catch (Exception e) {
                System.out.println("Invalid parameters, please try again !");
            }

            System.out.println();
            Utils.delim();
        }
    }

    private void initEngine() {
        engine  = new Engine(config);
        put     = new Put(engine);
        resume  = new Resume(engine);

        // ---------------

        // Pour chaque joueur placer première colonie/route
        for (Player p: engine.getState().getPlayers())
        {
            System.out.println(p + " has to choose a road and a colony.");

            if (p.isBot())
                askBotFirstCity(p);
            else
                askUserFirstCity(p);

            engine.endTurn();
        }

        // Pour chaque joueur placer seconde colonie/route
        for (Player p: engine.getState().getPlayers())
        {
            System.out.println(p + " has to choose a road and a colony.");

            if (p.isBot())
                askBotFirstCity(p);
            else
                askUserFirstCity(p);

            engine.endTurn();
        }

        System.out.println();
        resume.showMap();
        System.out.println();
        Utils.delim();
    }

    private void askUserFirstCity(Player p) {
        System.out.println();
        resume.showMap();
        System.out.println();
        Utils.delim();

        game.state.Map map = engine.getMap();

        System.out.println("Enter a location for your colony.");
        System.out.println("> {x} {y}");
        System.out.println("Exemple:");
        System.out.println("> 1 3");

        while (true)
        {
            try {
                System.out.print("> ");
                String[] args = sc.nextLine().split(" ");

                if (args[0].startsWith("exit"))
                    Utils.exit();

                int who = p.getIndex();
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);

                if (!map.canBuyColony(who, x, y)) 
                    throw new Exception();

                map.canBuyColony(who, x, y);

                break;
            } catch (Exception e) {
                System.out.println("Error, try again.");
            }
        }

        System.out.println("Enter a location for your road.");
        System.out.println("> (h/v) {x} {y}");
        System.out.println("Exemple:");
        System.out.println("> h 1 3");

        while (true)
        {
            try {
                System.out.print("> ");
                String[] args = sc.nextLine().split(" ");

                if (args[0].startsWith("exit"))
                    Utils.exit();

                int who = p.getIndex();
                boolean horizontal = args[0].toUpperCase().equals("H");
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);

                if (!map.canBuyRoad(who, horizontal, x, y)) 
                    throw new Exception();

                map.canBuyRoad(who, horizontal, x, y);

                break;
            } catch (Exception e) {
                System.out.println("Error, try again.");
            }
        }

    }

    private void askBotFirstCity(Player p) {
        
        
        System.out.println(p + " played ...");
    }
}
