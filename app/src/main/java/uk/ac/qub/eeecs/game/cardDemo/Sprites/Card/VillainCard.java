package uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

/**
 * defines the methods and
 * properties of a Villain Card
 *
 * Created by Niamh McCartney
 */

public class VillainCard extends Card {

    private Bitmap cardBase;
    private Bitmap cardBaseSelected;

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
    public VillainCard(float x, float y, GameScreen gameScreen, String mName, String cardTypeValue, Bitmap mCardPortrait, Vector2 scaleValue, int mAttack, int mHealth, float portraitYPos) {
        super(x, y, gameScreen, mName, cardTypeValue, mCardPortrait, scaleValue, mAttack, mHealth, portraitYPos);
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport){
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        // Draw the attack value depending on how many digits it has [Niamh McCartney]
        //if attack has one digit
        if(attackLength == 1){
            mAttackOffset = new Vector2(0.59f, -0.1f);
            drawBitmap(mCardDigits[attack], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);
        }//if attack has two digits
        else if(attackLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(attack).charAt(0)));
            mAttackOffset = new Vector2(0.54f, -0.1f);
            drawBitmap(mCardDigits[firstDigit], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(attack).charAt(1)));
            mAttackOffset = new Vector2(0.64f, -0.1f);
            drawBitmap(mCardDigits[secondDigit], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);
        }

        // Draw the health value[Niamh McCartney]
        //if health has one digit
        if(healthLength == 1){
           mHealthOffset = new Vector2(-0.7f, -0.11f);
        drawBitmap(mCardDigits[health], mHealthOffset, mHealthScale,
                graphics2D, layerViewport, screenViewport);
        }//if health has two digits
        else if(healthLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(health).charAt(0)));
            mHealthOffset = new Vector2(-0.75f, -0.1f);
            drawBitmap(mCardDigits[firstDigit], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(health).charAt(1)));
            mHealthOffset = new Vector2(-0.65f, -0.1f);
            drawBitmap(mCardDigits[secondDigit], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);
        }
    }


    /**
     * Sets Images, Images Scale and Images
     * Offset values used by the Villain Card
     *
     * Created by Niamh McCartney
     */
    @Override
    public void createCardImages(){
        super.createCardImages();
        AssetManager assetManager = gameScreen.getGame().getAssetManager();

        // Store the common card base image
        cardBase = assetManager.getBitmap("VillainCardBackground");
        setCardBase(cardBase);
        cardBaseSelected = assetManager.getBitmap("VillainCardBackgroundSelected");
        setCardBaseSelected(cardBaseSelected);
        mCardBaseImage = mCardBase;

        mAttackContainer = assetManager.getBitmap("VillainAttackContainer");
        mAttackContainerScale = new Vector2(0.18f, 0.18f);
        mAttackContainerOffset = new Vector2(0.6f, -0.05f);

        mHealthContainer = assetManager.getBitmap("HealthContainer");
        mHealthContainerScale = new Vector2(0.15f, 0.15f);
        mHealthContainerOffset = new Vector2(-0.7f, -0.1f);
        textXpos = 0.08f;

    }

}
