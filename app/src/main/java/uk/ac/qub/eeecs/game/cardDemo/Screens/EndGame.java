package uk.ac.qub.eeecs.game.cardDemo.Screens;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.User.User;
import uk.ac.qub.eeecs.game.cardDemo.User.UserStore;


public class EndGame extends GameScreen {

    private PushButton menuButton;

    private GameObject background, result;

    private ScreenViewport ScreenViewport;

    private LayerViewport LayerViewport;

    private int gameHeight, gameWidth;

    private Boolean winner;

    //Define the Game's UserStore[Niamh McCartney]
    private UserStore userStore;

    //Define the Game's Current User[Niamh McCartney]
    private User currentUser;

    //Define player decks used by the Game
    private Deck heroDeck;
    private Deck villainDeck;


    public EndGame(Game game, boolean winner) {

        super("EndGame", game);

        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        this.winner = winner;

        //set screen properties [Niamh McCartney]
        userStore = game.getUserStore();
        currentUser = game.getCurrentUser();
        heroDeck = game.getHero().getPlayerDeck();
        villainDeck = getGame().getVillain().getPlayerDeck();

        //Loads the assets used by the screen [Niamh McCartney]
        loadScreenAssets();

        String resultString;
        if (winner)
            resultString = "win";
        else
            resultString = "lose";


        menuButton = new PushButton(420.0f, 30.0f, 80.0f, 28.0f, "menuBtn", "menuBtn", this);
        background = new GameObject(mDefaultLayerViewport.halfWidth, mDefaultLayerViewport.halfHeight,
                mDefaultLayerViewport.getWidth(), mDefaultLayerViewport.getHeight(), game.getAssetManager().getBitmap("Background"), this);
        result = new GameObject(mDefaultLayerViewport.halfWidth, mDefaultLayerViewport.halfHeight,
                mDefaultLayerViewport.getHeight(), mDefaultLayerViewport.getHeight(), game.getAssetManager().getBitmap(resultString), this);

        //Updates the Game's UserStore[Niamh McCartney]
        updateUserStore();

        //Ensures new player decks are created if User decides to play again [Niamh McCartney]
        heroDeck.setDeckCreated(false);
        villainDeck.setDeckCreated(false);
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        background.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        result.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        menuButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

    }

    @Override
    public void update(ElapsedTime elapsedTime) {
        menuButton.update(elapsedTime);

        if (menuButton.isPushTriggered()){
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }
    }

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    private void loadScreenAssets(){
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAssets("txt/assets/EndGameScreenAssets.JSON");
    }

    /**
     * Updates the UserStore with the current
     * users's wins/losses depending on the
     * outcome of the battle on the BattleScreen
     *
     * Created by Niamh McCartney
     */
    private void updateUserStore(){
        int userPos;
        userPos = userStore.checkUserStore(currentUser.getName());
        if(winner){
            userStore.getUserList().get(userPos).addWin();
            userStore.saveUsers();
        }else{
            userStore.getUserList().get(userPos).addLoss();
            userStore.saveUsers();
        }
    }

}
