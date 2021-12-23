package game;

import java.security.InvalidParameterException;

public class Config {
    // Enum
    static Config DEFAULT = new Config (3, 1, 2, 8);

    // Params
    private int nBots;
    private int nPlayers;
    private int nDices;
    private int sizeOfDices;

    private int sizeOfMap;

    public Config(int nBots, int nPlayers, int nDices, int sizeOfDices) throws InvalidParameterException
    {
        if (
            (nBots+nPlayers != 4)
            || (nPlayers < 1 || nBots < 0)
            || (nDices < 1)
            || (sizeOfDices < 1)
        )
            throw new InvalidParameterException();

        this.nBots = nBots;
        this.nPlayers = nPlayers;
        this.nDices = nDices;
        this.sizeOfDices = sizeOfDices;
    }

    // ----------------------------------

    public void setnBots(int nBots) throws InvalidParameterException {
        if (nBots < 0) throw new InvalidParameterException();
        
        this.nBots = nBots;
        this.nPlayers = 4 - nBots;
    }

    public void setnPlayers(int nPlayers) throws InvalidParameterException {
        if (nPlayers < 1) throw new InvalidParameterException();

        this.nPlayers = nPlayers;
        this.nBots = 4 - nPlayers;
    }

    public void setnDices(int nDices) throws InvalidParameterException {
        if (nDices < 1) throw new InvalidParameterException();

        this.nDices = nDices;
        computeSizeOfMap();
    }

    public void setSizeOfDices(int sizeOfDices) throws InvalidParameterException {
        if (sizeOfDices < 1) throw new InvalidParameterException();

        this.sizeOfDices = sizeOfDices;
        computeSizeOfMap();
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

    public int getSizeOfMap() {
        return sizeOfMap;
    }

    // ----------------------------------
    
    private void computeSizeOfMap() {
        int nCases = sizeOfDices*nDices;
        int side = (int)Math.round(Math.sqrt((float)nCases));

        sizeOfMap = side;
    }
}
