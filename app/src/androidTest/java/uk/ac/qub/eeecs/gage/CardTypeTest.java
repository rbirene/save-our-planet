package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.eeecs.game.cardDemo.Enums.CardType;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.VillainCard;

import static junit.framework.Assert.assertTrue;

/**
 * Tests for CardType Enum Class
 * Created by Niamh McCartney
 */
public class CardTypeTest {

    private TestGame aGame;
    private CardType heroCardType;
    private CardType villainCardType;

    @Before
    public void cardType_SetUp() {
        aGame = new TestGame(420, 360);
        aGame.mAssetManager.loadAssets("txt/assets/CardAssets.JSON");

        heroCardType = CardType.HERO_CARD;
        villainCardType = CardType.VILLAIN_CARD;
    }

    @Test
    public void cardType_getCardObjectType_heroCard_correctObjectCardTypeReturned(){
        Card cardObject = heroCardType.getCardObjectType(aGame, "testCard",
                null, null, 20, 40, 0.0f);

        assertTrue(cardObject instanceof HeroCard);
    }

    @Test
    public void cardType_getCardObjectType_heroCard_cardConstructedCorrectly(){
        String name = "testCard";
        int attackValue = 20;
        int healthValue = 40;

        Card cardObject = heroCardType.getCardObjectType(aGame, name,
                null, null, attackValue, healthValue, 0.0f);

        String cardName = cardObject.getCardName();
        int cardAttackValue = cardObject.getAttackValue();
        int cardHealthValue = cardObject.getHealthValue();

        Boolean cardConstructedCorrectly = cardName.equals(name) &&
                cardAttackValue == attackValue && cardHealthValue == healthValue;

        assertTrue(cardConstructedCorrectly);
    }

    @Test
    public void cardType_getCardObjectType_villainCard_correctObjectCardTypeReturned(){
        Card cardObject = villainCardType.getCardObjectType(aGame, "testCard",
                null, null, 20, 40, 0.0f);

        assertTrue(cardObject instanceof VillainCard);
    }

    @Test
    public void cardType_getCardObjectType_villainCard_cardConstructedCorrectly(){
        String name = "testCard";
        int attackValue = 4;
        int healthValue = 55;

        Card cardObject = villainCardType.getCardObjectType(aGame, name,
                null, null, attackValue, healthValue, 0.0f);

        String cardName = cardObject.getCardName();
        int cardAttackValue = cardObject.getAttackValue();
        int cardHealthValue = cardObject.getHealthValue();

        Boolean cardConstructedCorrectly = cardName.equals(name) &&
                cardAttackValue == attackValue && cardHealthValue == healthValue;

        assertTrue(cardConstructedCorrectly);
    }
}