package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Paint;

import java.util.ArrayList;
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
import uk.ac.qub.eeecs.game.MenuScreen;


public class BattleScreen extends GameScreen {

    private GameBoard board;
    private ArrayList<Card> cards = new ArrayList<>();
    private PushButton pause;
    private PushButton resume;
    private PushButton exit;
    private boolean paused = false;
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private GameObject pauseMenu, heroAvatarImg, villainAvatarImg;
    private Paint paint;

    private AssetManager assetManager = mGame.getAssetManager();

    //set up hero [Irene Bhuiyan]
    private Hero hero = getGame().getHero();
    //set up villain [Niamh McCartney]
    private Villain villain = getGame().getVillain();

    //Define player decks [Niamh McCartney]
    private Deck heroDeck = hero.getPlayerDeck();
    private Deck villainDeck = villain.getPlayerDeck();

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
                2000.0f, 1300.0f, game.getAssetManager().getBitmap("battleBackground"), this);

        pause = new PushButton(465.0f, 300.0f,
                30.0f, 30.0f, "pauseBtn", "pauseBtn", this);

        paint = new Paint();
        paint.setTextSize(90.0f);
        paint.setARGB(255, 0, 0, 0);

        setupPause();

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

        //if information button is pushed then load the instructions screen [Niamh McCartney]
        if (infoButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));

        //if settings button is pushed then load the settings screen [Niamh McCartney]
        if (settingsButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new OptionsScreen(mGame));

            if(pause.isPushTriggered()){
                paused = true;
            }else if(resume.isPushTriggered()){
                paused = false;
            }
        }

    private void pauseUpdate(ElapsedTime elapsedTime) {

        pauseMenu.update(elapsedTime);
        resume.update(elapsedTime);
        exit.update(elapsedTime);

        if(mGame.getInput().getTouchEvents().size() > 0){
            if(resume.isPushTriggered()){
                paused = false;
            }
            if(exit.isPushTriggered()){
                paused = false;
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            }
        }



    }

    public void setupPause(){

        pauseMenu = new GameObject(240.0f,170.0f ,
                200.0f, 175.0f, mGame.getAssetManager().getBitmap("optionsBackground2"), this);

        resume = new PushButton(240.0f, 170.0f,
                100.0f, 50.0f, "resumeBtn", "resume2Btn", this);

        exit = new PushButton(240.0f, 115.0f,
                100.0f, 50.0f, "menuBtn", "menu2Btn", this);
    }

    public void drawPause(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        pauseMenu.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        resume.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        exit.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        graphics2D.drawText("Game Paused", mGame.getScreenWidth()/3, mGame.getScreenHeight()/3, paint);

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        board.draw(elapsedTime, graphics2D);
        infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        if(paused){
            drawPause(elapsedTime, graphics2D);
            pauseUpdate(elapsedTime);
        }else{
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        }
        //Add Player Decks to Screen [Niamh McCartney]
        //AddPlayerDecks(elapsedTime, graphics2D, "HeroCardBackground", heroDeck, 0.3f, 0.04f, 60, 120, 50);
        //AddPlayerDecks(elapsedTime, graphics2D, "VillainCardBackground", villainDeck, 0.11f, 0.23f, 60, 84, 45);
        AddPlayerDecks(elapsedTime, graphics2D, "HeroCardBackground", heroDeck, 0.215f, 0.036f);
        AddPlayerDecks(elapsedTime, graphics2D, "VillainCardBackground", villainDeck, 0.11f, 0.235f);

        // display players [Irene Bhuiyan]
        displayPlayers(elapsedTime, graphics2D);

    }

    /**
     * Draw the Cards on the Screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     *  {Created By Niamh McCartney}
     */
    private void AddPlayerDecks(ElapsedTime elapsedTime, IGraphics2D graphics2D, String CardBackgroundName, Deck aDeck, float xPos, float yPos){

        int counterX = 0;
        int cardNum = 0;

        for(int i = 0; i<aDeck.getDeck(this).size(); i++){
            Card card = aDeck.getDeck(this).get(i);
            //Card X co-ordinate
            float x = graphics2D.getSurfaceHeight() * xPos + counterX;
            //Card Y co-ordinate
            float y = graphics2D.getSurfaceHeight() * yPos;
            //Set Card Background
            card.setCardBase(assetManager.getBitmap(CardBackgroundName));
            //Set Card Width and Height
            card.setWidth(54);
            card.setHeight(72);
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

        infoButton = new PushButton(395.0f, 300.0f,
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
                430.0f, 300.0f, 30.0f, 30.0f,
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

    /**
     *
     * Created by [Irene Bhuiyan]
     * Displays hero and villain on screen.
     *
     */
    private void displayPlayers(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        //display hero
        Bitmap heroAvatar = mGame.getAssetManager().getBitmap("freta");
        heroAvatarImg = new GameObject(50.0f, 60.0f, 100.0f, 100.0f, heroAvatar, this);
        heroAvatarImg.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        //display villain
        Bitmap villainAvatar = mGame.getAssetManager().getBitmap("ronald");
        villainAvatarImg = new GameObject(50.0f, 260.0f, 100.0f, 100.0f, villainAvatar, this);
        villainAvatarImg.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

    }

}
