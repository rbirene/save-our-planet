package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.TrueFalseQuestionPopUpDialog;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.gameResultPopUpDialog;
import uk.ac.qub.eeecs.game.cardDemo.Question;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Deck;
import uk.ac.qub.eeecs.game.cardDemo.DialogBoxes.CoinFlipDialog;
import uk.ac.qub.eeecs.game.cardDemo.GameBoard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Villain;
import static uk.ac.qub.eeecs.game.cardDemo.ColourEnum.*;


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
    private AudioManager audioManager = getGame().getAudioManager();

    //set up hero [Irene Bhuiyan]
    private Hero hero = getGame().getHero();

    //set up villain [Niamh McCartney]
    private Villain villain = getGame().getVillain();

    //Define player decks [Niamh McCartney]
    private Deck heroDeck = hero.getPlayerDeck();
    private Deck villainDeck = villain.getPlayerDeck();


    private Player firstPlayer;
    private Player secondPlayer;

    //Define Card width and Height[Niamh McCartney]
    private int cardHeight = 72;
    private int cardWidth = 54;

    //Define spacing between Cards[Niamh McCartney]
    private int spacing = 50;

    private float heroCardXPosScale = 0.13f;

    //Buttons[Niamh McCartney]
    private PushButton infoButton;
    private PushButton settingsButton;
    private PushButton pause;
    private PushButton resume;
    private PushButton exit;
    private PushButton endTurnButton;
    private PushButton bonusButton; // [William Oliver]

    //Define up game height and width
    private int gameHeight;
    private int gameWidth;

    //returns true if the player to take the first turn has been decided[Niamh McCartney]
    private Boolean firstTurnDecided;

    private Boolean gameEnded = false;

    Boolean healthChanged = false;

    private Boolean playerTurn = true;

    private Boolean deckEnlarged = false;

    private Boolean deckSizeChanged = false;

    //Handler to access the UI thread
    private final Handler handler = new Handler(Looper.getMainLooper());

    public BattleScreen(Game game) {
        super("Battle", game);

        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();

        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //Load the assets to be used by the screen[Niamh McCartney]
        loadScreenAssets();

        board = new GameBoard(220.0f, 160.0f, 520.0f, 325.0f, assetManager.getBitmap("battleBackground"), this);
        endTurnButton = new PushButton(335.0f, 300.0f, 83.0f, 30.0f, "endTurn", "endTurn", this);

        paint = new Paint();
        paint.setTextSize(180.0f);
        paint.setARGB(255, 255, 255, 255);
        paint.setUnderlineText(true);


        hero.setGameScreen(this);
        hero.setGameBoard(board);

        villain.setGameBoard(board);
        villain.setGameScreen(this);
        //  villain.setPlayerCards(villainDeck.getDeck(this));

        //set start positions of hero and villain Decks[Niamh McCartney]
        moveCardsToStartPosition(heroDeck.getDeck(this), 0.13f, 0.03f, 50);
        moveCardsToStartPosition(villainDeck.getDeck(this), 0.07f, 0.235f, 45);

        setupPause();

        // [William Oliver]
        addBonusButton();
        setupMusic();
        playBattleMusic();

        //Add Buttons[Niamh McCartney]
        addInfoButton();
        addSettingsButton();
        addPauseButton();
        firstTurnDecided = false;

    }


    public void moveCardsToStartPosition(ArrayList<Card> cards, float xPosScale, float yPosScale, int spacing) {
        //defines the spacing between the cards
        int cardSpacing;

        for (int i = 0; i < cards.size(); i++) {
            cardSpacing = spacing * i;

            //Set the start X and Y co-ordinates for each of the cards
            cards.get(i).setStartPosX(gameWidth * xPosScale + cardSpacing);
            cards.get(i).setStartPosY(gameHeight * yPosScale);

            float xPos = cards.get(i).getStartPosX();
            float yPos = cards.get(i).getStartPosY();

            //set the position of the card on rhe screen
            cards.get(i).setPosition(xPos, yPos);
        }
    }

    public void gameLoop(List<TouchEvent> touchEvents) {
        if (!paused) {
            if (playerTurn) {
                if (!hero.getCardPlayed()) {
                    hero.ProcessTouchInput(touchEvents);
                }
            } else {
                // villain.playAI();
                villain.AICardSelect();
                playerTurn = true;
            }
        }
    }
            /**
             * Returns an ArrayList of Cards that
             * the player is not currently using
             * and aren't contained in card holders
             *
             *  Created By Niamh McCartney
             */
            public ArrayList<Card> getCardsNotInUse () {
                ArrayList<Card> cardsNotInUse = new ArrayList<>();
                for (int i = 0; i < heroDeck.getSize(); i++) {
                    Card card = heroDeck.getDeck(this).get(i);
                    if (!card.getCardInUse()) {
                        cardsNotInUse.add(card);
                    }
                }
                return cardsNotInUse;
            }

            public void gameLoop2 () {
                if (hero.getPlayerHealth(this) < 1) {
                    gameResultPopUpDialog popUp = new gameResultPopUpDialog();
                    String message = "You lost! " + villain.getPlayerName() + " has destroyed the planet!";
                    popUp.showDialog(getGame().getActivity(), message, R.drawable.sad_earth);
                }
                if (villain.getPlayerHealth(this) < 1) {
                    gameResultPopUpDialog popUp = new gameResultPopUpDialog();
                    String message = "You Won! You've saved the planet from destruction!";
                    popUp.showDialog(getGame().getActivity(), message, R.drawable.happy_earth);

                }
            }

            public void checkEndGame () {
                if (hero.getPlayerHealth(this) <= 0) {
                    mGame.getScreenManager().addScreen(new EndGame(mGame, hero));
                } else if (villain.getPlayerHealth(this) <= 0) {
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


        gameLoop(touchEvents);

        checkEndGame();

       hero.update(elapsedTime);
       villain.update(elapsedTime);

        pause.update(elapsedTime);
        infoButton.update(elapsedTime);
        settingsButton.update(elapsedTime);
        board.update(elapsedTime);
        endTurnButton.update(elapsedTime);
		bonusButton.update(elapsedTime); // [William Oliver]

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

            if(pause.isPushTriggered()){
                paused = true;
            }else if(resume.isPushTriggered()){
                paused = false;
            }


        for (int i = 0; i < touchEvents.size(); i++) {

            TouchEvent event = touchEvents.get(i);
            Vector2 layerTouch = new Vector2();
            ViewportHelper.convertScreenPosIntoLayer(this.getDefaultScreenViewport(), event.x, event.y,
                    this.getDefaultLayerViewport(), layerTouch);

            //If a card is touched change the background of the touched card
            for (int pointerIdx = 0; pointerIdx < touchEvents.size(); pointerIdx++) {
                for (int j = 0; j < heroDeck.getDeck(this).size(); j++) {
                    Card card = heroDeck.getDeck(this).get(j);
                    if (card.getBound().contains(layerTouch.x, layerTouch.y) && event.type == 0 && !card.getCardInUse()) {
                        card.setCardInUse(false);
                        if(deckEnlarged) {
                            cardWidth = 54;
                            cardHeight = 72;
                            deckEnlarged = false;
                            spacing = 50;
                            heroCardXPosScale = 0.13f;
                            heroDeck.setDeckChanged(true);
                        }else{
                            cardWidth = 92;
                            cardHeight = 122;
                            deckEnlarged = true;
                            spacing = 90;
                            heroCardXPosScale = 0.09f;
                            heroDeck.setDeckChanged(true);
                        }
                        audioManager.play(getGame().getAssetManager().getSound("CardSelect"));
                    }
                }
            }
        }
    }
	
	// [William Oliver]
	public void setupMusic(){
        assetManager.loadAndAddMusic("battleMusic","sound/BattleTheme.mp3");
    }														

	// [William Oliver]
    public void playBattleMusic(){
        audioManager.playMusic(assetManager.getMusic("battleMusic"));
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
        graphics2D.drawText("PAUSED", mGame.getScreenWidth()/3.1F, mGame.getScreenHeight()/3.0f, paint);

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        board.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        //Add Player Decks to Screen [Niamh McCartney]
        DrawPlayerDecks(elapsedTime, graphics2D, heroDeck, cardWidth, cardHeight, false);
        DrawPlayerDecks(elapsedTime, graphics2D, villainDeck, 54, 72, true);

        // [William Oliver]
		if (bonusButton.isPushTriggered())
            showDialog();

        if(paused){
            drawPause(elapsedTime, graphics2D);
            pauseUpdate(elapsedTime);
        }else{
            endTurnButton.draw(elapsedTime,graphics2D,LayerViewport, ScreenViewport);
            infoButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
            settingsButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
			bonusButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport); // [William Oliver]
        }

        if(heroDeck.getDeckChanged()) {
            //set start positions of hero and villain Decks[Niamh McCartney]
            moveCardsToStartPosition(getCardsNotInUse(), heroCardXPosScale, 0.03f, spacing);
            heroDeck.setDeckChanged(false);
        }

        // display players [Irene Bhuiyan]
        displayPlayers(elapsedTime, graphics2D);

        //call method if first turn has not been decided yet
        if(!firstTurnDecided){
            randomiseFirstTurn();
        }
    }
	
	
//Method to create PopUp Dialog on Battle Screen [William Oliver]
    public void showDialog() {

        Question Q1 = new Question("Q1", "Driving a car everywhere you go is good for the planet.", "false");
        Question Q2 = new Question("Q2", "We need to save as many trees as we can.", "true");

        TrueFalseQuestionPopUpDialog popUp = new TrueFalseQuestionPopUpDialog();

        //index for switch case to call random question
        double randQuestionIndex;
        int min = 1;
        int max = 2;

        randQuestionIndex = Math.random() * (max - min + 1) + min;

            switch ((int) randQuestionIndex) {		 

                case 1:
                    popUp.showDialog(getGame().getActivity(), Q1.getQuestion(), Q1.getAnswer(), GREEN, R.drawable.question_symbol);
                    break;									

                default:
                    popUp.showDialog(getGame().getActivity(), Q2.getQuestion(), Q2.getAnswer(), GREEN, R.drawable.question_symbol);
												 
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
        ArrayList<Player> players = new ArrayList<Player>(2);
        players.add(hero);
        players.add(villain);

        // get a random number
        Random rand = new Random();
        int max = players.size()-1;
        int min = 0;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        //iterate through the array list
        for(int i = min; i< players.size(); i++){
            //if the players position in the array list is equal to the random number they will go first
            if(i == randomNum){
                firstPlayer = players.get(i);
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
    private void DrawPlayerDecks(ElapsedTime elapsedTime, IGraphics2D graphics2D, Deck aDeck, int width, int height, Boolean cardFlipped){

        for(int i = 0; i<aDeck.getDeck(this).size(); i++){
            Card card = aDeck.getDeck(this).get(i);
            //Set card to unselected
            card.setSelected(false);
            if(!card.getCardInUse()) {
                //Set Card Width
                card.setWidth(width);
                //Set Card Height
                card.setHeight(height);
            }else{
                //Set Card Width
                card.setWidth(54);
                //Set Card Height
                card.setHeight(72);
            }
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
	
	//Add Bonus Button to Screen [William Oliver]
    private void addBonusButton() {

        mGame.getAssetManager().loadAndAddBitmap("bonusBtn", "img/bonusBtn.png");

        bonusButton = new PushButton(30.0f, 30.0f, 40.0f, 40.0f, "bonusBtn", "bonusBtn", this);
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

        //display villain
        villain.setPosition(36.0f, 271.0f);
        villain.Draw(elapsedTime, graphics2D,getDefaultLayerViewport(),getDefaultScreenViewport(), this);


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
