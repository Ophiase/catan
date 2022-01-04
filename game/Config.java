package game;

import java.security.InvalidParameterException;

public class Config {
    // Enum
    public static Config DEFAULT() { return new Config (1, 2, 2, 8); }
    public static Config THREE_BOT()  { return new Config (1, 3, 3, 8); }

    // Constants
    public static final int MIN_PLAYER = 1;
    public static final int MIN_BOT = 0;
    public static final int MIN_N_PARTICIPANTS = 3;
    public static final int MAX_N_PARTICIPANTS = 4;
    public static final int MIN_N_DICES = 2;
    public static final int MIN_SIZE_OF_DICES = 4;

    // Params
    private int nBots;
    private int nPlayers;
    private int nDices;
    private int sizeOfDices;

    private int nParticipants;
    private int nCases;
    private int sizeOfMap;

    public Config(int nPlayers, int nBots, int nDices, int sizeOfDices) throws InvalidParameterException
    {
        if (
               (nBots+nPlayers) < MIN_N_PARTICIPANTS 
            || (nBots+nPlayers) > MAX_N_PARTICIPANTS
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

    private boolean valid() {
        if (
            (nBots+nPlayers) < MIN_N_PARTICIPANTS 
         || (nBots+nPlayers) > MAX_N_PARTICIPANTS
         || (nPlayers < MIN_PLAYER || nBots < MIN_BOT)
         || (nDices < MIN_N_DICES)
         || (sizeOfDices < MIN_SIZE_OF_DICES)
        ) return false;
        return true;
    }

    public void setnBots(int nBots) throws InvalidParameterException {
        if (nBots < 0) throw new InvalidParameterException();
        
        this.nBots = nBots;
        if (!valid())
            this.nPlayers = nParticipants - nBots;
    }

    public void setnParticipants(int nParticipants) {
        if (nPlayers > nParticipants) throw new InvalidParameterException();

        this.nParticipants = nParticipants;
        this.nBots = nParticipants - nPlayers;
    }

    public void setnPlayers(int nPlayers) throws InvalidParameterException {
        if (nPlayers < 1) throw new InvalidParameterException();
        if (nPlayers > nParticipants) throw new InvalidParameterException();

        this.nPlayers = nPlayers;
        if (!valid())
            this.nBots = nParticipants - nPlayers;
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

    public int getnParticipants() {
        return nParticipants;
    }

    // ----------------------------------

    private void updateDependencies() {
        computeNParticipants();
        computeNCases();
        computeSizeOfMap();
    }

    private void computeNParticipants() {
        this.nParticipants = nBots + nPlayers;
    }

    private void computeNCases() {
        nCases = (sizeOfDices*nDices) - nDices + 1;
    }

    private void computeSizeOfMap() {
        sizeOfMap = (int)Math.round(Math.sqrt((float)nCases));
    }

    // ----------------------------------

    @Override
    public String toString() {
        return "Players: " + getnPlayers() + " | Bots: " + getnBots() + " | Participants: " + getnParticipants()
            + " | NDices: " + getnDices() + " | SDices: " + getSizeOfDices()
        ;
    }

}
