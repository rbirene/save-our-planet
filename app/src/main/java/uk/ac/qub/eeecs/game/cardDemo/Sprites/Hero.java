package uk.ac.qub.eeecs.game.cardDemo.Sprites;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.game.cardDemo.CardHolder;

/**
 *
 * Created by [Irene Bhuiyan]
 * This class represents a hero in the game (using traits and behaviours from Player).
 *
 */

public class Hero extends Player {

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////
    private ArrayList<Card> playerCards = new ArrayList<>();
    private CardHolder tempContainer;
    private Card cardSelected;

    /**
     *
     * Create a new Hero.
     *
     */

    public Hero(Bitmap portrait){
        super(0.0f, 0.0f, "Freta Funberg", null, portrait);


    }

    @Override
    public void takeFirstTurn(){}

    @Override
    public void takeTurn(){ }


    public void moveCards(List<TouchEvent> touchEvents) {
        for (TouchEvent t : touchEvents) {

            Vector2 layerTouch = new Vector2();
            ViewportHelper.convertScreenPosIntoLayer(gameScreen.getDefaultScreenViewport(), t.x, t.y,
                    gameScreen.getDefaultLayerViewport(), layerTouch);

            if (cardSelected  != null){
                if (cardSelected.getBound().contains(layerTouch.x, layerTouch.y) && t.type == 0) {
                    cardSelected.setSelected(true);
                }
                if (t.type == 2 && cardSelected.cardSelected()) {
                    cardSelected.setPosition(layerTouch.x, layerTouch.y);
                }
                if (t.type == 1 && cardSelected.cardSelected()) {
                    cardSelected.setSelected(false);
                    if (checkDropLocation()) {
                        // cardSelected.setPosition(tempContainer.getBound().x, tempContainer.getBound().y);
                        tempContainer.AddCardToHolder(cardSelected);
                        tempContainer = null;
                        cardSelected = null;
                    }else{
                        cardSelected.setPosition(cardSelected.getStartPosX(),cardSelected.getStartPosY());
                    }
                }
            }
        }
    }


    public boolean checkDropLocation() {

            for (int i = 0; i < gameBoard.getHeroContainers().size(); i++) {
                tempContainer = gameBoard.getHeroContainers().get(i);
                if (tempContainer.getBound().intersects(cardSelected.getBound())
                        //tempContainer.getBound().contains(cardSelected.getxPos(),cardSelected.getYPos())
                        //cardSelected.getBound().intersects(tempContainer.getBound()) ||
                      // cardSelected.getBound().contains(tempContainer.getX(), tempContainer.getY())
                        && tempContainer.isEmpty()) {
                    return true;
                }
        }
            return false;
    }


    public void selectCard(List<TouchEvent> touchEvents) {
        for (TouchEvent t : touchEvents) {

            Vector2 layerTouch = new Vector2();
            ViewportHelper.convertScreenPosIntoLayer(gameScreen.getDefaultScreenViewport(), t.x, t.y,
                    gameScreen.getDefaultLayerViewport(), layerTouch);

            for (int i = 0; i < playerCards.size(); i++) {
                Card temp = playerCards.get(i);
                if (temp.getBound().contains(layerTouch.x, layerTouch.y)) {
                    cardSelected = temp;
                }
            }
        }
    }


    public void ProcessTouchInput(List<TouchEvent> touchEvents){
        playerCards = playerDeck.getDeck(null);
        selectCard(touchEvents);
        moveCards(touchEvents);


    }


}
