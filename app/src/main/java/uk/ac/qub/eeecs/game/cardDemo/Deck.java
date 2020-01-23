package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> cardDeck;

    public Deck(int size, ArrayList<Card> cards){
        cardDeck = new ArrayList<>(size);
        cardDeck.addAll(cards);


    }


    public void clearDeck(){
        cardDeck.clear();
    }
}
