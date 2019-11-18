package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.miscDemos.InputDemoScreen;


public class OptionsScreen extends GameScreen {

    private GameObject OptionsBackground;
    private LayerViewport mLayerViewport;
    private ScreenViewport mScreenViewport;
    private PushButton BackButton;
    private ToggleButton Toggle;

    public OptionsScreen(Game game) {
        super("OptionsScreen", game);

        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        Bitmap bm1 = this.getGame().getAssetManager().getBitmap("optionsBackground");
        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");
        mGame.getAssetManager().loadAndAddBitmap("toggleOn", "img/toggleOn.png");
        mGame.getAssetManager().loadAndAddBitmap("toggleOff", "img/toggleOff.png");

        OptionsBackground = new GameObject(1000.0f, 1000.0f, 2000.0f, 1200.0f,
                bm1, this);
        BackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "BackArrow", "BackArrowSelected", this);
        Toggle = new ToggleButton(spacingX*5f, screenHeight * 0.28f, screenWidth * 0.137f, screenHeight * 0.111f,
                "toggleOff", "toggleOff", "toggleOn", "toggleOn", this);

        mScreenViewport = new ScreenViewport(0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        mLayerViewport = new LayerViewport(1000.0f, 1000.0f, 1000.0f, 600.0f);
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            BackButton.update(elapsedTime);
            Toggle.update(elapsedTime);


            if (BackButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));

        }
    }
        @Override
        public void draw (ElapsedTime elapsedTime, IGraphics2D graphics2D){

            graphics2D.clear(Color.WHITE);
            OptionsBackground.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            BackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            Toggle.draw(elapsedTime, graphics2D);
        }
}