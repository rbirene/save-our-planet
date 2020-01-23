package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class CardContainer extends GameObject {

    private ArrayList<Card> cards;
    private boolean taken;

    public CardContainer(float x, float y, GameScreen gameScreen) {
        super(x, y, 50.0f, 70.0f,
                gameScreen.getGame().getAssetManager().getBitmap("deckContainer"), gameScreen);

    this.taken = false;

    }






    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        super.draw(elapsedTime, graphics2D,
                mGameScreen.getDefaultLayerViewport(),mGameScreen.getDefaultScreenViewport());
    }
}
