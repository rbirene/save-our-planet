package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

public class GameBoard extends GameObject {


   // private int screenHeight = getGameScreen().getGame().getScreenHeight();
    //private int screenWidth = getGameScreen().getGame().getScreenWidth();

    private GameScreen BattleScreen;
    private ArrayList<CardHolder> heroContainers;
    private ArrayList<CardHolder> villianContainers;


    public GameBoard(float x, float y ,float width, float height,Bitmap bitmap, GameScreen gameScreen) {
      super(x, y,width,height, bitmap, gameScreen);


        this.heroContainers = new ArrayList<>();
        this.villianContainers = new ArrayList<>();

        heroContainers.add(new CardHolder(140.0f, 130.0f, gameScreen));
        heroContainers.add(new CardHolder(240.0f, 130.0f,gameScreen));
        heroContainers.add(new CardHolder(340.0f, 130.0f, gameScreen));

        villianContainers.add(new CardHolder(140.0f, 210.0f, gameScreen));
        villianContainers.add(new CardHolder(240.0f, 210.0f,gameScreen));
        villianContainers.add(new CardHolder(340.0f, 210.0f, gameScreen));



    }

    public ArrayList<CardHolder> getHeroContainers(){
        return heroContainers;
    }


    public ArrayList<CardHolder> getVillianContainers(){
        return villianContainers;
    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        for (int i = 0; i < heroContainers.size(); i++) {
            heroContainers.get(i).draw(elapsedTime, graphics2D,layerViewport,screenViewport);
        }
        for (int i = 0; i < villianContainers.size(); i++) {
            villianContainers.get(i).draw(elapsedTime, graphics2D,layerViewport,screenViewport);
        }
    }

}
