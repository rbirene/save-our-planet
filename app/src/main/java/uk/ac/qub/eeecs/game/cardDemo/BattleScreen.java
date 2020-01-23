package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
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

    public BattleScreen(Game game) {
        super("Battle", game);

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

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


    }
}
