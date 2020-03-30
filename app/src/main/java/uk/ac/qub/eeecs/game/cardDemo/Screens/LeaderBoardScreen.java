package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.Boards.LeaderBoard;
import uk.ac.qub.eeecs.game.cardDemo.User.User;

public class LeaderBoardScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the LeaderBoard object which displays list of top Users and their scores
    private LeaderBoard board;

    //Define the AssetManager used by the screen
    private AssetManager assetManager;

    //Define the background of the Screen as a GameObject
    private GameObject screenBackground;

    //Define the list of stored Users
    private ArrayList<User> userList;

    //Bitmap containing the background image for the screen
    private Bitmap screenBackgroundImage;

    //Define screen buttons
    private PushButton backButton;

    //Define screen viewports
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;

    //Define the screen dimensions
    private int gameHeight, gameWidth;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the LeaderBoard game screen
     *
     * @param game Game to which this screen belongs
     *
     * Created by Niamh McCartney
     */
    public LeaderBoardScreen(Game game) {
        super("LeaderBoard", game);

        //Initialise the Screen properties//

        userList = game.getUserStore().getUserList();
        assetManager = getGame().getAssetManager();

        gameWidth = game.getScreenWidth();
        gameHeight = game.getScreenHeight();

        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //Load assets used by screen
        loadScreenAssets();

        //Create the screen objects
        createBoard();
        createScreenBackground();

        //Add buttons
        addBackButton();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the LeaderBoard screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     * Created by Niamh McCartney
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Draw screen background
        screenBackground.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        //Draw LeaderBoard
        board.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        //Draw screen Buttons
        backButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }

    /**
     * Update the LeaderBoard screen
     *
     * @param elapsedTime Elapsed time information
     *
     * {Created By Niamh McCartney}
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        //Update buttons displayed on the screen
        backButton.update(elapsedTime);

        //If backButton is pushed then return to the MenuScreen
        if (backButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);
    }

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    public void loadScreenAssets(){
        assetManager.loadAssets("txt/assets/LeaderboardScreenAssets.JSON");
        screenBackgroundImage = assetManager.getBitmap("ScreenBackground");
    }

    /**
     * Define the properties of the LeaderBoard
     * and create a new LeaderBoard object
     *
     * Created By Niamh McCartney
     */
    public void createBoard(){
        float xPos = mDefaultLayerViewport.halfWidth;
        float yPos = mDefaultLayerViewport.halfHeight;
        float width = xPos*1.35f;
        float height = yPos*1.65f;

        board = new LeaderBoard(xPos, yPos, this, width, height, userList);
    }

    /**
     * add a background to the screen
     *
     * Created By Niamh McCartney
     */
    public void createScreenBackground(){
        screenBackground = new GameObject(mDefaultLayerViewport.halfWidth, mDefaultLayerViewport.halfHeight,
                mDefaultLayerViewport.getWidth(), mDefaultLayerViewport.getHeight(), screenBackgroundImage , this);
    }

    /**
     * Add a Back Button to the screen
     * that takes you to the previous screen
     *
     * Created By Niamh McCartney
     */
    private void addBackButton() {
        backButton = new PushButton(20.0f, 40.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);
        backButton.setPlaySounds(true, true);
    }
}