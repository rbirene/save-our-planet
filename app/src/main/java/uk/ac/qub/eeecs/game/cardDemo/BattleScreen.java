package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.SteeringBehaviours;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.BattleCard;

public class BattleScreen extends GameScreen {

    private GameBoard board;
    private ArrayList<BattleCard> battleCards = new ArrayList<>();
    private PushButton pause;
    private PushButton resume;
    private PushButton exit;
    private boolean paused = false;
    private ScreenViewport ScreenViewport;
    private LayerViewport LayerViewport;
    private GameObject pauseMenu;
    private Paint paint;
    private HashMap<String, Card> cardPool = new HashMap<>();
    private BattleCard c1, c2;


    // Define number of cards to move
    private int numCardsInHand = 2;

    // Define a card to be displayed
    private ArrayList<Card> cards = new ArrayList<>();

    // Variables used to track card movement
    private ArrayList<Boolean> isTriggered = new ArrayList<>();
    private ArrayList<Double> triggerTime = new ArrayList<>();
    private ArrayList<Vector2> targetLocation = new ArrayList<>();


    public BattleScreen(Game game) {
        super("Battle", game);

        ScreenViewport = mDefaultScreenViewport;
        LayerViewport = mDefaultLayerViewport;

        paint = new Paint();
        paint.setTextSize(game.getScreenWidth() * 0.05f);
        paint.setARGB(255, 170, 200, 0);


        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        board = new GameBoard(240.0f, 160.0f,
                475.0f, 270.0f, game.getAssetManager().getBitmap("tempBack"), this);

        pause = new PushButton(470.0f, 300.0f,
                30.0f, 30.0f, "pauseBtn", "pauseBtn", this);

        setupPause();
        // cardPool = getGame().getCardStore().getAllHeroCards(this);
        // Collection<Card> values = cardPool.values();
        //cards = new ArrayList<>(values);

        createCards();


    }

    public void createCards() {

        c1 = new BattleCard(mDefaultLayerViewport.x, mDefaultLayerViewport.y*0.5f,
                mGame.getAssetManager().getBitmap("testCard"), this);
        c2 = new BattleCard(mDefaultLayerViewport.x*1.5f, mDefaultLayerViewport.y*0.5f,
               mGame.getAssetManager().getBitmap("testCard"), this);

        battleCards.add(0, c1);
       battleCards.add(1, c2);
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if (touchEvents.size() > 0) {
            for (int i = 0; i < battleCards.size(); i++) {
                battleCards.get(i).moveCard(touchEvents);
            }
        }


        for (BattleCard card : battleCards)
            card.update(elapsedTime);


        if (paused) {
            pauseUpdate(elapsedTime);
        } else {
            if (pause.isPushTriggered()) {
                paused = true;
            }
            pause.update(elapsedTime);
        }
    }





    private void pauseUpdate(ElapsedTime elapsedTime) {

        pauseMenu.update(elapsedTime);
        resume.update(elapsedTime);
        exit.update(elapsedTime);


        if(mGame.getInput().getTouchEvents().size() > 0){
            if(resume.isPushTriggered()){
                paused = false;
            }
            if(exit.isPushTriggered()){
                paused = false;
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            }
        }



    }
    public void setupPause(){

        pauseMenu = new GameObject(mGame.getScreenWidth()/2,mGame.getScreenHeight()/2 ,
        800.0f, 700.0f, mGame.getAssetManager().getBitmap("optionsBackground"), this);

        resume = new PushButton(240.0f, 170.0f,
                100.0f, 50.0f, "resumeBtn", "resume2Btn", this);

        exit = new PushButton(240.0f, 115.0f,
                100.0f, 50.0f, "menuBtn", "menu2Btn", this);
    }

    public void drawPause(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        pauseMenu.draw(elapsedTime,graphics2D);
        resume.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        exit.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        graphics2D.drawText("Game Paused", mGame.getScreenWidth()/3, mGame.getScreenHeight()/3, paint);

    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        board.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);

       for (BattleCard b:battleCards) {
           b.draw(elapsedTime, graphics2D);
        }

        if(paused){
            drawPause(elapsedTime, graphics2D);
            pauseUpdate(elapsedTime);
        }else{
            pause.draw(elapsedTime,graphics2D,LayerViewport,ScreenViewport);
        }

    }
}
