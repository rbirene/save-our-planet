package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;


/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class ChooseCardScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////
    private HashMap<String, Card> heroCardPool;
    private HashMap<String, Card> screenCardPool = new HashMap<>();
    //private Card screenCardPool[] = new Card[3];
    // Define a card to be displayed
    private Card card;

    private PushButton BackButton;
    private Card Card01;
    private Card Card02;
    private Card Card03;

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;

    /**
     * Define storage of touch points. Up to 5 simultaneous touch
     * events are tested (an arbitrary value that displays well on ]
     * screen). An array of booleans is used to determine if a given
     * touch point exists, alongside this a corresponding 2D array of
     * x/y points is maintained to hold the location of touch points
     */
    private static final int mTouchIdToDisplay = 5;
    private boolean[] mTouchIdExists = new boolean[mTouchIdToDisplay];
    private float[][] mTouchLocation = new float[mTouchIdExists.length][2];

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public ChooseCardScreen(Game game) {
        super("CardScreen", game);

        // Load the various images used by the cards
        loadScreenAssets();

        AddBackButton();

        // get all the cards of type hero
        heroCardPool = getGame().getCardStore().getAllHeroCards(this);

        // generate 3 random cards
        generateCards(3);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Add a Back Button to the screen
     *
     * Created By Niamh McCartney
     */
    private void AddBackButton(){

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");

        BackButton = new PushButton(20.0f, 50.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);
    }

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    private void loadScreenAssets(){
        // Load the various images used by the cards
        //mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        mGame.getAssetManager().loadAssets("txt/assets/ChooseCardsScreenAssets.JSON");
        mGame.getAssetManager().loadAssets("txt/assets/CardAssets.JSON");
        //mGame.getAssetManager().loadAssets("txt/assets/Card.JSON");
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the last update
        Input input = mGame.getInput();

        mGame.getInput().getKeyEvents();

        List<TouchEvent> touchEvents = input.getTouchEvents();

        AudioManager audioManager = getGame().getAudioManager();

        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);

            if (touchEvents.size() > 0) {

                BackButton.update(elapsedTime);

                if (BackButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new MenuScreen(mGame));
                    BackButton.setPlaySounds(true, true);

                // Store touch point information.
                for (int pointerId = 0; pointerId < touchEvents.size(); pointerId++) {
                    mTouchLocation[pointerId][0] = event.x;
                    mTouchLocation[pointerId][1] = event.y;
                }

                for (int pointerIdx = 0; pointerIdx < touchEvents.size(); pointerIdx++) {
                    if (mTouchLocation[pointerIdx][1] > 300 && mTouchLocation[pointerIdx][1] < 900 && mTouchLocation[pointerIdx][0] > 110 && mTouchLocation[pointerIdx][0] < 540) {
                        Card01.changeCardBackground();
                        audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                    }
                    if (mTouchLocation[pointerIdx][1] > 300 && mTouchLocation[pointerIdx][1] < 900 && mTouchLocation[pointerIdx][0] > 710 && mTouchLocation[pointerIdx][0] < 1140) {
                        Card02.changeCardBackground();
                        audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                    }
                    if (mTouchLocation[pointerIdx][1] > 300 && mTouchLocation[pointerIdx][1] < 900 && mTouchLocation[pointerIdx][0] > 1310 && mTouchLocation[pointerIdx][0] < 1740) {
                        Card03.changeCardBackground();
                        audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                    }

                }
            }

            // Update the card
            // card.angularVelocity = 40.0f;

            //card.update(elapsedTime);
        }
    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        drawCards(elapsedTime, graphics2D);
        BackButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
    }

    /**
     * Draw the cards
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     *  Created By Niamh McCartney
     */
    private void drawCards(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        int counterX = 0;

        for (Map.Entry<String, Card> entry : screenCardPool.entrySet()) {
            int w = screenCardPool.size();
            int q = 1;
            float x1 = mDefaultLayerViewport.x / w;
            float y = mDefaultLayerViewport.y / q;
            float spacing = 70;
            float x = spacing + 2 * x1 * counterX++;
            Card value = entry.getValue();
            value.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            value.setPosition(x, y);
            if(counterX == 1){
                Card01 = value;
            }if(counterX == 2){
                Card02 = value;
            }if(counterX == 3){
                Card03 = value;
            }
        }
    }

    /**
     * generates the cards to present to the player
     *
     * @param numOfCards the number of cards to be generated
     *
     *  Created By Niamh McCartney
     */
    private void generateCards(int numOfCards){
        int num = 0;
        while(num<numOfCards) {
            card = getGame().getCardStore().getRandCard(heroCardPool);
            String name = card.getCardName();
            //If Card is not already chosen then add to the HashMap
            if(!screenCardPool.containsKey(name)) {
                screenCardPool.put(name, card);
                num++;
            }
        }
    }


//    private void generateCards(int numOfCards){
//        int num = 0;
//        while(num<numOfCards) {
//            card = getGame().getCardStore().getRandCard(heroCardPool);
//            String name = card.getCardName();
//            //If Card is not already chosen then add to the HashMap
//            if(!screenCardPool.containsKey(name)) {
//                screenCardPool.put(name, card);
//                num++;
//            }
//        }
//    }



}
