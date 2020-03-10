package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Screens.ChooseCardScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Screens.InstructionsScreen;
import uk.ac.qub.eeecs.game.cardDemo.Screens.OptionsScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Villain;

/**
 * This class creates the MenuScreen
 *
 * @version 1.0
 */
public class MenuScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //define HashMap to contain all hero cards[Niamh McCartney]
    private HashMap<String, Card> heroCardPool;

    //define HashMap to contain all villain cards[Niamh McCartney]
    private HashMap<String, Card> villainCardPool = new HashMap<>();
    private HashMap<String, Card> screenCardPool = new HashMap<>();

    /*define card and deck objects used during
     *generation of the player decks
     *[Niamh McCartney]
     */
    private Card randCard;
    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Deck deck;

    //define Hero and Villains and their Decks [Niamh McCartney]
    private Deck heroDeck = getGame().getHero().getPlayerDeck();
    private Deck villainDeck = getGame().getVillain().getPlayerDeck();
    private Hero hero = getGame().getHero();
    private Villain villain = getGame().getVillain();

    //define game dimensions and viewports [Irene Bhuiyan]
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private int gameHeight, gameWidth;

    //define buttons [Irene Bhuiyan]
    private PushButton infoButton;
    private PushButton settingsButton;
    private PushButton playGame;
    private PushButton exit;

    //background [Irene Bhuiyan]
    private GameObject menuBackground;

//    /**
//     * Define the buttons for playing the 'games'
//     */
//    private PushButton mOptionsIcon;
//    private PushButton playGame;
//    private PushButton instructions;
//    private PushButton options;
//    private PushButton exit;

    private AssetManager assetManager;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a menu screen
     *
     * @param game Game to which this screen belongs
     */
    public MenuScreen(Game game) {
        super("MenuScreen", game);

        //define game dimensions and viewports [Irene Bhuiyan]
        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        // Load assets used by screen[Niamh McCartney]
        assetManager = mGame.getAssetManager();
        loadScreenAssets();

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 3;

        assetManager.loadAndAddMusic("gameMusic","sound/InPursuitOfSilence.mp3");

        //set up background [Irene Bhuiyan]
        Bitmap menuBackgroundImg = assetManager.getBitmap("menuBackground");
        menuBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f, menuBackgroundImg , this);

        //creates hero deck if deck is not already created [Niamh McCartney]
        if(heroDeck == null ) {
            //Create Hero Deck
            createHeroDeck();
        }

        //creates villain deck if deck is not already created [Niamh McCartney]
        if(villainDeck == null ) {
            //Create villain Deck
            createVillainDeck();
        }

        // add info and settings
        addInfoButton();
        addSettingsButton();

        // set up play and exit buttons [Irene Bhuiyan]
        playGame = new PushButton(240.0f, 180.0f, 145.0f, 40.0f, "btnPlay", "btnPlay",this);
        playGame.setPlaySounds(true, true);
        exit = new PushButton(240.0f, 130.0f, 76.0f, 40.0f, "btnExit", "btnExit", this);
        exit.setPlaySounds(true, true);

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Update each button and transition if needed
            infoButton.update(elapsedTime);
            settingsButton.update(elapsedTime);
            playGame.update(elapsedTime);
            exit.update(elapsedTime);

            if (playGame.isPushTriggered()){
                mGame.getScreenManager().addScreen(new ChooseCardScreen(mGame));
            }
            else if (infoButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame, this));
            }
            else if(settingsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new OptionsScreen(mGame));
            }
            else if(exit.isPushTriggered()) {
                System.exit(1);
            }
        }
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // draw background and buttons [Irene Bhuiyan]
        graphics2D.clear(Color.WHITE);
        menuBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        playGame.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        exit.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);

    }

    /**
     * generates random cards for the player decks
     *
     * @param numOfCards the number of cards to be generated
     * @param cardPool group of cards the deck will be chosen from
     *
     *  Created By Niamh McCartney
     */
    private Deck generateRandCards(int numOfCards, HashMap<String, Card> cardPool){
        screenCardPool = new HashMap<>();
        int num = 0;
        while(num<numOfCards) {
            randCard = getGame().getCardStore().getRandCard(cardPool);
            String name = randCard.getCardName();
            //If Card has not already been chosen then add to the HashMap
            if(!screenCardPool.containsKey(name)) {
                screenCardPool.put(name, randCard);
                num++;
                if (num == 1) {
                    Card01 = randCard;
                }
                if (num == 2) {
                    Card02 = randCard;
                }
                if (num == 3) {
                    Card03 = randCard;
                }
            }

        }
        //create deck with new Cards
        deck = new Deck(Card01, Card02, Card03);
        return deck;
    }

    /**
     * Creates a Hero Deck
     *
     *  Created By Niamh McCartney
     */
    private void createHeroDeck(){
        // get all the cards of type hero
        heroCardPool = getGame().getCardStore().getAllHeroCards(this);
        //create a deck with 3 randomly generated cards
        deck = generateRandCards(3, heroCardPool);
        //set this deck as the hero's deck
        hero.setPlayerDeck(deck);
    }

    /**
     * Creates a Villain Deck
     *
     *  Created By Niamh McCartney
     */
    private void createVillainDeck(){
        // get all the cards of type hero
        villainCardPool = getGame().getCardStore().getAllVillainCards(this);
        //create a deck with 3 randomly generated cards
        deck = generateRandCards(3, villainCardPool);
        //set this deck as the hero's deck
        villain.setPlayerDeck(deck);
    }

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    private void loadScreenAssets(){
        assetManager.loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        assetManager.loadAssets("txt/assets/CardAssets.JSON");

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


}