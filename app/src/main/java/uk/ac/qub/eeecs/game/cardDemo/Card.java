package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.util.Vector2;
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
    private static final int DEFAULT_CARD_WIDTH = 108;
    private static final int DEFAULT_CARD_HEIGHT = 144;

    // Define the common card base
    private Bitmap mCardBase;

    // Define the card portrait image
    private Bitmap cardPortrait;

    //Define the Card attack Value
   // private Bitmap mAttackValue;

    // Define the card digit images
    private Bitmap[] mCardDigits = new Bitmap[10];

    // Define the offset locations and scaling for the card portrait
    // card attack and card health values - all measured relative
    // to the centre of the object as a percentage of object size

    private Vector2 mAttackOffset = new Vector2(-0.68f, -0.84f);
    private Vector2 mAttackScale = new Vector2(0.1f, 0.1f);

    private Vector2 mHealthOffset = new Vector2(0.72f, -0.84f);
    private Vector2 mHealthScale = new Vector2(0.1f, 0.1f);

    private Vector2 mPortraitOffset = new Vector2(0.0f, 0.3f);
    private Vector2 mPortraitScale = new Vector2(0.55f, 0.55f);

    // Define the health and attack values
    private int attack;
    private int health;

    private String name;
    private String cardType;

    private float x;
    private float y;

    private static GameScreen gameScreen;

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
    public Card(float x, float y, GameScreen gameScreen, String mName, String cardTypeValue, Bitmap mCardPortrait, int mAttack, int mHealth) {
        super(x, y, DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT, null, gameScreen);

       /* if(gameScreen != null) {
            AssetManager assetManager = gameScreen.getGame().getAssetManager();

            // Store the common card base image
            mCardBase = assetManager.getBitmap("CardBackground");

            // Store the card portrait image
            mCardPortrait = assetManager.getBitmap("CardPortrait");

            // Store each of the damage/health digits
            for (int digit = 0; digit <= 9; digit++)
                mCardDigits[digit] = assetManager.getBitmap(String.valueOf(digit));
        }*/

        name = mName;
        cardType = cardTypeValue;
        cardPortrait = mCardPortrait;
        attack = mAttack;
        health = mHealth;
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
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Draw the portrait
        drawBitmap(cardPortrait, mPortraitOffset, mPortraitScale,
                graphics2D, layerViewport, screenViewport);

        // Draw the card base background
        mBitmap = mCardBase;
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        // Draw the attack value
        drawBitmap(mCardDigits[attack], mAttackOffset, mAttackScale,
                graphics2D, layerViewport, screenViewport);

        // Draw the attack value
        drawBitmap(mCardDigits[health], mHealthOffset, mHealthScale,
                graphics2D, layerViewport, screenViewport);
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

    //Getter to return the Card Type of the Card [Niamh McCartney]
    public String getCardType(){
        return cardType;
    }

    public String getCardName(){
        return name;
    }

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

    //Creates the images used by the Card [Niamh McCartney]
    public void createCardImages(){
        AssetManager assetManager = gameScreen.getGame().getAssetManager();

        // Store the common card base image
        mCardBase = assetManager.getBitmap("CardBackground");

        // Store each of the damage/health digits
        for (int digit = 0; digit <= 9; digit++)
            mCardDigits[digit] = assetManager.getBitmap(String.valueOf(digit));
    }


}
