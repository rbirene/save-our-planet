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


    private CardContainer human;
    private int screenHeight = mGameScreen.getGame().getScreenHeight();
    private int screenWidth = mGameScreen.getGame().getScreenWidth();

    private ArrayList<CardContainer> containers;

    public GameBoard(float x, float y, float width, float height, Bitmap bitmap, GameScreen gameScreen) {
        super(x, y, width, height, bitmap, gameScreen);
        this.containers = new ArrayList<>();

        containers.add(new CardContainer(260.0f, 160.0f, gameScreen));
        containers.add(new CardContainer(200.0f, 160.0f, gameScreen));
        containers.add(new CardContainer(320.0f, 160.0f, gameScreen));







        // human = new CardContainer(50,50,gameScreen);

    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,LayerViewport LayerViewport,ScreenViewport ScreenViewport) {
        super.draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        for (int i = 0; i < containers.size(); i++) {
            containers.get(i).draw(elapsedTime, graphics2D,LayerViewport,ScreenViewport);
        }
    }
}
