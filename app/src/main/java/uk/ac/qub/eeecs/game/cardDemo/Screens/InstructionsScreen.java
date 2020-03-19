package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

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

public class InstructionsScreen extends GameScreen {

    //define back button
    private PushButton backButton, menuButton, playGame;

    //paints for text [Irene Bhuiyan]
    private Paint headingPaint, subheadingPaint, textPaint;

    //images [Irene Bhuiyan]
    private GameObject instructionsBackground, instructionsTitle, instructionsCardDesc, instructionsCardSelect, instructionsVillain, instructionsBonus;

    //previous game screen [Irene Bhuiyan]
    private String prevGameScreen;

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private int gameHeight, gameWidth;
    private AssetManager assetManager = mGame.getAssetManager();

    public InstructionsScreen(Game game, GameScreen previousGameScreen) {
        super("Instructions", game);

        //define game dimensions and viewports [Irene Bhuiyan]
        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        //get previous game screen name [Irene Bhuiyan]
        prevGameScreen = previousGameScreen.getName();

        //load assets
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");

        //set up back button[Niamh McCartney]
        backButton = new PushButton(30.0f, 30.0f, 50.0f, 50.0f, "BackArrow", "BackArrowSelected", this);

        //set up play button [Irene Bhuiyan]
        playGame = new PushButton(240.0f, 90.0f, 145.0f, 40.0f, "btnPlay", "btnPlay",this);

        //set up menu button [Irene Bhuiyan]
        menuButton = new PushButton(420.0f, 30.0f, 80.0f, 28.0f, "menuBtn", "menuBtn", this);

        //set up background [Irene Bhuiyan]
        Bitmap instructionsBackgroundImg = assetManager.getBitmap("optionsBackground");
        instructionsBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f, instructionsBackgroundImg , this);

        //set up instruction images [Irene Bhuiyan]
        Bitmap instructionsTitleImg = assetManager.getBitmap("instructions");
        instructionsTitle = new GameObject(240.0f, 270.0f, 200.0f, 39.0f, instructionsTitleImg, this);
        Bitmap instructionsCardDescImg = assetManager.getBitmap("instructionsCardDesc");
        instructionsCardDesc = new GameObject(240.0f, 100.0f, 350.0f, 128.0f, instructionsCardDescImg, this);
        Bitmap instructionsCardSelectImg = assetManager.getBitmap("instructionsCardSelect");
        instructionsCardSelect = new GameObject(340.0f, 190.0f, 150.0f, 123.0f, instructionsCardSelectImg, this);
        Bitmap instructionsVillainImg = assetManager.getBitmap("instructionsVillain");
        instructionsVillain = new GameObject(100.0f, 140.0f, 70.0f, 86.0f, instructionsVillainImg, this);
        Bitmap instructionsBonusImg = assetManager.getBitmap("instructionsBonus");
        instructionsBonus = new GameObject(340.0f, 70.0f, 130.0f, 56.0f, instructionsBonusImg, this);

        //heading text style setup [Irene Bhuiyan]
        headingPaint = new Paint();
        headingPaint.setTextSize(100.0f);
        headingPaint.setARGB(255, 0, 0, 0);
        headingPaint.setUnderlineText(true);

        //subheading text style setup [Irene Bhuiyan]
        subheadingPaint = new Paint();
        subheadingPaint.setTextSize(50.0f);
        subheadingPaint.setARGB(255, 0, 0, 0);
        subheadingPaint.setUnderlineText(true);

        //body text style setup [Irene Bhuiyan]
        textPaint = new Paint();
        textPaint.setTextSize(mGame.getScreenWidth() * 0.0182f);
        textPaint.setARGB(255, 0, 0, 0);
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            backButton.update(elapsedTime);
            playGame.update(elapsedTime);
            menuButton.update(elapsedTime);

            if (backButton.isPushTriggered()) {
                mGame.getScreenManager().removeScreen(this);
            }
            else if (playGame.isPushTriggered()){
                mGame.getScreenManager().addScreen(new ChooseCardScreen(mGame));
            }
            else if (menuButton.isPushTriggered()){
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            }
        }
    }

    /**
     * Draw the instructions
     *
     * @param elapsedTime    Elapsed time information
     * @param graphics2D     Graphics instance
     *
     * Created by Irene Bhuiyan
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        instructionsBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        backButton.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        menuButton.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        instructionsTitle.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);

        //if user clicks instructions button on main menu...
        if (prevGameScreen.equals("MenuScreen")){
            graphics2D.drawText("Looks like the planet is in danger!", 300.0f, 400.0f, textPaint);
            graphics2D.drawText("You are Freta Funberg, an environmental activist ready to save our world!", 300.0f, 500.0f, textPaint);
            graphics2D.drawText("Can you help defeat Ronald Rump and tackle climate change?", 300.0f, 600.0f, textPaint);
            playGame.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        }
        //else if user clicks instructions button when choosing cards...
        else if (prevGameScreen.equals("CardScreen")){
            graphics2D.drawText("You have 3 cards in your deck. If you're happy with the cards, select 'continue'.", 240.0f, 300.0f, textPaint);
            graphics2D.drawText("If you would like to change any of your cards, select the card(s) you want to change, and", 240.0f, 370.0f, textPaint);
            graphics2D.drawText("select 'shuffle'.", 240.0f, 410.0f, textPaint);

            graphics2D.drawText("About the Card", 750.0f, 550.0f, subheadingPaint);
            instructionsCardDesc.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            //health value text
            graphics2D.drawText("This is how much health", 300.0f, 780.0f, textPaint);
            graphics2D.drawText("your card has. The", 300.0f, 820.0f, textPaint);
            graphics2D.drawText("health from all 3 cards", 300.0f, 860.0f, textPaint);
            graphics2D.drawText("in your deck will be", 300.0f, 900.0f, textPaint);
            graphics2D.drawText("your total health.", 300.0f, 940.0f, textPaint);
            //attack value text
            graphics2D.drawText("This is the attack value", 1250.0f, 770.0f, textPaint);
            graphics2D.drawText("of your card. You can", 1250.0f, 810.0f, textPaint);
            graphics2D.drawText("use your card to attack", 1250.0f, 850.0f, textPaint);
            graphics2D.drawText("your opponent and", 1250.0f, 890.0f, textPaint);
            graphics2D.drawText("damage their health.", 1250.0f, 930.0f, textPaint);
        }
        //else if user clicks instructions button during battle...
        else if (prevGameScreen.equals("Battle")){
            instructionsCardSelect.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            instructionsVillain.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            instructionsBonus.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            //card select text
            graphics2D.drawText("When its your turn, select a card you want", 410.0f, 320.0f, textPaint);
            graphics2D.drawText("to use to attack your opponent.", 410.0f, 360.0f, textPaint);
            graphics2D.drawText("When you have played your chosen card,", 410.0f, 420.0f, textPaint);
            graphics2D.drawText("select 'End Turn' to finish your turn.", 410.0f, 460.0f, textPaint);
            //villain text
            graphics2D.drawText("Your opponent will take damage, depending", 550.0f, 680.0f, textPaint);
            graphics2D.drawText("on the attack value of your card.", 550.0f, 720.0f, textPaint);
            //bonus text
            graphics2D.drawText("Don't forget to answer bonus questions to", 450.0f, 920.0f, textPaint);
            graphics2D.drawText("gain rewards!", 450.0f, 960.0f, textPaint);
        }

    }
}
