package uk.ac.qub.eeecs.game;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Card;
import uk.ac.qub.eeecs.game.cardDemo.ChooseCardScreen;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Hero;
import uk.ac.qub.eeecs.game.cardDemo.InstructionsScreen;
import uk.ac.qub.eeecs.game.cardDemo.OptionsScreen;
import uk.ac.qub.eeecs.game.cardDemo.Villain;
import uk.ac.qub.eeecs.game.miscDemos.DemoMenuScreen;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
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

    /**
     * Define the buttons for playing the 'games'
     */
    private PushButton mSpaceshipDemoButton;
    private PushButton mPlatformDemoButton;
    private PushButton mCardDemoButton;
    private PushButton mDemosButton;
    private PushButton mOptionsIcon;

    private PushButton playGame;
    private PushButton instructions;
    private PushButton options;
    private PushButton exit;

    private AudioManager audioManager = mGame.getAudioManager();

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public MenuScreen(Game game) {
        super("MenuScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("SpaceDemoIcon", "img/SpaceDemoIcon.png");
        assetManager.loadAndAddBitmap("SpaceDemoIconSelected", "img/SpaceDemoIconSelected.png");
        assetManager.loadAndAddBitmap("CardDemoIcon", "img/CardDemoIcon.png");
        assetManager.loadAndAddBitmap("CardDemoIconSelected", "img/CardDemoIconSelected.png");
        assetManager.loadAndAddBitmap("PlatformDemoIcon", "img/PlatformDemoIcon.png");
        assetManager.loadAndAddBitmap("PlatformDemoIconSelected", "img/PlatformDemoIconSelected.png");
        assetManager.loadAndAddBitmap("DemosIcon", "img/DemosIcon.png");
        assetManager.loadAndAddBitmap("DemosIconSelected", "img/DemosIconSelected.png");
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");
        assetManager.loadAndAddBitmap("btnExit","img/btnExit.png");
        assetManager.loadAndAddBitmap("btnInstructions","img/btnInstructions.png");
        assetManager.loadAndAddBitmap("btnOptions","img/btnOptions.png");
        assetManager.loadAndAddBitmap("OptionsIcon","img/OptionsIcon.png");
        assetManager.loadAndAddBitmap("btnPlay","img/btnPlay.png");
        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 3;

        assetManager.loadAndAddMusic("gameMusic","sound/InPursuitOfSilence.mp3");

        //creates hero deck if deck is not already created [Niamh McCartney]
        if(heroDeck == null ) {
            assetManager.loadAssets("txt/assets/CardAssets.JSON");
            //Create Hero Deck
            createHeroDeck();
        }

        //creates villain deck if deck is not already created [Niamh McCartney]
        if(villainDeck == null ) {
            assetManager.loadAssets("txt/assets/CardAssets.JSON");
            //Create villain Deck
            createVillainDeck();
        }


        playGame = new PushButton(
                spacingX * 0.50f, spacingY * 1.5f, spacingX, spacingY,
                "btnPlay", "btnPlay",this);
        playGame.setPlaySounds(true, true);
        instructions = new PushButton(
                spacingX * 1.83f, spacingY * 1.5f, spacingX, spacingY,
                "btnInstructions", "btnInstructions", this);
        instructions.setPlaySounds(true, true);
        options = new PushButton(
                spacingX * 3.17f, spacingY * 1.5f, spacingX, spacingY,
                "btnOptions", "btnOptions", this);
        options.setPlaySounds(true, true);
        exit = new PushButton(spacingX * 4.50f, spacingY * 1.5f, spacingX, spacingY,
                "btnExit", "btnExit", this);
        exit.setPlaySounds(true, true);

        // Create the trigger buttons
        mSpaceshipDemoButton = new PushButton(
                spacingX * 0.50f, spacingY * 1.5f, spacingX, spacingY,
                "SpaceDemoIcon", "SpaceDemoIconSelected",this);
        mSpaceshipDemoButton.setPlaySounds(true, true);
        mPlatformDemoButton = new PushButton(
                spacingX * 1.83f, spacingY * 1.5f, spacingX, spacingY,
                "PlatformDemoIcon", "PlatformDemoIconSelected", this);
        mPlatformDemoButton.setPlaySounds(true, true);
        mCardDemoButton = new PushButton(
                spacingX * 3.17f, spacingY * 1.5f, spacingX, spacingY,
                "CardDemoIcon", "CardDemoIconSelected", this);
        mCardDemoButton.setPlaySounds(true, true);
        mDemosButton = new PushButton(
                spacingX * 4.50f, spacingY * 1.5f, spacingX, spacingY,
                "DemosIcon", "DemosIconSelected", this);
        mDemosButton.setPlaySounds(true, true);
        mOptionsIcon = new PushButton(
                spacingX*2.0f,spacingY*2.65f,spacingX,spacingY,
                "OptionsIcon","OptionsIcon",this);
        mOptionsIcon.setPlaySounds(true, true);

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
            mSpaceshipDemoButton.update(elapsedTime);
            mCardDemoButton.update(elapsedTime);
            mPlatformDemoButton.update(elapsedTime);
            mDemosButton.update(elapsedTime);
            mOptionsIcon.update(elapsedTime);

            playGame.update(elapsedTime);
            instructions.update(elapsedTime);
            options.update(elapsedTime);
            exit.update(elapsedTime);


            if (playGame.isPushTriggered())
                mGame.getScreenManager().addScreen(new ChooseCardScreen(mGame));
            else if (instructions.isPushTriggered())
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
            else if(options.isPushTriggered())
                mGame.getScreenManager().addScreen(new OptionsScreen(mGame));
            else if(mOptionsIcon.isPushTriggered())
                mGame.getScreenManager().addScreen(new DemoMenuScreen(mGame));
            else if(exit.isPushTriggered())
                mGame.getScreenManager().removeAllScreens();
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

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);

        playGame.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        instructions.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        options.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        exit.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);

        // mSpaceshipDemoButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        // mPlatformDemoButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        // mDemosButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        // mCardDemoButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mOptionsIcon.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }

    /**
     * generates random cards for the player decks
     *
     * @param numOfCards the number of cards to be generated
     * @param cardPool group of cards the deck will be chosen from
     *
     *  Created By Niamh McCartney
     */
    private void generateRandCards(int numOfCards, HashMap<String, Card> cardPool){
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
    }


    /**
     * Creates a Hero Deck
     *
     *  Created By Niamh McCartney
     */
    private void createHeroDeck(){
        // get all the cards of type hero
        heroCardPool = getGame().getCardStore().getAllHeroCards(this);
        //generate three random cards
        generateRandCards(3, heroCardPool);
        //create a deck with generated cards
        deck = new Deck(Card01, Card02, Card03);
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
        //generate three random cards
        generateRandCards(3, villainCardPool);
        //create a deck with generated cards
        deck = new Deck(Card01, Card02, Card03);
        //set this deck as the hero's deck
        villain.setPlayerDeck(deck);
    }

}