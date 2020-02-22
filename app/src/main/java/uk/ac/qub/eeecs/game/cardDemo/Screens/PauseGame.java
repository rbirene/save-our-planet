package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Color;

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
import uk.ac.qub.eeecs.game.MenuScreen;

public class PauseGame extends GameScreen {

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private PushButton Resume,Exit;
    private GameObject background;

    public PauseGame(Game game){
        super("pause", game);

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        int screenWidth = mGame.getScreenWidth()/2;
        int screenHeight = mGame.getScreenHeight()/2;
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        Resume = new PushButton(50.0f, 50.0f,
                50.0f, 50.0f, "BackArrow", "BackArrow", this);

        Exit = new PushButton(50.0f, 100.0f,
                50.0f, 50.0f, "BackArrow", "BackArrow", this);
    }
    @Override
    public void update(ElapsedTime elapsedTime){
        Input input = mGame.getInput();
        List<TouchEvent> EventList = input.getTouchEvents();

        Resume.update(elapsedTime);
        Exit.update(elapsedTime);

        if (EventList.size() > 0) {


        }

    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        graphics2D.clear(Color.WHITE);
        // splashScreenBackground.draw(elapsedTime, graphics2D,mLayerViewport,mScreenViewport);
        Resume.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
    }
}



