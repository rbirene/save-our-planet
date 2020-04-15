package uk.ac.qub.eeecs.game.cardDemo.Screens;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
//Sam Harper
public class SplashScreen extends GameScreen {

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private GameObject splashScreenBackground;
    private int timer =0;
    private AssetManager assetManager;
    private AudioManager audioManager = mGame.getAudioManager();
    private int gameHeight, gameWidth, alpha;
    private int  alphaLimit = 255;
    private Sprite moveLogo, moveText;
    private Paint paint = new Paint();

    public SplashScreen(Game game) {
        super("Splash", game);

         gameHeight = mGame.getScreenHeight();
         gameWidth = mGame.getScreenWidth();

        setup();
    }

    public void setup() {

        assetManager = new AssetManager(mGame);
        assetManager.loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        assetManager.loadAndAddMusic("gameMusic","sound/InPursuitOfSilence.mp3");
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = new LayerViewport(1000.0f, 1000.0f, 1000.0f, 600.0f);

        setupTitle();
        setupBackground();
        setupLogo();
    }

    public void stopSprite(int x){
        if(x == 40){
            moveText.velocity = new Vector2(0.f, 0.f);
            moveLogo.velocity = new Vector2(0.f, 0.f);
        }
    }

    public void setupTitle(){
        Bitmap bmTitle = assetManager.getBitmap("SplashTitle");
        moveText = new Sprite(1000.0f, gameHeight*1.4f, 1000.0f, 400.0f, bmTitle, this);
        moveText.velocity = new Vector2(0.f, -125.f);
    }

    public void setupBackground(){
        Bitmap bmBackground = assetManager.getBitmap("splashScreenBackground");
         splashScreenBackground = new GameObject(1000.0f, 1000.0f, gameWidth*1.2f, gameHeight,
                 bmBackground , this);
    }

    public void setupLogo(){
        Bitmap bmLogo = assetManager.getBitmap("TreeHuggersLogo");
        moveLogo = new Sprite(1000.0f, 470.0f, 1000.0f, 775.0f, bmLogo, this);
        moveLogo.velocity = new Vector2(0.f, 170.f);
    }

    public void setTimer(int x)
    {
        timer = x;
    }

    public void delay(int x) {
        if (x == 100) {
            playMusic();
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }
    }

    public int getTimer(){
        return timer;
    }

        @Override
        public void update(ElapsedTime elapsedTime){
            Input input = mGame.getInput();
            List<TouchEvent> EventList = input.getTouchEvents();

            moveText.update(elapsedTime);
            moveLogo.update(elapsedTime);

            if (EventList.size() > 0) {
                playMusic();
                mGame.getScreenManager().addScreen(new MenuScreen(mGame))   ;
            }
            delay(timer);
            stopSprite(timer);
            timer++;

            if(alpha < alphaLimit) {
                updateAlpha();
            }
        }
    public void updateAlpha(){
        paint.setAlpha(alpha);
        alpha +=5;
    }

    public void playMusic(){ audioManager.playMusic(assetManager.getMusic("gameMusic")); }

        @Override
        public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){
            graphics2D.clear(Color.WHITE);
            splashScreenBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport,paint);
            moveText.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            moveLogo.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        }
    }