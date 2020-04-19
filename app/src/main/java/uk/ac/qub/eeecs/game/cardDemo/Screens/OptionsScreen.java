package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
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
import uk.ac.qub.eeecs.game.cardDemo.Enums.DifficultyLevels;

public class OptionsScreen extends GameScreen {

    //paints for text [Irene Bhuiyan]
    private Paint textPaint;

    private DifficultyLevels diff = DifficultyLevels.EASY;
    private Paint paint;

    //Sam Harper
    private GameObject optionsBackground, optionsTitle, volumeBar;
    private PushButton backButton, menuButton, muteToggle, volumeUp, volumeDown, changeDifficulty;

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private int gameHeight, gameWidth;
    private AssetManager assetManager = mGame.getAssetManager();

    private AudioManager audioManager = mGame.getAudioManager();
    private float volume;

    public OptionsScreen(Game game) {
        super("OptionsScreen", game);

        //define game dimensions and viewports [Irene Bhuiyan]
        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        volume = audioManager.getMusicVolume();

        //body text style setup [Irene Bhuiyan]
        textPaint = new Paint();
        textPaint.setTextSize(mGame.getScreenWidth() * 0.023f);
        textPaint.setARGB(255, 0, 0, 0);

        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        mGame.getAssetManager().loadAndAddBitmap("diffEasy", "img/ScreenImages/OptionsScreen/DiffEasy.png");
        mGame.getAssetManager().loadAndAddBitmap("diffNormal", "img/ScreenImages/OptionsScreen/DiffNormal.png");
        mGame.getAssetManager().loadAndAddBitmap("diffHard", "img/ScreenImages/OptionsScreen/DiffHard.png");

        //set up background [Irene Bhuiyan]
        Bitmap optionsBackgroundImg = assetManager.getBitmap("optionsBackground");
        optionsBackground = new GameObject(240.0f, 160.0f, 490.0f, 325.0f, optionsBackgroundImg , this);

        //set up option title [Irene Bhuiyan]
        Bitmap optionsTitleImg = assetManager.getBitmap("settings");
        optionsTitle = new GameObject(240.0f, 270.0f, 143.2f, 39.0f, optionsTitleImg, this);

        //set up menu button [Irene Bhuiyan]
        menuButton = new PushButton(420.0f, 30.0f, 80.0f, 28.0f, "menuBtn", "menuBtn", this);

        //Sam Harper
        //Creation of on screen objects
        backButton = new PushButton(30.0f, 30.0f, 50.0f, 50.0f, "BackArrow", "BackArrowSelected", this);
        muteToggle = new PushButton(110.0f, 220.0f, 60.0f, 60.0f, "muteOff","muteOff",this );
        volumeDown = new PushButton(100.0f, 140.0f, 60.0f, 60.0f, "volDown","volDown",this );
        volumeUp = new PushButton(375.0f, 140.0f, 60.0f, 60.0f, "volUp","volUp",this );
        volumeBar = new GameObject(235.0f, 140.0f, 200.0f, 38.8f, this.getGame().getAssetManager().getBitmap("soundBar0"), this);
        changeDifficulty = new PushButton(100.0f, 70.0f, 60.0f, 21.1f, "diffEasy","diffNormal",this );

        //Updates volume slider depending on volume level
        volChecker();

        if (audioManager.isMusicPlaying()) {
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOff"));
        } else {
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOn"));
        }
    }
    //Sam Harper
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

    /**
     * Checks the difficulty of the game, if it is equal to a predetermined enum values
     * update the bitmap of the difficulty button
     * Keith Tennyson
     */
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
    //Sam Harper
    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        diffChecker(mGame.mDifficultyLevel,changeDifficulty);

        if (touchEvents.size() > 0) {
            backButton.update(elapsedTime);
            menuButton.update(elapsedTime);
            muteToggle.update(elapsedTime);
            volumeUp.update(elapsedTime);
            volumeBar.update(elapsedTime);
            volumeDown.update(elapsedTime);
            changeDifficulty.update(elapsedTime);

            if (backButton.isPushTriggered()) {
                mGame.getScreenManager().removeScreen(this);
                mGame.getAudioManager().setMusicVolume(volume);
            }
            else if (menuButton.isPushTriggered()){
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
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
    //Updates mute toggle button
    public void muteButton(){
        if(audioManager.isMusicPlaying()){
            audioManager.pauseMusic();
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOn"));
        }else if(!audioManager.isMusicPlaying()){
            audioManager.resumeMusic();
            muteToggle.setBitmap(mGame.getAssetManager().getBitmap("muteOff"));
        }
    }
    //Draws all objects to screen.
    @Override
    public void draw (ElapsedTime elapsedTime, IGraphics2D graphics2D){

        graphics2D.clear(Color.WHITE);
        optionsBackground.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        optionsTitle.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        backButton.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        menuButton.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);

        //mute
        muteToggle.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        graphics2D.drawText("Turn music off/on", 600.0f, 400.0f, textPaint);

        //volume
        volumeBar.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        volumeDown.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        volumeUp.draw(elapsedTime, graphics2D,LayerViewport, ScreenViewport);
        graphics2D.drawText("Adjust volume", 600.0f, 690.0f, textPaint);

        //difficulty
        changeDifficulty.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        graphics2D.drawText("Choose difficulty", 600.0f, 950.0f, textPaint);

    }

    /**
     * If the difficulty button is pushed, increment the value by one
     * If difficulty is at max value (Hard) set to lowest if pushed (Easy)
     * Keith Tennyson.
     */
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