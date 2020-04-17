package uk.ac.qub.eeecs.game.cardDemo.Screens;

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
import uk.ac.qub.eeecs.game.cardDemo.Enums.CardType;
import uk.ac.qub.eeecs.game.cardDemo.CardStore;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Villain;

/**
 * This class creates the MenuScreen
 *
 */
public class MenuScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define card and deck objects used during
    //generation of the player decks [Niamh McCartney]
    private Card randCard;
    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Deck deck;

    //Define CardStore used by the Game[Niamh McCartney]
    private CardStore cardStore;

    //Define Hero and Villain used by Game and their Decks [Niamh McCartney]
    private Hero hero = getGame().getHero();
    private Villain villain = getGame().getVillain();
    private Deck heroDeck = getGame().getHero().getPlayerDeck();
    private Deck villainDeck = getGame().getVillain().getPlayerDeck();

    //Define game dimensions and viewports [Irene Bhuiyan]
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private int gameHeight, gameWidth;

    //Define buttons
    private PushButton infoButton; //[Niamh McCartney]
    private PushButton settingsButton;//[Niamh McCartney]
    private PushButton playGame; //[Irene Bhuiyan]
    private PushButton exit; //[Irene Bhuiyan]
    private PushButton leaderBoardsButton; //[Niamh McCartney]

    //Background [Irene Bhuiyan]
    private GameObject menuBackground;

    //define the AssetManager used by the Game
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

        assetManager.loadAndAddMusic("gameMusic","sound/InPursuitOfSilence.mp3");

        //set up background [Irene Bhuiyan]
        Bitmap menuBackgroundImg = assetManager.getBitmap("menuBackground");
        menuBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f, menuBackgroundImg , this);

        //Create Player Decks used during Game [Niamh McCartney]
        cardStore = game.getCardStore();
        createPlayerDecks();

        //Add buttons [Niamh McCartney]
        addInfoButton();
        addSettingsButton();
        addLeaderBoardButton();

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
            leaderBoardsButton.update(elapsedTime);

            if (playGame.isPushTriggered()){
                mGame.getScreenManager().addScreen(new ChooseCardScreen(mGame));
            }//If infoButton is selected take User to Instructions Screen [Niamh McCartney]
            else if (infoButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame, this));
            }//If settingsButton is selected take User to Options Screen [Niamh McCartney]
            else if(settingsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new OptionsScreen(mGame));
            }
            else if(exit.isPushTriggered()) {
                System.exit(1);
            }//If leaderBoardsButton is selected take User to LeaderBoard Screen [Niamh McCartney]
            else if(leaderBoardsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new LeaderBoardScreen(mGame));
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
        leaderBoardsButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
    }

    /**
     * generates random cards for the player decks
     *
     * @param numOfCards the number of cards to be generated
     * @param cardTypeEnum Type of Cards to be generated
     *
     *  Created By Niamh McCartney
     */
    private Deck generateRandCards(int numOfCards, CardType cardTypeEnum){
        HashMap<String, Card> cardPool = new HashMap<>();
        int num = 0;

        while(num<numOfCards) {
            randCard = cardStore.getRandCard(cardTypeEnum);
            String name = randCard.getCardName();
            //If Card has not already been chosen then add to the HashMap
            if(!cardPool.containsKey(name)) {
                cardPool.put(name, randCard);
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
     * Create Player Decks used during Gameplay
     *
     *  Created By Niamh McCartney
     */
    private void createPlayerDecks(){
        //creates hero deck if deck is not already created
        if(heroDeck == null || !heroDeck.getDeckCreated()) {
            cardStore.renewHealthOfCards(CardType.HERO_CARD);
            //Create Hero Deck
            createHeroDeck();
        }

        //creates villain deck if deck is not already created
        if(villainDeck == null || !villainDeck.getDeckCreated()) {
            cardStore.renewHealthOfCards(CardType.VILLAIN_CARD);
            //Create villain Deck
            createVillainDeck();
        }
    }

    /**
     * Creates a Hero Deck
     *
     *  Created By Niamh McCartney
     */
    private void createHeroDeck(){
        //create a deck with 3 randomly generated cards
        deck = generateRandCards(3, CardType.HERO_CARD);
        //set this deck as the hero's deck
        hero.setPlayerDeck(deck);
    }

    /**
     * Creates a Villain Deck
     *
     *  Created By Niamh McCartney
     */
    private void createVillainDeck(){
        //create a deck with 3 randomly generated cards
        deck = generateRandCards(3, CardType.VILLAIN_CARD);
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

    /**
     * Add a LeaderBoard Button to the screen
     * that takes you to the LeaderboardScreen Screen
     *
     * Created By Niamh McCartney
     */
    private void addLeaderBoardButton() {

        leaderBoardsButton = new PushButton(
                405.0f, 300.0f, 32.0f, 32.0f,
                "HighScoreButton", "HighScoreButtonSelected", this);
        leaderBoardsButton.setPlaySounds(true, true);
    }

}