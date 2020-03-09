package uk.ac.qub.eeecs.game.cardDemo;

import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;


public class CardHolder extends GameObject {

    private Card card;
    private boolean empty;

    public CardHolder(float x, float y,GameScreen gameScreen) {
        super(x, y, 40.0f, 60.0f,
                gameScreen.getGame().getAssetManager().getBitmap("deckContainer"), gameScreen);

        this.empty = true;
    }

    public boolean isEmpty(){
        return empty;
    }

     public void AddCardToHolder(Card card){
        this.card = card;
        this.empty = false;
        card.setPosition(this.getBound().x, this.getBound().y);
        card.setCardHolder(this);
     }

     public void removeCard(){
        this.card = null;
        this.empty = true;
     }

     public Card returnCardHeld(){
        return card;
     }
}
