package game.state;

import game.Config;
import game.constants.Ressource;
import game.utils.Fnc;

public class State {

    private int time;
    private int focus;
    private Map map;
    private Player[] players;

    // --------------------

    public State(game.Config conf, Map map) {
        this.time = 0;
        this.map = map;

        this.players = new Player[conf.getnParticipants()];
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

    public int getnPlayers() {
        return players.length;
    }

    public Map getMap() {
        return map;
    }

    public int getTime() {
        return time;
    }

    // ------------------------------

    public void addRoad(int who, boolean horizontal, int x, int y) {
        if (horizontal)
        {
            map.getRoadsH()[x][y] = who;
            players[who].getRoadH().add(Fnc.conv2dto1d(x, y, map.getSize()));
        }
        else
        {
            map.getRoadsV()[x][y] = who;
            players[who].getRoadV().add(Fnc.conv2dto1d(x, y, map.getSize()));
        }
    }

    public void addColony(int who, int x, int y) {
        int position = Fnc.conv2dto1d(x, y, map.getSize());
        Player p = players[who];
        
        map.getColonies()[x][y] = Map.makeColony(false, who);
        p.getColonies().add(position);
        map.givePorts(position, p);

        p.getDicesIdx().addAll(map.nearDicesIdx(x, y));
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
        if (focus == getnPlayers())
            focus = 0;

        time++;
    }

    public boolean focusOnBot() {
        return players[focus].isBot();
    }
}