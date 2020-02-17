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

    //Viewports{Niamh McCartney}
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;

    private boolean paused = false;

    private GameObject pauseMenu, heroAvatarImg, villainAvatarImg;

    private Paint paint;

    private GameBoard board;

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

    //Buttons[Niamh McCartney]
    private PushButton infoButton;
    private PushButton settingsButton;
    private PushButton pause;
    private PushButton resume;
    private PushButton exit;
    private PushButton endTurnButton;

    //Define up game height and width
    private int gameHeight = getGame().getScreenHeight();
    private int gameWidth = getGame().getScreenWidth();

    public BattleScreen(Game game) {
        super("Battle", game);

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        //Load the assets to be used by the screen[Niamh McCartney]
        loadScreenAssets();

        board = new GameBoard(250.0f, 100.0f, 400.0f, 400.0f,assetManager.getBitmap("battleBackground"),this);
                
        //board = new GameBoard(game.getScreenWidth() / 2, game.getScreenHeight() / 2, 2000.0f, 1300.0f, game.getAssetManager().getBitmap("battleBackground"), this);

        paint = new Paint();
        paint.setTextSize(90.0f);
        paint.setARGB(255, 0, 0, 0);

        hero.setGameScreen(this);
        hero.setGameBoard(board);

        //set start positions of hero and villain Decks[Niamh McCartney]
        moveCardsToStartPosition(heroDeck.getDeck(this), 0.13f, 0.03f);
        moveCardsToStartPosition(villainDeck.getDeck(this), 0.07f, 0.235f);

        setupPause();

        //Add Buttons[Niamh McCartney]
        addInfoButton();
        addSettingsButton();
        addPauseButton();
    }

    public void moveCardsToStartPosition(ArrayList<Card> cards, float xPosScale, float yPosScale){
        //defines the spacing between the cards
        int spacing = 0;
        for(int i=0;i<cards.size();i++){
            //Set the start X and Y co-ordinates for each of the cards
            cards.get(i).setStartPosX(gameWidth*xPosScale + spacing);
            cards.get(i).setStartPosY(gameHeight*yPosScale);

            float xPos = cards.get(i).getStartPosX();
            float yPos = cards.get(i).getStartPosY();

            //set the position of the card on rhe screen
            cards.get(i).setPosition(xPos, yPos);
            spacing += 50;
        }
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        pause.update(elapsedTime);
        infoButton.update(elapsedTime);
        settingsButton.update(elapsedTime);
        board.update(elapsedTime);


        for (Card c:heroDeck.getDeck(this)) {
            c.update(elapsedTime);
        }

        if(touchEvents.size() > 0){
            hero.ProcessTouchInput(touchEvents);
        }


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

        board.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        if(paused){
            drawPause(elapsedTime, graphics2D);
            pauseUpdate(elapsedTime);
        }else{
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        }
        //Add Player Decks to Screen [Niamh McCartney]
        AddPlayerDecks(elapsedTime, graphics2D, "HeroCardBackground", heroDeck);
        AddPlayerDecks(elapsedTime, graphics2D, "VillainCardBackground", villainDeck);

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
    private void AddPlayerDecks(ElapsedTime elapsedTime, IGraphics2D graphics2D, String CardBackgroundName, Deck aDeck){

        for(int i = 0; i<aDeck.getDeck(this).size(); i++){
            Card card = aDeck.getDeck(this).get(i);
            //Set Card Background
            card.setCardBase(assetManager.getBitmap(CardBackgroundName));
            //Set Card Width and Height
            card.setWidth(54);
            card.setHeight(72);
            //Draw Card
            card.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
        }
    }


    /**
     * Add a info Button to the screen that
     * takes you to the instructions screen
     *
     * Created By Niamh McCartney
     */
    private void addInfoButton() {

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

        settingsButton = new PushButton(
                430.0f, 300.0f, 30.0f, 30.0f,
                "settingsBtn", "settingsBtnSelected", this);
        settingsButton.setPlaySounds(true, true);
    }

    /**
     * Add a pause Button to the screen
     * that brings up a pause screen
     *
     */
    private void addPauseButton() {

        pause = new PushButton(465.0f, 300.0f,
                30.0f, 30.0f, "pauseBtn", "pauseBtn", this);
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
        heroAvatarImg = new GameObject(430.0f, 50.0f, 100.0f, 100.0f, heroAvatar, this);
        heroAvatarImg.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        //display villain
        Bitmap villainAvatar = mGame.getAssetManager().getBitmap("ronald");
        villainAvatarImg = new GameObject(50.0f, 270.0f, 100.0f, 100.0f, villainAvatar, this);
        villainAvatarImg.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

    }

    /**public int getTurnNumber(int turnnumber) {
        return turnnumber;
    }

    public void setTurnNumber(int turnNumber)
    {
        this.turnnumber = turnnumber;
    }
     **/

}
