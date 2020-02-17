package uk.ac.qub.eeecs.game.cardDemo;

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

/**
 * Card class that can be drawn using a number of overlapping images.
 *
 * Note: See the course documentation for extension/refactoring stories
 * for this class.
 *
 * @version 1.0
 */
public class Card extends Sprite {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////

    // Define the default card width and height
    private static final int DEFAULT_CARD_WIDTH = 180;
    private static final int DEFAULT_CARD_HEIGHT = 240;

    // Define the common card base
    private Bitmap mCardBase;

    // Define the card portrait image
    private Bitmap cardPortrait;

    //Define Bitmap to contain attack Value
    private Bitmap mAttackContainer;

    //Define Bitmap to contain health Value
    private Bitmap mHealthContainer;

    // Define the card digit images
    private Bitmap[] mCardDigits = new Bitmap[10];

    // Define the offset locations and scaling for the card portrait
    // card attack and card health values - all measured relative
    // to the centre of the object as a percentage of object size

    private float mAttackOffsetXPos;
    private float mAttackOffsetYPos;
    private Vector2 mAttackOffset = new Vector2(mAttackOffsetXPos, mAttackOffsetYPos);
    private Vector2 mAttackScale = new Vector2(0.04f, 0.04f);

    private Vector2 mHealthOffset;
    private Vector2 mHealthScale = new Vector2(0.04f, 0.04f);

    private Vector2 mPortraitOffset;
    private Vector2 mPortraitScale;

    private Vector2 mAttackContainerOffset;
    private Vector2 mAttackContainerScale;

    private Vector2 mHealthContainerOffset;
    private Vector2 mHealthContainerScale;

    // Define the health and attack values
    private int attack;
    private int health;

    //Defines number of digits in the attack and health values
    private int attackLength;
    private int healthLength;

    private float textXpos;

    //Define the Card Name and Type
    private String name;
    private String cardType;

    private float x;
    private float y;

    private static GameScreen gameScreen;

    private Paint mTextPaint;

    //Boolean to determine whether the card has been selected
    private Boolean selected = false;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new platform.
     *
     * @param x          Centre y location of the platform
     * @param y          Centre x location of the platform
     * @param gameScreen Gamescreen to which this platform belongs
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

        //calculate the number of digits in the cards attack and health values
        attackLength = String.valueOf(attack).length();
        healthLength = String.valueOf(health).length();

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the game platform
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

        // Draw the card base background[Niamh McCartney]
        mBitmap = mCardBase;
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);


        // Draw the portrait[Niamh McCartney]
        drawBitmap(cardPortrait, mPortraitOffset, mPortraitScale,
                graphics2D, layerViewport, screenViewport);

        // Draw the Attack Container[Niamh McCartney]
//        float attackYCoordinate = mBound.halfHeight*-0.0005f;
//        float attackXCoordinate = mBound.halfWidth*0.007f;
//        mAttackContainerOffset = new Vector2(attackXCoordinate,attackYCoordinate);
        //float attackYScale = mBound.halfHeight*0.0018f;
        //float attackXScale = mBound.halfWidth*0.0018f;
        //mAttackContainerScale = new Vector2(attackXScale,attackYScale);
//        mAttackContainerOffset = new Vector2(0.6f, -0.1f);
        drawBitmap(mAttackContainer, mAttackContainerOffset, mAttackContainerScale,
                graphics2D, layerViewport, screenViewport);

        // Draw the Health Container[Niamh McCartney]
//        float healthYCoordinate = mBound.halfHeight*-0.0005f;
//        float healthXCoordinate = mBound.halfWidth*-0.009f;
//        mHealthContainerOffset = new Vector2(healthXCoordinate, healthYCoordinate);
//        float healthYScale = mBound.halfHeight*0.0018f;
//        float healthXScale = mBound.halfWidth*0.0018f;
//        mAttackContainerScale = new Vector2(healthXScale, healthYScale);
        //mHealthContainerOffset = new Vector2(-0.7f, -0.18f);
        mHealthContainerScale = new Vector2(0.15f, 0.15f);
        drawBitmap(mHealthContainer, mHealthContainerOffset, mHealthContainerScale,
                graphics2D, layerViewport, screenViewport);

        //sets paint properties for card text
        setupTextPaint();

        //Draw the Card text[Niamh McCartney]
        String text = name;
        float textXCoordinate = getWidth() * 0.0f;
        float textYCoordinate = getHeight() * textXpos;
        for (String line: text.split("\n")) {
            Vector2 offset = new Vector2(textXCoordinate, textYCoordinate);
            drawText(line, offset, getHeight() * 0.7f,
                    graphics2D, layerViewport, screenViewport);
            textYCoordinate += getHeight() * 0.045;
        }

        // Draw the attack value depending on how many digits it has [Niamh McCartney]
        if(attackLength == 1){
            if(cardType.equals("villainCard")){mAttackOffset = new Vector2(0.59f, -0.1f);}
            else{mAttackOffset = new Vector2(0.64f, -0.18f);}
            drawBitmap(mCardDigits[attack], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);
        }else if(attackLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(attack).charAt(0)));
            if(cardType.equals("villainCard")){mAttackOffset = new Vector2(0.54f, -0.1f);}
            else{mAttackOffset = new Vector2(0.59f, -0.18f);}
            drawBitmap(mCardDigits[firstDigit], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(attack).charAt(1)));
            if(cardType.equals("villainCard")){mAttackOffset = new Vector2(0.64f, -0.1f);}
            else{mAttackOffset = new Vector2(0.69f, -0.18f);}
            drawBitmap(mCardDigits[secondDigit], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);
        }

        // Draw the health value[Niamh McCartney]
        if(healthLength == 1){
            if(cardType.equals("villainCard")){mHealthOffset = new Vector2(-0.7f, -0.11f);}
            else{mHealthOffset = new Vector2(-0.70f, -0.18f);}
        drawBitmap(mCardDigits[health], mHealthOffset, mHealthScale,
                graphics2D, layerViewport, screenViewport);
        }else if(healthLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(health).charAt(0)));
            if(cardType.equals("villainCard")){mHealthOffset = new Vector2(-0.75f, -0.1f);}
            else{mHealthOffset = new Vector2(-0.75f, -0.18f);}
            drawBitmap(mCardDigits[firstDigit], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(health).charAt(1)));
            if(cardType.equals("villainCard")){mHealthOffset = new Vector2(-0.65f, -0.1f);}
            else{mHealthOffset = new Vector2(-0.65f, -0.18f);}
            drawBitmap(mCardDigits[secondDigit], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);
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
     */
    private void drawBitmap(Bitmap bitmap, Vector2 offset, Vector2 scale,
                            IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {

//        // Calculate a game layer bound for the bitmap to be drawn
//        bound.set(position.x + mBound.halfWidth * offset.x,
//                position.y + mBound.halfHeight * offset.y,
//                mBound.halfWidth * scale.x,
//                mBound.halfHeight * scale.y);

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

    //Changes the Card Background [Niamh McCartney]
    public void changeHeroCardBackground(){
        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        if(mCardBase == assetManager.getBitmap("HeroCardBackground")){
            mCardBase = assetManager.getBitmap("HeroCardBackgroundSelected");
            selected = true;
        }else if(mCardBase == assetManager.getBitmap("HeroCardBackgroundSelected")){
            mCardBase = assetManager.getBitmap("HeroCardBackground");
            selected = false;
        }
    }

    //Returns true if Cards is selected [Niamh McCartney]
    public Boolean cardSelected(){
        return selected;
    }

    //Creates the images used by the Card [Niamh McCartney]
    public void createCardImages(String cardBackgroundName){
        if(gameScreen != null) {
            AssetManager assetManager = gameScreen.getGame().getAssetManager();

            // Store the common card base image
            mCardBase = assetManager.getBitmap(cardBackgroundName);

            // Store each of the damage/health digits
            for (int digit = 0; digit <= 9; digit++)
                mCardDigits[digit] = assetManager.getBitmap(String.valueOf(digit));

            if(cardType.equals("villainCard")){
                mAttackContainer = assetManager.getBitmap("VillainAttackContainer");
                mAttackContainerScale = new Vector2(0.18f, 0.18f);
                mAttackContainerOffset = new Vector2(0.6f, -0.05f);
                mHealthContainerOffset = new Vector2(-0.7f, -0.1f);
                textXpos = 0.1f;
            }else{
                mAttackContainer = assetManager.getBitmap("HeroAttackContainer");
                mAttackContainerScale = new Vector2(0.25f, 0.25f);
                mAttackContainerOffset = new Vector2(0.6f, -0.15f);
                mHealthContainerOffset = new Vector2(-0.7f, -0.18f);
                textXpos = 0.15f;
            }
            mHealthContainer = assetManager.getBitmap("HealthContainer");
        }
    }


    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    //Getter to return the Card Type of the Card [Niamh McCartney]
    public String getCardType(){
        return cardType;
    }

    //Getter to return the Card Name of the Card [Niamh McCartney]
    public String getCardName(){
        return name;
    }


    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    //Setter to set the GameScreen the Card has been called in [Niamh McCartney]
    public static void setGameScreen(GameScreen gameScreenValue){
        gameScreen = gameScreenValue;
    }

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
}