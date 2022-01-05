package gui;

import game.Engine;
import game.state.Player;
import gui.gamepanel.GameScreen;
import gui.gamepanel.context.MapContext;

/**
 * Like the GameLoop class of cli,
 * this class manage what happens in game.
 * This class decides when to give the focus on player etc..
 */
public class GameLoopGUI {

    /**
     * <p>Cheap solution to make the gameloop wait when it need a response.
     * <p>
     * <p>TODO: find a better solution that take 
     * into account potentials multithreading issues
     */
    public boolean flowing;

    // ----------------------------------

    private static final long BOT_SLEEP_TIME = 1000;
    private static final long DELAY = 300;
    private static final long LAG = 100;

    // ----------------------------------

    GameScreen gameScreen;
    Engine engine;

    public GameLoopGUI(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void publish(String s) {         
        gameScreen.informationContext.publish(s);
    }

    /**
     * TODO:
     * differenciate readable delay and short delay
     */
    public void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (Exception e) {}
    }

    /** 
     * <p>Dans le doute je prefere couper le flow
     * avant de laisser à une class la possibiliter de le relancer.
     * <p>
     * <p>J'évite le risque que je propose à une class une action
     * Qu'elle mette le flow à true trop vite
     * (avant l'execution de wait for flow)
     * Et que wait for flow le coupe juste après (boucle infini)
     */
    public void interupFlow() {
        flowing = false;
    }
    public void waitForFlow() {
        try {
            while (!flowing) 
                Thread.sleep(LAG);
        } catch (Exception e) {}
    }

    // ----------------------------------

    public void init() {
        engine = gameScreen.engine;

        flowing = true;

        publish("Game started.");
        delay();
        publish("Init phase !");
        delay();

        // -----------------------
        
        // Pour chaque joueur placer première colonie/route
        for (Player p: engine.getState().getPlayers())
        {
            publish(p + " has to choose a road and a colony.");
            delay();

            if (p.isBot())
                askBotFirstCity(p);
            else
                askUserFirstCity(p);

            engine.endTurn();
            gameScreen.mapContext.updateMap();
            gameScreen.updateContextes();
        }

        // Pour chaque joueur placer seconde colonie/route
        for (Player p: engine.getState().getPlayers())
        {
            publish(p + " has to choose a road and a colony.");
            delay();

            if (p.isBot())
                askBotFirstCity(p);
            else
                askUserFirstCity(p);

            engine.endTurn();
            gameScreen.mapContext.updateMap();
            gameScreen.updateContextes();
        }

        // -----------------------

        gameloop();
    }

    private void askUserFirstCity(Player p) {
        interupFlow();
        gameScreen.mapContext.setState(MapContext.PUT_FIRST_COLONY_STATE);
        waitForFlow();

        interupFlow();
        gameScreen.mapContext.setState(MapContext.PUT_ROAD_STATE);
        waitForFlow();
    }

    private void askBotFirstCity(Player p) {

        int who = p.getIndex();
        game.state.Map map = engine.getMap();
        int sizepp = map.getSizePP();
        int size = map.getSize();
        int[] priority = game.utils.Fnc.randomIndexArray(sizepp*sizepp);

        for (int i = 0; i < priority.length; i++)
        {
            int cx = game.utils.Fnc.conv1dto2d_x(priority[i], sizepp);
            int cy = game.utils.Fnc.conv1dto2d_y(priority[i], sizepp);
            if (map.hasColony(cx, cy) || map.hasNearColony(who, cx, cy))
                continue;
   
            map.getColonies()[cx][cy] = map.makeColony(false, who);
            game.AI.RoadResponse road = engine.getAI().roadOnLocation(who, cx, cy);
            map.getColonies()[cx][cy] = 0;
            if (!road.valid)
                continue;

            // ACTION
            engine.getState().addColony(who, cx, cy);
            engine.getState().addRoad(who, road.h, road.x, road.y);
            
            cli.Utils.debug(p + " played.");
            cli.Utils.debug("colony : "+cx +";"+cy);
            cli.Utils.debug("road : "+(road.h?"horizontal":"vertical")+" "+road.x+";"+road.y);

            break;
        }

        try {
            Thread.sleep(BOT_SLEEP_TIME);
        } catch (Exception e) {}
    }

    public void gameloop () {
        publish("Init phase finished !");
        delay();
    }
    
}
