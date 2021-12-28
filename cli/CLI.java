package cli;

import java.util.Scanner;

import game.Config;
import game.Engine;
import game.state.Player;
import cli.actions.*;
import cli.visuals.*;

/**
 * Define an instance of the game playing in command line.
 */
public class CLI {

    public static Scanner sc = new Scanner(System.in);

    public CLI() {
        while (true) {
            Utils.clear();

            play();

            System.out.println();
            System.out.println("Do you want to play again ? (y/n)");
            String r = Utils.input();

            if (r.isEmpty() || !(r.toLowerCase().charAt(0) == 'y'))
                break;
        }
    }

    // --------------------------------

    Config config;
    Engine engine;

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
            String arg = Utils.input();

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
            Utils.delim();
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
            Utils.delim();
        }
    }

    private void askUserFirstCity(Player p) {
        System.out.println();
        resume.resume();

        game.state.Map map = engine.getMap();
        int who = p.getIndex();

        while (true) try {
            // INPUTS
                System.out.println("Enter a location for your colony.");
                System.out.println("\t> {x} {y}");
                System.out.println("\tExemple:");
                System.out.println("\t\t> 1 3");

                String[] args1 = Utils.input().split(" ");
                if (args1[0].startsWith("exit"))
                            Utils.exit();

                System.out.println("Enter a location for your road.");
                System.out.println("\t> (h/v) {x} {y}");
                System.out.println("\tExemple:");
                System.out.println("\t\t> h 1 3");

                String[] args2 = Utils.input().split(" ");
                if (args2[0].startsWith("exit"))
                            Utils.exit();


            // STORE INPUTS
                int x1 = Integer.parseInt(args1[0]);
                int y1 = Integer.parseInt(args1[1]);
                boolean horizontal = args2[0].toUpperCase().equals("H");
                int x2 = Integer.parseInt(args2[1]);
                int y2 = Integer.parseInt(args2[2]);

            // VERIFIY INPUTS
                if (map.hasColony(x1, y1) || map.hasNearColony(who, x1, y1)) 
                    throw new Exception();

                
                // placer la colonie temporairement (pour verif)
                map.getColonies()[x1][y1] = map.makeColony(false, who);
                if (!map.canBuyRoad(who, horizontal, x2, y2)) 
                    throw new Exception();
                // retirer la colonie de verification
                map.getColonies()[x1][y1] = 0;

            // ACTION        
                engine.getState().addColony(who, x1, y1);
                engine.getState().addRoad(who, horizontal, x2, y2);

            break;
        } catch (Exception e) {
            System.out.println("Error, try again.");
        }

    }

    //TODO
    private void askBotFirstCity(Player p) {
        int who = p.getIndex();
        int sizepp = engine.getMap().getSizePP();
        int[] priority = game.utils.Fnc.randomIndexArray(sizepp);
        game.state.Map map = engine.getMap();

        for (int i = 0; i < priority.length; i++)
        {
            int cx = game.utils.Fnc.conv1dto2d_x(priority[i], sizepp);
            int cy = game.utils.Fnc.conv1dto2d_y(priority[i], sizepp);
            if (map.hasColony(cx, cy) || map.hasNearColony(p.getIndex(), cx, cy))
                continue;

            
            boolean foundRoad = false;
            boolean h = false;
            int rx = 0;
            int ry = 0;
            map.getColonies()[cx][cy] = map.makeColony(false, who);
            
            map.getColonies()[cx][cy] = 0;
            if (!foundRoad)
                continue;

            // ACTION
            engine.getState().addColony(who, cx, cy);
            engine.getState().addRoad(who, h, rx, ry);

            break;
        }
        
        System.out.println(p + " played ...");
    }
}
