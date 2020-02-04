package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class CardContainer extends GameObject {

    private ArrayList<Card> cards;


    public CardContainer(float x, float y, GameScreen gameScreen) {
        super(x, y, 170.0f, 220.0f,
                gameScreen.getGame().getAssetManager().getBitmap("deckContainer"), gameScreen);





    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        super.draw(elapsedTime, graphics2D);
    }
}
