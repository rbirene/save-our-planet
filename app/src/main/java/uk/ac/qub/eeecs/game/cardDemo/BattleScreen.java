package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;

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


public class BattleScreen extends GameScreen {

    private GameBoard board;
    private ArrayList<Card> cards = new ArrayList<>();
    private PushButton pause;
    private PushButton resume;
    private boolean paused = false;
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private GameObject pauseMenu;

    private AssetManager assetManager = mGame.getAssetManager();

    //Define Users Deck of Cards [Niamh McCartney]
    private Deck heroDeck = getGame().getHero().getPlayerDeck();

    public BattleScreen(Game game) {
        super("Battle", game);

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        //Load Assets to Screen
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");

        board = new GameBoard(game.getScreenWidth() / 2, game.getScreenHeight() / 2,
                1700.0f, 1000.0f, game.getAssetManager().getBitmap("tempBack"), this);

        pause = new PushButton(470.0f, 300.0f,
                30.0f, 30.0f, "pauseBtn", "pauseBtn", this);

        pauseGame();
    }
    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

            pause.update(elapsedTime);

            if(pause.isPushTriggered()){
                paused = true;
            }else if(resume.isPushTriggered()){
                paused = false;
            }
        }



    public void pauseGame(){

        pauseMenu = new GameObject(mGame.getScreenWidth()/2,mGame.getScreenHeight()/2 ,
        800.0f, 700.0f, mGame.getAssetManager().getBitmap("optionsBackground"), this);

        resume = new PushButton(mGame.getScreenWidth()/2, mGame.getScreenHeight()/2,
                40.0f, 40.0f, "pauseBtn", "pauseBtn", this);

    }


    public void drawPause(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        pauseMenu.draw(elapsedTime,graphics2D);
        resume.draw(elapsedTime,graphics2D);

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        board.draw(elapsedTime, graphics2D);
        if(!paused){
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        }

        if(paused){
            drawPause(elapsedTime, graphics2D);
        }

        //Add Player Decks to Screen [Niamh McCartney]
        AddPlayerDecks(elapsedTime, graphics2D);


    }

    /**
     * Draw the Cards on the Screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     *  {Created By Niamh McCartney}
     */
    private void AddPlayerDecks(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        int counterX = 0;

        for(int i = 0; i<heroDeck.getDeck(this).size(); i++){
            Card card = heroDeck.getDeck(this).get(i);
            //Card X co-ordinate
            float x = graphics2D.getSurfaceHeight() * 0.3f + counterX;
            //Card Y co-ordinate
            float y = graphics2D.getSurfaceHeight() * 0.04f;
            //Set Card Background
            card.setCardBase(assetManager.getBitmap("CardBackground"));
            //Set Card Width and Height
            card.setWidth(60);
            card.setHeight(120);
            //Draw Card
            card.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            //Set Card position on Screen
            card.setPosition(x, y);
            counterX += 50;
        }
    }

}
