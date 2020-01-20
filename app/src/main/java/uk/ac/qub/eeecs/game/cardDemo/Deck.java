package uk.ac.qub.eeecs.game.cardDemo;

import java.util.HashMap;
import java.util.Map;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Deck {

    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Boolean DeckCreated;

    private HashMap<String, Card> cardDeck;

    public Deck(Card Card1, Card Card2, Card Card3){
        Card01 = Card1;
        Card02 = Card2;
        Card03 = Card3;

        cardDeck = new HashMap<>();

        cardDeck.put(Card01.getCardName(), Card01);
        cardDeck.put(Card02.getCardName(), Card02);
        cardDeck.put(Card03.getCardName(), Card03);
        DeckCreated = true;

    }

    public void AddCard(Card newCard, Card cardNum){
        cardDeck.remove(cardNum.getCardName());
        cardNum = newCard;
        cardDeck.put(cardNum.getCardName(), cardNum);

    }

    public HashMap<String, Card> getDeck(GameScreen screen){
        for (Map.Entry<String, Card> entry : cardDeck.entrySet()) {
            Card value = entry.getValue();
            value.setGameScreen(screen);
        }
        return cardDeck;
    }

    public Card getCard01(GameScreen screen){ Card01.setGameScreen(screen); return Card01;}
    public Card getCard02(GameScreen screen){ Card01.setGameScreen(screen); return Card02;}
    public Card getCard03(GameScreen screen){ Card01.setGameScreen(screen); return Card03;}

    public void setCard01(Card newCard){ Card01 = newCard;}
    public void setCard02(Card newCard){ Card02 = newCard;}
    public void setCard03(Card newCard){ Card03 = newCard;}

    public Boolean deckCreated(){ return DeckCreated;}


}
