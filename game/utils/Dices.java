package game.utils;

public class Dices {

    private int nDices;
    private int sizeOfDices;

    public Dices(game.Config conf) {
        this.nDices = conf.getnDices();
        this.sizeOfDices = conf.getSizeOfDices();
    }

    public int roll() {
        int res = 0;

        for (int i = 0; i < nDices; i++)
            res += 1 + Fnc.rand(sizeOfDices);

        return res;
    }

}