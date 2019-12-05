package uk.ac.qub.eeecs.game.cardDemo;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class DeckContainer extends Sprite {

    public DeckContainer(float x, float y, GameScreen gameScreen) {
        super(x, y, 100.0f, 120.0f,
                gameScreen.getGame().getAssetManager().getBitmap("deckContainer"), gameScreen);

    }



}
