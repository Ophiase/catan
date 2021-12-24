package game.state;

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
}