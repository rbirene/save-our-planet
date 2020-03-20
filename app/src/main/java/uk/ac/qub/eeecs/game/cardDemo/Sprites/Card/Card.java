package uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

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
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoardObjects.CardHolder;

/**
 * Card class drawn using a number of overlapping images.
 *
 */
public class Card extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////

    // Define the default card width and height
    private static final int DEFAULT_CARD_WIDTH = 180;
    private static final int DEFAULT_CARD_HEIGHT = 240;

    // Define the common card base
    protected Bitmap mCardBaseImage;

    protected Bitmap mCardBase;
    protected Bitmap mCardBaseSelected;

    private Bitmap cardBack;

    // Define the card portrait image
    private Bitmap cardPortrait;

    //Define Bitmap to contain attack Value
    protected Bitmap mAttackContainer;

    //Define Bitmap to contain health Value
    protected Bitmap mHealthContainer;

    // Define the card digit images
    protected Bitmap[] mCardDigits = new Bitmap[10];

    // Define the offset locations and scaling for the card portrait
    // card attack and card health values - all measured relative
    // to the centre of the object as a percentage of object size

    private float mAttackOffsetXPos;
    private float mAttackOffsetYPos;
    protected Vector2 mAttackOffset = new Vector2(mAttackOffsetXPos, mAttackOffsetYPos);
    protected Vector2 mAttackScale = new Vector2(0.04f, 0.04f);

    protected Vector2 mHealthOffset;
    protected Vector2 mHealthScale = new Vector2(0.04f, 0.04f);

    private Vector2 mPortraitOffset;
    private Vector2 mPortraitScale;

    protected Vector2 mAttackContainerOffset;
    protected Vector2 mAttackContainerScale;

    protected Vector2 mHealthContainerOffset;
    protected Vector2 mHealthContainerScale;

    // Define the health and attack values
    protected int attack;
    protected int health;

    //Defines number of digits in the attack and health values
    protected int attackLength;
    protected int healthLength;

    protected float textXpos;

    private float x;
    private float y;

    private float startPosX;
    private float startPosY;

    //Define the Card Name and Type
    private String name;
    private String cardType;

    protected static GameScreen gameScreen;
    private CardHolder mCardHolder;
    private Paint mTextPaint;

    //Boolean to determine whether the card has been selected
    private Boolean selected = false;
    private Boolean cardLocked = false;
    private Boolean cardDragged = false;


    private Boolean hasHolder = false;

    private Boolean cardInUse = false;
    private Boolean cardFlipped = false;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Constructs the Card object
     *
     * @param x             Centre y location of the platform
     * @param y             Centre x location of the platform
     * @param gameScreen    Gamescreen to which this platform belongs
     * @param mName         Name of the Card
     * @param cardTypeValue Type of the Card
     * @param mCardPortrait the Bitmap containing the Cards portrait image
     * @param scaleValue    Vector that determines the Scale of the Card portrait
     * @param mAttack       Attack value of the Card
     * @param mHealth       Health value of the Card
     * @param portraitYPos  The Y co-ordinate of the Card
     */
    public Card(float x, float y, GameScreen gameScreen, String mName, String cardTypeValue, Bitmap mCardPortrait, Vector2 scaleValue, int mAttack, int mHealth, float portraitYPos) {
        super(x, y, DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT, null, gameScreen);

        name = mName;
        cardType = cardTypeValue;
        cardPortrait = mCardPortrait;
        attack = mAttack;
        health = mHealth;
        mPortraitScale = scaleValue;
        mPortraitOffset = new Vector2(0.0f, portraitYPos);

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
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
            drawBitmap(cardBack, cardBackOffset, cardBackScale, graphics2D, layerViewport, screenViewport);

        }else {
            // Draw the card base background[Niamh McCartney]
            mBitmap = setCardBackground();
            super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);


            // Draw the portrait[Niamh McCartney]
            drawBitmap(cardPortrait, mPortraitOffset, mPortraitScale,
                    graphics2D, layerViewport, screenViewport);

            // Draw the Attack Container[Niamh McCartney]
            drawBitmap(mAttackContainer, mAttackContainerOffset, mAttackContainerScale,
                    graphics2D, layerViewport, screenViewport);

            // Draw the Health Container[Niamh McCartney]
            mHealthContainerScale = new Vector2(0.15f, 0.15f);
            drawBitmap(mHealthContainer, mHealthContainerOffset, mHealthContainerScale,
                    graphics2D, layerViewport, screenViewport);

            //sets paint properties for card text
            setupTextPaint();

            //Draw the Card text[Niamh McCartney]
            String text = name;
            float textXCoordinate = getWidth() * 0.0f;
            float textYCoordinate = getHeight() * textXpos;
            for (String line : text.split("\n")) {
                Vector2 offset = new Vector2(textXCoordinate, textYCoordinate);
                drawText(line, offset, getHeight() * 0.7f,
                        graphics2D, layerViewport, screenViewport);
                textYCoordinate += getHeight() * 0.045;
            }

            //calculate the number of digits in the cards attack and health values
            attackLength = String.valueOf(attack).length();
            healthLength = String.valueOf(health).length();
        }
    }

    private BoundingBox bound = new BoundingBox();

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
    protected void drawBitmap(Bitmap bitmap, Vector2 offset, Vector2 scale,
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
     */
    private void drawText(String text, Vector2 offset, float textWidth,
                          IGraphics2D graphics2D,
                          LayerViewport layerViewport, ScreenViewport screenViewport) {

        Vector2 textPosition = new Vector2();
        ViewportHelper.convertLayerPosIntoScreen(
                layerViewport, this.position, screenViewport, textPosition);

        //x and y Co-ordinates for text
        textPosition.x += ViewportHelper.convertXDistanceFromLayerToScreen(offset.x, layerViewport, screenViewport);
        textPosition.y += ViewportHelper.convertYDistanceFromLayerToScreen(offset.y, layerViewport, screenViewport);


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

    //sets paint properties for card text [Niamh McCartney]
    private void setupTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(getWidth() * 0.15f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void returnToHolder(){
        this.setPosition(mCardHolder.getBound().x,mCardHolder.getBound().y);
    }
    public boolean returnHolder(){
        return hasHolder;
    }

    public void hasHolder(Boolean holder){
        hasHolder = holder;
    }


    //Returns true if Cards is selected [Niamh McCartney]
  //  public Boolean cardSelected(){
    //    return selected;
    //}





    //Creates the images used by the Card [Niamh McCartney]
    public void createCardImages(){
        if(gameScreen != null) {
            AssetManager assetManager = gameScreen.getGame().getAssetManager();
            // Store each of the damage/health digits
            for (int digit = 0; digit <= 9; digit++)
                mCardDigits[digit] = assetManager.getBitmap(String.valueOf(digit));
            cardBack = assetManager.getBitmap("BackOfCard");
        }
    }

    //Changes the Card Background when card is selected [Niamh McCartney]
    public Bitmap setCardBackground(){
        if(cardSelected()){
            mCardBaseImage = getCardBaseSelected();
        }
        else {
            mCardBaseImage = getCardBase();
        }
        return mCardBaseImage;
    }


    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    public CardHolder getmCardHolder(){return this.mCardHolder;}

    //Getter to return the Card Type of the Card [Niamh McCartney]
    public String getCardType(){
        return cardType;
    }

    //Getter to return the Card Name of the Card [Niamh McCartney]
    public String getCardName(){
        return name;
    }

    //Getter to return the attack value of the Card [Niamh McCartney]
    public int getAttackValue(){ return attack; }

    //Getter to return the health value of the Card [Niamh McCartney]
    public int getHealthValue(){
        return health;
    }

    public float getStartPosX(){return startPosX;}

    public float getStartPosY(){return startPosY;}

    private Bitmap getCardBaseSelected() {
        return mCardBaseSelected;
    }

    private Bitmap getCardBase() {
        return mCardBase;
    }

    //Returns true if Cards is selected [Niamh McCartney]
    public Boolean cardSelected(){
        return selected;
    }

    //Returns true if Card has been dragged
    public Boolean getCardDragged(){return cardDragged;}

    public boolean getCardLocked(){return cardLocked;}

    public Boolean getCardInUse(){return cardInUse;}

    public Boolean getCardFlipped(){return cardFlipped;};

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    public void setCardHolder(CardHolder cardHolder){
        this.mCardHolder = cardHolder;}

    // Setter to set the health value of the Card [Niamh McCartney]
    public void setHealthValue(int value){
        health = value;
    }

    public void setStartPosX(float xValue){startPosX = xValue;}

    public void setStartPosY(float yValue){startPosY = yValue;}

    //Setter to set the GameScreen the Card has been called in [Niamh McCartney]
    public static void setGameScreen(GameScreen gameScreenValue){
        gameScreen = gameScreenValue; }

    //Setter to set the width of the layer View Port[Niamh McCartney]
    public void setLayerViewPortWidth(float xValue){
        x = xValue;
    }

    //Setter to set the height of the layer View Port[Niamh McCartney]
    public void setLayerViewPortHeight(float yValue){
        y = yValue;
    }

    public void setCardPortrait(Bitmap cardPortraitBitmap){
        cardPortrait = cardPortraitBitmap;
    }

    public void setCardBase(Bitmap cardBase){
        mCardBase = cardBase;
    }

    public void setCardBaseSelected(Bitmap mCardBaseSelected) {
        this.mCardBaseSelected = mCardBaseSelected; }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    //Setter to set the boolean value of 'cardDragged' [Niamh McCartney]
    public void setCardDragged(Boolean bool){this.cardDragged = bool;}

    public void setCardLocked(Boolean locked){cardLocked = locked;}

    public void setCardInUse(Boolean bool){cardInUse = bool;}

    public void setCardFlipped(Boolean bool){cardFlipped = bool;}

}