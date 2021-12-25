package cli;

import java.util.Scanner;

import game.Config;
import game.Engine;
import cli.actions.*;
import cli.visuals.*;

public class CLI {

    public static Scanner sc = new Scanner(System.in);

    public CLI() {
        while (true) {

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

        while (gameloop()) {}
    }

    private void initConfig() {
        boolean validConfig = false;
        
        while (!validConfig)
        {
            System.out.println(Utils.readAll("configHelper.txt"));
            Utils.delim();
            System.out.println("Enter your config :");
            System.out.print("> ");
            String arg = sc.nextLine();

            try {
                if (arg.startsWith("exit"))
                    Utils.exit();

                if (arg.startsWith("preset")) {

                    switch (arg.split(" ")[1].toUpperCase()) {                        
                        case "DEFAULT": config = Config.DEFAULT(); break;
                        default: throw new Exception();
                    }
                
                } else if (arg.startsWith("config")) {
                    
                    String[] args = arg.split(" ");
                    config = new Config(
                        game.Config.N_PARTICIPANTS - Integer.parseInt(args[1]), 
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

        resume.showMap();

        // ---------------

        // Pour chaque joueur placer première colonie/route
        for (int i = 0; i < game.Config.MIN_PLAYER; i++) {
            
        }
    }

    private boolean gameloop() {
        if (sc.nextLine().equals("exit"))
            Utils.exit();

        // -------------------------------------

        int won = engine.won();
        if (won != -1) {
            System.out.println("Player " + won + " won !!!");
            return false;
        }

        engine.endTurn();

        return true;
    }
}
