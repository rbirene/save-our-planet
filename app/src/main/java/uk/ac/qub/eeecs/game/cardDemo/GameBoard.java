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

    private int screenHeight = mGameScreen.getGame().getScreenHeight();
    private int screenWidth = mGameScreen.getGame().getScreenWidth();

    private ArrayList<CardContainer> heroContainers;
    private ArrayList<CardContainer> villianContainers;

    public GameBoard(float x, float y, float width, float height, Bitmap bitmap, GameScreen gameScreen) {
        super(x, y, width, height, bitmap, gameScreen);
        this.heroContainers = new ArrayList<>();
        this.villianContainers = new ArrayList<>();

        heroContainers.add(new CardContainer(260.0f, 160.0f, gameScreen));
        heroContainers.add(new CardContainer(200.0f, 160.0f, gameScreen));
        heroContainers.add(new CardContainer(320.0f, 160.0f, gameScreen));

        villianContainers.add(new CardContainer(260.0f, 265.0f, gameScreen));
        villianContainers.add(new CardContainer(200.0f, 265.0f, gameScreen));
        villianContainers.add(new CardContainer(320.0f, 265.0f, gameScreen));



        // human = new CardContainer(50,50,gameScreen);

    }

    public ArrayList<CardContainer> heroContainers() { return heroContainers; }
    public ArrayList<CardContainer> villianContainers() {
        return villianContainers;
    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,LayerViewport LayerViewport,ScreenViewport ScreenViewport) {
        super.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        for (int i = 0; i < heroContainers.size(); i++) {
            heroContainers.get(i).draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        }
        for (int i = 0; i < villianContainers.size(); i++) {
            villianContainers.get(i).draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        }
    }
}
