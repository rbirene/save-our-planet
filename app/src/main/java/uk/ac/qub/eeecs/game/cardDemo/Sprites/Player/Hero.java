package uk.ac.qub.eeecs.game.cardDemo.Sprites.Player;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoardObjects.CardHolder;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;

/**
 *
 * Created by [Irene Bhuiyan]
 * This class represents a hero in the game (using traits and behaviours from Player).
 *
 */
//Sam Harper
public class Hero extends Player {

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////
    private ArrayList<Card> playerCards = new ArrayList<>();
    private ArrayList<CardHolder> enemyCardHolders = new ArrayList<>();
    private CardHolder tempContainer;
    private Card cardSelected;
    private Card enemyCard;

    /**
     *
     * Create a new Hero.
     *
     */
    public Hero(Bitmap portrait){
        super(0.0f, 0.0f, "Freta Funberg", null, portrait);
    }

    public void moveCards(List<TouchEvent> touchEvents) {
        for (TouchEvent t : touchEvents) {

            Vector2 layerTouch = new Vector2();
            ViewportHelper.convertScreenPosIntoLayer(gameScreen.getDefaultScreenViewport(), t.x, t.y,
                    gameScreen.getDefaultLayerViewport(), layerTouch);

            if (cardSelected != null) {
                cardSelected.setCardDragged(true);

                if (t.type == 2 && cardSelected.getCardDragged()) {
                    cardSelected.setPosition(layerTouch.x, layerTouch.y);
                    cardSelected.setCardDragged(true);
                    cardSelected.setCardInUse(true);
                }

                if (t.type == 1 && cardSelected.cardSelected()) {
                    cardSelected.setSelected(false);

                    if (checkDropLocationContainer()) {
                        tempContainer.AddCardToHolder(cardSelected);
                        cardSelected.setCardInUse(true);
                        getPlayerDeck().setDeckChanged(true);
                        cardSelected = null;
                        cardPlayed = true;
                    } else if (checkDropLocationAttack()) {
                        attackPhase();
                        cardSelected.returnToHolder();
                        cardSelected = null;
                        cardPlayed = true;
                    } else if (cardSelected.cardHasHolder()) {
                        cardSelected.returnToHolder();
                    } else {
                        cardSelected.setPosition(cardSelected.getStartPosX(), cardSelected.getStartPosY());
                    }
                }
            }
        }
    }

    public boolean checkDropLocationAttack(){

        for (int i = 0; i < gameBoard.getVillianContainers().size(); i++) {
            if (gameBoard.getVillianContainers().get(i).getBound().intersects(cardSelected.getBound())
                    && !gameBoard.getVillianContainers().get(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDropLocationContainer() {

            for (int i = 0; i < gameBoard.getHeroContainers().size(); i++) {
                tempContainer = gameBoard.getHeroContainers().get(i);
                if (tempContainer.getBound().intersects(cardSelected.getBound())
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
                if (playerCards.get(i).getBound().contains(layerTouch.x, layerTouch.y)) {
                    cardSelected = playerCards.get(i);
                    cardSelected.setSelected(true);
                }
            }
        }
    }

    public void attackPhase(){
        enemyCardHolders.addAll(gameBoard.getVillianContainers());

        for(int i=0;i<enemyCardHolders.size();i++){
            if(cardSelected.getBound().intersects(enemyCardHolders.get(i).getBound())){
                gameBoard.playAttackAnimation(enemyCardHolders.get(i));
                enemyCard = enemyCardHolders.get(i).returnCardHeld();
                enemyCard.setHealthValue(enemyCard.getHealthValue() - cardSelected.getAttackValue());
            }
        }
    }

    // add more health when bonus question is answered correctly [Irene Bhuiyan]
    public void setHeroBonusHealth(GameScreen aScreen){
        ArrayList<Card> deck  = getPlayerDeck().getDeck(aScreen);
        int bonusHealth = 5;
        int health = 0;
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            //get current health value
            int healthValue = card.getHealthValue();
            //add bonus health
            card.setHealthValue(healthValue+bonusHealth);
            //get new health value
            health += card.getHealthValue();
        }
        playerHealth = health;
        playerHealthLength = String.valueOf(playerHealth).length();
    }

    // take away health when bonus question is answered incorrectly [Irene Bhuiyan]
    public void setHeroPenaltyHealth(GameScreen aScreen){
        ArrayList<Card> deck  = getPlayerDeck().getDeck(aScreen);
        int penaltyHealth = 5;
        int health = 0;
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            //get current health value
            int healthValue = card.getHealthValue();
            //add bonus health
            card.setHealthValue(healthValue-penaltyHealth);
            //get new health value
            health += card.getHealthValue();
        }
        playerHealth = health;
        playerHealthLength = String.valueOf(playerHealth).length();
    }

    public boolean attackPossible() {
        return false;
}

    public void ProcessTouchInput(List<TouchEvent> touchEvents){
            playerCards = playerDeck.getDeck(null);
            selectCard(touchEvents);
            moveCards(touchEvents);
        }

}