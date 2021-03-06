package game.state;

import cli.Utils;
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
        for (int i = 0; i < players.length; i++) {
            boolean isBot = i >= conf.getnPlayers();
            
            players[i] = new Player(i, isBot, map.getSize());
        }

    }

    public int getFocus() {
        return focus;
    }

    /** ONLY USE IS FOR GUI (tmp user have to accept) */
    public void setFocus(int focus) {
        this.focus = focus;
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
            players[who].getRoadV().add(Fnc.conv2dto1d(x, y, map.getSizePP()));
        }
    }

    public void addColony(int who, int x, int y) {
        int position = Fnc.conv2dto1d(x, y, map.getSizePP());
        Player p = players[who];
        
        map.getColonies()[x][y] = Map.makeColony(false, who);
        p.getColonies().add(position);
        map.givePorts(position, p);

        p.getDicesIdx().addAll(map.nearDicesIdx(x, y)); // obsolète
        map.nearDicesToPlayer(p, x, y);

        p.getRessources()[0]++;

        cli.Utils.debug(p + " buy colony on " + x + ":" + y);
    }

    public void improveColony (int who, int x, int y) {
        Player p = players[who];

        map.getColonies()[x][y] = Map.makeColony(true, who);
        p.getCities().add(Fnc.conv2dto1d(x, y, map.getSizePP()));
        
        map.nearDicesToPlayer(p, x, y);

        p.getRessources()[0]++;
    }

    // ------------------------------

    public void collect(int score) {
        if (score == map.getRobberIndex())
            return;

        for (Player p: players)
        {
            int x = map.getDicesX()[score];
            int y = map.getDicesY()[score];

            p.getRessources()[map.getBiomes()[x][y]] += p.getDices()[x][y];
        }
    }

    public Player won() {
        for (Player p: players)
            if (p.getRessource(Ressource.POINT) >= 10)
                return p;

        return null;
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