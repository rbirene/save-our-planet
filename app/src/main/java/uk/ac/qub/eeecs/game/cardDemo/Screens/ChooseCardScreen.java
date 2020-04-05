package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
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
 * Created by Niamh McCartney
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

    //Define Player and Player's Deck
    private Hero hero;
    private Deck heroDeck;

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
    private Boolean userFormDisplayed = false;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the ChooseCard game screen
     * @param game Game to which this screen belongs
     *
     * Created by Niamh McCartney
     */
    public ChooseCardScreen(Game game) {
        super("CardScreen", game);

        //Define the screen parameters
        this.aGame = game;

        //Define game dimensions and viewports [Irene Bhuiyan]
        gameHeight = aGame.getScreenHeight();
        gameWidth = aGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //Initialise the Screen properties//

        audioManager = aGame.getAudioManager();
        assetManager = aGame.getAssetManager();
        cardStore = aGame.getCardStore();

        heroCardPool = cardStore.getAllHeroCards(this);

        hero = aGame.getHero();
        heroDeck = hero.getPlayerDeck();

        //Load the various images used by the screen
        loadScreenAssets();

        //set up background [Irene Bhuiyan]
        Bitmap chooseCardBackgroundImg = assetManager.getBitmap("chooseCardBackground");
        chooseCardBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f,
                chooseCardBackgroundImg , this);

        //Create the screen Buttons
        setButtonProperties();

        //Reset cards in the hero deck to their original state
        resetDeck(heroDeck);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Update Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the ChooseCards screen
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
     * @param elapsedTime Elapsed time information
     *
     * Created By Niamh McCartney
     */
    private void updateButtons(ElapsedTime elapsedTime){
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
     * @param event touch event to be processed
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
        if(!userFormDisplayed){
            drawUserFormPopUp();
            userFormDisplayed = true;
        }

        //Draw the screen background
        chooseCardBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);

        //Draw the Cards on the Screen
        drawCards(elapsedTime, graphics2D);
        //Draw the Buttons on the Screen
        drawButtons(elapsedTime, graphics2D);

        //Updates actions taken for shuffle button touch events
        drawShuffleButtonEvents();
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
    private void drawShuffleButtonEvents(){
        if (shuffleButton.isPushTriggered()){
            if(heroDeck.getDeckShuffled()){
                drawInfoPopUp("You can only shuffle your deck once");
            }
            else if(heroDeck.noCardsSelected()){
                drawInfoPopUp("You must select the cards in your\ndeck you wish to shuffle");
            }else{
                shuffleCards();
            }
        }
    }

    /**
     * Display PopUp box with message for the User
     * @param message PopUp Box Message
     *
     * Created By Niamh McCartney
     */
    private void drawInfoPopUp(String message){
        InfoPopUp popUp = new InfoPopUp(getGame().getActivity(), message, ColourEnum.GREEN,
                R.drawable.info_symbol, "OK", R.drawable.green_btn);
        popUp.showDialog();
    }

    /**
     * Display PopUp box which allows user to choose from
     * a list of previous users or create a new user
     *
     * Created By Niamh McCartney
     */
    private void drawUserFormPopUp(){
        String message = "Choose your name from the list of players below or fill out" +
                " the form to add your name to the list";
        FormPopUp popUp = new FormPopUp(getGame().getActivity(), getGame(), message,
                ColourEnum.WHITE, R.drawable.profile_icon, R.drawable.green_btn);
        popUp.showDialog();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Button Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Sets the properties for each of the Screen's Buttons
     *
     * Created By Niamh McCartney
     */
    private void setButtonProperties(){
        backButton = createButton(20.0f, 40.0f, 50.0f, 50.0f,
                "BackArrow", "BackArrowSelected",
                true, true);
        continueButton = createButton(450.0f, 42.0f, 50.0f, 50.0f,
                "continueBtn", "continueBtn",
                true, true);
        shuffleButton = createButton(235.0f, 42.0f, 55.0f, 55.0f,
                "shuffleBtn", "shuffleBtn",
                true, true);
        infoButton = createButton( 430.0f, 300.0f, 28.0f, 28.0f,
                "infoBtn", "infoBtnSelected",
                true, true);
        settingsButton = createButton(465.0f, 300.0f, 30.0f, 30.0f,
                "settingsBtn", "settingsBtnSelected",
                true, true);
    }

    /**
     * Creates a Button with the given properties
     * @param xPos              X Co-ordinate of the Button
     * @param xPos              Y Co-ordinate of the Button
     * @param width             Width of the Button
     * @param height            Height of the Button
     * @param imageName         Name of button's image in text file
     * @param selectedImageName Name of button's image in text file
     * @param playPushSound     Boolean to determine if sound plays when Button is pushed
     * @param playReleaseSound  Boolean to determine if sound plays when Button is released
     *
     * Created By Niamh McCartney
     */
    private PushButton createButton(float xPos, float yPos, float width,
                                    float height, String imageName, String selectedImageName,
                                    Boolean playPushSound, Boolean playReleaseSound){
        //Create Button
        PushButton aButton = new PushButton(xPos, yPos, width, height, imageName,
                selectedImageName, this);
        //Set-up Button Sounds
        aButton.setPlaySounds(playPushSound, playReleaseSound);

        return aButton;
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

    /**
     * Resets each Card in the displayed
     * deck to its original state
     *
     * Created By Niamh McCartney
     */
    private void resetDeck(Deck aDeck){
        for(int i = 0; i<aDeck.getDeck(this).size(); i++){
            Card card = aDeck.getDeck(this).get(i);
            //Set card to unselected
            card.setSelected(false);
        }
    }

    /**
     * Removes selected cards from the players
     * deck and replaces them with new cards
     *
     * Created By Niamh McCartney
     */
    private void shuffleCards() {
        //Temporary list of Cards to contain new card deck
        HashMap<String, Card> tempCardPool = new HashMap<>();

        //Iterates through the players current deck of cards
        for (int i = 0; i < heroDeck.getDeck(this).size(); i++) {
            Card card = heroDeck.getDeck(this).get(i);

            //Checks for selected cards
            if (card.cardSelected()) {
                Boolean cardFound = false;
                //Finds a new random card that isn't in the old deck or the new deck
                while (!cardFound) {
                    Card randCard = cardStore.getRandCard(heroCardPool);
                    String name = randCard.getCardName();

                    //If Card has not already been chosen then add to temporary list
                    if (!tempCardPool.containsKey(name) && heroDeck.checkDeck(randCard) == -1) {
                        tempCardPool.put(randCard.getCardName(), randCard);
                        cardFound = true;
                    }
                }
                //Add Card to temporary HashMap if Card is not selected
            } else {
                tempCardPool.put(card.getCardName(), card);
            }
        }
        //Get Cards from HashMap
        Collection<Card> newCards = tempCardPool.values();
        //Assigns new deck to the player
        heroDeck.setDeck(new ArrayList<>(newCards));
        //Deck has now been shuffled
        heroDeck.setDeckShuffled(true);
    }

    /**
     * Return a string that holds the corresponding label
     * for the specified type of touch event.
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