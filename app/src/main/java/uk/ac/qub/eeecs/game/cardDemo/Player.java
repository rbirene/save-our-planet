package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public abstract class Player extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    // define the player
    protected String playerName;
    protected Deck playerDeck;
    protected Bitmap playerAvatar;

    // player avatar
    protected static final float DEFAULT_AVATAR_WIDTH = 0.88f;
    protected static final float DEFAULT_AVATAR_HEIGHT = 0.88f;
    protected static final float DEFAULT_AVATAR_X = 0.88f;
    protected static final float DEFAULT_AVATAR_Y = 0.88f;
    protected float x;
    protected float y;

    protected GameScreen gameScreen;
    protected GameBoard gameBoard;
    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new player.
     *
     * @param playerName Name of the player
     * @param playerDeck Decks of cards belonging to the player
     * @param gameScreen Gamescreen to which this player belongs
     *
     */
    public Player(String playerName, Deck playerDeck, GameScreen gameScreen) {
        super(DEFAULT_AVATAR_X, DEFAULT_AVATAR_Y, DEFAULT_AVATAR_WIDTH, DEFAULT_AVATAR_HEIGHT, null, gameScreen);

        this.playerName = playerName;
        this.playerDeck = playerDeck;



    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////


    public GameBoard getGameBoard(){return gameBoard; }

    public void setGameBoard(GameBoard gameBoard){this.gameBoard = gameBoard;}


    // getter to return the player name
    public String getPlayerName(){ return playerName; }

    // getter to return the player deck
    public Deck getPlayerDeck() { return playerDeck; }

    // getter to return the player avatar
    public Bitmap getPlayerAvatar(){ return playerAvatar; }

    // setter to set the GameScreen the player has been called in
    public  void setGameScreen(GameScreen gameScreenValue){
        this.gameScreen = gameScreenValue; }

    // setter to set player deck
    public void setPlayerDeck(Deck playerDeck) { this.playerDeck = playerDeck; }

    // setter to set player avatar
    public void setPlayerAvatar(Bitmap playerAvatar){ this.playerAvatar = playerAvatar; }

}
