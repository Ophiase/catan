package game.state;

public class State {

    private int focus;
    private Player[] players = new Player[4];

    // --------------------

    public State(game.Config conf) {

        for (int i = 0; i < 4; i++) {
            boolean isBot = i >= conf.getnPlayers();
            
            players[i] = new Player(isBot);
        }

    }
}