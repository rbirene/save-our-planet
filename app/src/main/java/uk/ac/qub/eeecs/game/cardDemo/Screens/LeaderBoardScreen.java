package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Boards.LeaderBoard;
import uk.ac.qub.eeecs.game.cardDemo.User.User;

public class LeaderBoardScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the LeaderBoard object to display the list of Users and their scores
    private LeaderBoard board;

    //Define the AssetManager
    private AssetManager assetManager;

    //Define the background of the Screen as a GameObject
    private GameObject screenBackground;

    //Define the list of Users
    private ArrayList<User> userList;

    //Bitmap containing the background image for the screen
    private Bitmap screenBackgroundImage;

    //Define the Buttons
    private PushButton backButton;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public LeaderBoardScreen(Game game) {
        super("LeaderBoard", game);

        //Load assets used by screen
        loadScreenAssets();

        //Initialise the Screen properties
        userList = game.getUserStore().getUserList();

        createBoard();
        //createScreenBackground();

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
        graphics2D.clear(Color.WHITE);
//        screenBackground.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        board.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
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
        //update buttons displayed on the screen
        backButton.update(elapsedTime);

        //if back button is pushed then return to the MenuScreen
        if (backButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);
    }

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    public void loadScreenAssets(){
        assetManager = getGame().getAssetManager();
        assetManager.loadAssets("txt/assets/LeaderboardScreenAssets.JSON");
        //screenBackgroundImage = assetManager.getBitmap("ScreenBackground");
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

        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");

        backButton = new PushButton(20.0f, 40.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);
        backButton.setPlaySounds(true, true);
    }
}