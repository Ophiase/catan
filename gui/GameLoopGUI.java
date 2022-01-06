package gui;

import java.util.ArrayList;

import game.Engine;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.utils.Fnc;
import gui.gamepanel.GameScreen;
import gui.gamepanel.context.ActionContext;
import gui.gamepanel.context.InteractionContext;
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

    private static final long BOT_SLEEP_TIME = 100;
    private static final long DELAY = 300;
    private static final long LAG = 100;

    private static final boolean ROBBER_ENABLED = true;

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

        publish("Init phase finished !");
        delay();

        while (gameloop());

        gameScreen.gamePanel.mainWindow.focusOnMenu();
    }

    private void askUserFirstCity(Player p) {
        interupFlow();
        gameScreen.mapContext.setState(MapContext.PUT_FIRST_COLONY_STATE);
        waitForFlow();

        interupFlow();
        gameScreen.mapContext.setState(MapContext.PUT_FIRST_ROAD_STATE);
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

    public boolean gameloop () {
        int     time  = engine.getState().getTime();
        int     focus = engine.getState().getFocus();
        boolean isBot = engine.getState().focusOnBot();

        gameScreen.mapContext.updateMap();
        gameScreen.repaint(); // dans le doute

        // -------------------------------------

        publish("Focus on " + engine.getState().getPlayer(focus) + ".");

        if (isBot)
            botTurn();
        else
            playerTurn();

        // -------------------------------------

        Player winner = engine.won();
        if (winner != null) {
            publish(winner + " won !!!");
            return false;
        }

        engine.endTurn();
        return true;
    }

    private void playerTurn() {
        // dices
        int who = engine.getState().getFocus();
        int score = dicesRoll(who);
        
        // play
        interupFlow();
        gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
        waitForFlow();
    }

    private void botTurn() {
        int who = engine.getState().getFocus();
        int score = dicesRoll(who);
        engine.getAI().play(engine.getState().getPlayer(who));

        try {
            Thread.sleep(BOT_SLEEP_TIME);
        } catch (Exception e) {}
    }

    // --------------------

    private int dicesRoll(int who) {
        int score = engine.getDices().roll();
        publish("Dices gave : " + score);
        delay();
        
        if (ROBBER_ENABLED && score == engine.getDices().getRobberDice()) { // DEFAULT = 7
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
        int memFocus = engine.getState().getFocus();

        for (Player p: engine.getState().getPlayers()) if (p.nCards() > engine.getDices().getRobberDice()) {
            engine.getState().setFocus(p.getIndex());

            if (p.isBot())
            {
                engine.getAI().retribution(p);
                continue;
            }

            interupFlow();

            publish("Choose "+(p.nCards()/2)+" cards to abandon");
            gameScreen.interactionContext.in_nCards = p.nCards()/2;
            gameScreen.interactionContext.setState(InteractionContext.ROBBER_RESSOURCE_STATE);

            waitForFlow();
        }

        engine.getState().setFocus(memFocus);
    }

    /**
     * After a 7 at dices
     * Player choose where to put the robber.
     * And get steal a ressource.
     */
    private void steal(int who) {
        Player currentPlayer = engine.getState().getPlayer(who);
        Map map = engine.getMap();
                
        if (currentPlayer.isBot()) {
            engine.getAI().steal(currentPlayer);
            gameScreen.mapContext.updateMap();
        } else {
            interupFlow();
            gameScreen.mapContext.setState(MapContext.PUT_ROBBER_STATE);
            waitForFlow();
        }

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
                
                publish(currentPlayer+" stole "+Ressource.toString(r).toLowerCase()+" to "+victim+".");
                return;
            }
    }
    
}
