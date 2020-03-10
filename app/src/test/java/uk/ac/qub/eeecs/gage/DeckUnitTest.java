
/**
 * Created by Niamh McCartney
 */

package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DeckUnitTest {

    private Deck aDeck;
    private ArrayList<Card> newDeck;

    @Mock
    GameScreen aScreen = Mockito.mock(GameScreen.class);

    @Mock
    Card Card01 = Mockito.mock(Card.class);
    Card Card02 = Mockito.mock(Card.class);
    Card Card03 = Mockito.mock(Card.class);
    Card Card04 = Mockito.mock(Card.class);
    Card Card05 = Mockito.mock(Card.class);
    Card Card06 = Mockito.mock(Card.class);

    @Before
    public void SetUp() {
        aDeck = new Deck(Card01, Card02, Card03);
        newDeck = new ArrayList<>();
        newDeck.add(Card04);
        newDeck.add(Card05);
        newDeck.add(Card06);
    }

    @Test
    public void Deck_constructor_ContainsCards_Success() {
        Boolean containsCards = aDeck.getDeck(aScreen).contains(Card01)
                && aDeck.getDeck(aScreen).contains(Card02)
                && aDeck.getDeck(aScreen).contains(Card03);
        assertTrue(containsCards);
    }

    @Test
    public void Deck_getSize_CorrectSize_Success() {
        assertEquals(3, aDeck.getSize());
    }

    @Test
    public void Deck_getSize_IncorrectSize_Failure() {
        assertNotEquals(5, aDeck.getSize());
    }

    @Test
    public void Deck_getCard01_CorrectCard_Success(){
        assertEquals(Card01, aDeck.getCard01(aScreen));
    }

    @Test
    public void Deck_getCard02_CorrectCard_Success(){
        assertEquals(Card02, aDeck.getCard02(aScreen));
    }

    @Test
    public void Deck_getCard03_CorrectCard_Success(){
        assertEquals(Card03, aDeck.getCard03(aScreen));
    }

    @Test
    public void Deck_setCard01_CorrectCard_Success(){
        aDeck.setCard01(Card06);
        assertEquals(Card06, aDeck.getCard01(aScreen));
    }

    @Test
    public void Deck_setCard02_CorrectCard_Success(){
        aDeck.setCard02(Card04);
        assertEquals(Card04, aDeck.getCard02(aScreen));
    }

    @Test
    public void Deck_setCard03_CorrectCard_Success(){
        aDeck.setCard03(Card05);
        assertEquals(Card05, aDeck.getCard03(aScreen));
    }

    @Test
    public void Deck_setDeck_ContainsNewDeck_Success(){
        aDeck.setDeck(newDeck);
        Boolean containsNewDeck = aDeck.getDeck(aScreen).contains(Card04)
                && aDeck.getDeck(aScreen).contains(Card05)
                && aDeck.getDeck(aScreen).contains(Card06);
        assertTrue(containsNewDeck);
    }

    @Test
    public void Deck_setDeck_CardsAssignedCorrectValues_Success(){
        aDeck.setDeck(newDeck);
        assertEquals(Card04, aDeck.getCard01(aScreen));
        assertEquals(Card05, aDeck.getCard02(aScreen));
        assertEquals(Card06, aDeck.getCard03(aScreen));
    }

    @Test
    public void Deck_setDeckShuffled_Success() {
        assertEquals(false, aDeck.getDeckShuffled());
        aDeck.setDeckShuffled(true);
        assertEquals(true, aDeck.getDeckShuffled());
    }

    @Test
    public void Deck_checkDeck_CardPositionReturned_Success(){
        assertEquals(0, aDeck.checkDeck(Card01));
        assertEquals(1, aDeck.checkDeck(Card02));
        assertEquals(2, aDeck.checkDeck(Card03));
    }

    @Test
    public void Deck_checkDeck_CardNotInDeck_NegativeIntegerReturned_Success(){
        assertEquals(-1, aDeck.checkDeck(Card04));
        assertEquals(-1, aDeck.checkDeck(Card05));
        assertEquals(-1, aDeck.checkDeck(Card06));
    }

//    @Test
//    public void Deck_ChangeDeckSize_DeckSizChanged_Success(){
//        int width = 250;
//        int height = 300;
//        aDeck.changeDeckSize(width, height);
//        Boolean deckSizeChanged = true;
//        for(int i = 0; i< aDeck.getSize(); i++){
//            Card card = aDeck.getDeck(aScreen).get(i);
//            if(card.getWidth() != width || card.getHeight() != height){
//                deckSizeChanged = false;
//            }
//        }
//
//        assertTrue(deckSizeChanged);
//    }

    @Test
    public void Deck_removeCard_DeckSizeDecreased_Success(){
        int originalDeckSize = aDeck.getSize() - 1;
        aDeck.removeCard(Card02);
        int deckSize = aDeck.getSize();

        assertEquals(deckSize,originalDeckSize);
    }

}
