package game.utils;

import java.util.Random;

public class Dices {

    private Random rnd = new Random();

    private int nDices;
    private int sizeOfDices;

    public Dices(game.Config conf) {
        this.nDices = conf.getnDices();
        this.sizeOfDices = conf.getSizeOfDices();
    }

    public int roll() {
        int res = 0;

        for (int i = 0; i < nDices; i++)
            res += 1 + rnd.nextInt(sizeOfDices);

        return res;
    }

}