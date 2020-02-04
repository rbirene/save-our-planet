package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Deck {

    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Boolean deckCreated;
    private Boolean deckShuffled = false;
    private ArrayList<Card> cardDeck;

    public Deck(Card Card1, Card Card2, Card Card3){
        Card01 = Card1;
        Card02 = Card2;
        Card03 = Card3;

        cardDeck = new ArrayList<Card>();

        cardDeck.add(0, Card01);
        cardDeck.add(1, Card02);
        cardDeck.add(2, Card03);
        deckCreated = true;

    }

//    public void AddCard(Card newCard, Card cardNum){
//        cardDeck.remove(cardNum.getCardName());
//        cardNum = newCard;
//        cardDeck.put(cardNum.getCardName(), cardNum);
//
//    }

    public int checkDeck(Card card){
        for(int i = 0; i<cardDeck.size(); i++){
            if(card == cardDeck.get(i)){
                return i;
            }
        }
        return -1;
    }

    public void enlargeDeck(float width, float height){
        for(int i = 0; i<cardDeck.size(); i++){
            cardDeck.get(i).setWidth(width);
            cardDeck.get(i).setHeight(height);
        }
    }

    public int getSize(){
       return  cardDeck.size();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////
    /**
     * returns True if Deck has already
     * been shuffled
     *
     *  {Created By Niamh McCartney}
     */
    public Boolean getDeckShuffled(){
        return deckShuffled;
    }
    /**
     * Returns cardDeck
     *
     * @param screen screen trying to access Deck
     *
     *  {Created By Niamh McCartney}
     */
    public  ArrayList<Card> getDeck(GameScreen screen){
        for (int i = 0; i< cardDeck.size(); i++) {
           Card card = cardDeck.get(i);
           card.setGameScreen(screen);
        }
        return cardDeck;
    }

    public void setDeck(GameScreen screen, ArrayList<Card> newCardDeck){
        cardDeck = newCardDeck;

        for (int i = 0; i< cardDeck.size(); i++) {
            Card card = cardDeck.get(i);
            card.setGameScreen(screen);
        }
    }

    public Card getCard01(GameScreen screen){ Card01.setGameScreen(screen); return Card01;}
    public Card getCard02(GameScreen screen){ Card01.setGameScreen(screen); return Card02;}
    public Card getCard03(GameScreen screen){ Card01.setGameScreen(screen); return Card03;}


    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Sets CardDeck equal to a new ArrayList of Cards
     *
     * @param newCardDeck ArrayList containing the Deck's new Cards
     *
     *  {Created By Niamh McCartney}
     */
    public void setDeck(ArrayList<Card> newCardDeck){
        cardDeck = newCardDeck;
        Card01 = cardDeck.get(0);
        Card02 = cardDeck.get(1);
        Card03 = cardDeck.get(2);
    }

    public void setDeckShuffled(Boolean bool){
        deckShuffled = bool;
    }

    public void setCard01(Card newCard){ Card01 = newCard; }
    public void setCard02(Card newCard){ Card02 = newCard;}
    public void setCard03(Card newCard){ Card03 = newCard;}

}
