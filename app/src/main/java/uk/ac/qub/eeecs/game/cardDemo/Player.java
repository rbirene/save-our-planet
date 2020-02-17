package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

/**
 *
 * Created by [Irene Bhuiyan]
 * This class represents a general player in the game.
 * Contains traits and behaviours both hero and villain share.
 *
 */

public abstract class Player extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    // define the player
    protected String playerName;
    protected Deck playerDeck;
    protected boolean hasAttacked;
    protected boolean hasPlayedCard;

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
     *
     */
    public Player(String playerName, Deck playerDeck) {
        super(null);

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

    // setter to set the GameScreen the player has been called in
    public void setGameScreen(GameScreen gameScreen){ this.gameScreen = gameScreen; }

    // setter to set player deck
    public void setPlayerDeck(Deck playerDeck) { this.playerDeck = playerDeck; }












}
