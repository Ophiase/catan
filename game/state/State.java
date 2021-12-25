package game.state;

import game.Config;
import game.constants.Ressource;
import game.utils.Fnc;

public class State {

    private int time;
    private int focus;
    private Map map;
    private Player[] players = new Player[4];

    // --------------------

    public State(game.Config conf, Map map) {
        this.time = 0;
        this.map = map;

        for (int i = 0; i < 4; i++) {
            boolean isBot = i >= conf.getnPlayers();
            
            players[i] = new Player(i, isBot);
        }

    }

    public int getFocus() {
        return focus;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public Map getMap() {
        return map;
    }

    // ------------------------------

    public void addRoad(int who, boolean horizontal, int x, int y) {
        if (horizontal)
            map.getRoadsH()[x][y] = who;
        else
            map.getRoadsV()[x][y] = who;
    }

    public void addColony(int who, int x, int y) {
        map.getColonies()[x][y] = Map.makeColony(false, who);
        players[who].getColonies().add(Fnc.conv2dto1d(x, y, map.getSize()));
    }

    public void improveColony (int who, int x, int y) {
        map.getColonies()[x][y] = Map.makeColony(true, who);
        players[who].getCities().add(Fnc.conv2dto1d(x, y, map.getSize()));
    }

    // ------------------------------

    public void collect(int score) {
        /**
         * TODO
         */
    }

    public int won() {
        for (Player p: players)
            if (p.getRessource(Ressource.POINT) >= 10)
                return p.getIndex();

        return -1;
    }

    public void endturn() {
        focus++;
        if (focus == Config.N_PARTICIPANTS)
            focus = 0;

        time++;
    }

    public boolean focusOnBot() {
        return players[focus].isBot();
    }
}