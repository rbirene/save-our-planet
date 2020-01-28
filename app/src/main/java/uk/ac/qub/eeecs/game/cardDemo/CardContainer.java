package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class CardContainer extends GameObject {

    private Card card;
    private boolean taken;

    public CardContainer(float x, float y, GameScreen gameScreen) {
        super(x, y, 50.0f, 70.0f,
                gameScreen.getGame().getAssetManager().getBitmap("deckContainer"), gameScreen);

    this.taken = false;
    this.card = null;
    }


    public void setCard(Card card)
    {
        this.card = card;
        taken = true;
    }
    public void removeCard() {
        card = null;
        taken = false;
    }

}
