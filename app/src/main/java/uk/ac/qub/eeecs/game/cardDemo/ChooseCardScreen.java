package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;

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
import uk.ac.qub.eeecs.gage.util.BoundingBox;
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

    //contains all hero cards
    private HashMap<String, Card> heroCardPool = getGame().getCardStore().getAllHeroCards(this);
    private HashMap<String, Card> screenCardPool = new HashMap<>();

    //Define Player and Player's Deck
    private Hero hero = getGame().getHero();
    private Deck heroDeck = hero.getPlayerDeck();

    //Define Cards to be displayed on Screen
    private Card Card01;
    private Card Card02;
    private Card Card03;

    private uk.ac.qub.eeecs.gage.world.ScreenViewport ScreenViewport;
    private uk.ac.qub.eeecs.gage.world.LayerViewport LayerViewport;

    //Define Buttons
    private PushButton BackButton;
    private PushButton continueButton;
    private PushButton shuffleButton;
    private PushButton infoButton;
    private PushButton settingsButton;

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

        //Load the various images used by the cards
        loadScreenAssets();

        //Add Buttons
        AddBackButton();
        AddContinueButton();
        AddShuffleButton();
        addInfoButton();
        addSettingsButton();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Add a Back Button to the screen
     * that takes you to the previous screen
     * <p>
     * Created By Niamh McCartney
     */
    private void AddBackButton() {

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");

        BackButton = new PushButton(20.0f, 40.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);
        BackButton.setPlaySounds(true, true);
    }

    /**
     * Add a Continue Button to the screen
     * that takes you to the Battle Screen
     * <p>
     * Created By Niamh McCartney
     */
    private void AddContinueButton() {

        continueButton = new PushButton(
                450.0f, 42.0f, 50.0f, 50.0f,
                "continueBtn", "continueBtn", this);
        continueButton.setPlaySounds(true, true);
    }

    /**
     * Add a Shuffle Button to the screen
     * that shuffles any selected cards
     * and replaces them with new random cards
     * <p>
     * Created By Niamh McCartney
     */
    private void AddShuffleButton() {

        shuffleButton = new PushButton(
                235.0f, 42.0f, 55.0f, 55.0f,
                "shuffleBtn", "shuffleBtn", this);
        shuffleButton.setPlaySounds(true, true);
    }
    /**
     * Add a info Button to the screen that
     * takes you to the instructions screen
     *
     * Created By Niamh McCartney
     */
    private void addInfoButton() {

        infoButton = new PushButton(430.0f, 300.0f,
                28.0f, 28.0f,
                "infoBtn", "infoBtnSelected", this);
        infoButton.setPlaySounds(true, true);
    }

    /**
     * Add a settings Button to the screen
     * that takes you to the settings Screen
     *
     * Created By Niamh McCartney
     */
    private void addSettingsButton() {

        settingsButton = new PushButton(
                465.0f, 300.0f, 30.0f, 30.0f,
                "settingsBtn", "settingsBtnSelected", this);
        settingsButton.setPlaySounds(true, true);
    }


    /**
     * Load Assets used by screen
     * <p>
     * Created By Niamh McCartney
     */
    private void loadScreenAssets() {
        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/ChooseCardsScreenAssets.JSON");
        mGame.getAssetManager().loadAssets("txt/assets/CardAssets.JSON");
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     *                    <p>
     *                    {Created By Niamh McCartney}
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        //if information button is pushed then load the instructions screen [Niamh McCartney]
        if (infoButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));

        //if settings button is pushed then load the settings screen [Niamh McCartney]
        if (settingsButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new OptionsScreen(mGame));


        // Process any touch events occurring since the last update
        Input input = mGame.getInput();

        dragCard();

        //List of touch events
        List<TouchEvent> touchEvents = input.getTouchEvents();

        AudioManager audioManager = getGame().getAudioManager();

        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            touchEventType = touchEventTypeToString(event.type);

            if (touchEvents.size() > 0) {

                BackButton.update(elapsedTime);
                continueButton.update(elapsedTime);
                shuffleButton.update(elapsedTime);
                infoButton.update(elapsedTime);
                settingsButton.update(elapsedTime);

                //if continue button is pushed then load the battle screen
                if (continueButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new BattleScreen(mGame));

                //if back button is pushed then return to the MenuScreen
                if (BackButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new MenuScreen(mGame));

                if (touchEventType.equals("TOUCH_DOWN")) {
                    // Store touch point information.
                    for (int pointerId = 0; pointerId < touchEvents.size(); pointerId++) {
                        //x co-ordinate
                        mTouchLocation[pointerId][0] = event.x;
                        //y co-ordinate
                        mTouchLocation[pointerId][1] = event.y;
                    }

                    //If a card is touched change the background of the touched card
                    for (int pointerIdx = 0; pointerIdx < touchEvents.size(); pointerIdx++) {
                        if (mTouchLocation[pointerIdx][1] > 300 && mTouchLocation[pointerIdx][1] < 900 && mTouchLocation[pointerIdx][0] > 110 && mTouchLocation[pointerIdx][0] < 540) {
                            Card01.changeHeroCardBackground();
                            audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                        }
                        if (mTouchLocation[pointerIdx][1] > 300 && mTouchLocation[pointerIdx][1] < 900 && mTouchLocation[pointerIdx][0] > 710 && mTouchLocation[pointerIdx][0] < 1140) {
                            Card02.changeHeroCardBackground();
                            audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                        }
                        if (mTouchLocation[pointerIdx][1] > 300 && mTouchLocation[pointerIdx][1] < 900 && mTouchLocation[pointerIdx][0] > 1310 && mTouchLocation[pointerIdx][0] < 1740) {
                            Card03.changeHeroCardBackground();
                            audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                        }
                    }
                }
            }

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
        continueButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        shuffleButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        /*If shuffle button is pushed and deck has been shuffled then
         * display dialog informing user.  If deck has not been shuffled
         * and cards are selected then shuffle all selected cards.
         * Created By [Niamh McCartney]
         */
        if (shuffleButton.isPushTriggered()){
            if(heroDeck.getDeckShuffled()){
                displayDialogs("You can only shuffle your deck once");
            }
            else if(noCardsSelected()){
                displayDialogs("You must select the cards in your\ndeck you wish to shuffle");
            }else{
                shuffleCards();
            }
        }

    }

    /**
     * Display PopUp Dialog box
     * @param text Dialog Box Message
     *
     * Created By Niamh McCartney
     */
    public void displayDialogs(String text){
            InfoPopUpDialog popUp = new InfoPopUpDialog();
            popUp.showDialog(getGame().getActivity(), text);
    }

    /**
     * Returns true if no Cards have been selected
     *
     * Created By Niamh McCartney
     */
    public Boolean noCardsSelected(){
        for (int i =0; i<heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);
            if(value.cardSelected()){
                return false;
            }
        }
        return true;
    }

    /**
     * Draw the cards
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *                    <p>
     *                    Created By Niamh McCartney
     */
    private void drawCards(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        int counterX = 0;

        for (int i = 0; i < heroDeck.getDeck(this).size(); i++) {
            int w = heroDeck.getDeck(this).size();
            int q = 1;
            //Card y co-ordinate
            float y = mDefaultLayerViewport.y / q;
            float spacing = 70;
            float x1 = mDefaultLayerViewport.x / w;
            //Card x co-ordinate
            float x = spacing + 2 * x1 * counterX++;
            Card value = heroDeck.getDeck(this).get(i);
            //set Card width
            value.setWidth(144);
            //set Card Height
            value.setHeight(192);
            //draw cards
            value.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            //set Card position on screen
            value.setPosition(x, y);
            if (counterX == 1) {
                Card01 = value;
            }
            if (counterX == 2) {
                Card02 = value;
            }
            if (counterX == 3) {
                Card03 = value;
            }
        }
    }


    /**
     * Removes selected cards from
     * the players deck and replaces them
     * with new cards
     * <p>
     * Created By Niamh McCartney
     */
    private void shuffleCards() {
        screenCardPool.put(Card01.getCardName(), Card01);
        screenCardPool.put(Card02.getCardName(), Card02);
        screenCardPool.put(Card03.getCardName(), Card03);

        ArrayList<Card> tempCardPool = new ArrayList<>();

        //Iterates through the players current deck of cards
        for (int i = 0; i < heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);

            //Checks for selected cards
            if (value.cardSelected()) {
                Card randCard;
                int num = 0;
                //gets a new random card that isn't in the old deck or the new deck
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
                //Adds Cards to new deck if Card is not selected
            } else {
                tempCardPool.add(i, value);
            }
        }

        //Assigns new deck to the player
        heroDeck.setDeck(tempCardPool);
        //Deck has now been shuffled
        heroDeck.setDeckShuffled(true);

        int counterX = 0;
        for (int i = 0; i < heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);
            counterX++;
            if (counterX == 1) {
                Card01 = value;
            }
            if (counterX == 2) {
                Card02 = value;
            }
            if (counterX == 3) {
                Card03 = value;
            }
        }

    }

    private void dragCard()
    {
        Input input = mGame.getInput();
        //  the first (0) touch event is highlight card as explained above, 1 has been selected as its the next touch event.
        if (input.getTouchEvents().size() > 1)
        {
            TouchEvent touchEvent = input.getTouchEvents().get(1);
            float OriginalXPos = Card01.position.x;
            float OriginalYPos = Card01.position.y;
            // if there's a touch event within the card boundary, execute the following code
            if (Card01.getBound().contains(touchEvent.x, touchEvent.y))
            {   // only execute below code if touch event is dragged or scroll.
                if (touchEvent.type == 6 || touchEvent.type == 2 )
                {
                    // find the distanced moved (dx and dy) by removing the original position from the new position.
                    touchEvent.dx = touchEvent.x - OriginalXPos;
                    touchEvent.dy = touchEvent.y - OriginalYPos;
                    // add the distance to the original position.
                    Card01.position.add(touchEvent.dx,touchEvent.dy);
                    screenBoundary1();


                }
            }

            else if (Card02.getBound().contains(touchEvent.x, touchEvent.y))
            {
                if (touchEvent.type == 6 || touchEvent.type == 2 )
                {

                    touchEvent.dx = touchEvent.x - OriginalXPos;
                    touchEvent.dy = touchEvent.y - OriginalYPos;

                    Card02.position.add(touchEvent.dx,touchEvent.dy);

                    screenBoundary2();

                }
            }

            else if (Card03.getBound().contains(touchEvent.x, touchEvent.y))
            {

                if (touchEvent.type == 6 || touchEvent.type == 2 )
                {

                    touchEvent.dx = touchEvent.x - OriginalXPos;
                    touchEvent.dy = touchEvent.y - OriginalYPos;

                    Card03.position.add(touchEvent.dx,touchEvent.dy);
                    screenBoundary3();

                }
            }

        }
    }


    //boundary code reedited from sample game spaceship/platform demos - Keith
    private void screenBoundary1()
    {
        BoundingBox cardBound = Card01.getBound();
        if (cardBound.getLeft() < 0)
            Card01.position.x -= cardBound.getLeft();
        else if (cardBound.getRight() > mGame.getScreenWidth())
            Card01.position.x += (cardBound.getRight() - mGame.getScreenWidth());


        if (cardBound.getBottom() < 0)
            Card01.position.y -= cardBound.getBottom();
        else if (cardBound.getTop() > mGame.getScreenHeight())
            Card01.position.y += (cardBound.getTop() - mGame.getScreenHeight());

    }
    private void screenBoundary2()
    {
        BoundingBox cardBound = Card02.getBound();
        if (cardBound.getLeft() < 0)
            Card02.position.x -= cardBound.getLeft();
        else if (cardBound.getRight() > mGame.getScreenWidth())
            Card02.position.x -= (cardBound.getRight() - mGame.getScreenWidth());

        if (cardBound.getBottom() < 0)
            Card02.position.y -= cardBound.getBottom();
        else if (cardBound.getTop() > mGame.getScreenHeight())
            Card02.position.y -= (cardBound.getTop() - mGame.getScreenHeight());

    }
    private void screenBoundary3()
    {
        BoundingBox cardBound = Card03.getBound();
        if (cardBound.getLeft() < 0)
            Card03.position.x -= cardBound.getLeft();
        else if (cardBound.getRight() > mGame.getScreenWidth())
            Card03.position.x -= (cardBound.getRight() - mGame.getScreenWidth());
        if (cardBound.getBottom() < 0)
            Card03.position.y -= cardBound.getBottom();
        else if (cardBound.getTop() > mGame.getScreenHeight())
            Card03.position.y -= (cardBound.getTop() - mGame.getScreenHeight());

    }


    /**
     * Return a string that holds the corresponding label for the specified
     * type of touch event.
     *
     * @param type Touch event type
     * @return Touch event label
     *
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