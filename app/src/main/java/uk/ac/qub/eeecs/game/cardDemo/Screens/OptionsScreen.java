package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;

import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.DifficultyLevels;

public class OptionsScreen extends GameScreen {

    private GameObject OptionsBackground;
    private GameObject volumeBar;

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;

    private PushButton BackButton;
    private PushButton muteToggle;
    private PushButton volumeUp;
    private PushButton volumeDown;

    private PushButton changeDifficulty;
    DifficultyLevels diff = DifficultyLevels.EASY;
    private Paint paint;

    private AudioManager audioManager = mGame.getAudioManager();
    private float volume;
    private int  gameWidth;


    public OptionsScreen(Game game) {
        super("OptionsScreen", game);

        volume = audioManager.getMusicVolume();
        gameWidth= mGame.getScreenWidth();
        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        paint = new Paint();
        paint.setTextSize(gameWidth * 0.07f);
        paint.setARGB(255, 0, 0, 0);

        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        mGame.getAssetManager().loadAndAddBitmap("diffEasy", "img/DiffEasy.png");
        mGame.getAssetManager().loadAndAddBitmap("diffNormal", "img/DiffNormal.png");
        mGame.getAssetManager().loadAndAddBitmap("diffHard", "img/DiffHard.png");

        OptionsBackground = new GameObject(1000.0f, 1000.0f, 2000.0f, 2000.0f,
                this.getGame().getAssetManager().getBitmap("optionsBackground2"), this);

        volumeBar = new GameObject(235.0f, 120.0f,
                300.0f, 120.0f,
                this.getGame().getAssetManager().getBitmap("soundBar0"), this);

        BackButton = new PushButton(20.0f, 50.0f,
                50.0f, 50.0f,
                "BackArrow", "BackArrowSelected", this);

        muteToggle = new PushButton(400.0f, 250.0f, 75.0f, 100.0f,
                "muteOff","muteOff",this );

        volumeUp = new PushButton(375.0f, 125.0f, 75.0f, 100.0f,
                "volUp","volUp",this );

        volumeDown = new PushButton(100.0f, 125.0f, 75.0f, 60.0f,
                "volDown","volDown",this );
        changeDifficulty = new PushButton(420.0f, 50.0f, 75.0f, 60.0f,
                "diffEasy","diffNormal",this );


        volChecker();

        if (audioManager.isMusicPlaying()) {
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOff"));
        } else {
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOn"));
        }
    }

    public void volChecker(){

        if(volume >=0.80f && volume <=1.0f){
            volumeBar.setBitmap(mGame.getAssetManager().getBitmap("soundBar100"));
        }else if(volume >= 0.60f && volume <0.80f){
            volumeBar.setBitmap(mGame.getAssetManager().getBitmap("soundBar75"));
        }else if(volume >=0.40f  && volume < 0.60f){
            volumeBar.setBitmap(mGame.getAssetManager().getBitmap("soundBar50"));
        }else if(volume >=0.20f  && volume < 0.40f){
            volumeBar.setBitmap(mGame.getAssetManager().getBitmap("soundBar25"));
        }else if (volume >=0.0f  && volume < 0.20f) {
            volumeBar.setBitmap(mGame.getAssetManager().getBitmap("soundBar0"));
        }
    }

    public void diffChecker(DifficultyLevels diff ,PushButton changeDifficulty) {
        if(mGame.mDifficultyLevel == DifficultyLevels.EASY) {
            changeDifficulty.setBitmap(mGame.getAssetManager().getBitmap("diffEasy"));
        }else if(mGame.mDifficultyLevel == DifficultyLevels.NORMAL) {
            changeDifficulty.setBitmap(mGame.getAssetManager().getBitmap("diffNormal"));
        }else if(mGame.mDifficultyLevel == DifficultyLevels.HARD) {
            changeDifficulty.setBitmap(mGame.getAssetManager().getBitmap("diffHard"));
        }
    }

    //For testing
    public void updateVolumeBar(ElapsedTime elapsedTime){
        volumeBar.update(elapsedTime);
        volChecker();
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        diffChecker(mGame.mDifficultyLevel,changeDifficulty);

        if (touchEvents.size() > 0) {
            BackButton.update(elapsedTime);
            muteToggle.update(elapsedTime);
            volumeUp.update(elapsedTime);
            volumeBar.update(elapsedTime);
            volumeDown.update(elapsedTime);
            changeDifficulty.update(elapsedTime);

            if (BackButton.isPushTriggered()) {
                mGame.getScreenManager().removeScreen(this);
                mGame.getAudioManager().setMusicVolume(volume);
            }


            DifficultyChange(mGame.mDifficultyLevel,changeDifficulty);

            if (muteToggle.isPushTriggered()) {
                muteButton();
            }
            if(volumeUp.isPushTriggered()){
                volume = volume + 0.25f;
                mGame.getAudioManager().setMusicVolume(volume);
            }
            if(volumeDown.isPushTriggered()){
                volume = volume - 0.25f;
                mGame.getAudioManager().setMusicVolume(volume);
            }
            volChecker();
            diffChecker(mGame.mDifficultyLevel,changeDifficulty);
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
        graphics2D.drawText("Choose Difficulty : ", 300, 1050, paint);
        BackButton.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        muteToggle.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        volumeBar.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        volumeDown.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        volumeUp.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        changeDifficulty.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
    }

    public void DifficultyChange(DifficultyLevels diff, PushButton changeDifficulty) {
        if (changeDifficulty.isPushTriggered()) {
            if (mGame.mDifficultyLevel.equals(DifficultyLevels.EASY)) {
                mGame.mDifficultyLevel = DifficultyLevels.NORMAL;
            }else if (mGame.mDifficultyLevel.equals(DifficultyLevels.NORMAL)) {
                mGame.mDifficultyLevel = DifficultyLevels.HARD;
            }else mGame.mDifficultyLevel = DifficultyLevels.EASY;

            diffChecker(mGame.mDifficultyLevel,changeDifficulty);
        }
    }

    public void setVolume(float vol){
        this.volume = vol;
    }
    public float getVolume(){
        return volume;
    }
    public Bitmap getVolumeBar(){
        return volumeBar.getBitmap();
    }
}