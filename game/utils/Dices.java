package game.utils;

public class Dices {

    private int nDices;
    private int sizeOfDices;

    private final int robberDice;// DEFAULT = 7

    public Dices(game.Config conf) {
        this.nDices = conf.getnDices();
        this.sizeOfDices = conf.getSizeOfDices();

        this.robberDice = sizeOfDices + 1;
    }

    public int roll() {
        int res = 0;

        for (int i = 0; i < nDices; i++)
            res += 1 + Fnc.rand(sizeOfDices);

        return res;
    }

    public int getRobberDice() {
        return robberDice;
    }

}