package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class GameBoard extends GameObject {

    private CardContainer human;
    private int screenHeight = mGameScreen.getGame().getScreenHeight();
    private int screenWidth = mGameScreen.getGame().getScreenWidth();

    private ArrayList<CardContainer> containers;

    public GameBoard(float x, float y, float width, float height, Bitmap bitmap, GameScreen gameScreen) {
        super(x, y, width, height, bitmap, gameScreen);
        this.containers = new ArrayList<>();

        containers.add(new CardContainer(screenWidth/2, screenHeight * 0.65f, gameScreen));
        containers.add(new CardContainer(screenWidth/2, screenHeight * 0.35f, gameScreen));

        containers.add(new CardContainer(screenWidth * 0.6f, screenHeight * 0.65f, gameScreen));
        containers.add(new CardContainer(screenWidth * 0.6f, screenHeight * 0.35f, gameScreen));


        containers.add(new CardContainer(screenWidth * 0.4f, screenHeight * 0.65f, gameScreen));
        containers.add(new CardContainer(screenWidth * 0.4f, screenHeight * 0.35f, gameScreen));






        // human = new CardContainer(50,50,gameScreen);

    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        super.draw(elapsedTime, graphics2D);
        for (int i = 0; i < containers.size(); i++) {
            containers.get(i).draw(elapsedTime, graphics2D);
        }
    }
}
