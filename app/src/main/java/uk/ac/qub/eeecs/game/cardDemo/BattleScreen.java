package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;


public class BattleScreen extends GameScreen {

    private GameBoard board;
    private ArrayList<Card> cards = new ArrayList<>();
    private PushButton pause;
    private PushButton resume;
    private boolean paused = false;
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private GameObject pauseMenu;

    private AssetManager assetManager = mGame.getAssetManager();

    //set up hero [Irene Bhuiyan]
    private Hero hero = getGame().getHero();

    //Define Users Deck of Cards [Niamh McCartney]
    private Deck heroDeck = hero.getPlayerDeck();

    //Define the cards in the deck [Niamh McCartney]
    private Card Card01 = heroDeck.getCard01(this);
    private Card Card02 = heroDeck.getCard02(this);
    private Card Card03 = heroDeck.getCard03(this);

    private PushButton infoButton;
    private PushButton settingsButton;

    public BattleScreen(Game game) {
        super("Battle", game);

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        loadScreenAssets();

        board = new GameBoard(game.getScreenWidth() / 2, game.getScreenHeight() / 2,
                1700.0f, 1000.0f, game.getAssetManager().getBitmap("tempBack"), this);

        pause = new PushButton(470.0f, 300.0f,
                30.0f, 30.0f, "pauseBtn", "pauseBtn", this);

        pauseGame();

        //Add Buttons
        addInfoButton();
        addSettingsButton();
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        pause.update(elapsedTime);
        infoButton.update(elapsedTime);
        settingsButton.update(elapsedTime);

        //if continue button is pushed then load the battle screen
        if (infoButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));

        //if back button is pushed then return to the MenuScreen
        if (settingsButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new OptionsScreen(mGame));

            if(pause.isPushTriggered()){
                paused = true;
            }else if(resume.isPushTriggered()){
                paused = false;
            }
        }



    public void pauseGame(){

        pauseMenu = new GameObject(mGame.getScreenWidth()/2,mGame.getScreenHeight()/2 ,
        800.0f, 700.0f, mGame.getAssetManager().getBitmap("optionsBackground"), this);

        resume = new PushButton(mGame.getScreenWidth()/2, mGame.getScreenHeight()/2,
                40.0f, 40.0f, "pauseBtn", "pauseBtn", this);

    }


    public void drawPause(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        pauseMenu.draw(elapsedTime,graphics2D);
        resume.draw(elapsedTime,graphics2D);

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        board.draw(elapsedTime, graphics2D);
        infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        if(!paused){
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        }

        if(paused){
            drawPause(elapsedTime, graphics2D);
        }

        //Add Player Decks to Screen [Niamh McCartney]
        AddPlayerDecks(elapsedTime, graphics2D);


    }

    /**
     * Draw the Cards on the Screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     *  {Created By Niamh McCartney}
     */
    private void AddPlayerDecks(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        int counterX = 0;
        int cardNum = 0;

        for(int i = 0; i<heroDeck.getDeck(this).size(); i++){
            Card card = heroDeck.getDeck(this).get(i);
            //Card X co-ordinate
            float x = graphics2D.getSurfaceHeight() * 0.3f + counterX;
            //Card Y co-ordinate
            float y = graphics2D.getSurfaceHeight() * 0.04f;
            //Set Card Background
            card.setCardBase(assetManager.getBitmap("CardBackground"));
            //Set Card Width and Height
            card.setWidth(60);
            card.setHeight(120);
            //Draw Card
            card.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            //Set Card position on Screen
            card.setPosition(x, y);
            cardNum++;
//            if (cardNum == 1) {
//                Card01 = value;
//            }
//            if (cardNum == 2) {
//                Card02 = value;
//            }
//            if (cardNum == 3) {
//                Card03 = value;
//            }
            counterX += 50;
        }
    }


    /**
     * Add a info Button to the screen that
     * takes you to the instructions screen
     *
     * Created By Niamh McCartney
     */
    private void addInfoButton() {

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");

        infoButton = new PushButton(15.0f, 20.0f,
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

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        settingsButton = new PushButton(
                50.0f, 20.0f, 30.0f, 30.0f,
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
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }
}
