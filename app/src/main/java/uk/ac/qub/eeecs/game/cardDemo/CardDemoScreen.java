package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.DemoGame;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class CardDemoScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////
    private HashMap<String, Card> heroCardPool;
    // Define a card to be displayed
    private Card card;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public CardDemoScreen(Game game) {
        super("CardScreen", game);

        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();

        // Update the card
       // card.angularVelocity = 40.0f;

        //card.update(elapsedTime);
    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);

        drawCards(elapsedTime, graphics2D);
    }

    /**
     * Draw the cards
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     *
     *  Created By Niamh McCartney
     */
    private void drawCards(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        heroCardPool = getGame().getCardStore().getAllHeroCards(this, mDefaultLayerViewport.x, mDefaultLayerViewport.y);
        int counterX =0;

        for (Map.Entry<String, Card> entry : heroCardPool.entrySet()) {
            int w = heroCardPool.size();
            int q = 1;
            float x1 = mDefaultLayerViewport.x / w;
            float y = mDefaultLayerViewport.y / q;
            float spacing = 58;
            float x = spacing + 2 * x1 * counterX++;
            Card value = entry.getValue();
            value.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            value.setPosition(x, y);
        }
    }
}
