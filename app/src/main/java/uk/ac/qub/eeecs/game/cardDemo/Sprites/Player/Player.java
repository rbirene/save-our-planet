package uk.ac.qub.eeecs.game.cardDemo.Sprites.Player;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;

/**
 *
 * Created by [Irene Bhuiyan & Niamh McCartney]
 * This class represents a general player in the game.
 * Contains traits and behaviours both hero and villain share.
 *
 */
public abstract class Player extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    // define the default card width and height[Niamh McCartney]
    private static final int DEFAULT_PORTRAIT_WIDTH = 100;
    private static final int DEFAULT_PORTRAIT_HEIGHT = 100;

    // define the player
    protected String playerName;
    protected Deck  playerDeck;

    //boolean returns true if the player assets have been loaded[Niamh McCartney]
    private boolean assetsLoaded;

    //returns true if the player has been assigned a deck[Niamh McCartney]
    private boolean deckSet;

    protected boolean cardPlayed = false;

    protected GameScreen gameScreen;
    protected GameBoard gameBoard;

    //define the players current Health [Niamh McCartney]
    protected int playerHealth;
    //define number of digits in 'playerHealth' int above[Niamh McCartney]
    protected int playerHealthLength;
    //define the players original Health [Niamh McCartney]
    private int playerFullHealth;

    //Defines the position and scale of the health digits[Niamh McCartney]
    private Vector2 mPlayerHealthOffset;
    private Vector2 mPlayerHealthScale = new Vector2(0.06f, 0.06f);

    private BoundingBox bound = new BoundingBox();

    // define the card digit images[Niamh McCartney]
    private Bitmap[] mHealthDigits = new Bitmap[10];

    //define the image, scale and position of the players health bar[Niamh McCartney]
    private Bitmap mHealthBar;
    private float xScale = 0.6f;
    private float yScale = 0.15f;
    private Vector2 mHealthBarScale = new Vector2(xScale, yScale);
    private Vector2 mHealthBarOffset = new Vector2 (0.3f, -1.2f);

    //define the image, scale and position of the filling of the players health bar[Niamh McCartney]
    private Bitmap mHealthBarFiller;
    private Vector2 mHealthBarFillerScale;
    private Vector2 mHealthBarFillerOffset = new Vector2 (0.3f, -1.2f);

    //define the image, scale and position of the container containing the players health value[Niamh McCartney]
    private Bitmap mHealthContainer;
    private Vector2 mHealthContainerScale = new Vector2(0.25f, 0.2f);
    private Vector2 mHealthContainerOffset = new Vector2 (-0.65f, -1.2f);

    //defines the percentage of the players remaining health[Niamh McCartney]
    private double percentage;

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
    public Player(float x, float y, String playerName, Deck playerDeck, Bitmap portrait) {
        super(x, y, DEFAULT_PORTRAIT_WIDTH, DEFAULT_PORTRAIT_HEIGHT, portrait, null);

        this.playerName = playerName;
        this.playerDeck = playerDeck;

        deckSet = false;
        assetsLoaded = false;

        mBitmap = portrait;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the Player object
     *
     * @param elapsedTime    Elapsed time information
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     * @param aScreen        Gamescreen to which this platform belongs
     *
     * Created by Niamh McCartney
     */
    public void Draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport, GameScreen aScreen){

        gameScreen = aScreen;

        //If the deck has been set then update the players health
        if(deckSet){
            calculatePlayerHealth(aScreen);
        }

        //load assets if they have not been loaded
        if(!assetsLoaded){
            assetsLoaded = true;
            loadAssets();
            //The players original health is set here as
            //the loop will only be passed through once
            playerFullHealth = playerHealth;
        }

        //Draw the Player portrait
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        // Draw the Health Bar
        mHealthBarScale = new Vector2(0.6f, 0.15f);
        drawBitmap(mHealthBar, mHealthBarOffset, mHealthBarScale,
                graphics2D, layerViewport, screenViewport);

        //Calculate new scalar for the health bar filler
        calculateHealthBarFillerScale();

        //Draw health bar filler
        drawBitmap(mHealthBarFiller, mHealthBarFillerOffset, mHealthBarFillerScale,
                graphics2D, layerViewport, screenViewport);

        //Draw health value container
        drawBitmap(mHealthContainer, mHealthContainerOffset, mHealthContainerScale,
                graphics2D, layerViewport, screenViewport);

        //Draw the Player's health value depending on how many digits it has
        drawPlayerHealth(graphics2D, layerViewport, screenViewport);
    }

    /**
     * Method to draw out a specified bitmap using a specific offset (relative to the
     * position of this game object) and scaling (relative to the size of this game
     * object).
     *
     * @param bitmap Bitmap to draw
     * @param offset Offset vector
     * @param scale Scaling vector
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     *  Taken from CardDemo Gage Code
     */
    private void drawBitmap(Bitmap bitmap, Vector2 offset, Vector2 scale,
                            IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Calculate the center position of the rotated offset point.
        double rotation = Math.toRadians(-this.orientation);
        float diffX = mBound.halfWidth * offset.x;
        float diffY = mBound.halfHeight * offset.y;
        float rotatedX = (float)(Math.cos(rotation) * diffX - Math.sin(rotation) * diffY + position.x);
        float rotatedY = (float)(Math.sin(rotation) * diffX + Math.cos(rotation) * diffY + position.y);

        // Calculate a game layer bound for the bitmap to be drawn
        bound.set(rotatedX, rotatedY,
                mBound.halfWidth * scale.x, mBound.halfHeight * scale.y);

        // Draw out the specified bitmap using the calculated bound.
        // The following code is based on the Sprite's draw method.
        if (GraphicsHelper.getSourceAndScreenRect(
                bound, bitmap, layerViewport, screenViewport, drawSourceRect, drawScreenRect)) {

            // Build an appropriate transformation matrix
            drawMatrix.reset();

            float scaleX = (float) drawScreenRect.width() / (float) drawSourceRect.width();
            float scaleY = (float) drawScreenRect.height() / (float) drawSourceRect.height();
            drawMatrix.postScale(scaleX, scaleY);

            drawMatrix.postRotate(orientation, scaleX * bitmap.getWidth()
                    / 2.0f, scaleY * bitmap.getHeight() / 2.0f);

            drawMatrix.postTranslate(drawScreenRect.left, drawScreenRect.top);

            // Draw the bitmap
            graphics2D.drawBitmap(bitmap, drawMatrix, null);
        }
    }

    /**
     * Draws the health value of the Player
     * depending on how many digits it has
     *
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    private void drawPlayerHealth(IGraphics2D graphics2D,
                                  LayerViewport layerViewport, ScreenViewport screenViewport){
        //if health has one digit
        if(playerHealthLength == 1){
            mPlayerHealthOffset = new Vector2(-0.65f, -1.15f);
            drawBitmap(mHealthDigits[playerHealth], mPlayerHealthOffset, mPlayerHealthScale,
                    graphics2D, layerViewport, screenViewport);
        }//if health has two digits
        else if(playerHealthLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(playerHealth).charAt(0)));
            mPlayerHealthOffset = new Vector2(-0.7f, -1.15f);
            drawBitmap(mHealthDigits[firstDigit], mPlayerHealthOffset, mPlayerHealthScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(playerHealth).charAt(1)));
            mPlayerHealthOffset = new Vector2(-0.55f, -1.15f);
            drawBitmap(mHealthDigits[secondDigit], mPlayerHealthOffset, mPlayerHealthScale,
                    graphics2D, layerViewport, screenViewport);
        }//if health has three digits
        else if(playerHealthLength == 3){
            int firstDigit = Character.getNumericValue((String.valueOf(playerHealth).charAt(0)));
            mPlayerHealthOffset = new Vector2(-0.77f, -1.15f);
            drawBitmap(mHealthDigits[firstDigit], mPlayerHealthOffset, mPlayerHealthScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(playerHealth).charAt(1)));
            mPlayerHealthOffset = new Vector2(-0.65f, -1.15f);
            drawBitmap(mHealthDigits[secondDigit], mPlayerHealthOffset, mPlayerHealthScale,
                    graphics2D, layerViewport, screenViewport);
            int thirdDigit = Character.getNumericValue((String.valueOf(playerHealth).charAt(1)));
            mPlayerHealthOffset = new Vector2(-0.53f, -1.15f);
            drawBitmap(mHealthDigits[thirdDigit], mPlayerHealthOffset, mPlayerHealthScale,
                    graphics2D, layerViewport, screenViewport);
        }
    }

    /**
     * Calculates the player's health by adding the
     * health of each of the cards in their deck
     *
     * Created By Niamh McCartney
     */
    private void calculatePlayerHealth(GameScreen aScreen){
        //the players health is the total health of the cards in the players deck
        int health = 0;
        ArrayList<Card> deck  = getPlayerDeck().getDeck(aScreen);
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            health += card.getHealthValue();
        }
        playerHealth = health;
        playerHealthLength = String.valueOf(playerHealth).length();
    }

    /**
     * Calculate new scalar for the health bar filler
     * to represent any changes in the players health
     *
     * Created By Niamh McCartney
     */
    private void calculateHealthBarFillerScale(){
        //Work out what percentage of the players health is remaining
        percentage = ((double)playerHealth/(double)playerFullHealth);

        //Use the percentage to determine the new length of the health bar filler
        float xBarFillerScale = xScale*(float)percentage;

        //Set the new scaled health bar filler
        mHealthBarFillerScale = new Vector2(xBarFillerScale, yScale);
    }

    /**
     * Loads the assets used by the Player class
     * and assigns them to the relevant variables
     *
     * Created By Niamh McCartney
     */
    private void loadAssets(){
        if(gameScreen != null) {
            AssetManager assetManager = gameScreen.getGame().getAssetManager();

            // Store each of the damage/health digits
            for (int digit = 0; digit <= 9; digit++)
                mHealthDigits[digit] = assetManager.getBitmap(String.valueOf(digit));

            mHealthBar = assetManager.getBitmap("BarBorder");
            mHealthBarFiller = assetManager.getBitmap("BarFiller");
            mHealthContainer = assetManager.getBitmap("HealthContainer");
        }
    }

    @Override
    public void update(ElapsedTime elapsedTime){
        super.update(elapsedTime);
        this.playerDeck.update();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns the player's deck of Cards
     *
     * Created By Niamh McCartney
     */
    public Deck getPlayerDeck() { return playerDeck;}

    public GameBoard getGameBoard(){return gameBoard;}

    public Boolean getCardPlayed(){
        return cardPlayed;
    }

    /**
     * Returns the player's health
     *
     * Created By Niamh McCartney
     */
    public int getPlayerHealth(GameScreen aScreen){
        calculatePlayerHealth(aScreen);
        return playerHealth;
    }

    /**
     * Returns the player's name
     */
    public String getPlayerName(){ return playerName;}

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Sets the player's deck of Cards
     * @param playerDeck player's new deck
     *
     * Created By Niamh McCartney
     */
    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
        deckSet = true;
    }

    /**
     * Sets the gameBoard the player is displayed in
     */
    public void setGameBoard(GameBoard gameBoard){this.gameBoard = gameBoard;}

    public void setCardPlayed(boolean cardPlayed){
        this.cardPlayed = cardPlayed;
    }

    /**
     * Sets the gameScreen the player has been called in
     */
    public void setGameScreen(GameScreen gameScreen){ this.gameScreen = gameScreen; }
}