package uk.ac.qub.eeecs.gage;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.game.cardDemo.Enums.CardType;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for Card Class
 * Created by Niamh McCartney
 */
@RunWith(AndroidJUnit4.class)
public class CardTest {

    private TestGame aGame;

    private Card aCard;
    private String cardName;

    private int attackValue;
    private int healthValue;

    @Before
    public void cardTest_setUp() {
        aGame = new TestGame(420, 360);

        cardName = "cardName";
        attackValue = 5;
        healthValue = 9;

        aCard = new HeroCard(aGame, cardName,null, null, attackValue, healthValue, 0.3f);
    }

    @Test
    public void card_getCardType_Success(){ assertEquals(CardType.HERO_CARD, aCard.getCardType());}

    @Test
    public void card_getCardType_Failure(){
        assertNotEquals(CardType.VILLAIN_CARD, aCard.getCardType());
    }

    @Test
    public void card_getCardName_Success(){ assertEquals(cardName, aCard.getCardName());}

    @Test
    public void card_getCardName_Failure(){assertNotEquals("name", aCard.getCardName());}

    @Test
    public void card_getAttackValue_correctValueReturned_Success(){
        assertEquals(attackValue, aCard.getAttackValue());
    }

    @Test
    public void card_getHealthValue_correctValueReturned_Success(){
        assertEquals(healthValue, aCard.getHealthValue());
    }

    @Test
    public void card_setHealthValue_valueReturnedEqualToValueSet_Success(){
        int newHealthValue = healthValue+4;
        aCard.setHealthValue(newHealthValue);
        assertEquals(newHealthValue, aCard.getHealthValue());
    }

    @Test
    public void card_setStartPosX_getStartPosX_correctValueReturned_Success(){
        float startPosX = 0.2f;
        aCard.setStartPosX(startPosX);
        assertEquals(startPosX, aCard.getStartPosX(), 0.01);
    }

    @Test
    public void card_setStartPosY_getStartPosY_correctValueReturned_Success(){
        float startPosY = 0.2f;
        aCard.setStartPosY(startPosY);
        assertEquals(startPosY, aCard.getStartPosY(), 0.01);
    }

    @Test
    public void card_setSelected_getCardSelected_correctValuesReturned(){
        Boolean correctValue = false;
        aCard.setSelected(true);

        if(aCard.getCardSelected()){
            aCard.setSelected(false);
            if(!aCard.getCardSelected()){
                correctValue = true;
            }
        }
        assertTrue(correctValue);
    }

    @Test
    public void card_setCardDragged_getCardSelected_correctValuesReturned(){
        Boolean correctValue = false;
        aCard.setCardDragged(true);

        if(aCard.getCardDragged()){
            aCard.setCardDragged(false);
            if(!aCard.getCardDragged()){
                correctValue = true;
            }
        }
        assertTrue(correctValue);
    }

    @Test
    public void card_setCardInUse_getCardInUse_correctValuesReturned(){
        Boolean correctValue = false;
        aCard.setCardInUse(true);

        if(aCard.getCardInUse()){
            aCard.setCardInUse(false);
            if(!aCard.getCardInUse()){
                correctValue = true;
            }
        }
        assertTrue(correctValue);
    }

    @Test
    public void card_setCardFlipped_getCardFlipped_correctValuesReturned(){
        Boolean correctValue = false;
        aCard.setCardFlipped(true);

        if(aCard.getCardFlipped()){
            aCard.setCardFlipped(false);
            if(!aCard.getCardFlipped()){
                correctValue = true;
            }
        }
        assertTrue(correctValue);
    }

}