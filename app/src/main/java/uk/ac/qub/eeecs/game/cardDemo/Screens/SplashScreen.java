package uk.ac.qub.eeecs.game.cardDemo.Screens;

import java.util.ArrayList;
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
    private ArrayList<Sprite> screenText = new ArrayList<>();

    public SplashScreen(Game game) {
        super("Splash", game);

         gameHeight = mGame.getScreenHeight();
         gameWidth = mGame.getScreenWidth();

        setup();
    }

    public void setup() {

        // Load assets and setup screen dimensions

        assetManager = new AssetManager(mGame);
        assetManager.loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        assetManager.loadAndAddMusic("gameMusic","sound/InPursuitOfSilence.mp3");
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = new LayerViewport(1000.0f, 1000.0f, 1000.0f, 600.0f);

        setupTitle();
        setupBackground();
        setupLogo();
    }

    //Stops text moving after a certain numbers of updates has happened
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
        screenText.add(moveText);
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
        screenText.add(moveLogo);
    }

    public void setTimer(int x)
    {
        timer = x;
    }

    //After a certain number of updates, menu screen will be displayed
    public void checkTimer(int x) {
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

            for (Sprite sprite:screenText) {
                sprite.update(elapsedTime);
            }

            if (EventList.size() > 0) {
                playMusic();
                mGame.getScreenManager().addScreen(new MenuScreen(mGame))   ;
            }

            checkTimer(timer);
            stopSprite(timer);
            timer++;

            if(alpha < alphaLimit) {
                updateAlpha();
            }
        }
        //Update background transparency
    public void updateAlpha(){
        paint.setAlpha(alpha);
        alpha +=5;
    }

    public void playMusic(){ audioManager.playMusic(assetManager.getMusic("gameMusic")); }

    //Draws all objects to screen
        @Override
        public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){
            graphics2D.clear(Color.WHITE);
            splashScreenBackground.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport,paint);

            for (Sprite sprite:screenText) {
             sprite.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            }
        }
    }