package uk.ac.qub.eeecs.game.cardDemo;

import java.util.List;
import java.util.Timer;


import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.MenuScreen;
import android.graphics.Bitmap;
import android.graphics.Color;


public class SplashScreen extends GameScreen {

    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    // Graphics
    private GameObject splashScreenBackground;
    private int timer =0;
    private AssetManager assetManager;
    private int gameHeight, gameWidth;
   // private Bitmap bmTitle;
    private Sprite moveLogo, moveText;

    public SplashScreen(Game game) {
        super("Splash", game);

         gameHeight = mGame.getScreenHeight();
         gameWidth = mGame.getScreenWidth();

        setup();
    }

    public void setup() {

        assetManager = new AssetManager(mGame);
        assetManager.loadAssets("txt/assets/CardDemoScreenAssets.JSON");

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
        Bitmap bmBackground = assetManager.getBitmap("SplashBackground");
         splashScreenBackground = new GameObject(1000.0f, 1000.0f, gameWidth, gameHeight,
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
        if (x == 80) {
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }
    }

        @Override
        public void update(ElapsedTime elapsedTime){
            Input input = mGame.getInput();
            List<TouchEvent> EventList = input.getTouchEvents();

            moveText.update(elapsedTime);
            moveLogo.update(elapsedTime);

            if (EventList.size() > 0) {
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            }
            delay(timer);
            stopSprite(timer);
            timer++;
        }

        @Override
        public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D){
            graphics2D.clear(Color.WHITE);
           // splashScreenBackground.draw(elapsedTime, graphics2D,mLayerViewport,mScreenViewport);
            moveText.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
            moveLogo.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        }
    }



