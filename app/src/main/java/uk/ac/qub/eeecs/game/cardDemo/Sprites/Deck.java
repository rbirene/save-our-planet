package uk.ac.qub.eeecs.game.cardDemo.Sprites;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;

/**
 * Defines Deck containing 3 Cards
 * Provides methods to access
 *
 * Created By Niamh McCartney
 */

public class Deck {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the cards in the Deck
    private Card Card01;
    private Card Card02;
    private Card Card03;

    //Boolean to determine whether deck has een created
    private Boolean deckCreated;
    //Boolean to determine whether deck has been shuffled
    private Boolean deckShuffled;

    //Defines ArrayList to hold the cards in the deck
    private ArrayList<Card> cardDeck;

    //Boolean that returns true if deck has been changed
    private Boolean deckChanged;


    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public Deck(Card Card1, Card Card2, Card Card3){
        Card01 = Card1;
        Card02 = Card2;
        Card03 = Card3;

        cardDeck = new ArrayList<Card>();

        cardDeck.add(0, Card01);
        cardDeck.add(1, Card02);
        cardDeck.add(2, Card03);

        //set the booleans
        deckShuffled = false;
        deckCreated = true;
        deckChanged = false;

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Check if given Card exists in Deck
     * Returns position of Card in Deck if
     * Deck contains card.
     * Returns -1 if Deck does not contain Card
     *
     * @param card card to be checked
     *
     *  {Created By Niamh McCartney}
     */
    public int checkDeck(Card card){
        for(int i = 0; i<cardDeck.size(); i++){
            if(card == cardDeck.get(i)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Change the width and height of
     * the Cards in the deck
     *
     * @param width New width of Cards in Deck
     * @param height New height of Cards in deck
     *
     *  {Created By Niamh McCartney}
     */
    public void changeDeckSize(float width, float height){
        for(int i = 0; i<cardDeck.size(); i++){
            cardDeck.get(i).setWidth(width);
            cardDeck.get(i).setHeight(height);
        }
    }

    /**
     * Checks that the Card 'card'
     * exists in the deck. If it does
     * the Card is removed from the deck
     *
     * @param card Card to be removed
     *
     *  {Created By Niamh McCartney}
     */
    public void removeCard(Card card){
        int cardPos = checkDeck(card);
        if(cardPos != -1){
            cardDeck.remove(card);
        }
    }

    /**
     * returns the size of the deck
     * Created by [Niamh McCartney]
     */
    public int getSize(){
       return  cardDeck.size();
    }

    //Sam
    public void update(){
        for(int i=0;i<cardDeck.size();i++){
            if(cardDeck.get(i).getHealthValue() < 0){
                cardDeck.remove(i);
            }
        }
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

    /**
     * returns the first Card in the deck
     * @param screen GameScreen that is calling the method
     *
     * Created By Niamh McCartney
     */
    public Card getCard01(GameScreen screen){ Card01.setGameScreen(screen); return Card01;}

    /**
     * returns the second Card in the deck
     * @param screen GameScreen that is calling the method
     *
     * Created By Niamh McCartney
     */
    public Card getCard02(GameScreen screen){ Card02.setGameScreen(screen); return Card02;}

    /**
     * returns the third Card in the deck
     * @param screen GameScreen that is calling the method
     *
     * Created By Niamh McCartney
     */
    public Card getCard03(GameScreen screen){ Card03.setGameScreen(screen); return Card03;}

    public Boolean getDeckChanged(){return deckChanged;}


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
    //Returns true if the deck has been shuffled
    public void setDeckShuffled(Boolean bool){
        deckShuffled = bool;
    }

    //Setter for the first Card in the deck
    public void setCard01(Card newCard){
        Card01 = newCard;
        cardDeck.set(0, newCard);}

    //Setter for the second Card in the deck
    public void setCard02(Card newCard){
        Card02 = newCard;
        cardDeck.set(1, newCard);}

    //Setter for the third Card in the deck
    public void setCard03(Card newCard){
        Card03 = newCard;
        cardDeck.set(2, newCard); }

    public void setDeckChanged(Boolean bool){deckChanged = bool;}

}