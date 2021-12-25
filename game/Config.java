package game;

import java.security.InvalidParameterException;

public class Config {
    // Enum
    public static Config DEFAULT() { return new Config (3, 1, 2, 8); }

    // Constants
    public static final int MIN_PLAYER = 1;
    public static final int MIN_BOT = 0;
    public static final int N_PARTICIPANTS = 4;
    public static final int MIN_N_DICES = 2;
    public static final int MIN_SIZE_OF_DICES = 4;

    // Params
    private int nBots;
    private int nPlayers;
    private int nDices;
    private int sizeOfDices;

    private int nCases;
    private int sizeOfMap;

    public Config(int nBots, int nPlayers, int nDices, int sizeOfDices) throws InvalidParameterException
    {
        if (
            (nBots+nPlayers != N_PARTICIPANTS)
            || (nPlayers < MIN_PLAYER || nBots < MIN_BOT)
            || (nDices < MIN_N_DICES)
            || (sizeOfDices < MIN_SIZE_OF_DICES)
        )
            throw new InvalidParameterException();

        this.nBots = nBots;
        this.nPlayers = nPlayers;
        this.nDices = nDices;
        this.sizeOfDices = sizeOfDices;

        updateDependencies();
    }

    // ----------------------------------

    public void setnBots(int nBots) throws InvalidParameterException {
        if (nBots < 0) throw new InvalidParameterException();
        
        this.nBots = nBots;
        this.nPlayers = N_PARTICIPANTS - nBots;
    }

    public void setnPlayers(int nPlayers) throws InvalidParameterException {
        if (nPlayers < 1) throw new InvalidParameterException();

        this.nPlayers = nPlayers;
        this.nBots = N_PARTICIPANTS - nPlayers;
    }

    public void setnDices(int nDices) throws InvalidParameterException {
        if (nDices < MIN_N_DICES) throw new InvalidParameterException();

        this.nDices = nDices;
        updateDependencies();
    }

    public void setSizeOfDices(int sizeOfDices) throws InvalidParameterException {
        if (sizeOfDices < MIN_SIZE_OF_DICES) throw new InvalidParameterException();

        this.sizeOfDices = sizeOfDices;
        updateDependencies();
    }

    // ----------------------------------

    public int getnPlayers() {
        return nPlayers;
    }

    public int getnBots() {
        return nBots;
    }

    public int getnDices() {
        return nDices;
    }

    public int getSizeOfDices() {
        return sizeOfDices;
    }

    public int getnCases() {
        return nCases;
    }

    public int getSizeOfMap() {
        return sizeOfMap;
    }

    // ----------------------------------

    private void updateDependencies() {
        computenCases();
        computeSizeOfMap();
    }

    private void computenCases() {
        nCases = (sizeOfDices*nDices) - nDices + 1;
    }

    private void computeSizeOfMap() {
        sizeOfMap = (int)Math.round(Math.sqrt((float)nCases));
    }
}
