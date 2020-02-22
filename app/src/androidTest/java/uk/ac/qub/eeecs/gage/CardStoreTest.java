
/**
 * Created by Niamh McCartney
 */

package uk.ac.qub.eeecs.gage;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CardStoreTest {

    private TestGameScreen aScreen;
    private TestGame aGame;
    private HashMap<String, Card> heroCardPool;
    private HashMap<String, Card> villainCardPool;


    @Before
    public void cardStore_setup(){
        aGame = new TestGame(420, 360);

        aGame.mAssetManager.loadAssets("txt/assets/CardAssets.JSON");

        aScreen = new TestGameScreen(aGame){};

        heroCardPool = aGame.mCardStore.getAllHeroCards(aScreen);
        villainCardPool = aGame.mCardStore.getAllVillainCards(aScreen);

    }

    @Test
    public void cardStore_getAllHeroCards_correctAmountReturned_Success(){
        //sixteen hero cards expected
        assertEquals(16, heroCardPool.size());
    }

    @Test
    public void cardStore_getAllHeroCards_IncorrectAmountReturned_Failure(){
        //sixteen hero cards expected
        assertNotEquals(2, heroCardPool.size());
    }

    @Test
    public void cardStore_getAllHeroCards_correctTypeReturned_Failure(){
        Boolean correctType = true;
        for (Map.Entry<String, Card> entry : heroCardPool.entrySet()) {
            Card card = entry.getValue();
            if(!card.getCardType().equals("heroCard")){
                correctType = false;
            }
        }
        assertTrue(correctType);
    }

    @Test
    public void cardStore_getAllVillainCards_correctAmountReturned_Success(){
        //sixteen villain cards expected
        assertEquals(16, villainCardPool.size());
    }

    @Test
    public void cardStore_getVillainCards_IncorrectAmountReturned_Failure(){
        //sixteen villain cards expected
        assertNotEquals(2, villainCardPool.size());
    }

    @Test
    public void cardStore_getAllVillainCards_correctObjectTypeReturned_Success() {
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
    public void cardStore_getAllVillainCards_correctCardTypeReturned_Success() {
        Boolean correctType = true;
        for (Map.Entry<String, Card> entry : villainCardPool.entrySet()) {
            Card card = entry.getValue();
            if (!card.getCardType().equals("villainCard")) {
                correctType = false;
            }
        }
        assertTrue(correctType);
    }


        @Test
    public void cardStore_getRandCard_CardReturned_Success(){
        Boolean cardObject = aGame.mCardStore.getRandCard(villainCardPool) instanceof Card;
        //Expected to return Card object
        assertTrue(cardObject);
    }

    @Test
    public void cardStore_getRandCard_HeroCardTypeReturned_Success(){
        Boolean heroCardType = false;
        if(aGame.mCardStore.getRandCard(heroCardPool).getCardType().equals("heroCard")){
            heroCardType = true;
        }
        //Expected to return Card object
        assertTrue(heroCardType);
    }

    @Test
    public void cardStore_getRandCard_VillainCardTypeReturned_Success(){
        Boolean heroCardType = false;
        if(aGame.mCardStore.getRandCard(villainCardPool).getCardType().equals("villainCard")){
            heroCardType = true;
        }
        //Expected to return Card object
        assertTrue(heroCardType);
    }

    @Test
    public void cardStore_testHeroCardAttackValues_NumberWithinLimitsReturned_Success(){ ;
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
    public void cardStore_testHeroCardHealthValues_NumberWithinLimitsReturned_Success(){
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
    public void cardStore_testVillainCardAttackValues_NumberWithinLimitsReturned_Success(){
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
    public void cardStore_testVillainCardHealthValues_NumberWithinLimitsReturned_Success(){
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


}

