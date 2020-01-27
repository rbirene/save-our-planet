package uk.ac.qub.eeecs.game.cardDemo;

/**
 * [Irene Bhuiyan]
 * This class represents a player in the game (either human or AI).
 */

public abstract class Player {

    // Properties
    protected String playerName = "";
    protected Deck playerDeck;

    // Constructor
    public Player(String playerName, Deck playerDeck){
        this.playerName = playerName;
        this.playerDeck = playerDeck;
    }

    // Getters
    public String getPlayerName() {
        return playerName;
    }
    public Deck getPlayerDeck(){return playerDeck;}

    // Setters
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setPlayerDeck(Deck aDeck){playerDeck = aDeck;}
}
