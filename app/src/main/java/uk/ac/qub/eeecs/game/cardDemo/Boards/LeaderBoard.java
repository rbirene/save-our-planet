package uk.ac.qub.eeecs.game.cardDemo.Boards;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.cardDemo.User.User;

/**
 * Defines LeaderBoard object
 * to display information on
 * the Game's Top 5 Users from
 * best to worst
 *
 * Created By Niamh McCartney
 */
public class LeaderBoard extends Sprite {
    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Defines the AssetManager used by the Game
    private AssetManager assetManager;

    //Defines the game screen that called the object
    private GameScreen aScreen;

    //Define the LeaderBoard image
    private Bitmap leaderBoardImage;

    //Paint used to draw text on the LeaderBoard
    private Paint mTextPaint;

    //Define the list of all the saved Users
    private ArrayList<User> aUserList;

    //Define the Y co-ordinate fo the text on the LeaderBoard
    private float textYCoordinate;

    //Define the width of the text
    private float textWidth;

    //Number of Users to be displayed on the leaderBoard
    private int numOfUsers;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Create the LeaderBoard object
     *
     * @param xPos x co-ordinate of LeaderBoard
     * @param yPos y co-ordinate of LeaderBoard
     * @param screen screen the object is in
     * @param width width of the LeaderBoard
     * @param height height of the LeaderBoard
     * @param userList list of users to be sorted
     *                and displayed by the LeaderBoard
     *
     * Created by Niamh McCartney
     */
    public LeaderBoard(float xPos, float yPos, GameScreen screen, float width,
                       float height, ArrayList<User> userList){
        super(xPos, yPos, width, height, null, screen);

        //Define the parameters
        this.aScreen = screen;
        this.aUserList = userList;

        //Initialise the LeaderBoard properties
        this.textWidth = getHeight() * 0.7f;

        //Load the images used by the LeaderBoard object
        loadLeaderBoardImages();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Draw the LeaderBoard Object
     *
     * @param elapsedTime    Elapsed time information
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {
        //Draw the Board
        mBitmap = leaderBoardImage;
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        //Set paint properties for card text
        setupTextPaint();

        //Get Users with the Top 5 WinRateRatios
        sortUserList();

        //Define Y co-ordinate of the text in relation
        //to the size of the LeaderBoard image
        textYCoordinate = getHeight() * -0.085f;

        //Draw Users information of the LeaderBoard
        drawUsers(graphics2D, layerViewport, screenViewport);
    }

    /**
     * Method to draw out a specified bitmap using a specific offset (relative to the
     * position of this game object) and scaling (relative to the size of this game
     * object).
     *
     * @param text text to be drawn on LeaderBoard
     * @param offset Offset vector
     * @param textWidth distance between characters
     * @param graphics2D Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Code taken from 'Card.java' in the
     * 'CardHandAnimationLectureCode.zip' shown
     * during Week 14 lecture - NO MODIFICATIONS MADE
     *
     */
    private void drawText(String text, Vector2 offset, float textWidth,
                          IGraphics2D graphics2D,
                          LayerViewport layerViewport, ScreenViewport screenViewport) {

        Vector2 textPosition = new Vector2();
        ViewportHelper.convertLayerPosIntoScreen(
                layerViewport, this.position, screenViewport, textPosition);

        //x and y Co-ordinates for text
        textPosition.x += ViewportHelper.convertXDistanceFromLayerToScreen(offset.x,
                layerViewport, screenViewport);
        textPosition.y += ViewportHelper.convertYDistanceFromLayerToScreen(offset.y,
                layerViewport, screenViewport);


        float targetSize
                = ViewportHelper.convertXDistanceFromLayerToScreen(
                textWidth, layerViewport, screenViewport);
        float actualSize = mTextPaint.measureText(text);
        while( actualSize > targetSize) {
            mTextPaint.setTextSize(mTextPaint.getTextSize() * 0.9f);
            actualSize = mTextPaint.measureText(text);
        }

        graphics2D.drawText(text, textPosition.x, textPosition.y, mTextPaint);
    }


    /**
     * Draw the Information for Top 5
     * Users on the board
     *
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    private void drawUsers(IGraphics2D graphics2D, LayerViewport layerViewport,
                           ScreenViewport screenViewport){
        //Iterates through the top 5 users
        for(int i = 0; i<numOfUsers; i++){

            //Define the User information to be displayed on the LeaderBoard
            User user = aUserList.get(i);
            String wins = Integer.toString(aUserList.get(i).getWins());
            String losses = Integer.toString(aUserList.get(i).getLosses());
            String userName = user.getName();

            //Draw the User's name
            float playerNameXCoordinate = getWidth() * -0.165f;
            drawUserInformation(userName, playerNameXCoordinate, graphics2D,
                    layerViewport, screenViewport);

            //Draw the number of games the User has won
            float winValueXCoordinate = playerNameXCoordinate * -1.035f;
            drawUserInformation(wins, winValueXCoordinate, graphics2D,
                    layerViewport, screenViewport);


            //Draw the number of games the User has lost
            float lossValueXCoordinate = winValueXCoordinate * 1.705f;
            drawUserInformation(losses, lossValueXCoordinate, graphics2D,
                    layerViewport, screenViewport);

            textYCoordinate += getHeight() * 0.12f;
        }
    }

    /**
     * Draw User Information
     *
     * @param information    information to be displayed
     * @param xCoordinate    x co-ordinate of information
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Created by Niamh McCartney
     */
    private void drawUserInformation(String information, float xCoordinate, IGraphics2D graphics2D,
                                     LayerViewport layerViewport, ScreenViewport screenViewport){

        Vector2 informationOffset = new Vector2(xCoordinate, textYCoordinate);
        drawText(information, informationOffset, textWidth,
                graphics2D, layerViewport, screenViewport);
    }

    /**
     * Sets paint properties for LeaderBoard text
     *
     * Code taken from 'Card.java' in the
     * 'CardHandAnimationLectureCode.zip' shown
     * during Week 14 lecture - PARAMETERS CHANGED
     */
    private void setupTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(getWidth() * 0.15f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Load LeaderBoard images
     *
     * Created by Niamh McCartney
     */
    private void loadLeaderBoardImages(){
        assetManager = aScreen.getGame().getAssetManager();
        assetManager.loadAssets("txt/assets/LeaderboardScreenAssets.JSON");
        leaderBoardImage = assetManager.getBitmap("LeaderBoardImage");
    }

    /**
     * Fills the 'sortedUserList' with
     * the Top 5 Users from the 'aUserList'
     * with the highest winRateRatios
     *
     * Created by Niamh McCartney
     */
    private void sortUserList(){
        Collections.sort(aUserList, Collections.<User>reverseOrder());

        if(aUserList.size() < 5){
            numOfUsers = aUserList.size();
        }else{ numOfUsers = 5;}
    }
}