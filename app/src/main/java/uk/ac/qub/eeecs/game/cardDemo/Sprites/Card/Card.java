package uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.CardType;
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoardObjects.CardHolder;

/**
 * Card class drawn using a number of overlapping images.
 *
 */
public abstract class Card extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////

    //Define the Game this Card is created in
    private Game game;

    //Define the default Card width and height
    private static final int DEFAULT_CARD_WIDTH = 180;
    private static final int DEFAULT_CARD_HEIGHT = 240;

    //Define the Card base
    protected Bitmap mCardBaseImage;

    //Define unselected Card base image
    protected Bitmap mCardBase;
    //Define selected Card base image
    protected Bitmap mCardBaseSelected;
    //Define back of Card image
    private Bitmap cardBack;

    //Define the Card portrait image
    private Bitmap cardPortrait;

    //Define Bitmap to contain attack Value container
    protected Bitmap mAttackContainer;

    //Define Bitmap to contain health Value container
    protected Bitmap mHealthContainer;

    //Define the Card digit images
    protected Bitmap[] mCardDigits = new Bitmap[10];

    //Define the offset locations and scaling for the card portrait
    //card attack and card health values - all measured relative
    //to the centre of the object as a percentage of object size
    protected Vector2 mAttackOffset;
    protected Vector2 mAttackScale = new Vector2(0.04f, 0.04f);

    protected Vector2 mHealthOffset;
    protected Vector2 mHealthScale = new Vector2(0.04f, 0.04f);

    private Vector2 mPortraitOffset;
    private Vector2 mPortraitScale;

    protected Vector2 mAttackContainerOffset;
    protected Vector2 mAttackContainerScale;

    protected Vector2 mHealthContainerOffset;
    protected Vector2 mHealthContainerScale;

    //Define the health and attack values
    protected int attack;
    protected int health;

    //original health when Card is created
    //before any health is deducted/added
    private int originalHealth;

    //Define number of digits in the attack and health values
    protected int attackLength;
    protected int healthLength;

    //Define the number to scale the y co-ordinate of the Card's text
    protected float textYPosScale;

    //X co-ordinate of the Card's start position
    private float startPosX;
    //Y co-ordinate of the Card's start position
    private float startPosY;

    //Define the Card Name and Type
    private String name;
    private CardType cardType;

    //Define the GameScreen the Card is displayed in
    protected static GameScreen gameScreen;

    //Define CardHolder the Card is positioned in on BattleScreen
    private CardHolder mCardHolder;

    //Paint used to draw text on the Card
    private Paint mTextPaint;

    //Returns true if Card is selected
    private Boolean selected = false;
    //Returns true if Card is being dragged
    private Boolean cardDragged = false;
    //Returns true if Card has been assigned to a Card holder
    private Boolean hasHolder = false;
    //Returns true if Card is currently being used
    private Boolean cardInUse = false;
    //Returns true if Card is facing downwards
    private Boolean cardFlipped = false;

    private BoundingBox bound;

    //Define the AssetManger of the Game the Card is in
    private AssetManager assetManager;


    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Construct the Card object
     *
     * @param aGame               Game to which this Card belongs
     * @param mName               Name of the Card
     * @param cardTypeValue       Type of the Card
     * @param mCardPortrait       the Bitmap containing the Cards portrait image
     * @param portraitScaleValue  Vector that determines the Scale of the Card portrait
     * @param mAttack             Attack value of the Card
     * @param mHealth             Health value of the Card
     * @param portraitYPos        The Y co-ordinate of the Card
     *
     * Created by Niamh McCartney
     */
    public Card(Game aGame, String mName, CardType cardTypeValue, Bitmap mCardPortrait,
                Vector2 portraitScaleValue, int mAttack, int mHealth, float portraitYPos) {
        super(0.0f, 0.0f, DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT, null,
                null);

        //Define the parameters
        this.name = mName;
        this.cardType = cardTypeValue;
        this.cardPortrait = mCardPortrait;
        this.attack = mAttack;
        this.health = mHealth;
        this.mPortraitScale = portraitScaleValue;
        this.game = aGame;

        //Initialise the Card properties
        this.mPortraitOffset = new Vector2(0.0f, portraitYPos);
        this.bound = new BoundingBox();
        this.originalHealth = health;
        assetManager = game.getAssetManager();

        //Load images used by the Card
        loadCardAssets();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Draw Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the Card Object
     *
     * @param elapsedTime    Elapsed time information
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        if(cardFlipped){
            Vector2 cardBackScale = new Vector2(0.7f, 0.7f);
            Vector2 cardBackOffset = new Vector2(0.15f, 0.15f);
            drawBitmap(cardBack, cardBackOffset, cardBackScale, graphics2D, layerViewport,
                    screenViewport);

        }else {
            // Draw the card base background
            mBitmap = setCardBackground();
            super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);


            // Draw the portrait
            drawBitmap(cardPortrait, mPortraitOffset, mPortraitScale,
                    graphics2D, layerViewport, screenViewport);

            // Draw the Attack Container
            drawBitmap(mAttackContainer, mAttackContainerOffset, mAttackContainerScale,
                    graphics2D, layerViewport, screenViewport);

            // Draw the Health Container
            mHealthContainerScale = new Vector2(0.15f, 0.15f);
            drawBitmap(mHealthContainer, mHealthContainerOffset, mHealthContainerScale,
                    graphics2D, layerViewport, screenViewport);

            //Set paint properties for card text
            setupTextPaint();

            //Draw the Card text on the Card base
            drawCardText(graphics2D, layerViewport, screenViewport);

            //calculate the number of digits in the cards attack and health values
            attackLength = String.valueOf(attack).length();
            healthLength = String.valueOf(health).length();
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
     *  Taken from 'Card.java' in the CardDemo Gage Code - NO MODIFICATIONS MADE
     *  Set up by Niamh McCartney
     */
    protected void drawBitmap(Bitmap bitmap, Vector2 offset, Vector2 scale, IGraphics2D graphics2D,
                              LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Calculate the center position of the rotated offset point.
        double rotation = Math.toRadians(-this.orientation);
        float diffX = mBound.halfWidth * offset.x;
        float diffY = mBound.halfHeight * offset.y;
        float rotatedX = (float)(Math.cos(rotation) * diffX - Math.sin(rotation) *
                diffY + position.x);
        float rotatedY = (float)(Math.sin(rotation) * diffX + Math.cos(rotation) *
                diffY + position.y);

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
     * Method to draw out a specified bitmap using a specific offset (relative to the
     * position of this game object) and scaling (relative to the size of this game
     * object).
     *
     * @param text line of text to be drawn on Card
     * @param offset Offset vector
     * @param textWidth distance between characters
     * @param graphics2D Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Code taken from 'Card.java' in the
     * 'CardHandAnimationLectureCode.zip' shown
     * during Week 14 lecture - NO MODIFICATIONS MADE
     *
     * Set up by Niamh McCartney
     */
    private void drawText(String text, Vector2 offset, float textWidth,
                          IGraphics2D graphics2D,
                          LayerViewport layerViewport, ScreenViewport screenViewport) {

        Vector2 textPosition = new Vector2();
        ViewportHelper.convertLayerPosIntoScreen(
                layerViewport, this.position, screenViewport, textPosition);

        //x and y Co-ordinates for text
        textPosition.x += ViewportHelper.convertXDistanceFromLayerToScreen(offset.x,
                layerViewport, screenViewport);
        textPosition.y += ViewportHelper.convertYDistanceFromLayerToScreen(offset.y,
                layerViewport, screenViewport);


        float targetSize
                = ViewportHelper.convertXDistanceFromLayerToScreen(
                textWidth, layerViewport, screenViewport);
        float actualSize = mTextPaint.measureText(text);
        while( actualSize > targetSize) {
            mTextPaint.setTextSize(mTextPaint.getTextSize() * 0.9f);
            actualSize = mTextPaint.measureText(text);
        }

        graphics2D.drawText(text, textPosition.x, textPosition.y, mTextPaint);
    }

    /**
     * Draw Card Text
     *
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    private void drawCardText(IGraphics2D graphics2D, LayerViewport layerViewport,
                              ScreenViewport screenViewport){
        //Define the text and it's co-ordinates in relation to the Card base
        String text = name;
        float textXCoordinate = getWidth() * 0.0f;
        float textYCoordinate = getHeight() * textYPosScale;

        //Draw the text line by line
        for (String line : text.split("\n")) {
            Vector2 offset = new Vector2(textXCoordinate, textYCoordinate);
            drawText(line, offset, getHeight() * 0.7f,
                    graphics2D, layerViewport, screenViewport);
            textYCoordinate += getHeight() * 0.045;
        }
    }

    /**
     * Draws the attack value of the Card onto the Card
     *
     * Created by Niamh McCartney
     */
    protected abstract void drawAttackValue(IGraphics2D graphics2D, LayerViewport layerViewport,
                                            ScreenViewport screenViewport);
    /**
     * Draws the health value of the Card onto the Card
     *
     * Created by Niamh McCartney
     */
    protected abstract void drawHealthValue(IGraphics2D graphics2D, LayerViewport layerViewport,
                                            ScreenViewport screenViewport);


    // /////////////////////////////////////////////////////////////////////////
    // Other Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Sets paint properties for LeaderBoard text
     *
     * Code taken from 'Card.java' in the
     * 'CardHandAnimationLectureCode.zip' shown
     * during Week 14 lecture - PARAMETERS CHANGED
     *
     * Created by Niamh McCartney
     */
    private void setupTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(getWidth() * 0.15f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Load Assets used by Card Class
     *
     * Created By Niamh McCartney
     */
    private void loadCardAssets() {
        assetManager.loadAssets("txt/assets/CardAssets.JSON");
    }

    /**
     * Creates the images used by the Cards
     *
     * Created by Niamh McCartney
     */
    protected void createCardImages(){
            //Store each of the attack/health digit images
            for (int digit = 0; digit <= 9; digit++)
                mCardDigits[digit] = assetManager.getBitmap(String.valueOf(digit));

            //Create image for the back of the Card
            cardBack = assetManager.getBitmap("BackOfCard");
    }

    /**
     * Sets the Card Background of the Card depending
     * on whether its selected/unselected
     *
     * Created by Niamh McCartney
     */
    private Bitmap setCardBackground(){
        if(cardSelected()){
            mCardBaseImage = getCardBaseSelected();
        }
        else {
            mCardBaseImage = getCardBase();
        }
        return mCardBaseImage;
    }

    /**
     * Sets Card's health equal to its original value
     *
     * Created by Niamh McCartney
     */
    public void setCardToOriginalHealth(){
        health = originalHealth;
    }

    /**
     * Returns Card to its assigned Card Holder
     */
    public void returnToHolder(){
        this.setPosition(mCardHolder.getBound().x,mCardHolder.getBound().y);}

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns hasHolder Boolean
     */
    public Boolean cardHasHolder(){ return hasHolder;}

    /**
     * Returns the type of the Card
     *
     * Created By Niamh McCartney
     */
    public CardType getCardType(){ return cardType; }

    /**
     * Returns the name of the Card
     *
     * Created By Niamh McCartney
     */
    public String getCardName(){
        return name;
    }

    /**
     * Returns the attack value of the Card
     *
     * Created By Niamh McCartney
     */
    public int getAttackValue(){ return attack; }

    /**
     * Returns the health value of the Card
     *
     * Created By Niamh McCartney
     */
    public int getHealthValue(){
        return health;
    }

    /**
     * Returns x co-ordinate of the Card's start position
     */
    public float getStartPosX(){return startPosX;}

    /**
     * Returns y co-ordinate of the Card's start position
     */
    public float getStartPosY(){return startPosY;}

    /**
     * Returns Bitmap that represents the selected background of the Card
     *
     * Created By Niamh McCartney
     */
    private Bitmap getCardBaseSelected() {
        return mCardBaseSelected;
    }

    /**
     * Returns Bitmap that represents the background of the Card
     *
     * Created By Niamh McCartney
     */
    private Bitmap getCardBase() {
        return mCardBase;
    }

    /**
     * Returns selected Boolean
     * @return selected Boolean returns true if Card is selected
     *
     *  {Created By Niamh McCartney}
     */
    public Boolean cardSelected(){ return selected;}

    /**
     * Returns cardDragged Boolean
     * @return cardDragged Boolean returns true if Card is being dragged
     *
     *  {Created By Niamh McCartney}
     */
    public Boolean getCardDragged(){return cardDragged;}

    /**
     * Returns cardInUse Boolean
     * @return cardInUse Boolean returns true if Card is currently being used
     *
     *  {Created By Niamh McCartney}
     */
    public Boolean getCardInUse(){return cardInUse;}

    /**
     * Returns cardFlipped Boolean
     * @return cardFlipped Boolean returns true if Card is facing downwards
     *
     *  {Created By Niamh McCartney}
     */
    public Boolean getCardFlipped(){return cardFlipped;}

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Sets hasHolder Boolean
     * @param bool Boolean returns true if Card has been assigned to a Card holder
     */
    public void cardHasHolder(Boolean bool){
        hasHolder = bool;
    }

    /**
     * Assign a cardHolder to the Card
     * @param cardHolder The Card holder that contains a Card
     */
    public void setCardHolder(CardHolder cardHolder){
        this.mCardHolder = cardHolder;}

    /**
     * Sets health value of the Card
     * @param value
     *
     * Created By Niamh McCartney
     */
    public void setHealthValue(int value){
        health = value;
    }

    /**
     * Sets x co-ordinate of the Card's start position
     */
    public void setStartPosX(float xValue){startPosX = xValue;}

    /**
     * Sets y co-ordinate of the Card's start position
     */
    public void setStartPosY(float yValue){startPosY = yValue;}

    /**
     * Sets GameScreen the Card is displayed in
     * @param aGameScreen GameScreen
     *
     * Created By Niamh McCartney
     */
    public static void setGameScreen(GameScreen aGameScreen){
        gameScreen = aGameScreen;
    }

    /**
     * Assigns Bitmap to represent the background of the Card
     * @param cardBaseImage Bitmap to be assigned
     *
     * Created By Niamh McCartney
     */
    public void setCardBase(Bitmap cardBaseImage){
        mCardBase = cardBaseImage;
    }

    /**
     * Assigns Bitmap to represent the selected background of the Card
     * @param cardBaseSelectedImage Bitmap to be assigned
     *
     * Created By Niamh McCartney óóó
     */
    public void setCardBaseSelected(Bitmap cardBaseSelectedImage) {
        this.mCardBaseSelected = cardBaseSelectedImage;
    }

    /**
     * Sets selected Boolean
     * @param bool Boolean returns true if Card is selected
     *
     * Created By Niamh McCartney
     */
    public void setSelected(boolean bool){
        selected = bool;
    }

    /**
     * Sets cardDragged Boolean
     * @param bool Boolean returns true if Card is being dragged
     *
     * Created By Niamh McCartney
     */
    public void setCardDragged(Boolean bool){cardDragged = bool;}

    /**
     * Sets cardInUse Boolean
     * @param bool Boolean returns true if Card is currently being used
     *
     * Created By Niamh McCartney
     */
    public void setCardInUse(Boolean bool){cardInUse = bool;}

    /**
     * Sets cardFlipped Boolean
     * @param bool Boolean returns true if Card is facing downwards
     *
     * Created By Niamh McCartney
     */
    public void setCardFlipped(Boolean bool){cardFlipped = bool;}

}