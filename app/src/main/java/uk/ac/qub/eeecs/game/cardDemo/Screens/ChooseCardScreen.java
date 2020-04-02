package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.CardStore;
import uk.ac.qub.eeecs.game.cardDemo.Colour.ColourEnum;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.FormPopUp;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.InfoPopUp;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Hero;


/**
 * Screen displays the players deck and gives
 * the player the option to swap out cards in
 * their deck for new random cards. Once the
 * player has chosen their cards they can
 * proceed to the BattleScreen
 *
 */
public class ChooseCardScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the Game the screen is created in
    private Game aGame;

    //Defines HashMap which contains all hero cards
    private HashMap<String, Card> heroCardPool;
    //Defines HashMap which contains all Cards displayed on screen
    private HashMap<String, Card> screenCardPool;

    //Define Player and Player's Deck
    private Hero hero;
    private Deck heroDeck;

    //Define Cards displayed on Screen
    private Card Card01;
    private Card Card02;
    private Card Card03;

    //Define the viewports and screen dimensions
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private int gameHeight, gameWidth;

    //Define Buttons
    private PushButton backButton;
    private PushButton continueButton;
    private PushButton shuffleButton;
    private PushButton infoButton;
    private PushButton settingsButton;

    //background [Irene Bhuiyan]
    private GameObject chooseCardBackground;

    //Define the AudioManager used by the screen
    private AudioManager audioManager;
    //Define the AssetManager used by the screen
    private AssetManager assetManager;
    //Define the CardStore used by the screen
    private CardStore cardStore;

    //Define type of touch event
    private String touchEventType;

    //Returns true if Sign in form has been displayed
    private Boolean formDisplayed = false;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the ChooseCard game screen
     *
     * @param game Game to which this screen belongs
     *
     * Created by Niamh McCartney
     */
    public ChooseCardScreen(Game game) {
        super("CardScreen", game);

        //Define the screen parameters
        this.aGame = game;

        //define game dimensions and viewports [Irene Bhuiyan]
        gameHeight = aGame.getScreenHeight();
        gameWidth = aGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //Initialise the Screen properties//

        audioManager = aGame.getAudioManager();
        assetManager = aGame.getAssetManager();
        cardStore = aGame.getCardStore();

        heroCardPool = cardStore.getAllHeroCards(this);
        screenCardPool = new HashMap<>();

        hero = aGame.getHero();
        heroDeck = hero.getPlayerDeck();

        Card01 = heroDeck.getCard01(this);
        Card02 = heroDeck.getCard02(this);
        Card03 = heroDeck.getCard03(this);

        //Load the various images used by the screen
        loadScreenAssets();

        //set up background [Irene Bhuiyan]
        Bitmap chooseCardBackgroundImg = assetManager.getBitmap("chooseCardBackground");
        chooseCardBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f,
                chooseCardBackgroundImg , this);

        //Add Buttons
        addBackButton();
        addContinueButton();
        addShuffleButton();
        addInfoButton();
        addSettingsButton();

        resetDecks();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Update Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the ChooseCards screen
     *
     * @param elapsedTime Elapsed time information
     *
     * {Created By Niamh McCartney}
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        //Process any touch events occurring since the last update
        Input input = mGame.getInput();

        //List of touch events
        List<TouchEvent> touchEvents = input.getTouchEvents();

        //Iterate through touch events
        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            touchEventType = touchEventTypeToString(event.type);

            //If touch event has occurred update the screen objects
            if (touchEvents.size() > 0) {
                updateButtons(elapsedTime);
                updateButtonEvents();
                updateCards(event);
            }
        }
    }

    /**
     * Update the ChooseCards screen Buttons
     *
     * @param elapsedTime Elapsed time information
     *
     * Created By Niamh McCartney
     */
    private void updateButtons(ElapsedTime elapsedTime){
        //update buttons displayed on the screen
        backButton.update(elapsedTime);
        continueButton.update(elapsedTime);
        shuffleButton.update(elapsedTime);
        infoButton.update(elapsedTime);
        settingsButton.update(elapsedTime);
    }

    /**
     * Updates the ChooseCards screen button events
     * and provides information on actions to be
     * performed when a button event occurs
     *
     * Created By Niamh McCartney
     */
    private void updateButtonEvents(){
        //if continue button is pushed then load the battle screen
        if (continueButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new BattleScreen(mGame));

        //if back button is pushed then return to the MenuScreen
        if (backButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));

        //if information button is pushed then load the instructions screen
        if (infoButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame,
                    this));

        //if settings button is pushed then load the settings screen
        if (settingsButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new OptionsScreen(mGame));
    }

    /**
     * Reacts to touch events on Cards displayed
     * on the screen by updating the background
     * of the relevant Card/s
     *
     * Created By Niamh McCartney
     */
    private void updateCards(TouchEvent event){
        if (touchEventType.equals("TOUCH_DOWN")) {
            Vector2 layerTouch = new Vector2();
            ViewportHelper.convertScreenPosIntoLayer(this.getDefaultScreenViewport(), event.x,
                    event.y, this.getDefaultLayerViewport(), layerTouch);

            //iterates through Cards on screen
            for(int j=0; j<heroDeck.getDeck(this).size(); j++){

                Card card = heroDeck.getDeck(this).get(j);

                //If card is touched set the card to
                // selected/unselected depending on its current state
                if (card.getBound().contains(layerTouch.x, layerTouch.y) && event.type == 0) {
                    if(card.cardSelected()){
                        card.setSelected(false);}
                    else{card.setSelected(true);}
                    audioManager.play(assetManager.getSound("CardSelect"));
                }
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Draw Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the ChooseCards screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     * Created by Niamh McCartney
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        //Display Sign-In Pop-Up box if it has not already been displayed
        if(!formDisplayed){
            displayFormPopUp();
            formDisplayed = true;
        }

        //Draw the screen background
        chooseCardBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);

        //Draw the Cards on the Screen
        drawCards(elapsedTime, graphics2D);
        //Draw the Buttons on the Screen
        drawButtons(elapsedTime, graphics2D);

        //Updates actions taken for shuffle button touch events
        updateShuffleButtonEvents();

    }

    /**
     * Draw the cards to be displayed on the Screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     * Created By Niamh McCartney
     */
    private void drawCards(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Define the position in the deck of the Card currently being drawn
        int counterX = 0;
        //Define the size of the deck
        int deckSize = heroDeck.getDeck(this).size();

        for (int i = 0; i < deckSize; i++) {
            Card card = heroDeck.getDeck(this).get(i);

            //Set card co-ordinates//

            //Card y co-ordinate
            float y = mDefaultLayerViewport.y;
            //Spacing between Cards
            float spacing = 70;
            //Card x co-ordinate
            float x1 = mDefaultLayerViewport.x / deckSize;
            float x = spacing + 2 * x1 * counterX++;
            //set Card position on screen
            card.setPosition(x, y);

            //Set card Dimensions//

            //set card width
            card.setWidth(144);
            //set card Height
            card.setHeight(192);


            //Draw card on Screen
            card.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }
    }

    /**
     * Draw the buttons to be displayed on the Screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     * Created By Niamh McCartney
     */
    private void drawButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        backButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        continueButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        shuffleButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
    }

    /**
     * If shuffle button is pushed and deck has been shuffled then
     * display dialog informing user.
     * If deck has not been shuffled and cards are selected then
     * shuffle all selected cards.
     *
     * Created By [Niamh McCartney]
     */
    private void updateShuffleButtonEvents(){
        if (shuffleButton.isPushTriggered()){
            if(heroDeck.getDeckShuffled()){
                displayInfoPopUp("You can only shuffle your deck once");
            }
            else if(noCardsSelected()){
                displayInfoPopUp("You must select the cards in your\ndeck you wish to shuffle");
            }else{
                shuffleCards();
            }
        }
    }

    /**
     * Display PopUp box with message for the User
     *
     * @param message PopUp Box Message
     *
     * Created By Niamh McCartney
     */
    private void displayInfoPopUp(String message){
        InfoPopUp popUp = new InfoPopUp(getGame().getActivity(), message, ColourEnum.GREEN ,R.drawable.info_symbol, "OK", R.drawable.green_btn);
        popUp.showDialog();
    }

    /**
     * Display PopUp box which allows user
     * to choose from a list of previous
     * users or create a new user
     *
     * Created By Niamh McCartney
     */
    private void displayFormPopUp(){
        String message = "Choose your name from the list of players below or fill out the form to add your name to the list";
        FormPopUp dialog = new FormPopUp(getGame().getActivity(), getGame(), message ,ColourEnum.WHITE, R.drawable.profile_icon, R.drawable.green_btn);
        dialog.showDialog();
    }

    /**
     * Returns true if no Cards have been selected
     *
     * Created By Niamh McCartney
     */
    private Boolean noCardsSelected(){
        for (int i =0; i<heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);
            if(value.cardSelected()){
                return false;
            }
        }
        return true;
    }

    /**
     * Removes selected cards from
     * the players deck and replaces them
     * with new cards
     *
     * Created By Niamh McCartney
     */
    private void shuffleCards() {
        //Gets the Cards currently displayed on screen
        screenCardPool.put(Card01.getCardName(), Card01);
        screenCardPool.put(Card02.getCardName(), Card02);
        screenCardPool.put(Card03.getCardName(), Card03);

        //Temporary list of Cards to contain new card deck
        ArrayList<Card> tempCardPool = new ArrayList<>();

        //Iterates through the players current deck of cards
        for (int i = 0; i < heroDeck.getDeck(this).size(); i++) {
            Card value = heroDeck.getDeck(this).get(i);

            //Checks for selected cards
            if (value.cardSelected()) {
                Card randCard;
                int num = 0;
                //Finds a new random card that isn't in the old deck or the new deck
                while (num < 1) {
                    randCard = cardStore.getRandCard(heroCardPool);
                    String name = randCard.getCardName();
                    //If Card has not already been chosen then add to temporary list
                    if (!screenCardPool.containsKey(name) && heroDeck.checkDeck(randCard) == -1) {
                        tempCardPool.add(i, randCard);
                        screenCardPool.put(name, randCard);
                        num++;
                    }

                }
                //Adds Card to new deck if Card is not selected
            } else {
                tempCardPool.add(i, value);
            }
        }

        //Assigns new deck to the player
        heroDeck.setDeck(tempCardPool);
        //Deck has now been shuffled
        heroDeck.setDeckShuffled(true);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Button Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Add a Back Button to the screen
     * that takes you to the previous screen
     * <p>
     * Created By Niamh McCartney
     */
    private void addBackButton() {

        backButton = new PushButton(20.0f, 40.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);
        backButton.setPlaySounds(true, true);
    }

    /**
     * Add a Continue Button to the screen
     * that takes you to the Battle Screen
     * <p>
     * Created By Niamh McCartney
     */
    private void addContinueButton() {

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
    private void addShuffleButton() {

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

    // /////////////////////////////////////////////////////////////////////////
    // Other Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    private void loadScreenAssets() {
        assetManager.loadAssets("txt/assets/ChooseCardsScreenAssets.JSON");
        assetManager.loadAssets("txt/assets/CardAssets.JSON");
    }

    private void resetDecks(){
        for(int i = 0; i<heroDeck.getDeck(this).size(); i++){
            Card card = heroDeck.getDeck(this).get(i);
            //Set card to unselected
            card.setSelected(false);

        }
    }

    /**
     * Return a string that holds the corresponding label for the specified
     * type of touch event.
     *
     * @param type Touch event type
     * @return Touch event label
     *
     * Code taken from 'InputDemoScreen' class in 'miscDemos' folder
     * of provided gage code - no modifications made
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