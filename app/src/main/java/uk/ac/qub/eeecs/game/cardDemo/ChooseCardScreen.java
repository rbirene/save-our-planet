package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
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
    private HashMap<String, Card> heroCardPool = getGame().getCardStore().getAllHeroCards(this);
    private HashMap<String, Card> screenCardPool = new HashMap<>();
   // private Deck heroDeck;
    private Deck heroDeck = getGame().getHero().getPlayerDeck();

    private Hero hero = getGame().getHero();
    //private Card screenCardPool[] = new Card[3];
    // Define a card to be displayed
    private Card card;

    private PushButton BackButton;
    private Card Card01;
    private Card Card02;
    private Card Card03;

    private uk.ac.qub.eeecs.gage.world.ScreenViewport ScreenViewport;
    private uk.ac.qub.eeecs.gage.world.LayerViewport LayerViewport;

    private PushButton continueButton;
    private PushButton shuffleButton;

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
    private String touchEventType;

    //private Vector2 touchLocation = new Vector2();
    private final static int GAMEOBJECT_DENSITY = 3;
    private GameObject[] mGameObjects = new GameObject[GAMEOBJECT_DENSITY];

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

        //MessageDialog popUp = new MessageDialog();
        //popUp.showDialog();

        // Load the various images used by the cards
        loadScreenAssets();

        AddBackButton();
        AddContinueButton();
        AddShuffleButton();

        heroDeck = mGame.getHero().getPlayerDeck();
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

        BackButton = new PushButton(20.0f, 40.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);
        BackButton.setPlaySounds(true, true);
    }

    private void AddContinueButton(){

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        continueButton = new PushButton(
                450.0f, 42.0f, 100.0f, 100.0f,
                "continueBtn", "continueBtn",this);
        continueButton.setPlaySounds(true, true);
    }

    private void AddShuffleButton(){

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        shuffleButton = new PushButton(
                235.0f, 42.0f, 100.0f, 100.0f,
                "shuffleBtn", "shuffleBtn",this);
        shuffleButton.setPlaySounds(true, true);
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
     *
     *  {Created By Niamh McCartney}
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
            touchEventType = touchEventTypeToString(event.type);

            if (touchEvents.size() > 0) {

                BackButton.update(elapsedTime);
                continueButton.update(elapsedTime);
                shuffleButton.update(elapsedTime);

                if (continueButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new BattleScreen(mGame));

                if (BackButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new MenuScreen(mGame));

                if (shuffleButton.isPushTriggered()){
                    if(!heroDeck.getDeckshuffled()){
                    shuffleCards();
                    }else{

                    }
                }


                    if(touchEventType.equals("TOUCH_DOWN")){
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
            }
        }

            // Update the card
            // card.angularVelocity = 40.0f;

            //card.update(elapsedTime);

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
        continueButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        shuffleButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
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

        for (int i =0; i<heroDeck.getDeck(this).size(); i++) {
            int w = heroDeck.getDeck(this).size();
            int q = 1;
            float x1 = mDefaultLayerViewport.x / w;
            float y = mDefaultLayerViewport.y / q;
            float spacing = 70;
            float x = spacing + 2 * x1 * counterX++;
            Card value = heroDeck.getDeck(this).get(i);
            //value.setCardSize(180,240);
            value.setWidth(180);
            value.setHeight(240);
            value.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            value.setPosition(x, y);
            if (counterX == 1) {
                Card01 = value;
                Log.d("bound", Card01.getBound() + "");
                mGameObjects[0] = Card01;
            }
            if (counterX == 2) {
                Card02 = value;
                mGameObjects[1] = Card02;
            }
            if (counterX == 3) {
                Card03 = value;
                mGameObjects[2] = Card03;
            }
        }
    }

    private void shuffleCards(){
        screenCardPool.put(Card01.getCardName(), Card01);
        screenCardPool.put(Card02.getCardName(), Card02);
        screenCardPool.put(Card03.getCardName(), Card03);

        ArrayList<Card> tempCardPool = new ArrayList<>();

        for (int i =0; i<heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);

            if(value.cardSelected()) {
                Card randCard;
                int num = 0;
                while (num < 1) {
                    randCard = getGame().getCardStore().getRandCard(heroCardPool);
                    String name = randCard.getCardName();
                    //If Card has not already been chosen then add to the HashMap
                    if (!screenCardPool.containsKey(name) && heroDeck.checkDeck(randCard) == -1) {
                        tempCardPool.add(i, randCard);
                        screenCardPool.put(name, randCard);
                        num++;
                    }

                }
            }else{
                tempCardPool.add(i, value);
            }
        }

        heroDeck.setDeck(this, tempCardPool);
        heroDeck.setDeckshuffled(true);

        int counterX = 0;
        for (int i =0; i<heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);
            counterX++;
            if (counterX == 1) {
                Card01 = value;
                Log.d("bound", Card01.getBound() + "");
                mGameObjects[0] = Card01;
            }
            if (counterX == 2) {
                Card02 = value;
                mGameObjects[1] = Card02;
            }
            if (counterX == 3) {
                Card03 = value;
                mGameObjects[2] = Card03;
            }
        }

    }


    /**
     * Return a string that holds the corresponding label for the specified
     * type of touch event.
     *
     * @param type Touch event type
     * @return Touch event label
     *
     * [Niamh McCartney]
     */
    private String touchEventTypeToString(int type) {
        switch (type) {
            case 0:
                return "TOUCH_DOWN";
            case 1:
                return "TOUCH_UP";
            case 2:
                return "TOUCH_DRAGGED";
            case 3:
                return "TOUCH_SHOW_PRESS";
            case 4:
                return "TOUCH_LONG_PRESS";
            case 5:
                return "TOUCH_SINGLE_TAP";
            case 6:
                return "TOUCH_SCROLL";
            case 7:
                return "TOUCH_FLING";
            default:
                return "ERROR: Unknown Touch Event Type";
        }
    }

}
