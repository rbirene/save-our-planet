package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;
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

    //contains all hero cards
    private HashMap<String, Card> heroCardPool;
    private HashMap<String, Card> screenCardPool;

    //Define Player and Player's Deck
    private Hero hero;
    private Deck heroDeck;

    //Define Cards to be displayed on Screen
    private Card Card01;
    private Card Card02;
    private Card Card03;

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

    //Define the audioManager used by the screen
    private AudioManager audioManager;

    //Define type of touch event
    private String touchEventType;

    private Boolean formSubmitted = false;

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

        //Define the screen parameters[Niamh McCartney]
        this.aGame = game;

        //define game dimensions and viewports [Irene Bhuiyan]
        gameHeight = aGame.getScreenHeight();
        gameWidth = aGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //Initialise the Screen properties[Niamh McCartney]
        audioManager = aGame.getAudioManager();
        heroCardPool = aGame.getCardStore().getAllHeroCards(this);
        screenCardPool = new HashMap<>();
        hero = aGame.getHero();
        heroDeck = hero.getPlayerDeck();
        Card01 = heroDeck.getCard01(this);
        Card02 = heroDeck.getCard02(this);
        Card03 = heroDeck.getCard03(this);

        //Load the various images used by the screen
        loadScreenAssets();

        //set up background [Irene Bhuiyan]
        Bitmap chooseCardBackgroundImg = aGame.getAssetManager().getBitmap("chooseCardBackground");
        chooseCardBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f, chooseCardBackgroundImg , this);

        //Add Buttons
        addBackButton();
        addContinueButton();
        addShuffleButton();
        addInfoButton();
        addSettingsButton();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
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

        //if information button is pushed then load the instructions screen [Niamh McCartney]
        if (infoButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame, this));

        //if settings button is pushed then load the settings screen [Niamh McCartney]
        if (settingsButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new OptionsScreen(mGame));


        // Process any touch events occurring since the last update
        Input input = mGame.getInput();

        //List of touch events
        List<TouchEvent> touchEvents = input.getTouchEvents();

        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            touchEventType = touchEventTypeToString(event.type);

            if (touchEvents.size() > 0) {

                //update buttons displayed on the screen
                backButton.update(elapsedTime);
                continueButton.update(elapsedTime);
                shuffleButton.update(elapsedTime);
                infoButton.update(elapsedTime);
                settingsButton.update(elapsedTime);

                //if continue button is pushed then load the battle screen
                if (continueButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new BattleScreen(mGame));

                //if back button is pushed then return to the MenuScreen
                if (backButton.isPushTriggered())
                    mGame.getScreenManager().addScreen(new MenuScreen(mGame));

                if (touchEventType.equals("TOUCH_DOWN")) {
                    Vector2 layerTouch = new Vector2();
                    ViewportHelper.convertScreenPosIntoLayer(this.getDefaultScreenViewport(), event.x, event.y,
                            this.getDefaultLayerViewport(), layerTouch);

                    //If a card is touched change the background of the touched card
                    for (int pointerIdx = 0; pointerIdx < touchEvents.size(); pointerIdx++) {
                        for(int j=0; j<heroDeck.getDeck(this).size(); j++){
                            Card card = heroDeck.getDeck(this).get(j);
                            if (card.getBound().contains(layerTouch.x, layerTouch.y) && event.type == 0) {
                                if(card.cardSelected()){
                                    card.setSelected(false);}
                                else{card.setSelected(true);}
                                audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                            }
                        }
                    }
                }
            }
        }
    }

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
        graphics2D.clear(Color.WHITE);

        if(!formSubmitted){
            DisplayFormDialog();
            formSubmitted = true;
        }

        //Draw the screen background
        chooseCardBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);

        //Draw the Cards on the Screen
        drawCards(elapsedTime, graphics2D);

        //Draw the Buttons
        backButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
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
    private void displayDialogs(String text){
            InfoPopUp popUp = new InfoPopUp(getGame().getActivity(), text, ColourEnum.GREEN ,R.drawable.info_symbol, "OK", R.drawable.green_btn);
            popUp.showDialog();
    }

    private void DisplayFormDialog(){
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
     * Draw the cards
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     * Created By Niamh McCartney
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
        }
    }


    /**
     * Removes selected cards from
     * the players deck and replaces them
     * with new cards
     *
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

    /**
     * Add a Back Button to the screen
     * that takes you to the previous screen
     * <p>
     * Created By Niamh McCartney
     */
    private void addBackButton() {

        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");

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


    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    private void loadScreenAssets() {
        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/ChooseCardsScreenAssets.JSON");
        mGame.getAssetManager().loadAssets("txt/assets/CardAssets.JSON");
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }

}