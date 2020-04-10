package uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.Enums.CardType;

/**
 * Defines the methods and
 * properties of a Hero Card
 *
 * Created by Niamh McCartney
 */
public class HeroCard extends Card {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the Game the Card is created in
    private Game game;

    //Define common Card background
    private Bitmap cardBase;
    //Define selected Card background
    private Bitmap cardBaseSelected;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Constructs the Hero Card object
     *
     * @param aGame         Game to which this Card belongs
     * @param mName         Name of the Card
     * @param mCardPortrait the Bitmap containing the Cards portrait image
     * @param scaleValue    Vector that determines the Scale of the Card portrait
     * @param mAttack       Attack value of the Card
     * @param mHealth       Health value of the Card
     * @param portraitYPos  The Y co-ordinate of the Card
     *
     * Created by Niamh McCartney
     */
    public HeroCard(Game aGame, String mName, Bitmap mCardPortrait, Vector2 scaleValue,
                    int mAttack, int mHealth, float portraitYPos) {
        super(aGame, mName, CardType.HERO_CARD, mCardPortrait, scaleValue, mAttack, mHealth,
                portraitYPos);

        //Define the parameters
        this.game = aGame;

        //Set up the Cards images
        createCardImages();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the Hero Card Object
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
                     LayerViewport layerViewport, ScreenViewport screenViewport){
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        //Draw the attack value depending on how many digits it has
        drawAttackValue(graphics2D, layerViewport, screenViewport);

        //Draw the health value depending on how many digits it has
        drawHealthValue(graphics2D, layerViewport, screenViewport);
    }

    /**
     * Draws the attack value of the Card onto the Card
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    @Override
    protected void drawAttackValue(IGraphics2D graphics2D,
                                   LayerViewport layerViewport, ScreenViewport screenViewport){
        //if attack has one digit
        if(attackLength == 1){
            mAttackOffset = mAttackContainerOffset;
            drawBitmap(mCardDigits[attack], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);
        }//if attack has two digits
        else if(attackLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(attack).charAt(0)));
            mAttackOffset = new Vector2(0.59f, -0.18f);
            drawBitmap(mCardDigits[firstDigit], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(attack).charAt(1)));
            mAttackOffset = new Vector2(0.69f, -0.18f);
            drawBitmap(mCardDigits[secondDigit], mAttackOffset, mAttackScale,
                    graphics2D, layerViewport, screenViewport);
        }
    }

    /**
     * Draws the health value of the Card onto the Card
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    @Override
    protected void drawHealthValue(IGraphics2D graphics2D,
                                   LayerViewport layerViewport, ScreenViewport screenViewport){
        //if health has one digit
        if(healthLength == 1){
            mHealthOffset = mHealthContainerOffset;
            drawBitmap(mCardDigits[health], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);
        }//if health has two digits
        else if(healthLength == 2){
            int firstDigit = Character.getNumericValue((String.valueOf(health).charAt(0)));
            mHealthOffset = new Vector2(-0.75f, -0.18f);
            drawBitmap(mCardDigits[firstDigit], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);

            int secondDigit = Character.getNumericValue((String.valueOf(health).charAt(1)));
            mHealthOffset = new Vector2(-0.65f, -0.18f);
            drawBitmap(mCardDigits[secondDigit], mHealthOffset, mHealthScale,
                    graphics2D, layerViewport, screenViewport);
        }
    }

    /**
     * Sets Images, Images Scale and Images
     * Offset values used by the Hero Card
     *
     * Created by Niamh McCartney
     */
    @Override
    public void createCardImages(){
       super.createCardImages();
       AssetManager assetManager = game.getAssetManager();

        //Store the common Card base image
        cardBase = assetManager.getBitmap("HeroCardBackground");
        setCardBase(cardBase);
        //Store the selected Card image
        cardBaseSelected = assetManager.getBitmap("HeroCardBackgroundSelected");
        setCardBaseSelected(cardBaseSelected);
        mCardBaseImage = mCardBase;

        //Store attack container image
        mAttackContainer = assetManager.getBitmap("HeroAttackContainer");
        mAttackContainerScale = new Vector2(0.25f, 0.25f);
        mAttackContainerOffset = new Vector2(0.6f, -0.15f);

        //Store health container image
        mHealthContainer = assetManager.getBitmap("HealthContainer");
        mHealthContainerScale = new Vector2(0.15f, 0.15f);
        mHealthContainerOffset = new Vector2(-0.7f, -0.18f);
        textYPosScale = 0.15f;
    }
}