package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

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

    private Paint mTextPaint;

    private BoundingBox bound;

    //Define the list of all the saved Users
    private ArrayList<User> aUserList;

    //Define the list of the top 5 Users with the best WinRateRatio
    private ArrayList<User> sortedUserList;

    //Define the Y co-ordinate fo the text on the LeaderBoard
    private float textYCoordinate;

    //Define the width of the text
    private float textWidth;

    //Number of Users to be displayed on the leaderBoard
    private int numOfUsers;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public LeaderBoard(float xPos, float yPos, GameScreen screen, float width, float height, ArrayList<User> userList){
        super(xPos, yPos, width, height, null, screen);

        //Define the parameters
        this.aScreen = screen;
        this.aUserList = userList;

        //Initialise the UserStore properties
        this.sortedUserList = new ArrayList<>();
        this.textWidth = getHeight() * 0.7f;
        this.bound = new BoundingBox();

        //Loads the images used by the LeaderBoard object
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
        //Draws the leaderBoard image
        mBitmap = leaderBoardImage;
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        //Sets paint properties for card text
        setupTextPaint();

        //Gets the Users with the Top 5 WinRateRatios
        sortArrayList();

        //Defines the Y co-ordinate of the text in relation
        // to the size of the LeaderBoard image
        textYCoordinate = getHeight() * -0.085f;

        //Iterates through the top 5 users
        for(int i = 0; i<numOfUsers; i++){
            //Define the User information to be displayed on the LeaderBoard
            User user = aUserList.get(i);
            String win = Integer.toString(aUserList.get(i).getWins());
            String loss = Integer.toString(aUserList.get(i).getLosses());
            String userName = user.getName();

            //Draw the Users name[Niamh McCartney]
            float playerNameXCoordinate = getWidth() * -0.165f;
            Vector2 playerNameOffset = new Vector2(playerNameXCoordinate, textYCoordinate);
            drawText(userName, playerNameOffset, textWidth,
                        graphics2D, layerViewport, screenViewport);


            //Draw the number of games the User has won[Niamh McCartney]
            float winValueXCoordinate = playerNameXCoordinate * -1.035f;
            Vector2 winOffset = new Vector2(winValueXCoordinate, textYCoordinate);
            drawText(win, winOffset, textWidth,
                    graphics2D, layerViewport, screenViewport);


            //Draw the number of games the User has lost[Niamh McCartney]
            float lossValueXCoordinate = winValueXCoordinate * 1.705f;
            Vector2 lossOffset = new Vector2(lossValueXCoordinate, textYCoordinate);
            drawText(loss, lossOffset, textWidth,
                    graphics2D, layerViewport, screenViewport);

            textYCoordinate += getHeight() * 0.12f;
        }
    }

    /**
     * Method to draw out a specified bitmap using a specific offset (relative to the
     * position of this game object) and scaling (relative to the size of this game
     * object).
     *
     * @param bitmap Bitmap to draw
     * @param offset Offset vector
     * @param scale Scaling vector
     * @param graphics2D     Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     *  Taken from Card Class in CardDemo Gage Code
     */
    protected void drawBitmap(Bitmap bitmap, Vector2 offset, Vector2 scale,
                              IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Calculate the center position of the rotated offset point.
        double rotation = Math.toRadians(-this.orientation);
        float diffX = mBound.halfWidth * offset.x;
        float diffY = mBound.halfHeight * offset.y;
        float rotatedX = (float)(Math.cos(rotation) * diffX - Math.sin(rotation) * diffY + position.x);
        float rotatedY = (float)(Math.sin(rotation) * diffX + Math.cos(rotation) * diffY + position.y);

        // Calculate a game layer bound for the bitmap to be drawn
        bound.set(rotatedX, rotatedY,
                mBound.halfWidth * scale.x, mBound.halfHeight * scale.y);

        // Draw out the specified bitmap using the calculated bound.
        // The following code is based on the Sprite's draw method.
        if (GraphicsHelper.getSourceAndScreenRect(
                bound, bitmap, layerViewport, screenViewport, drawSourceRect, drawScreenRect)) {

            // Build an appropriate transformation matrix
            drawMatrix.reset();

            float scaleX = (float) drawScreenRect.width() / (float) drawSourceRect.width();
            float scaleY = (float) drawScreenRect.height() / (float) drawSourceRect.height();
            drawMatrix.postScale(scaleX, scaleY);

            drawMatrix.postRotate(orientation, scaleX * bitmap.getWidth()
                    / 2.0f, scaleY * bitmap.getHeight() / 2.0f);

            drawMatrix.postTranslate(drawScreenRect.left, drawScreenRect.top);

            // Draw the bitmap
            graphics2D.drawBitmap(bitmap, drawMatrix, null);
        }
    }

    /**
     * Method to draw out a specified bitmap using a specific offset (relative to the
     * position of this game object) and scaling (relative to the size of this game
     * object).
     *
     * @param text line of text to be drawn on Card
     * @param offset Offset vector
     * @param textWidth distance between characters
     * @param graphics2D Graphics instance
     * @param layerViewport  Game layer viewport
     * @param screenViewport Screen viewport
     *
     * Code taken from lecture code
     *
     */
    private void drawText(String text, Vector2 offset, float textWidth,
                          IGraphics2D graphics2D,
                          LayerViewport layerViewport, ScreenViewport screenViewport) {

        Vector2 textPosition = new Vector2();
        ViewportHelper.convertLayerPosIntoScreen(
                layerViewport, this.position, screenViewport, textPosition);

        //x and y Co-ordinates for text
        textPosition.x += ViewportHelper.convertXDistanceFromLayerToScreen(offset.x, layerViewport, screenViewport);
        textPosition.y += ViewportHelper.convertYDistanceFromLayerToScreen(offset.y, layerViewport, screenViewport);


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
     * Sets paint properties for card text
     *
     * Created by Niamh McCartney
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
    private void sortArrayList(){
        Collections.sort(aUserList, Collections.<User>reverseOrder());

        if(aUserList.size() < 5){
            numOfUsers = aUserList.size();
        }else{ numOfUsers = 5;}

    }
}
