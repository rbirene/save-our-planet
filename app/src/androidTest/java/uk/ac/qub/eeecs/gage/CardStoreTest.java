package uk.ac.qub.eeecs.gage;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import uk.ac.qub.eeecs.game.cardDemo.Enums.CardType;
import uk.ac.qub.eeecs.game.cardDemo.CardStore;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.VillainCard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static uk.ac.qub.eeecs.game.cardDemo.Enums.CardType.HERO_CARD;
import static uk.ac.qub.eeecs.game.cardDemo.Enums.CardType.VILLAIN_CARD;

/**
 * Tests for CardStore Class
 * Created by Niamh McCartney
 */
@RunWith(AndroidJUnit4.class)
public class CardStoreTest {

    private TestGame aGame;
    private CardStore aCardStore;
    private HashMap<String, Card> heroCardPool;
    private HashMap<String, Card> villainCardPool;


    @Before
    public void cardStore_setup(){
        aGame = new TestGame(420, 360);

        aGame.mAssetManager.loadAssets("txt/assets/CardAssets.JSON");

        aCardStore = aGame.mCardStore;
        heroCardPool = aCardStore.getAllCardsOfType(HERO_CARD);
        villainCardPool = aGame.mCardStore.getAllCardsOfType(CardType.VILLAIN_CARD);

    }

    // /////////////////////////////////////////////////////////////////////////
    // Tests for 'getAllCardsOfType()'
    // /////////////////////////////////////////////////////////////////////////

    @Test
    public void cardStore_getAllCardsOfType_HeroCard_correctAmountReturned_Success(){
        //sixteen hero cards expected
        assertEquals(16, heroCardPool.size());
    }

    @Test
    public void cardStore_getAllCardsOfType_HeroCard_IncorrectAmountReturned_Failure(){
        //sixteen hero cards expected
        assertNotEquals(2, heroCardPool.size());
    }

    @Test
    public void cardStore_getAllCardsOfType_HeroCard_correctTypeReturned_Failure(){
        Boolean correctType = true;
        for (Map.Entry<String, Card> entry : heroCardPool.entrySet()) {
            Card card = entry.getValue();
            if(!(card.getCardType() ==HERO_CARD)){
                correctType = false;
            }
        }
        assertTrue(correctType);
    }

    @Test
    public void cardStore_getAllCardsOfType_VillainCard_correctAmountReturned_Success(){
        assertEquals(16, villainCardPool.size());
    }

    @Test
    public void cardStore_getAllCardsOfType_VillainCard_IncorrectAmountReturned_Failure(){
        assertNotEquals(2, villainCardPool.size());
    }

    @Test
    public void cardStore_getAllCardsOfType_VillainCard_correctObjectTypeReturned_Success() {
        Boolean correctObject = true;
        for (Map.Entry<String, Card> entry : villainCardPool.entrySet()) {
            Card card = entry.getValue();
            if (!(card instanceof Card)) {
                correctObject = false;
            }
        }
        assertTrue(correctObject);
    }

    @Test
    public void cardStore_getAllCardsOfType_VillainCard_correctCardTypeReturned_Success() {
        Boolean correctType = true;
        for (Map.Entry<String, Card> entry : villainCardPool.entrySet()) {
            Card card = entry.getValue();
            if (!(card.getCardType() == VILLAIN_CARD)) {
                correctType = false;
            }
        }
        assertTrue(correctType);
    }


    // /////////////////////////////////////////////////////////////////////////
    // Tests for 'getRandCard()'
    // /////////////////////////////////////////////////////////////////////////

        @Test
    public void cardStore_getRandCard_CardReturned_Success(){
        Boolean cardObject = aGame.mCardStore.getRandCard(HERO_CARD) instanceof Card;
        //Expected to return Card object
        assertTrue(cardObject);
    }

    @Test
    public void cardStore_getRandCard_HeroCard_CorrectTypeReturned_Success(){
        Boolean heroCardType = false;
        if(aGame.mCardStore.getRandCard(HERO_CARD).getCardType() == HERO_CARD){
            heroCardType = true;
        }
        //Expected to return Card object
        assertTrue(heroCardType);
    }

    @Test
    public void cardStore_getRandCard_HeroCard_CardReturned_Success(){
        Boolean cardTypeReturned;
    }

    @Test
    public void cardStore_getRandCard_VillainCardTypeReturned_Success(){
        Boolean villainCardType = false;
        if(aGame.mCardStore.getRandCard(CardType.VILLAIN_CARD).getCardType() == VILLAIN_CARD){
            villainCardType = true;
        }
        //Expected to return Card object
        assertTrue(villainCardType);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Tests for 'getRandNum()'
    // /////////////////////////////////////////////////////////////////////////

    @Test
    public void cardStore_loadCardObjects_HeroCardAttackValues_NumberWithinLimitsReturned(){
        int cardAttackValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : heroCardPool.entrySet()) {
            Card card = entry.getValue();
            cardAttackValue = card.getAttackValue();
        }

        if(cardAttackValue>0 && cardAttackValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    @Test
    public void cardStore_loadCardObjects_HeroCard_HealthValues_NumberWithinLimitsReturned(){
        int cardHealthValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : heroCardPool.entrySet()) {
            Card card = entry.getValue();
            cardHealthValue = card.getHealthValue();
        }

        if(cardHealthValue>29 && cardHealthValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    @Test
    public void cardStore_loadCardObjects_VillainCard_AttackValues_NumberWithinLimitsReturned(){
        int cardAttackValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : villainCardPool.entrySet()) {
            Card card = entry.getValue();
            cardAttackValue = card.getAttackValue();
        }

        if(cardAttackValue>0 && cardAttackValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    @Test
    public void cardStore_loadCardObjects_VillainCard_HealthValues_NumberWithinLimitsReturned(){
        int cardHealthValue = 0;
        Boolean correctLimits = false;
        for (Map.Entry<String, Card> entry : villainCardPool.entrySet()) {
            Card card = entry.getValue();
            cardHealthValue = card.getHealthValue();
        }

        if(cardHealthValue>29 && cardHealthValue<100){
            correctLimits = true;
        }

        assertTrue(correctLimits);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Tests for 'renewHealthOfCards()'
    // /////////////////////////////////////////////////////////////////////////

    @Test
    public void cardStore_renewHealthOfCards_HeroCards_CardHealthValuesEqualOriginalValues(){
        //Returns true if Card health has been renewed
        Boolean cardHealthRenewed = false;

        //Create new Card
        HeroCard testCard = new HeroCard(aGame, "testCard", null,
                null, 20, 40, 0.0f);
        String testCardKey = "test";

        //Increase card health
        int cardOriginalHealth = testCard.getHealthValue();
        int newCardHealth = cardOriginalHealth + 20;
        testCard.setHealthValue(newCardHealth);

        //Add Card to CardStore and renew health of Hero Cards in the CardStore
        aCardStore.getAllCardsOfType(HERO_CARD).put(testCardKey, testCard);
        aCardStore.renewHealthOfCards(HERO_CARD);

        //retrieve the Card and its health value from the CardStore
        Card renewedCard = aCardStore.getAllCardsOfType(HERO_CARD).get(testCardKey);
        int renewedCardHealth = renewedCard.getHealthValue();

        //Compare health values
        if(renewedCard == testCard && renewedCardHealth == cardOriginalHealth){
            cardHealthRenewed = true;
        }

        assertTrue(cardHealthRenewed);
    }

    @Test
    public void cardStore_renewHealthOfCards_VillainCards_CardHealthValuesEqualOriginalValues(){
        //Returns true if Card health has been renewed
        Boolean cardHealthRenewed = false;

        //Create new Card
        VillainCard testCard = new VillainCard(aGame, "testCard", null,
                null, 20, 40, 0.0f);
        String testCardKey = "test";

        //Increase card health
        int cardOriginalHealth = testCard.getHealthValue();
        int newCardHealth = cardOriginalHealth + 20;
        testCard.setHealthValue(newCardHealth);

        //Add Card to CardStore and renew health of Hero Cards in the CardStore
        aCardStore.getAllCardsOfType(VILLAIN_CARD).put(testCardKey, testCard);
        aCardStore.renewHealthOfCards(VILLAIN_CARD);

        //retrieve the Card and its health value from the CardStore
        Card renewedCard = aCardStore.getAllCardsOfType(VILLAIN_CARD).get(testCardKey);
        int renewedCardHealth = renewedCard.getHealthValue();

        //Compare health values
        if(renewedCard == testCard && renewedCardHealth == cardOriginalHealth){
            cardHealthRenewed = true;
        }

        assertTrue(cardHealthRenewed);
    }
}