package uk.ac.qub.eeecs.game.cardDemo;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;

/**
 * Defines Deck containing 3 Cards
 * Provides methods to access the
 * Deck's cards and change its
 * properties
 *
 * Created By Niamh McCartney
 */

public class Deck {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the cards in the Deck
    private Card card01;
    private Card card02;
    private Card card03;

    //Boolean to determine whether deck has een created
    private Boolean deckCreated;
    //Boolean to determine whether deck has been shuffled
    private Boolean deckShuffled;
    //Boolean that returns true if deck has been changed
    private Boolean deckChanged;

    //Defines ArrayList to hold the cards in the deck
    private ArrayList<Card> cardDeck;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the Deck object
     *
     * @param card1 first card in the deck
     * @param card2 second card in the deck
     * @param card3 third card in the deck
     *
     * Created by Niamh McCartney
     */
    public Deck(Card card1, Card card2, Card card3){
        card01 = card1;
        card02 = card2;
        card03 = card3;

        cardDeck = new ArrayList<>();

        cardDeck.add(0, card01);
        cardDeck.add(1, card02);
        cardDeck.add(2, card03);

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
     * Returns cardDeck
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
    public Card getCard01(GameScreen screen){ card01.setGameScreen(screen); return card01;}

    /**
     * returns the second Card in the deck
     * @param screen GameScreen that is calling the method
     *
     * Created By Niamh McCartney
     */
    public Card getCard02(GameScreen screen){ card02.setGameScreen(screen); return card02;}

    /**
     * returns the third Card in the deck
     * @param screen GameScreen that is calling the method
     *
     * Created By Niamh McCartney
     */
    public Card getCard03(GameScreen screen){ card03.setGameScreen(screen); return card03;}

    /**
     * Returns deckChanged Boolean
     * @return deckChanged Boolean returns true if deck has been shuffled
     *
     *  {Created By Niamh McCartney}
     */
    public Boolean getDeckShuffled(){
        return deckShuffled;
    }

    /**
     * Returns deckCreated Boolean
     * @return deckCreated Boolean returns true if deck has been created
     *
     * Created By Niamh McCartney
     */
    public Boolean getDeckCreated() { return deckCreated; }

    /**
     * Returns deckChanged Boolean
     * @return deckChanged Boolean returns true if deck has been changed
     *
     * Created By Niamh McCartney
     */
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
        card01 = cardDeck.get(0);
        card02 = cardDeck.get(1);
        card03 = cardDeck.get(2);
    }

    /**
     * Sets first Card in Deck
     * @param newCard card to replace first Card in deck
     *
     * Created By Niamh McCartney
     */
    public void setCard01(Card newCard){
        card01 = newCard;
        cardDeck.set(0, newCard);}

    /**
     * Sets second Card in Deck
     * @param newCard card to replace second Card in deck
     *
     * Created By Niamh McCartney
     */
    public void setCard02(Card newCard){
        card02 = newCard;
        cardDeck.set(1, newCard);}

    /**
     * Sets third Card in Deck
     * @param newCard card to replace third Card in deck
     *
     * Created By Niamh McCartney
     */
    public void setCard03(Card newCard){
        card03 = newCard;
        cardDeck.set(2, newCard);
    }

    /**
     * Sets deckShuffled Boolean
     * @param bool Boolean returns true if deck has been shuffled
     *
     * Created By Niamh McCartney
     */
    public void setDeckShuffled(Boolean bool){
        deckShuffled = bool;
    }

    /**
     * Sets deckCreated Boolean
     * @param bool Boolean returns true if deck has been changed
     *
     * Created By Niamh McCartney
     */
    public void setDeckChanged(Boolean bool){deckChanged = bool;}

    /**
     * Sets deckCreated Boolean
     * @param bool Boolean returns true if deck has been created
     *
     * Created By Niamh McCartney
     */
    public void setDeckCreated(Boolean bool) { deckCreated = bool;}
}