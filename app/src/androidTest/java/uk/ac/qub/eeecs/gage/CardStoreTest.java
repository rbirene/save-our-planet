package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import uk.ac.qub.eeecs.game.cardDemo.Card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CardStoreTest {

    private TestGameScreen aScreen;
    private TestGame aGame;
    private HashMap<String, Card> cardPool;

    @Before
    public void cardStore_setup(){
        aGame = new TestGame(420, 360);

        aGame.mAssetManager.loadAssets("txt/assets/CardDemoScreenAssets.JSON");

        aScreen = new TestGameScreen(aGame){};

    }

    @Test
    public void cardStore_getAllHeroCards_correctAmountReturned_Success(){
        //loads all hero cards into HashMap
        HashMap<String, Card> cards =  aGame.mCardStore.getAllHeroCards(aScreen);
        //four hero cards expected
        assertEquals(4, cards.size());
    }

    @Test
    public void cardStore_getAllHeroCards_IncorrectAmountReturned_Failure(){
        //loads all hero cards into HashMap
        HashMap<String, Card> cards =  aGame.mCardStore.getAllHeroCards(aScreen);
        //four hero cards expected
        assertNotEquals(2, cards.size());
    }

    @Test
    public void cardStore_getAllVillainCards_correctAmountReturned_Success(){
        //loads all villain cards into HashMap
        HashMap<String, Card> cards =  aGame.mCardStore.getAllVillainCards(aScreen);
        //zero villain cards expected
        assertEquals(0, cards.size());
    }

    @Test
    public void cardStore_getVillainCards_IncorrectAmountReturned_Failure(){
        //loads all villain cards into HashMap
        HashMap<String, Card> cards =  aGame.mCardStore.getAllVillainCards(aScreen);
        //zero villain cards expected
        assertNotEquals(2, cards.size());
    }


    public void Test5_Setup(){
        cardPool = new HashMap<>();
        //create three cards
        Card card01 = new Card(0.0f, 0.0f, null, "hero01", "heroCard", null, 1, 8);
        Card card02 = new Card(0.0f, 0.0f, null, "hero02", "heroCard", null, 1, 8);
        Card card03 = new Card(0.0f, 0.0f, null, "villain01", "heroCard", null, 1, 8);
        //adds three cards to HashMap
        cardPool.put(card01.getCardName(), card01);
        cardPool.put(card02.getCardName(), card02);
        cardPool.put(card03.getCardName(), card03);
    }

    @Test
    public void cardStore_getRandCard_CardReturned_Success(){
        Test5_Setup();
        Boolean cardObject = aGame.mCardStore.getRandCard(cardPool) instanceof Card;
        //Expected to return Card object
        assertTrue(cardObject);
    }


}

