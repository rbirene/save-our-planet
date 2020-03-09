package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.gameResultPopUpDialog;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Deck;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.CoinFlipDialog;
import uk.ac.qub.eeecs.game.cardDemo.GameBoard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Villain;


public class BattleScreen extends GameScreen {

    //Viewports{Niamh McCartney}
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;

    private boolean paused = false;

    private GameObject pauseMenu, heroAvatarImg, villainAvatarImg;

    private Paint paint;

    private GameBoard board;

    private AssetManager assetManager = mGame.getAssetManager();
    private ScreenManager screenManager = mGame.getScreenManager();

    //set up hero [Irene Bhuiyan]
    private Hero hero = getGame().getHero();

    //set up villain [Niamh McCartney]
    private Villain villain = getGame().getVillain();

    //Define player decks [Niamh McCartney]
    private Deck heroDeck = hero.getPlayerDeck();
    private Deck villainDeck = villain.getPlayerDeck();

    private Player firstPlayer;
    private Player secondPlayer;

    //Buttons[Niamh McCartney]
    private PushButton infoButton;
    private PushButton settingsButton;
    private PushButton pause;
    private PushButton resume;
    private PushButton exit;
    private PushButton endTurnButton;

    //Define up game height and width
    private int gameHeight;
    private int gameWidth;

    //returns true if the player to take the first turn has been decided[Niamh McCartney]
    private Boolean firstTurnDecided;

    private Boolean gameEnded = false;

    Boolean healthChanged = false;

    private Boolean playerTurn = true;

    public BattleScreen(Game game) {
        super("Battle", game);

        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();

        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //Load the assets to be used by the screen[Niamh McCartney]
        loadScreenAssets();

        board = new GameBoard(220.0f, 160.0f, 520.0f, 325.0f,
                assetManager.getBitmap("battleBackground"),this);
        endTurnButton = new PushButton(360.0f, 300.0f,
                30.0f, 30.0f, "endTurn", "endTurn",this);

        paint = new Paint();
        paint.setTextSize(90.0f);
        paint.setARGB(255, 0, 0, 0);
        paint.setUnderlineText(true);


        hero.setGameScreen(this);
        hero.setGameBoard(board);

        villain.setGameBoard(board);
        villain.setGameScreen(this);
        villain.setPlayerCards(villainDeck.getDeck(this));

        //set start positions of hero and villain Decks[Niamh McCartney]
        moveCardsToStartPosition(heroDeck.getDeck(this), 0.13f, 0.03f);
        moveCardsToStartPosition(villainDeck.getDeck(this), 0.07f, 0.235f);

        setupPause();

        //Add Buttons[Niamh McCartney]
        addInfoButton();
        addSettingsButton();
        addPauseButton();
        firstTurnDecided = false;

        // gameLoop();
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

    public void checkHealth(){
        if(firstPlayer.getPlayerHealth()<1 && secondPlayer.getPlayerHealth()<1){
            gameEnded = true;
        }
    }

    public void gameLoop(){
        firstPlayer.takeFirstTurn();
        do{
            checkHealth();
            if(!gameEnded){ secondPlayer.takeTurn();}
            checkHealth();
            if(!gameEnded){firstPlayer.takeTurn();}
            checkHealth();

        }while(!gameEnded);

        if(hero.getPlayerHealth()<1){
            gameResultPopUpDialog popUp = new gameResultPopUpDialog();
            String message = "You lost! " + villain.getPlayerName() + " has destroyed the planet!";
            popUp.showDialog(getGame().getActivity(), message, R.drawable.sad_earth);
        }if(villain.getPlayerHealth()<1){
            gameResultPopUpDialog popUp = new gameResultPopUpDialog();
            String message = "You Won! You've saved the planet from destruction!";
            popUp.showDialog(getGame().getActivity(), message, R.drawable.happy_earth);
        }
    }

    public void checkEndGame(){
        if(hero.getPlayerHealth() <= 0){
            mGame.getScreenManager().addScreen(new EndGame(mGame, hero));
        }else if(villain.getPlayerHealth() <=0){
            mGame.getScreenManager().addScreen(new EndGame(mGame, villain));
        }
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if(pause.isPushTriggered()){
            paused = true;
        }else if(resume.isPushTriggered()){
            paused = false;
        }

       if(!paused) {
           if (playerTurn) {
               if (!hero.getCardPlayed()) {
                   hero.ProcessTouchInput(touchEvents);
               }
           } else {
               villain.playAI();
               playerTurn = true;
           }
       }
        checkEndGame();

       hero.update(elapsedTime);
       villain.update(elapsedTime);

        pause.update(elapsedTime);
        infoButton.update(elapsedTime);
        settingsButton.update(elapsedTime);
        board.update(elapsedTime);
        endTurnButton.update(elapsedTime);

        villainDeck.update(elapsedTime);
        heroDeck.update(elapsedTime);

        for (Card c:heroDeck.getDeck(this)) {
            c.update(elapsedTime);
        }

        for (Card c:villainDeck.getDeck(this)) {
            c.update(elapsedTime);
        }

        if(endTurnButton.isPushTriggered()){
            hero.setCardPlayed(false);
            if(playerTurn){
                playerTurn = false;
            }
        }

        //if information button is pushed then load the instructions screen [Niamh McCartney]
        if (infoButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame, this));

        //if settings button is pushed then load the settings screen [Niamh McCartney]
        if (settingsButton.isPushTriggered())
            mGame.getScreenManager().addScreen(new OptionsScreen(mGame));
        }

    private void pauseUpdate(ElapsedTime elapsedTime) {

        pauseMenu.update(elapsedTime);
        resume.update(elapsedTime);
        exit.update(elapsedTime);

            if(resume.isPushTriggered()){
                paused = false;
            }
            if(exit.isPushTriggered()){
                paused = false;
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            }
    }


    public void setupPause(){

        pauseMenu = new GameObject(240.0f,170.0f ,
                220.0f, 195.0f, mGame.getAssetManager().getBitmap("pauseBack"), this);

        resume = new PushButton(240.0f, 170.0f,
                100.0f, 50.0f, "resumeBtn", "resume2Btn", this);

        exit = new PushButton(240.0f, 115.0f,
                100.0f, 50.0f, "menuBtn", "menu2Btn", this);
    }

    public void drawPause(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        pauseMenu.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        resume.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        exit.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        graphics2D.drawText("Paused", mGame.getScreenWidth()/3, mGame.getScreenHeight()/3, paint);

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        board.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        //Add Player Decks to Screen [Niamh McCartney]
        AddPlayerDecks(elapsedTime, graphics2D, "HeroCardBackground", heroDeck);
        AddPlayerDecks(elapsedTime, graphics2D, "VillainCardBackground", villainDeck);

        if(paused){
            drawPause(elapsedTime, graphics2D);
            pauseUpdate(elapsedTime);
        }else{
            endTurnButton.draw(elapsedTime,graphics2D,LayerViewport, ScreenViewport);
            infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
            settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        }

        // display players [Irene Bhuiyan]
        displayPlayers(elapsedTime, graphics2D);

        //call method if first turn has not been decided yet
        if(!firstTurnDecided){
            randomiseFirstTurn();
        }

    }

    /**
     * Creates a Pop-Up Dialog box which allows
     * the player to flip a coin to determine
     * who takes the first turn. The outcome
     * is then displayed to the player
     *
     *  {Created By Niamh McCartney}
     */
    public void randomiseFirstTurn(){
        //Add players to an arrayList
        ArrayList<Player> players = new ArrayList(2);
        players.add(hero);
        players.add(villain);

        // get a random number
        Random rand = new Random();
        int max = players.size()-1;
        int min = 0;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        //iterate through the arraylist
        for(int i = min; i< players.size(); i++){
            //if the players position in the arraylist is equal to the random number they will go first
            if(i == randomNum){
                firstPlayer = players.get(i);
            }//if the players position in the arraylist is not equal to the random number they will go second
            else{
                secondPlayer = players.get(i);
            }
        }

        //get the name of the player who will go first
        String firstPlayerName = firstPlayer.getPlayerName();
        if(firstPlayer == hero){
            playerTurn = true;
        }else{playerTurn = false;}

        // create a pop-up dialog box, passing in the players name so the outcome can be displayed to the user
        CoinFlipDialog dialog = new CoinFlipDialog();
        dialog.showDialog(getGame().getActivity(), firstPlayerName);

        firstTurnDecided = true;
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
        hero.setPosition(442.0f, 73.0f);
        hero.Draw(elapsedTime, graphics2D,getDefaultLayerViewport(),getDefaultScreenViewport(), this);
//        Bitmap heroAvatar = mGame.getAssetManager().getBitmap("freta");
//        heroAvatarImg = new GameObject(430.0f, 50.0f, 100.0f, 100.0f, heroAvatar, this);
//        heroAvatarImg.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        //display villain
        villain.setPosition(36.0f, 271.0f);
        villain.Draw(elapsedTime, graphics2D,getDefaultLayerViewport(),getDefaultScreenViewport(), this);
//        Bitmap villainAvatar = mGame.getAssetManager().getBitmap("ronald");
//        villainAvatarImg = new GameObject(50.0f, 270.0f, 100.0f, 100.0f, villainAvatar, this);
//        villainAvatarImg.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

        //for testing purposes
//        if(!healthChanged){
//            for (int i = 0; i < heroDeck.getDeck(this).size(); i++) {
//                Card card = heroDeck.getDeck(this).get(i);
//                card.setHealthValue(40);
//            }
//
//            for (int i = 0; i < villainDeck.getDeck(this).size(); i++) {
//                Card card = villainDeck.getDeck(this).get(i);
//                card.setHealthValue(10);
//            }
//            healthChanged = true;
//        }

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
