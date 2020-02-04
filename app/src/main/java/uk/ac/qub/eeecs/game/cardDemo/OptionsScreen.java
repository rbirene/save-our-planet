package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
import java.util.Timer;


import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;

import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.MenuScreen;

public class OptionsScreen extends GameScreen {

    private GameObject OptionsBackground;
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;

    private PushButton BackButton;
    private PushButton muteToggle;

    private Paint paint;

    private AudioManager audioManager = mGame.getAudioManager();

    private int gameHeight, gameWidth;

    public OptionsScreen(Game game) {
        super("OptionsScreen", game);


        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        paint = new Paint();
        paint.setTextSize(gameWidth * 0.07f);
        paint.setARGB(255, 0, 0, 0);

        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
      //  mGame.getAssetManager().getBitmap("optionsBackground");
        mGame.getAssetManager().loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        mGame.getAssetManager().loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");
        mGame.getAssetManager().loadAndAddBitmap("muteOn", "img/muteOn.png");
        mGame.getAssetManager().loadAndAddBitmap("muteOff", "img/muteOff.png");

        OptionsBackground = new GameObject(1000.0f, 1000.0f, 2000.0f, 2000.0f,
               this.getGame().getAssetManager().getBitmap("optionsBackground"), this);
        BackButton = new PushButton(20.0f, 50.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);

        muteToggle = new PushButton(400.0f, 250.0f, 75.0f, 100.0f,
                "muteOff","muteOff",this );


        if (audioManager.isMusicPlaying()) {
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOff"));
        } else {
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOn"));
        }
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if (touchEvents.size() > 0) {

            BackButton.update(elapsedTime);
            muteToggle.update(elapsedTime);

            if (BackButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            }
            if (muteToggle.isPushTriggered()) {
               muteButton();
            }
        }
    }

public void muteButton(){
    if(audioManager.isMusicPlaying()){
        audioManager.pauseMusic();
        muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOn"));
    }else if(!audioManager.isMusicPlaying()){
        audioManager.resumeMusic();
        muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOff"));
    }
}
        @Override
        public void draw (ElapsedTime elapsedTime, IGraphics2D graphics2D){

            graphics2D.clear(Color.WHITE);
            OptionsBackground.draw(elapsedTime, graphics2D);
            graphics2D.drawText("Volume Control : ", mGame.getScreenWidth() * 0.095f, mGame.getScreenHeight() * 0.28f, paint);

            BackButton.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
            muteToggle.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);

        }

}