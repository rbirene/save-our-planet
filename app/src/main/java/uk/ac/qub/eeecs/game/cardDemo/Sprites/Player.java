package uk.ac.qub.eeecs.game.cardDemo.Sprites;

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
import uk.ac.qub.eeecs.game.cardDemo.GameBoard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;

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

    // define the default card width and height[Niamh McCartney]
    private static final int DEFAULT_PORTRAIT_WIDTH = 100;
    private static final int DEFAULT_PORTRAIT_HEIGHT = 100;

    // define the player
    protected String playerName;
    protected Deck  playerDeck;
    protected boolean hasAttacked;
    protected boolean hasPlayedCard;

    private Boolean yourTurn;
    //boolean returns true if the player assets have been loaded[Niamh McCartney]
    private boolean assetsLoaded;

    //returns true if the player has been assigned a deck[Niamh McCartney]
    private boolean deckSet;

    protected GameScreen gameScreen;
    protected GameBoard gameBoard;
    protected static final int MAX_CARDS_PLAYED = 1;
    protected boolean cardPlayed = false;

    //define the players current Health [Niamh McCartney]
    private int playerHealth;
    //define number of digits in 'playerHealth' int above[Niamh McCartney]
    private int playerHealthLength;
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
            setPlayerHealth(aScreen);
        }

        //load assets if they have not been loaded
        if(!assetsLoaded){
            assetsLoaded = true;
            loadAssets();
            //The players original health is set here as the loop will only be passed through once once
            playerFullHealth = playerHealth;
        }

        //Draw the Player portrait
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        // Draw the Health Bar
        mHealthBarScale = new Vector2(0.6f, 0.15f);
        drawBitmap(mHealthBar, mHealthBarOffset, mHealthBarScale,
                graphics2D, layerViewport, screenViewport);


        //works out what percentage of the players health is remaining
        percentage = ((double)playerHealth/(double)playerFullHealth);
        //uses the percentage to determine the length of the health bar filler
        float xBarFillerScale = xScale*(float)percentage;
        mHealthBarFillerScale = new Vector2(xBarFillerScale, yScale);

        //draw health bar filler
        drawBitmap(mHealthBarFiller, mHealthBarFillerOffset, mHealthBarFillerScale,
                graphics2D, layerViewport, screenViewport);

        //draw health value container
        drawBitmap(mHealthContainer, mHealthContainerOffset, mHealthContainerScale,
                graphics2D, layerViewport, screenViewport);


        // Draw the health value depending on how many digits it has
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

    public abstract void takeFirstTurn();

    public void takeTurn(){}

    @Override
    public void update(ElapsedTime elapsedTime){
        super.update(elapsedTime);

        this.playerDeck.update();



    }

    public void setCardPlayed(boolean cardPlayed){
        this.cardPlayed = cardPlayed;
    }
    public Boolean getCardPlayed(){
        return cardPlayed;
    }


    //getter to return the players health[Niamh McCartney]
    public int getPlayerHealth(){return playerHealth;}

    public GameBoard getGameBoard(){return gameBoard; }

    // getter to return the player name
    public String getPlayerName(){ return playerName; }

    // getter to return the player deck
    public Deck getPlayerDeck() { return playerDeck; }

    public void setGameBoard(GameBoard gameBoard){this.gameBoard = gameBoard;}

    // setter to set the GameScreen the player has been called in
    public void setGameScreen(GameScreen gameScreen){ this.gameScreen = gameScreen; }

    // setter to set player deck[Niamh McCartney]
    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
        deckSet = true;
    }

    public void AttackCard(Card attackCard,Card defendCard){
        int health = defendCard.getHealthValue();
       health = defendCard.getHealthValue() - attackCard.getAttackValue();

    }


    //setter to set the players Health[Niamh McCartney]
    public void setPlayerHealth(GameScreen aScreen){
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

    //loads the assets used by the Player class and assigns them to the relevant variables[Niamh McCartney]
    public void loadAssets(){
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

    public Boolean getYourTurn(){ return yourTurn;}

    public void setYourTurn(Boolean bool){yourTurn = bool;}
}
