package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.cardDemo.Card;
import uk.ac.qub.eeecs.game.cardDemo.CardStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CardStoreTest {

    private TestGameScreen aScreen;
    private TestGame aGame;
    private HashMap<String, Card> cardPool;
    private  CardStore aCardStore;

    private Context context;
    private DemoGame game;

    @Before
    public void cardStore_setup(){
        aGame = new TestGame(420, 360);

        aGame.mAssetManager.loadAssets("txt/assets/CardAssets.JSON");

        aScreen = new TestGameScreen(aGame){};
        aCardStore = new CardStore(aGame);

    }

    @Test
    public void cardStore_getAllHeroCards_correctAmountReturned_Success(){
        //loads all hero cards into HashMap
        HashMap<String, Card> cards =  aGame.mCardStore.getAllHeroCards(aScreen);
        //four hero cards expected
        assertEquals(16, cards.size());
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
        assertEquals(16, cards.size());
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
        Card card01 = new Card(0.0f, 0.0f, null, "hero01", "heroCard", null, new Vector2(0.03f, 0.03f) ,1, 8, 0);
        Card card02 = new Card(0.0f, 0.0f, null, "hero02", "heroCard", null,  new Vector2(0.03f, 0.03f) ,1, 8, 0);
        Card card03 = new Card(0.0f, 0.0f, null, "villain01", "heroCard", null,  new Vector2(0.03f, 0.03f) ,1, 8, 0);
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

    @Test
    public void cardStore_getRandCard_HeroCardTypeReturned_Success(){
        Test5_Setup();
        Boolean heroCardType = false;
        if(aGame.mCardStore.getRandCard(cardPool).getCardType() == "heroCard"){
            heroCardType = true;
        }
        //Expected to return Card object
        assertTrue(heroCardType);
    }

    @Test
    public void cardStore_testHeroCardAttackValues_NumberWithinLimitsReturned_Success(){
        HashMap<String, Card> cards = aGame.getCardStore().getAllHeroCards(aScreen);
        int cardAttackValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            cardAttackValue = card.getAttackValue();
        }

        if(cardAttackValue>29 && cardAttackValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    @Test
    public void cardStore_testHeroCardHealthValues_NumberWithinLimitsReturned_Success(){
        HashMap<String, Card> cards = aGame.getCardStore().getAllHeroCards(aScreen);
        int cardHealthValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            cardHealthValue = card.getHealthValue();
        }

        if(cardHealthValue>0 && cardHealthValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    @Test
    public void cardStore_testVillainCardAttackValues_NumberWithinLimitsReturned_Success(){
        HashMap<String, Card> cards = aGame.getCardStore().getAllHeroCards(aScreen);
        int cardAttackValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            cardAttackValue = card.getAttackValue();
        }

        if(cardAttackValue>0 && cardAttackValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    @Test
    public void cardStore_testVillainCardHealthValues_NumberWithinLimitsReturned_Success(){
        HashMap<String, Card> cards = aGame.getCardStore().getAllHeroCards(aScreen);
        int cardHealthValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            cardHealthValue = card.getHealthValue();
        }

        if(cardHealthValue>0 && cardHealthValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }


}

