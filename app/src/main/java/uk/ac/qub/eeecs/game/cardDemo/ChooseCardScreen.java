package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.engine.input.TouchHandler;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.DemoGame;


/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class ChooseCardScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////
    private HashMap<String, Card> heroCardPool;
    private HashMap<String, Card> screenCardPool = new HashMap<>();
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
    public ChooseCardScreen(Game game) {
        super("CardScreen", game);

        // Load the various images used by the cards
        loadScreenAssets();
        // get all the cards of type hero
        heroCardPool = getGame().getCardStore().getAllHeroCards(this);
        // generate 3 random cards
        generateCards(3);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    private void loadScreenAssets(){
        // Load the various images used by the cards
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();
        ScrollandDragCard();
        BoundingBox cardBoundaries = card.getBound();
/*
boundary snippets reedited from platform and spaceship demo - Keith
 */

        if (cardBoundaries.getBottom() < 0)
            card.position.y -= cardBoundaries.getBottom();
        else if (cardBoundaries.getTop() > mGame.getScreenHeight())
            card.position.y -= (cardBoundaries.getTop() - mGame.getScreenHeight());


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

        int counterX =0;

        for (Map.Entry<String, Card> entry : screenCardPool.entrySet()) {
            int w = screenCardPool.size();
            int q = 1;
            float x1 = mDefaultLayerViewport.x / w;
            float y = mDefaultLayerViewport.y / q;
            float spacing = 70;
            float x = spacing + 2 * x1 * counterX++;
            Card value = entry.getValue();
            value.draw(elapsedTime, graphics2D,
                    mDefaultLayerViewport, mDefaultScreenViewport);
            value.setPosition(x, y);
        }
    }

    /**
     * generates the cards to present to the player
     *
     * @param numOfCards the number of cards to be generated
     *
     *  Created By Niamh McCartney
     */
    private void generateCards(int numOfCards){
        int num = 0;
        while(num<numOfCards) {
            card = getGame().getCardStore().getRandCard(heroCardPool);
            String name = card.getCardName();
            //If Card is not already chosen then add to the HashMap
            if(!screenCardPool.containsKey(name)) {
                screenCardPool.put(name, card);
                num++;
            }
        }
    }

    public void ScrollandDragCard() {
        Input input = mGame.getInput();

        TouchHandler touch = new TouchHandler();

        /**
         * TouchHandler to recognize touch
         */
        /**
         * Add a reset Accumulator to gain latest inputs
         */
        touch.resetAccumulator();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        /**
         * If there is a touch event happening (more than 0) get the touch event.
         */
        if (touchEvents.size() > 0) {
            TouchEvent cardTouch = input.getTouchEvents().get(0);


            /** create a float to get the coordinates and location data before movement
             *
             */
            float cardxpos = card.position.x;
            float cardypos = card.position.y;


            /**
             * If the touch event is drag or scroll and there exists a touch event, get the card position and update it with
             * the movement (dx and dy to show the total movement between the touch event and. The delta values are calculated by minusing the touch event position value from the original position
             * add it to the original position)
             */

            if ( mGame.getInput().existsTouch(0) && cardTouch.type == 2 || cardTouch.type == 6) {


                cardTouch.dx =  cardTouch.x - cardxpos;
                cardTouch.dy = cardTouch.y - cardypos;
                card.position.add(cardTouch.dx,cardTouch.dy);
                cardxpos = card.position.x;
                cardypos = card.position.y;

                touch.resetAccumulator();

            }




        }
    }



}
